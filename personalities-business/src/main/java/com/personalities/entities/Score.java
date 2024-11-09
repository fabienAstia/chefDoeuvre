package com.personalities.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "t_scores")
public class Score {

    public Score() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "score_e")
    private Double scoreE;

    @Column(name = "score_i")
    private Double scoreI;

    @Column(name = "score_n")
    private Double scoreN;

    @Column(name = "score_s")
    private Double scoreS;

    @Column(name = "score_t")
    private Double scoreT;

    @Column(name = "score_f")
    private Double scoreF;

    @Column(name = "score_p")
    private Double scoreP;

    @Column(name = "score_j")
    private Double scoreJ;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public Double getScoreE() {
        return scoreE;
    }

    public void setScoreE(Double scoreE) {
        this.scoreE = scoreE;
    }

    public Double getScoreI() {
        return scoreI;
    }

    public void setScoreI(Double scoreI) {
        this.scoreI = scoreI;
    }

    public Double getScoreN() {
        return scoreN;
    }

    public void setScoreN(Double scoreN) {
        this.scoreN = scoreN;
    }

    public Double getScoreS() {
        return scoreS;
    }

    public void setScoreS(Double scoreS) {
        this.scoreS = scoreS;
    }

    public Double getScoreT() {
        return scoreT;
    }

    public void setScoreT(Double scoreT) {
        this.scoreT = scoreT;
    }

    public Double getScoreF() {
        return scoreF;
    }

    public void setScoreF(Double scoreF) {
        this.scoreF = scoreF;
    }

    public Double getScoreP() {
        return scoreP;
    }

    public void setScoreP(Double scoreP) {
        this.scoreP = scoreP;
    }

    public Double getScoreJ() {
        return scoreJ;
    }

    public void setScoreJ(Double scoreJ) {
        this.scoreJ = scoreJ;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
