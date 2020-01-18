package com.bridgelabz.fundoonotes.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Notes;

@Repository
@Transactional
public interface LabelNoteReopsitory extends JpaRepository<Notes, Long> {

	@Modifying
	@Query(value = "insert into label_note(note_id,label_note_id) values(?,?)", nativeQuery = true)
	public void labelMapToNote(Long note_id, Long label_note_id);

}