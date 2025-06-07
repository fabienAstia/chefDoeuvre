package co.simplon.personalities.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "t_constraints")
public class Constraint extends AbstractEntity {

    @Column(name = "minimum_value")
    private int min;

    @Column(name = "maximum_value")
    private int max;

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof Constraint that
                && min == that.min
                && max == that.max;
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max);
    }

    @Override
    public String toString() {
        return "Constraint{" +
                "min=" + min +
                ", max=" + max +
                "} " + super.toString();
    }
}
