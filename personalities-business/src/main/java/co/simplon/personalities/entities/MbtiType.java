package co.simplon.personalities.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "t_mbti_types")
public class MbtiType extends AbstractEntity {

    public MbtiType() {
        //for Hibernate
    }

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "population_percentage")
    private double populationPercentage;

    @Column(name = "interesting_fact")
    private String interestingFact;

    @Column(name = "image")
    private String image;

    @ManyToMany
    @JoinTable(
            name = "t_matches",
            joinColumns = @JoinColumn(name = "mbti_type_id"),
            inverseJoinColumns = @JoinColumn(name = "profession_id"))
    private List<Profession> professions;

    @ManyToMany
    @JoinTable(
            name = "t_includes",
            joinColumns = @JoinColumn(name = "mbti_type_id"),
            inverseJoinColumns = @JoinColumn(name = "personality_trait_id"))
    private List<StrengthAndWeakness> strengthAndWeaknesses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof MbtiType mbtiType
                && Objects.equals(code, mbtiType.code)
                && Objects.equals(name, mbtiType.name)
                && Objects.equals(description, mbtiType.description)
                && Objects.equals(populationPercentage, mbtiType.populationPercentage)
                && Objects.equals(interestingFact, mbtiType.interestingFact)
                && Objects.equals(image, mbtiType.image)
                && Objects.equals(professions, mbtiType.professions)
                && Objects.equals(strengthAndWeaknesses, mbtiType.strengthAndWeaknesses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, description, populationPercentage, image, interestingFact, professions, strengthAndWeaknesses);
    }

    @Override
    public String toString() {
        return "MbtiType{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", populationPercentage=" + populationPercentage +
                ", interestingFact='" + interestingFact + '\'' +
                ", image='" + image + '\'' +
                ", professions=" + professions +
                ", strengthAndWeaknesses=" + strengthAndWeaknesses +
                "} " + super.toString();
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

    public List<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(List<Profession> professions) {
        this.professions = professions;
    }

    public List<StrengthAndWeakness> getStrengthsAndWeaknesses() {
        return strengthAndWeaknesses;
    }

    public void setStrengthAndWeaknesses(List<StrengthAndWeakness> strengthAndWeaknesses) {
        this.strengthAndWeaknesses = strengthAndWeaknesses;
    }
}
