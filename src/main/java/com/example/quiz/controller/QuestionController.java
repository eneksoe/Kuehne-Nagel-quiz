package com.example.quiz.controller;

import com.example.quiz.model.Question;
import com.example.quiz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/all")
    public List<Question> getAll(@RequestParam(value = "topic", required = false) Long topicId){
        return questionService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<Question> create(@RequestBody Question question){
        questionService.create(question);
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Question> update(@RequestBody Question question){
        final Question updatedQuestion =questionService.update(question);
        return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long questionId){
        questionService.delete(questionId);
    }
}
