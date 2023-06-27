package com.faks.elepo.listener;

import com.faks.elepo.config.socket.SocketService;
import com.faks.elepo.config.socket.UpdateTypes;
import com.faks.elepo.model.Comment;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Component
public class CommentListener {

    private static ObjectFactory<SocketService> socketService;

    public CommentListener(ObjectFactory<SocketService> socketService) {
        CommentListener.socketService = socketService;
    }

    @PostPersist
    private void postPersist(Comment comment) {
        socketService.getObject().emit(comment.getProcessor().getId(), UpdateTypes.CREATE, "Comment with id " + comment.getId() + " created!");
    }

    @PostUpdate
    private void postUpdate(Comment comment) {
        socketService.getObject().emit(comment.getProcessor().getId(), UpdateTypes.UPDATE, "Comment with id " + comment.getId() + " updated!");
    }

    @PostRemove
    private void postRemove(Comment comment) {
        socketService.getObject().emit(comment.getProcessor().getId(), UpdateTypes.DELETE, "Comment with id " + comment.getId() + " deleted!");
    }
}
