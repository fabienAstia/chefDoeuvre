package com.personalities.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "t_evaluations")
public class Evaluation extends AbstractEntity {
    private String label;

    public Evaluation() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "label='" + label + '\'' +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof Evaluation evaluation
                && Objects.equals(label, evaluation.label);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(label);
    }
}
