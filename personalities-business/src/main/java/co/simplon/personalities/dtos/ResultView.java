package co.simplon.personalities.dtos;

import co.simplon.personalities.services.PsychPref;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ResultView {
    private String code;
    private String name;
    private String description;
    private double populationPercentage;
    private String interestingFact;
    private String image;
    private List<String> professions;
    private Map<String, String> strengthAndWeaknesses;
    private Map<PsychPref, Double> rateByPsych;

    public ResultView(String code, String name, String description, double populationPercentage, String interestingFact, String image, List<String> professions, Map<String, String> strengthAndWeaknesses, Map<PsychPref, Double> rateByPsych) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.populationPercentage = populationPercentage;
        this.interestingFact = interestingFact;
        this.image = image;
        this.professions = professions;
        this.strengthAndWeaknesses = strengthAndWeaknesses;
        this.rateByPsych = rateByPsych;
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

    public Map<String, String> getStrengthAndWeaknesses() {
        return strengthAndWeaknesses;
    }

    public void setStrengthAndWeaknesses(Map<String, String> strengthAndWeaknesses) {
        this.strengthAndWeaknesses = strengthAndWeaknesses;
    }

    public Map<PsychPref, Double> getRateByPsych() {
        return rateByPsych;
    }

    public void setRateByPsych(Map<PsychPref, Double> rateByPsych) {
        this.rateByPsych = rateByPsych;
    }

    @Override
    public String toString() {
        return "ResultView{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", populationPercentage=" + populationPercentage +
                ", interestingFact='" + interestingFact + '\'' +
                ", image='" + image + '\'' +
                ", professions=" + professions +
                ", personalityTraitsWithEval=" + strengthAndWeaknesses +
                ", rateByPsych=" + rateByPsych +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof ResultView resultView
                && Double.compare(populationPercentage, resultView.populationPercentage) == 0
                && Objects.equals(code, resultView.code)
                && Objects.equals(name, resultView.name)
                && Objects.equals(description, resultView.description)
                && Objects.equals(interestingFact, resultView.interestingFact)
                && Objects.equals(image, resultView.image)
                && Objects.equals(professions, resultView.professions)
                && Objects.equals(strengthAndWeaknesses, resultView.strengthAndWeaknesses)
                && Objects.equals(rateByPsych, resultView.rateByPsych);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, description, populationPercentage, interestingFact, image, professions, strengthAndWeaknesses, rateByPsych);
    }
}
