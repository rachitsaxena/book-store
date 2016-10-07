package com.rachit.bookstore.service.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rachit.bookstore.service.profile.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
