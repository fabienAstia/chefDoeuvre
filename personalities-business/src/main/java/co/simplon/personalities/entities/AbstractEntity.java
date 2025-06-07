package co.simplon.personalities.entities;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    protected AbstractEntity() {
    }

    public Long getId() {
        return id;
    }
}
