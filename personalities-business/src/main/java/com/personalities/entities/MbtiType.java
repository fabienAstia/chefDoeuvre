package com.personalities.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "t_mbti_types")
public class MbtiType extends AbstractEntity {

    public MbtiType() {
    }

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof MbtiType mbtiType
                && Objects.equals(code, mbtiType.code)
                && Objects.equals(description, mbtiType.description)
                && Objects.equals(image, mbtiType.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description, image);
    }

    @Override
    public String toString() {
        return "MbtiType{" +
                "typeCode='" + code + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                "} " + super.toString();
    }
}
