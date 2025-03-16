package com.personalities.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_personality_traits")
public class PersonalityTrait extends AbstractEntity {

    @Column(name = "trait")
    private String trait;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    public PersonalityTrait() {
    }

    public String getTrait() {
        return trait;
    }

    public void setTrait(String trait) {
        this.trait = trait;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {
        return "PersonalityTrait{" +
                "trait='" + trait + '\'' +
                ", evaluation=" + evaluation +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof PersonalityTrait personalityTrait
                && Objects.equals(trait, personalityTrait.trait)
                && Objects.equals(evaluation, personalityTrait.evaluation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trait, evaluation);
    }
}
