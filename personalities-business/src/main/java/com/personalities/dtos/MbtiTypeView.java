package com.personalities.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MbtiTypeView {
    private String code;
    private String name;
    private String description;
    private double populationPercentage;
    private String interestingFact;
    private String image;
    private List<String> professions;
    private Map<String, String> personalityTraitsWithEval;
    private Map<String, Double> rateByPsych;

    public MbtiTypeView(String code, String name, String description, double populationPercentage, String interestingFact, String image, List<String> professions, Map<String, String> personalityTraitsWithEval, Map<String, Double> rateByPsych) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.populationPercentage = populationPercentage;
        this.interestingFact = interestingFact;
        this.image = image;
        this.professions = professions;
        this.personalityTraitsWithEval = personalityTraitsWithEval;
        this.rateByPsych = rateByPsych;
    }

    @Override
    public String toString() {
        return "MbtiTypeView{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", populationPercentage=" + populationPercentage +
                ", interestingFact='" + interestingFact + '\'' +
                ", image='" + image + '\'' +
                ", professions=" + professions +
                ", personalityTraitsWithEval=" + personalityTraitsWithEval +
                ", rateByPsych=" + rateByPsych +
                '}';
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPopulationPercentage() {
        return populationPercentage;
    }

    public void setPopulationPercentage(double populationPercentage) {
        this.populationPercentage = populationPercentage;
    }

    public String getInterestingFact() {
        return interestingFact;
    }

    public void setInterestingFact(String interestingFact) {
        this.interestingFact = interestingFact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getProfessions() {
        return professions;
    }

    public void setProfessions(List<String> professions) {
        this.professions = professions;
    }

    public Map<String, String> getPersonalityTraitsWithEval() {
        return personalityTraitsWithEval;
    }

    public void setPersonalityTraitsWithEval(Map<String, String> personalityTraitsWithEval) {
        this.personalityTraitsWithEval = personalityTraitsWithEval;
    }

    public Map<String, Double> getRateByPsych() {
        return rateByPsych;
    }

    public void setRateByPsych(Map<String, Double> rateByPsych) {
        this.rateByPsych = rateByPsych;
    }
}
