package com.faks.elepo.database.repository;

import com.faks.elepo.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository  extends JpaRepository<Comment, Long> {
    List<Comment> findByProcessorId(Long id);

    @Query(value = "DELETE FROM comments WHERE processor_id = :processorId", nativeQuery = true)
    void deleteByProcessorId(@Param("processorId") Long processorId);

    @Query(value = "DELETE FROM comments WHERE user_id = :userId", nativeQuery = true)
    void deleteByUserId(@Param("userId") Long userId);
}
