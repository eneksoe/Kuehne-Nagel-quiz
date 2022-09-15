package com.example.quiz.service;

import com.example.quiz.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAll();

    List<Question> getAll(Long topicId);
    void create (Question question);

    void delete (Long questionId);

    Question update (Question question);
}
