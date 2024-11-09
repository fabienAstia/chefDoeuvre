package com.personalities.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_sessions")
public class Session {

    public Session(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "start_session")
    private Instant startSession;

    @Column(name = "end_session")
    private Instant endSession;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(optional = true)
    @JoinColumn(name = "score_id")
    private Score score;

    public Long getId() {
        return id;
    }

    public Instant getStartSession() {
        return startSession;
    }

    public void setStartSession(Instant startSession) {
        this.startSession = startSession;
    }

    public Instant getEndSession() {
        return endSession;
    }

    public void setEndSession(Instant endSession) {
        this.endSession = endSession;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
