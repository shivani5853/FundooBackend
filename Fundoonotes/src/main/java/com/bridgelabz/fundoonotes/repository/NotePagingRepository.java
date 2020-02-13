package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Notes;

@Repository
public interface NotePagingRepository extends PagingAndSortingRepository<Notes, Long> {

}
