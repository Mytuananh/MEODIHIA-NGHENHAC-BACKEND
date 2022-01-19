package com.example.meodihia_backend.repository;

import com.example.meodihia_backend.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILikeRepository extends JpaRepository<Likes, Long> {
}
