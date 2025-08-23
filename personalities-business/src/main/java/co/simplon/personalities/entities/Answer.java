package co.simplon.personalities.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_answers")
public class Answer extends AbstractEntity {

    public Answer() {
        //for Hibernate
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "rating")
    private int rating;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof Answer answer
                && rating == answer.rating
                && Objects.equals(user, answer.user)
                && Objects.equals(question, answer.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, question, rating);
    }

    @Override
    public String toString() {
        return "Answer{" +
                "user=" + user +
                ", question=" + question +
                ", rating=" + rating +
                "} " + super.toString();
    }
}
