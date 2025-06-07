package co.simplon.personalities.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_psych_pref")
public class PsychPreference extends AbstractEntity {

    public PsychPreference() {
    }

    @Column(name = "code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof PsychPreference that
                && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return "PsychPreference{" +
                "prefCode='" + code + '\'' +
                "} " + super.toString();
    }

}
