package com.faks.elepo.database.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "processors")
public class Processor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String manufacturerName;
    private String socket;
    private Date releaseDate;
    private Integer numberOfCores;
    private Integer numberOfThreads;
    private Double baseClockSpeed;
    private Double boostClockSpeed;
    private Double retailPrice;
    private String additionalInfo;

    public Processor(String name, String manufacturerName, String socket, Date releaseDate, Integer numberOfCores, Integer numberOfThreads, Double baseClockSpeed, Double boostClockSpeed, Double retailPrice, String additionalInfo) {
        this.name = name;
        this.manufacturerName = manufacturerName;
        this.socket = socket;
        this.releaseDate = releaseDate;
        this.numberOfCores = numberOfCores;
        this.numberOfThreads = numberOfThreads;
        this.baseClockSpeed = baseClockSpeed;
        this.boostClockSpeed = boostClockSpeed;
        this.retailPrice = retailPrice;
        this.additionalInfo = additionalInfo;
    }
}
