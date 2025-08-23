package co.simplon.personalities.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "t_users")
public class User extends AbstractEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "mbti_type_id")
    private MbtiType mbtiType;

    public User() {
        //for Hibernate
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MbtiType getMbtiType() {
        return mbtiType;
    }

    public void setMbtiType(MbtiType mbtiType) {
        this.mbtiType = mbtiType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof User user
                && Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
                && Objects.equals(role, user.role)
                && Objects.equals(mbtiType, user.mbtiType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, role, mbtiType);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", mbtiType=" + mbtiType +
                "} " + super.toString();
    }
}
