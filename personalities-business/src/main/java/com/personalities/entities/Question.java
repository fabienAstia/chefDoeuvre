package com.personalities.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "t_questions")
public class Question {

    public Question() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_question")
    private Long id;

    @Column(name = "question_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "question_dimension_id")
    private Dimension dimension;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public String getDimensionName() {
        return dimension.getName();
    }
}
