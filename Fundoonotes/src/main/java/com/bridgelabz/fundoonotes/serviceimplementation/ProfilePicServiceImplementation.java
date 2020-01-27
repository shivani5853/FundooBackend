package com.bridgelabz.fundoonotes.serviceimplementation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.fundoonotes.model.ProfilePic;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.ProfilePicRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ProfilePicService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfilePicServiceImplementation implements ProfilePicService {

	@Autowired
	private ProfilePicRepository profilePicRepository;

	@Autowired
	private JwtGenerator jwtGenerator;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RedisTemplate<String, Object> redis;

	@Value("${aws.bucket.name}")
	private String bucketName;

	@Autowired
	private AmazonS3 amazonS3Client;

	@Override
	public ProfilePic storeObjectInS3(MultipartFile file, String fileName, String contentType, String token) {
		try {
			long userId = getRedisCecheId(token);
			User user = userRepository.findById(userId);
			if (user != null) {
				ProfilePic profile = new ProfilePic(fileName, user);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				objectMetadata.setContentLength(file.getSize());

				amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), objectMetadata);
				profilePicRepository.save(profile);
				return profile;
			}
		} catch (AmazonClientException | IOException exception) {
			throw new RuntimeException("Error while uploading file.");
		}
		return null;
	}

	private Long getRedisCecheId(String token) {
		String[] splitedToken = token.split("\\.");
		String redisTokenKey = splitedToken[1] + splitedToken[2];
		Long idForRedis = 0l;
		if (redis.opsForValue().get(redisTokenKey) == null) {
			try {
				idForRedis = jwtGenerator.parse(token);
			} catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e) {

				e.printStackTrace();
			}

			redis.opsForValue().set(redisTokenKey, idForRedis, 5 * 60, TimeUnit.SECONDS);
		}
		Long userId = (Long) redis.opsForValue().get(redisTokenKey);
		return userId;
	}

	@Override
	public ProfilePic updateProfilePic(MultipartFile file, String originalFile,String contentType,String token) {
		try {
			long userId=jwtGenerator.parse(token);
			User user=userRepository.findById(userId);
			ProfilePic profile=profilePicRepository.findByUserId(userId);
			System.out.println(profile.getProfilePicName());
			
			if(user!=null&& profile!=null) {
				
				deleteProfilePic(profile.getProfilePicName());
				profilePicRepository.delete(profile);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				objectMetadata.setContentLength(file.getSize());
				amazonS3Client.putObject(bucketName,originalFile,file.getInputStream(),objectMetadata);
				profilePicRepository.save(profile);
				return profile;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteProfilePic(String key) {
		try {
			amazonS3Client.deleteObject(bucketName, key);
		} catch (AmazonServiceException serviceException) {
			log.error(serviceException.getErrorMessage());
		} catch (AmazonClientException exception) {
			log.error("Something went wrong while deleting File.");
		}
	}

	@Override
	public S3Object getProfilePic(MultipartFile file, String token) {
		try {
			long userId = getRedisCecheId(token);
			User user = userRepository.findById(userId);
			if (user != null) {
				ProfilePic profile = profilePicRepository.findByUserId(userId);
				if (profile != null) {
					return fetchObject(profile.getProfilePicName());
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public S3Object fetchObject(String awsFileName) {
		S3Object s3Object;
		try {
			s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName, awsFileName));
		} catch (AmazonServiceException serviceException) {


			throw new RuntimeException("Error while streaming File.");
		} catch (AmazonClientException exception) {

			throw new RuntimeException("Error while streaming File.");
		}
		return s3Object;
	}
}
