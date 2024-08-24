package com.personalities.dto;

public class QuestionView {

     String questionName;
     String dimensionName;

     public QuestionView() {
     }

     public QuestionView(String questionName, String dimensionName) {
          this.questionName = questionName;
          this.dimensionName = dimensionName;
     }

     public String getQuestionName() {
          return questionName;
     }

     public void setQuestionName(String questionName) {
          this.questionName = questionName;
     }

     public String getDimensionName() {
          return dimensionName;
     }

     public void setDimensionName(String dimensionName) {
          this.dimensionName = dimensionName;
     }

     @Override
     public String toString() {
          return "QuestionView{" +
                  "questionName='" + questionName + '\'' +
                  ", dimensionName='" + dimensionName + '\'' +
                  '}';
     }
}
