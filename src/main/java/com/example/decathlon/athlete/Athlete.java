package com.example.decathlon.athlete;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

/*
 * Main Athlete class to store all information related to each athlete like name, performance in each sport, total-score and rank.
 * */
@XmlRootElement(name = "athlete")
@XmlAccessorType(XmlAccessType.FIELD)
public class Athlete {

    private String name;
    private Map<String, Double> eventPerformance;

    private int totalScore;
    private String rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Double> getEventPerformance() {
        return eventPerformance;
    }

    public void setEventPerformance(Map<String, Double> eventPerformance) {
        this.eventPerformance = eventPerformance;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }


}
