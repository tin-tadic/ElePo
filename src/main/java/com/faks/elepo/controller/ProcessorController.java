package com.faks.elepo.controller;

import com.faks.elepo.model.Processor;
import com.faks.elepo.database.repository.ProcessorRepository;
import com.faks.elepo.dto.AddProcessorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/processor")
@PreAuthorize("hasRole('ADMIN')")
public class ProcessorController {
    @Autowired
    ProcessorRepository processorRepository;

    @GetMapping("/get/{id}")
    public ResponseEntity<Processor> getProcessorsByName(@PathVariable Long id) {
        Optional<Processor> optionalProcessor = processorRepository.findById(id);

        if (optionalProcessor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Processor processor = optionalProcessor.get();

        return new ResponseEntity<>(processor, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProcessor(@Validated @RequestBody AddProcessorDTO addProcessorDTO) {
        //TODO: add check for uniuque name

        processorRepository.save(new Processor(
            addProcessorDTO.getName(),
            addProcessorDTO.getManufacturerName(),
            addProcessorDTO.getSocket(),
            addProcessorDTO.getReleaseDate(),
            addProcessorDTO.getNumberOfCores(),
            addProcessorDTO.getNumberOfThreads(),
            addProcessorDTO.getBaseClockSpeed(),
            addProcessorDTO.getBoostClockSpeed(),
            addProcessorDTO.getRetailPrice(),
            addProcessorDTO.getAdditionalInfo()
        ));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<String> updateProcessor(@PathVariable Long id, @Validated @RequestBody AddProcessorDTO addProcessorDTO) {
        Optional<Processor> optionalProcessor = processorRepository.findById(id);

        if (optionalProcessor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Processor processor = optionalProcessor.get();

        processor.setName(addProcessorDTO.getName());
        processor.setManufacturerName(addProcessorDTO.getManufacturerName());
        processor.setSocket(addProcessorDTO.getSocket());
        processor.setReleaseDate(addProcessorDTO.getReleaseDate());
        processor.setNumberOfCores(addProcessorDTO.getNumberOfCores());
        processor.setNumberOfThreads(addProcessorDTO.getNumberOfThreads());
        processor.setBaseClockSpeed(addProcessorDTO.getBaseClockSpeed());
        processor.setBoostClockSpeed(addProcessorDTO.getBoostClockSpeed());
        processor.setRetailPrice(addProcessorDTO.getRetailPrice());
        processor.setAdditionalInfo(addProcessorDTO.getAdditionalInfo());

        processorRepository.save(processor);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProcessor(@PathVariable Long id) {
        Optional<Processor> optionalProcessor = processorRepository.findById(id);

        if (optionalProcessor.isPresent()) {
            processorRepository.delete(optionalProcessor.get());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
