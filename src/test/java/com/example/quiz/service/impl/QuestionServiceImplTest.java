package com.example.quiz.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.quiz.model.Question;
import com.example.quiz.model.Topic;
import com.example.quiz.repository.QuestionRepository;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {QuestionServiceImpl.class})
@ExtendWith(SpringExtension.class)
class QuestionServiceImplTest {
    @MockBean
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionServiceImpl questionServiceImpl;

    @Test
    void testGetAll() {
        ArrayList<Question> questionList = new ArrayList<>();
        when(questionRepository.findAll()).thenReturn(questionList);
        List<Question> actualAll = questionServiceImpl.getAll();
        assertSame(questionList, actualAll);
        assertTrue(actualAll.isEmpty());
        verify(questionRepository).findAll();
    }

    @Test
    void testGetAll2() {
        when(questionRepository.findAll()).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> questionServiceImpl.getAll());
        verify(questionRepository).findAll();
    }

    @Test
    void testGetByTopic() {
        ArrayList<Question> questionList = new ArrayList<>();
        when(questionRepository.findAllByTopicId(any())).thenReturn(questionList);
        List<Question> actualByTopic = questionServiceImpl.getAll(123L);
        assertSame(questionList, actualByTopic);
        assertTrue(actualByTopic.isEmpty());
        verify(questionRepository).findAllByTopicId(any());
    }

    @Test
    void testGetByTopic2() {
        when(questionRepository.findAllByTopicId(any())).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> questionServiceImpl.getAll(123L));
        verify(questionRepository).findAllByTopicId(any());
    }

    @Test
    void testCreate() {
        Topic topic = new Topic();
        topic.setId(123L);
        topic.setName("Name");

        Question question = new Question();
        question.setId(123L);
        question.setOptions(new HashMap<>());
        question.setRank("Rank");
        question.setText("Text");
        question.setTopic(topic);
        when(questionRepository.save(any())).thenReturn(question);

        Topic topic1 = new Topic();
        topic1.setId(123L);
        topic1.setName("Name");

        Question question1 = new Question();
        question1.setId(123L);
        question1.setOptions(new HashMap<>());
        question1.setRank("Rank");
        question1.setText("Text");
        question1.setTopic(topic1);
        questionServiceImpl.create(question1);
        verify(questionRepository).save(any());
        assertEquals(123L, question1.getId().longValue());
        assertEquals(topic, question1.getTopic());
        assertEquals("Text", question1.getText());
        assertTrue(question1.getOptions().isEmpty());
        assertEquals("Rank", question1.getRank());
        assertTrue(questionServiceImpl.getAll().isEmpty());
    }

    @Test
    void testCreate2() {
        when(questionRepository.save(any())).thenThrow(new IllegalArgumentException("foo"));

        Topic topic = new Topic();
        topic.setId(123L);
        topic.setName("Name");

        Question question = new Question();
        question.setId(123L);
        question.setOptions(new HashMap<>());
        question.setRank("Rank");
        question.setText("Text");
        question.setTopic(topic);
        assertThrows(IllegalArgumentException.class, () -> questionServiceImpl.create(question));
        verify(questionRepository).save(any());
    }

    @Test
    void testDelete() {
        doNothing().when(questionRepository).deleteById(any());
        questionServiceImpl.delete(123L);
        verify(questionRepository).deleteById(any());
        assertTrue(questionServiceImpl.getAll().isEmpty());
    }

    @Test
    void testDelete2() {
        doThrow(new IllegalArgumentException("foo")).when(questionRepository).deleteById(any());
        assertThrows(IllegalArgumentException.class, () -> questionServiceImpl.delete(123L));
        verify(questionRepository).deleteById(any());
    }

    @Test
    void testUpdate() {
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

        Topic topic1 = new Topic();
        topic1.setId(123L);
        topic1.setName("Name");

        Question question1 = new Question();
        question1.setId(123L);
        question1.setOptions(new HashMap<>());
        question1.setRank("Rank");
        question1.setText("Text");
        question1.setTopic(topic1);
        when(questionRepository.save(any())).thenReturn(question1);
        when(questionRepository.findById(any())).thenReturn(ofResult);

        Topic topic2 = new Topic();
        topic2.setId(123L);
        topic2.setName("Name");

        Question question2 = new Question();
        question2.setId(123L);
        question2.setOptions(new HashMap<>());
        question2.setRank("Rank");
        question2.setText("Text");
        question2.setTopic(topic2);
        assertSame(question1, questionServiceImpl.update(question2));
        verify(questionRepository).save(any());
        verify(questionRepository).findById(any());
    }

    @Test
    void testUpdate2() {
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
        when(questionRepository.save(any())).thenThrow(new IllegalArgumentException("foo"));
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
        assertThrows(IllegalArgumentException.class, () -> questionServiceImpl.update(question1));
        verify(questionRepository).save(any());
        verify(questionRepository).findById(any());
    }

    @Test
    void testUpdate3() {
        Topic topic = new Topic();
        topic.setId(123L);
        topic.setName("Name");

        Question question = new Question();
        question.setId(123L);
        question.setOptions(new HashMap<>());
        question.setRank("Rank");
        question.setText("Text");
        question.setTopic(topic);
        when(questionRepository.save(any())).thenReturn(question);
        when(questionRepository.findById(any())).thenReturn(Optional.empty());

        Topic topic1 = new Topic();
        topic1.setId(123L);
        topic1.setName("Name");

        Question question1 = new Question();
        question1.setId(123L);
        question1.setOptions(new HashMap<>());
        question1.setRank("Rank");
        question1.setText("Text");
        question1.setTopic(topic1);
        assertThrows(IllegalArgumentException.class, () -> questionServiceImpl.update(question1));
        verify(questionRepository).findById(any());
    }
}

