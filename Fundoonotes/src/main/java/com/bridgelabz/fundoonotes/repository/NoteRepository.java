package com.bridgelabz.fundoonotes.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
	@Query(value = "insert into notes(note_id,title,description,created_at,update_time ,  user_id,reminder,reminder_time,is_trash) values(?,?,?,?,?,?,?,?,?)", nativeQuery = true)
	public void insertData(Long note_id, String title, String description, LocalDateTime created_at, Date update_time,
			Long userId, String reminder, LocalDateTime reminderTime,Boolean isTrash);

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
	public void setColour(String colour, Long user_id, Long note_id);

	@Modifying
	@Transactional
	@Query(value = "update notes set reminder_time=?,reminder=?,update_time=?  where user_id=? and note_id=?", nativeQuery = true)
	public void remindMe(LocalDateTime reminder_time, String reminder, Date update_time, Long userId, Long note_id);

	
	@Modifying
	@Transactional
	@Query(value ="update notes set is_trash=? where user_id=? and note_id=?",nativeQuery=true)
	public void setDelete(Boolean is_delete,Long user_id,Long note_id);
	
	@Modifying
	@Transactional
	@Query(value = "Delete from notes where note_id=? and user_id=?",nativeQuery = true)
	public void deleteNote(Long note_id,Long user_id);

	@Query(value = "select * from notes where user_id=?", nativeQuery = true)
	public List<Notes> searchAllNoteByUserId(long userId);
	
	@Query(value = "select * from notes where user_id=? and note_id=?", nativeQuery = true)
	public List<Notes> searchAllNotesByNoteId(long userId,long noteId);
	
	@Query(value = "select * from notes where note_id=?", nativeQuery = true)
	public Notes searchNotesByNoteId(String note_id);

	@Modifying
	@Transactional
	@Query(value = "update notes set is_verified =true where note_id=?", nativeQuery = true)
	void updateIsVarified(Long id);
}





