package com.example.quiz.service.impl;

import com.example.quiz.model.Question;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.service.QuestionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepository repository;

    @Override
    public List<Question> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Question> getAll(Long topicId) {
        return repository.findAllByTopicId(topicId);
    }

    @Override
    public void create(Question question) {
        repository.save(question);
    }

    @Override
    public void delete(Long questionId) {
        repository.deleteById(questionId);
    }

    @Override
    @Transactional
    public Question update(Question question) {
        Question questionFromDb =
                repository
                        .findById(question.getId())
                        .orElseThrow(() -> new IllegalArgumentException("Question not found"));
        questionFromDb.setText(question.getText());
        questionFromDb.setRank(question.getRank());
        questionFromDb.setTopic(question.getTopic());
        questionFromDb.setOptions(question.getOptions());
        return repository.save(questionFromDb);
    }
}
