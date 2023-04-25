package com.example.dolearn.repository;

import com.example.dolearn.domain.User;
import com.example.dolearn.dto.UserIdMapping;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneById(Long id);

    List<UserIdMapping> findAllBy(Sort id);
}
