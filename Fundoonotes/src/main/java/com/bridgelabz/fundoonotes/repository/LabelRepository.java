package com.bridgelabz.fundoonotes.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.model.Labels;

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

}
