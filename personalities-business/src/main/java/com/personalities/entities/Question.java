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

    @Column(name = "question_dimension")
    private String dimension;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }
}
