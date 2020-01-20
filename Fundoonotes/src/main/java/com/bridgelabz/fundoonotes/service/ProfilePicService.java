package com.bridgelabz.fundoonotes.service;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.ProfilePic;

public interface ProfilePicService {

	ProfilePic storeObjectInS3(MultipartFile file, String originalFilename, String contentType, String token);

	ProfilePic updateProfilePic(MultipartFile file, String token);

}
