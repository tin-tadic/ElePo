package com.faks.elepo.listener;

import com.faks.elepo.config.socket.SocketService;
import com.faks.elepo.config.socket.UpdateTypes;
import com.faks.elepo.model.Processor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.util.HashSet;

@Component
public class ProcessorListener {

    private static ObjectFactory<SocketService> socketService;
    private static ObjectFactory<HashSet<Long>> processorIdTracker;

    public ProcessorListener(ObjectFactory<SocketService> socketService, ObjectFactory<HashSet<Long>> processorIdTracker) {
        ProcessorListener.socketService = socketService;
        ProcessorListener.processorIdTracker = processorIdTracker;
    }

    @PostPersist
    private void postPersist(Processor processor) {
        socketService.getObject().registerNamespace(processor.getId());
        processorIdTracker.getObject().add(processor.getId());
    }

    @PostUpdate
    private void postUpdate(Processor processor) {
        socketService.getObject().emit(processor.getId(), UpdateTypes.UPDATE, "Processor updated!");
    }

    @PostRemove
    private void postRemove(Processor processor) {
        socketService.getObject().emit(processor.getId(), UpdateTypes.DELETE, "Processor deleted!");
        socketService.getObject().removeNamespace(processor.getId());
        processorIdTracker.getObject().remove(processor.getId());
    }
}
