package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.User;

@Repository
@Transactional
public interface LabelRepository extends JpaRepository<Labels, Long> {

	@Query(value = "select * from label where label_name=?", nativeQuery = true)
	Labels findByName(String label_name);

	@Modifying
	@Query(value = "insert into label(label_Name,user_id) values(?,?)", nativeQuery = true)
	void insertData(String label_Name, long user_id);

	@Modifying
	@Query(value = "Delete from label where label_name=? and user_id=?", nativeQuery = true)
	void deleteLabel(String label_Name, Long user_id);

	@Query(value = "select * from label where label_id=? and user_id=?", nativeQuery = true)
	Labels findById(Long label_Id, Long user_id);

	@Modifying
	@Query(value = "update label set label_name=? where user_id=? and label_id=?", nativeQuery = true)
	void updateLabel(String labelName, long userId, long label_id);

	@Query(value = "select * from label where label_id=?", nativeQuery = true)
	Labels findByNoteIdAndLabelId(long labelId);

	@Query(value = "select * from label where user_id=?", nativeQuery = true)
	List<Labels> findAllLabels(long userId);

}