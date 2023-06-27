package com.faks.elepo.config.socket;

import com.faks.elepo.database.repository.ProcessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
public class ProcessorTracker {
    private final ProcessorRepository processorRepository;

    @Autowired
    public ProcessorTracker(ProcessorRepository processorRepository) {
        this.processorRepository = processorRepository;
    }

    @Bean(name = "processorIdTracker")
    public HashSet<Long> processorIdTracker() {
        HashSet<Long> processorIdTracker = new HashSet<>();

        processorRepository.findAll().forEach(processor -> {
            processorIdTracker.add(processor.getId());
        });

        return processorIdTracker;
    }
}
