package com.cts.springboot.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.springboot.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

}
