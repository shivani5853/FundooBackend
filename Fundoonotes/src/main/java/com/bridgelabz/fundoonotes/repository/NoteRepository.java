package com.bridgelabz.fundoonotes.repository;

import java.time.LocalDateTime;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Notes;

@Repository
public interface NoteRepository extends JpaRepository<Notes, Long> {

	@Modifying
	@Transactional
	@Query(value = "insert into notes(note_id,title,description,created_at,update_time ,  user_id,reminder,reminder_time) values(?,?,?,?,?,?,?,?)", nativeQuery = true)
	public void insertData(Long note_id, String title, String description, LocalDateTime created_at, Date update_time , Long userId,String reminder,Date reminderTime);

	@Query(value = "select * from notes where note_id=?", nativeQuery = true)
	public Notes findById(long note_id);

	@Modifying
	@Transactional
	@Query(value = "update notes set is_pinned=? where user_id=? and note_id=?", nativeQuery = true)
	public void setPinned(Boolean is_pinned, Long user_id, Long note_id);

	@Modifying
	@Transactional
	@Query(value = "update notes set is_archive=? where user_id=? and note_id=?", nativeQuery = true)
	public void setArchive(Boolean is_archive, Long user_id, Long note_id);

	@Modifying
	@Transactional
	@Query(value = "update notes set colour=? where user_id=? and note_id=?", nativeQuery = true)
	public void setColour(String colour,Long user_id, Long note_id);

	@Modifying
	@Transactional
	@Query(value ="update notes set reminder=? and reminder_time=? where user_id=? and note_id=?",nativeQuery=true)
	public void remindMe(String reminder, Date reminderTime,Long userId, Long note_id);

}
