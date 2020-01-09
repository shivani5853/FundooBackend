package com.bridgelabz.fundoonotes.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundoonotes.model.Notes;

public interface NoteRepository extends JpaRepository<Notes, Long> {

	@Modifying
	@Transactional
	@Query(value ="insert into Fundoonotes.notes (title,description,createdAt,updateTime) values(?,?,?,?,?,?)",nativeQuery=true) 
	public void insertData(Long id,String title, String description, Date createdAt, Date updateTime);
	
	
}
