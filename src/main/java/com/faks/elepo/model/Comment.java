package com.faks.elepo.model;

import com.faks.elepo.listener.CommentListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
@EntityListeners(CommentListener.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "processor_id", nullable = false)
    private Processor processor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Instant createdAt;

    public Comment(String text, Processor processor, User user) {
        this.text = text;
        this.processor = processor;
        this.user = user;
        this.createdAt = Instant.now();
    }
}
