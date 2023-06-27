package com.faks.elepo.config.socket;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.faks.elepo.model.Comment;
import com.faks.elepo.model.Processor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class SocketService {
    private final ObjectMapper objectMapper;
    private final SocketIOServer socketIOServer;

    @Autowired
    public SocketService(SocketIOServer socketIOServer, HashSet<Long> processorIdTracker) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        this.socketIOServer = socketIOServer;

        processorIdTracker.forEach(processorId -> socketIOServer.addNamespace("/" + processorId));
    }

    public void registerNamespace(Long processorId) {
        socketIOServer.addNamespace("/" + processorId);
    }

    public void removeNamespace(Long processorId) {
        socketIOServer.removeNamespace("/" + processorId);
    }

    public void emit(Long processorId, UpdateTypes updateType, Object message) {
        String jsonEncoded = null;

        try {
            jsonEncoded = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        socketIOServer.getNamespace("/" + processorId).getBroadcastOperations().sendEvent(updateType.toString(), jsonEncoded);
    }
}
