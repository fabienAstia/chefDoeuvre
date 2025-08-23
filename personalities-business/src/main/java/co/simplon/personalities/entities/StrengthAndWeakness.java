package co.simplon.personalities.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_strengths_and_weaknesses")
public class StrengthAndWeakness extends AbstractEntity {

    @Column(name = "trait")
    private String trait;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    public StrengthAndWeakness() {
        //for Hibernate
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
        return "StrengthAndWeakness{" +
                "trait='" + trait + '\'' +
                ", evaluation=" + evaluation +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof StrengthAndWeakness strengthAndWeakness
                && Objects.equals(trait, strengthAndWeakness.trait)
                && Objects.equals(evaluation, strengthAndWeakness.evaluation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trait, evaluation);
    }
}
