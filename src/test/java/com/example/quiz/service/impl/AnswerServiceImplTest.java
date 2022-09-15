package com.example.quiz.service.impl;

import com.example.quiz.dto.AnswerDto;
import com.example.quiz.model.Answer;
import com.example.quiz.model.Question;
import com.example.quiz.model.Result;
import com.example.quiz.model.Topic;
import com.example.quiz.repository.QuestionRepository;
import com.example.quiz.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AnswerServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AnswerServiceImplTest {
    @Autowired
    private AnswerServiceImpl answerServiceImpl;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private QuestionService questionService;

    @Test
    void testCheckAnswer1() {
        AnswerDto answerDto = mock(AnswerDto.class);
        when(answerDto.getAnswers()).thenReturn(new ArrayList<>());
        assertTrue(answerServiceImpl.checkAnswer(answerDto).isEmpty());
        verify(answerDto).getAnswers();
    }

    @Test
    void testCheckAnswer2() {
        when(questionRepository.findById(any())).thenReturn(Optional.empty());

        Topic topic = new Topic();
        topic.setId(123L);
        topic.setName("Name");

        Question question = new Question();
        question.setId(123L);
        question.setOptions(new HashMap<>());
        question.setRank("Rank");
        question.setText("Text");
        question.setTopic(topic);

        Answer answer = new Answer();
        answer.setQuestion(question);

        ArrayList<Answer> answerList = new ArrayList<>();
        answerList.add(answer);
        AnswerDto answerDto = mock(AnswerDto.class);
        when(answerDto.getAnswers()).thenReturn(answerList);
        assertTrue(answerServiceImpl.checkAnswer(answerDto).isEmpty());
        verify(questionRepository).findById(any());
        verify(answerDto).getAnswers();
    }

    @Test
    void testCheckAnswer3() {
        Topic topic = new Topic();
        topic.setId(123L);
        topic.setName("Name");

        Question question = new Question();
        question.setId(123L);
        question.setOptions(new HashMap<>());
        question.setRank("Rank");
        question.setText("Text");
        question.setTopic(topic);
        Optional<Question> ofResult = Optional.of(question);
        when(questionRepository.findById(any())).thenReturn(ofResult);

        Topic topic1 = new Topic();
        topic1.setId(123L);
        topic1.setName("Name");

        Question question1 = new Question();
        question1.setId(123L);
        question1.setOptions(new HashMap<>());
        question1.setRank("Rank");
        question1.setText("Text");
        question1.setTopic(topic1);

        Answer answer = new Answer();
        answer.setSelectedOption(-1L);
        answer.setQuestion(question1);

        ArrayList<Answer> answerList = new ArrayList<>();
        answerList.add(answer);
        AnswerDto answerDto = mock(AnswerDto.class);
        when(answerDto.getAnswers()).thenReturn(answerList);
        List<Result> actualCheckAnswerResult = answerServiceImpl.checkAnswer(answerDto);
        assertEquals(1, actualCheckAnswerResult.size());
        Result getResult = actualCheckAnswerResult.get(0);
        assertNull(getResult.getCorrectAnswer());
        assertFalse(getResult.isCorrect());
        assertNull(getResult.getSelectedAnswer());
        assertEquals("Text", getResult.getQuestionText());
        verify(questionRepository).findById(any());
        verify(answerDto).getAnswers();
    }
}

