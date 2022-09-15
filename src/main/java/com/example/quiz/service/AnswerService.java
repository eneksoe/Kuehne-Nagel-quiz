package com.example.quiz.service;

import com.example.quiz.dto.AnswerDto;
import com.example.quiz.model.Result;

import java.util.List;

public interface AnswerService {

    List<Result> checkAnswer(AnswerDto answer);
}
