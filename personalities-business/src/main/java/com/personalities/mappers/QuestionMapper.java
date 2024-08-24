package com.personalities.mappers;

import com.personalities.dto.QuestionView;
import com.personalities.entities.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionMapper {
    public static QuestionView mapToDto(Question question) {

        QuestionView questionView = new QuestionView();
        questionView.setQuestionName(question.getName());
        questionView.setDimensionName(question.getDimensionName());
        System.out.println(question.getDimensionName());
        return  questionView;
    }
    public static List<QuestionView> mapToDto(List<Question>questionList){
        List<QuestionView> listView = new ArrayList<>();
        for(Question question : questionList ){
            listView.add(mapToDto(question));
        }
        System.out.println("systemView : " + listView);
        return listView;
    }
}
