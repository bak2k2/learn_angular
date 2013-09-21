package com.gap.metrics.service;

import com.gap.metrics.dto.WordCount;
import com.gap.metrics.dto.WordList;
import com.gap.metrics.model.Iteration;
import com.gap.metrics.model.Project;
import com.gap.metrics.model.ProjectIterationDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RetroService {
    @Autowired
    private ProjectService projectService;

    private int WORD_SIZE = 3;

    public WordList getRetroWordList() {
        WordList wordList = new WordList();
        String retroComments = getRetroComments(projectService.listProjects());
        Map<String, Double> wordCounts = getWordCountsMap(retroComments);
        wordList.setWordCounts(getWordCounts(wordCounts));
        return wordList;
    }

    private String getRetroComments(List<Project> projects) {
        StringBuilder retroComments = new StringBuilder();
        for(Project project : projects){
            Iteration iteration = project.getLastIteration();
            if (iteration != null){
                ProjectIterationDetail detail = projectService.getProjectIterationDetails(project.getId(), iteration.getId());
                if (detail != null && detail.getRetroComments() != null){
                    retroComments.append(" " + detail.getRetroComments());
                }
            }
        }
        return retroComments.toString();
    }

    private Map<String, Double> getWordCountsMap(String str) {
        String[] words = str.toLowerCase().split(" ");
        Map<String, Double> wordCounts = new HashMap<String, Double>();
        for (String word : words) {
            Double count = wordCounts.get(word);
            if (count == null)
                count = 0.0;
            if (word.length() > WORD_SIZE)
                wordCounts.put(word, count + 1);
        }
        return wordCounts;
    }

    private List<WordCount> getWordCounts(Map<String, Double> wordCountMap) {
        List<WordCount> wordCounts = new ArrayList<WordCount>();
        Iterator iterator = wordCountMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry pairs = (Map.Entry)iterator.next();
            WordCount wc = new WordCount(pairs.getKey().toString(), (Double)pairs.getValue());
            wordCounts.add(wc);
        }
        return wordCounts;
    }
}
