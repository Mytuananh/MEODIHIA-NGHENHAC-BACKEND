package com.example.meodihia_backend.repository;

import com.example.meodihia_backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICommentRepository extends JpaRepository<Comment,Long> {

}
