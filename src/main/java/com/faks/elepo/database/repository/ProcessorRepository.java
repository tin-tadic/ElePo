package com.faks.elepo.database.repository;

import com.faks.elepo.model.Processor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessorRepository extends JpaRepository<Processor, Long> {

}
