package com.faks.elepo.database.repository;

import com.faks.elepo.database.model.Processor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessorRepository extends JpaRepository<Processor, Long> {
    List<Processor> findByName(String name);
}
