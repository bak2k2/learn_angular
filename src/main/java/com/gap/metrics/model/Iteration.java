package com.gap.metrics.model;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class Iteration {
    @Id
    private String id;

    private String iterationNumber;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

}
