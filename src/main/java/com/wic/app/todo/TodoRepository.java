package com.wic.app.todo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wic.app.todo.model.Todo;

import jakarta.transaction.Transactional;

@Transactional
public interface TodoRepository extends JpaRepository<Todo, Long> {
	List<Todo> findByTitleContainingIgnoreCase(String keyword);
	Page<Todo> findByTitleContainingIgnoreCase(String keyword,Pageable pageable);
}
