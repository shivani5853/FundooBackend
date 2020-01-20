package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.ProfilePic;

@Repository
public interface ProfilePicRepository extends JpaRepository<ProfilePic,Object> {

}
