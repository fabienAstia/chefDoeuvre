package com.personalities.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_questions")
public class Question extends AbstractEntity {

    public Question() {
    }

    @Column(name = "label")
    private String label;

    @Column(name = "order")
    private int order;

    @ManyToOne
    @JoinColumn(name = "constraint_id")
    private Constraint constraint;

    @ManyToOne
    @JoinColumn(name = "psych_pref_id")
    private PsychPreference psychPreference;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Constraint getConstraint() {
        return constraint;
    }

    public void setConstraint(Constraint constraint) {
        this.constraint = constraint;
    }

    public PsychPreference getPsychPreference() {
        return psychPreference;
    }

    public void setPsychPreference(PsychPreference psychPreference) {
        this.psychPreference = psychPreference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof Question question
                && Objects.equals(label, question.label)
                && Objects.equals(order, question.order)
                && Objects.equals(constraint, question.constraint)
                && Objects.equals(psychPreference, question.psychPreference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, order, constraint, psychPreference);
    }

    @Override
    public String toString() {
        return "Question{" +
                "label='" + label + '\'' +
                "questionOrder='" + order + '\'' +
                ", constraint=" + constraint +
                ", psychPreference=" + psychPreference +
                "} " + super.toString();
    }
}
