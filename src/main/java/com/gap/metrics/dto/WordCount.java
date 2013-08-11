package com.gap.metrics.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document
public class WordCount {
    private String text;
    private double weight;
}
