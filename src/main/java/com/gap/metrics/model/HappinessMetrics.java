package com.gap.metrics.model;

import java.util.ArrayList;
import java.util.List;

public class HappinessMetrics {
    private List<HappinessMetric> happinessMetrics = new ArrayList<HappinessMetric>();

    public void add(HappinessMetric happinessMetric) {
        happinessMetrics.add(happinessMetric);
    }

    public double getAverageCommitment() {
        List<Double> commitmentsGreaterThanZero = new ArrayList<Double>();
        for(HappinessMetric metric: happinessMetrics){
            if(metric.getCommitment() > 0)
                commitmentsGreaterThanZero.add(metric.getCommitment());
        }
        return (new ListUtil().sum(commitmentsGreaterThanZero)) / commitmentsGreaterThanZero.size();
    }

    public double getAverageEngagement() {
        List<Double> engagementsGreaterThanZero = new ArrayList<Double>();
        for(HappinessMetric metric: happinessMetrics){
            if(metric.getEngagement() > 0)
                engagementsGreaterThanZero.add(metric.getEngagement());
        }
        return (new ListUtil().sum(engagementsGreaterThanZero)) / engagementsGreaterThanZero.size();
    }

    public double getAveragePerceivedValue() {
        List<Double> valuesGreaterThanZero = new ArrayList<Double>();
        for(HappinessMetric metric: happinessMetrics){
            if(metric.getPerceivedValue() > 0)
                valuesGreaterThanZero.add(metric.getPerceivedValue());
        }
        return (new ListUtil().sum(valuesGreaterThanZero)) / valuesGreaterThanZero.size();
    }

    public double getAverageRespect() {
        List<Double> trustsGreaterThanZero = new ArrayList<Double>();
        for(HappinessMetric metric: happinessMetrics){
            if(metric.getRespectTrust() > 0)
                trustsGreaterThanZero.add(metric.getRespectTrust());
        }
        return (new ListUtil().sum(trustsGreaterThanZero)) / trustsGreaterThanZero.size();
    }

    public HappinessMetric getAverageHappinessMetric() {
        HappinessMetric metric = new HappinessMetric();
        metric.setCommitment(Math.round(getAverageCommitment()));
        metric.setEngagement(Math.round(getAverageEngagement()));
        metric.setPerceivedValue(Math.round(getAveragePerceivedValue()));
        metric.setRespectTrust(Math.round(getAverageRespect()));
        return metric;
    }

    class ListUtil{
        public  double sum(List<Double> doubles){
            if (doubles == null || doubles.size() < 1)
                return 0;
            double sum = 0;
            for(Double d: doubles)
                sum += d;
            return sum;
        }
    }
}
