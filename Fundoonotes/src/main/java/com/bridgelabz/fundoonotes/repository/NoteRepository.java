package com.bridgelabz.fundoonotes.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.fundoonotes.model.Notes;

public interface NoteRepository extends JpaRepository<Notes, Long> {

	@Query("insert into Fundoonotes.notes (title,")
	public void insertData(Long id,String title, String description, Date createdAt, Date updateTime);

}
