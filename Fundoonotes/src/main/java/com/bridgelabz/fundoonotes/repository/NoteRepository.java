package com.bridgelabz.fundoonotes.repository;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundoonotes.model.Notes;

@Repository
public interface NoteRepository extends JpaRepository<Notes, Long> {

	@Modifying
	@Transactional
	@Query(value = "insert into notes(note_id,title,description,created_at,update_time) values(?,?,?,?,?)", nativeQuery = true)
	void insertData(Long note_id, String title, String description, LocalDateTime created_at, Date update_time);

	@Query(value = "select * from notes where note_id=?", nativeQuery = true)
	Notes findById(long note_id);

}
