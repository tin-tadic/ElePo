package com.faks.elepo.controller;

import com.faks.elepo.config.security.ContextReader;
import com.faks.elepo.database.repository.CommentRepository;
import com.faks.elepo.database.repository.ProcessorRepository;
import com.faks.elepo.model.Comment;
import com.faks.elepo.model.Processor;
import com.faks.elepo.model.dto.AddCommentDTO;
import com.faks.elepo.model.dto.UpdateCommentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {
   private CommentRepository commentRepository;
   private ProcessorRepository processorRepository;
   private ContextReader contextReader;

    public CommentController(CommentRepository commentRepository, ProcessorRepository processorRepository, ContextReader contextReader) {
        this.commentRepository = commentRepository;
        this.processorRepository = processorRepository;
        this.contextReader = contextReader;
    }

    @GetMapping("/get/{processorId}")
    public ResponseEntity<List<Comment>> getCommentsForProcessor(@PathVariable Long processorId) {
        List<Comment> comments = new ArrayList<>();
        Optional<Processor> optionalProcessor = processorRepository.findById(processorId);

        if (optionalProcessor.isEmpty()) {
            comments = commentRepository.findByProcessorId(processorId);
        }

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/add/{processorId}")
    public ResponseEntity<String> addCommentForProcessor(@PathVariable Long processorId, @Valid @RequestBody AddCommentDTO addCommentDTO) {
        Optional<Processor> optionalProcessor = processorRepository.findById(processorId);

        if (optionalProcessor.isEmpty()) {
            return new ResponseEntity<>("Processor not found", HttpStatus.BAD_REQUEST);
        }
        Processor processor = optionalProcessor.get();

        commentRepository.save(new Comment(
                addCommentDTO.getText(),
                processor,
                contextReader.getLoggedInUser()
        ));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/{commentId}")
    public ResponseEntity<String> updateCommentById(@PathVariable Long commentId, @Valid @RequestBody UpdateCommentDTO updateCommentDTO) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);

        if (optionalComment.isEmpty()) {
            return new ResponseEntity<>("Comment does not exist", HttpStatus.BAD_REQUEST);
        }
        Comment comment = optionalComment.get();

        if (!contextReader.getLoggedInUser().getId().equals(comment.getUser().getId()) && !contextReader.getLoggedInUser().getRole().equals("ROLE_ADMIN")) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        comment.setText(updateCommentDTO.getText());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isPresent()) {
            commentRepository.delete(optionalComment.get());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
