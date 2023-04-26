package com.faks.elepo.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddProcessorDTO {
    @NotNull
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
}
