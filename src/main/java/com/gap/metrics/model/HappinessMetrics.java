package com.gap.metrics.model;

import java.util.ArrayList;
import java.util.List;

public class HappinessMetrics {
    private List<Double> commitmentsGreaterThanZero = new ArrayList<Double>();
    private List<Double> engagementsGreaterThanZero = new ArrayList<Double>();
    private List<Double> valuesGreaterThanZero = new ArrayList<Double>();
    private List<Double> trustsGreaterThanZero = new ArrayList<Double>();


    public void add(HappinessMetric happinessMetric) {
        addCommitment(happinessMetric.getCommitment());
        addEngagement(happinessMetric.getEngagement());
        addValue(happinessMetric.getPerceivedValue());
        addTrust(happinessMetric.getRespectTrust());
    }

    private void addTrust(double respectTrust) {
        if (respectTrust > 0)
            trustsGreaterThanZero.add(respectTrust);
    }

    private void addValue(double perceivedValue) {
        if (perceivedValue > 0)
            valuesGreaterThanZero.add(perceivedValue);
    }

    private void addEngagement(double engagement) {
        if (engagement > 0)
            engagementsGreaterThanZero.add(engagement);
    }

    private void addCommitment(double commitment) {
        if (commitment > 0)
            commitmentsGreaterThanZero.add(commitment);
    }

    public double getAverageCommitment() {
        return (new ListUtil().sum(commitmentsGreaterThanZero)) / commitmentsGreaterThanZero.size();
    }

    public double getAverageEngagement() {
        return (new ListUtil().sum(engagementsGreaterThanZero)) / engagementsGreaterThanZero.size();
    }

    public double getAveragePerceivedValue() {
        return (new ListUtil().sum(valuesGreaterThanZero)) / valuesGreaterThanZero.size();
    }

    public double getAverageRespect() {
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
