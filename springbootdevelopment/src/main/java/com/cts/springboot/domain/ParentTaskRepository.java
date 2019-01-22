package com.cts.springboot.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.springboot.entity.ParentTask;

public interface ParentTaskRepository extends JpaRepository<ParentTask, Long>{

}
