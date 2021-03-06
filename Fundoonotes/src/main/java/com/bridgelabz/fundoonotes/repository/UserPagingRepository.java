package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.User;

@Repository
public interface UserPagingRepository extends PagingAndSortingRepository<User, Long> {
}
