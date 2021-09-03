package com.example.clip.repository;

import com.example.clip.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    Page<User> findDistinctByPaymentsIsNotNull(Pageable pageable);

    List<User> findByIdIn(Set<Long> userIdList);

    Set<User> findByDisbursementsIsNotNull();

    User findByIdEquals(Long id);
}
