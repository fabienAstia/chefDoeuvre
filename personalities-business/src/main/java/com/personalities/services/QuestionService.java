package com.personalities.services;

import com.personalities.dto.QuestionCreate;
import com.personalities.dto.QuestionUpdate;
import com.personalities.dto.QuestionView;
import com.personalities.entities.Question;
import com.personalities.entities.Dimension;
import com.personalities.mappers.QuestionMapper;
import com.personalities.repositories.DimensionRepository;
import com.personalities.repositories.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final DimensionRepository dimensionRepository;

    public QuestionService(QuestionRepository questionRepository, DimensionRepository dimensionRepository) {
        this.questionRepository = questionRepository;
        this.dimensionRepository = dimensionRepository;
    }

    public void createQuestion(QuestionCreate questionCreate)  {
        Question question = new Question();
        Dimension dimension = dimensionRepository.findByNameIgnoreCase(questionCreate.dimension());
        question.setName(questionCreate.name());
        question.setDimension(dimension);
        questionRepository.save(question);
    }

    public List<QuestionView> getQuestions (){
        List<Question>questionList = questionRepository.findAllProjectedBy();
        return QuestionMapper.mapToDto(questionList);
    }

    public void deleteQuestion (Long id){
        questionRepository.deleteById(id);
    }

    public void updateQuestion (Long id, QuestionUpdate inputs) {
        Question question = questionRepository.findById(id).get();
        Dimension dimension = dimensionRepository.findByNameIgnoreCase(inputs.dimension());
        question.setName(inputs.name());
        question.setDimension(dimension);
        questionRepository.save(question);
    }

}
