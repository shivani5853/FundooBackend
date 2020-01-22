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
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bridgelabz.fundoonotes.model.ProfilePic;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.ProfilePicRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ProfilePicService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
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
	public ProfilePic updateProfilePic(MultipartFile file, String token) {
		
		return null;
	}

	@Override
	public ProfilePic deleteProfilePic(MultipartFile file, String token) {
		
		return null;
	}

	@Override
	public List<ProfilePic> getProfilePic(MultipartFile file, String token) {

		return null;
	}
	
	
	

}
