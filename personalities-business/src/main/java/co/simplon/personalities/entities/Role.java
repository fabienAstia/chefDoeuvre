package co.simplon.personalities.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "t_roles")
public class Role extends AbstractEntity {

    public Role() {
        //for Hibernate
    }

    @Column(name = "role")
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof Role role1
                && Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(role);
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                "} " + super.toString();
    }
}
