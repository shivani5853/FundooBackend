package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.ProfilePic;
import com.bridgelabz.fundoonotes.response.Responses;
import com.bridgelabz.fundoonotes.service.ProfilePicService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/profilePic")
public class ProfilePicController {

	@Autowired
	private ProfilePicService profilePicService;

	@PostMapping("/uploadprofilepic")
	@ApiOperation(value = "Api to upload profile pic of User for Fundoonotes", response = Responses.class)
	public ResponseEntity<Responses> addProfilePic(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {

		ProfilePic profile = profilePicService.storeObjectInS3(file, file.getOriginalFilename(), file.getContentType(),
				token);
		return profile.getUserLabel() != null
				? ResponseEntity.status(HttpStatus.OK).body(new Responses("profile added succussefully", 200, profile))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses("something went Wrong ", 400));
	}

	@PutMapping("/updateProfilePic")
	@ApiOperation(value = "Api to update profile pic of User", response = Responses.class)
	public ResponseEntity<Responses> updateProfilePic(@ModelAttribute MultipartFile file,
			@RequestHeader("token") String token) {
		ProfilePic profile = profilePicService.updateProfilePic(file,file.getOriginalFilename(),file.getContentType(), token);
		return profile != null
				? ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new Responses("Profile Pic update Sucessfully!!!", 200))
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses("Something went wrong!!!", 400));
	}

//	@DeleteMapping("/deleteProfilePic")
//	@ApiOperation(value = "Api to delete Profile Pic", response = Responses.class)
//	public ResponseEntity<Responses> deleteProfilePic(@ModelAttribute MultipartFile file,
//			@RequestHeader("token") String token) {
//		ProfilePic result = profilePicService.deleteProfilePic(file, token);
//		return result != null
//				? ResponseEntity.status(HttpStatus.ACCEPTED)
//						.body(new Responses("Profile pic deleted Sucessfully!!!", 200))
//				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses("Something went wrong!!!", 400));
//	}
	
	@GetMapping("/getProfilePic")
	@ApiOperation(value = "Api to get Profile Pic",response = Responses.class)
	public ResponseEntity<Responses> getProfilePic(@ModelAttribute MultipartFile file,@RequestHeader("token")String token){
		S3Object result=profilePicService.getProfilePic(file,token);
		return result!=null ? ResponseEntity.status(HttpStatus.ACCEPTED).body(new Responses("All ProfilePic are",200)):
			ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Responses("Something went wrong!!!",400));
	}
}
