package com.example.demo.local.repository;

import com.example.demo.local.modal.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    List<User> findAllByUserId(String id);
    Page<User> findAll(Pageable pageable);
    User findByUserIdAndAndPassword(String id,String password);
    @Modifying
    @Query(value ="INSERT INTO users  VALUES ('2','2','3')", nativeQuery = true)
    void insertUser();


}
