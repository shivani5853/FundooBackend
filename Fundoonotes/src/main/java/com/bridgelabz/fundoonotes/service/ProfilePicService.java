package com.bridgelabz.fundoonotes.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.ProfilePic;

public interface ProfilePicService {

	ProfilePic storeObjectInS3(MultipartFile file, String originalFilename, String contentType, String token);

	ProfilePic updateProfilePic(MultipartFile file, String token);

	ProfilePic deleteProfilePic(MultipartFile file, String token);

	List<ProfilePic> getProfilePic(MultipartFile file, String token);

}
