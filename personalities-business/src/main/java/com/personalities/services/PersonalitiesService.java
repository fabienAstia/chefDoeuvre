package com.personalities.services;

import com.personalities.dto.QuestionCreate;
import com.personalities.dto.QuestionView;
import com.personalities.entities.Question;
import com.personalities.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalitiesService {

    private QuestionRepository questionRepository;

    public PersonalitiesService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void create(QuestionCreate questionCreate){
        Question question = new Question();
        question.setName(questionCreate.name());
        questionRepository.save(question);
    }

    public List<QuestionView> getQuestions (){
        return questionRepository.findAllProjectedBy();
    }

    public void deleteQuestion (Long id){
        questionRepository.deleteById(id);
    }

}
