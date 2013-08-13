package com.gap.metrics.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Document
public class WordList {
    private List<WordCount> wordCounts = new ArrayList<WordCount>();
}
