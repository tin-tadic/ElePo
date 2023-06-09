package com.faks.elepo.controller;

import com.faks.elepo.database.repository.CommentRepository;
import com.faks.elepo.model.Processor;
import com.faks.elepo.database.repository.ProcessorRepository;
import com.faks.elepo.model.dto.AddProcessorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/processor")
public class ProcessorController {
    private ProcessorRepository processorRepository;
    private CommentRepository commentRepository;

    public ProcessorController(ProcessorRepository processorRepository, CommentRepository commentRepository) {
        this.processorRepository = processorRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Processor>> getAllProcessors() {
        return new ResponseEntity<>(processorRepository.findAll(), HttpStatus.OK);
    }

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
        Optional<Processor> optionalProcessor = processorRepository.findByName(addProcessorDTO.getName());

        if (optionalProcessor.isPresent()) {
            return new ResponseEntity<>("Processor with that name already exists!", HttpStatus.BAD_REQUEST);
        }

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

        if (!processor.getName().equals(addProcessorDTO.getName())) {
            Optional<Processor> existingProcessor = processorRepository.findByName(addProcessorDTO.getName());
            if (existingProcessor.isPresent()) {
                return new ResponseEntity<>("Processor with that name already exists!", HttpStatus.BAD_REQUEST);
            }
        }

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
            commentRepository.deleteByProcessorId(id);
            processorRepository.delete(optionalProcessor.get());
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
