package com.personalities.dto;

public class QuestionView {

     Long id;
     String name;
     String dimension;

     public QuestionView() {
     }

     public QuestionView(Long id, String name, String dimension) {
          this.id = id;
          this.name = name;
          this.dimension = dimension;
     }

     public Long getId() {return id;}

     public void setId(Long id) {
          this.id = id;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getDimension() {
          return dimension;
     }

     public void setDimension(String dimension) {
          this.dimension = dimension;
     }

     @Override
     public String toString() {
          return "QuestionView{" +
                  "questionId='" + id + '\'' +
                  "questionName='" + name + '\'' +
                  ", dimensionName='" + dimension + '\'' +
                  '}';
     }
}
