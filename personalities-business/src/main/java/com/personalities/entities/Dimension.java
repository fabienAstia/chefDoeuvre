package com.personalities.entities;

import jakarta.persistence.*;

@Entity
@Table(name="t_dimensions")
public class Dimension {

    public Dimension() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dimension")
    private Long id;

    @Column(name = "dimension_name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

}
