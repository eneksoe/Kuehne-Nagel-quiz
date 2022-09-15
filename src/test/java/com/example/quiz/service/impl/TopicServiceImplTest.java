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

import com.example.quiz.model.Topic;
import com.example.quiz.repository.TopicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TopicServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TopicServiceImplTest {
    @MockBean
    private TopicRepository topicRepository;

    @Autowired
    private TopicServiceImpl topicServiceImpl;

    @Test
    void testGetAll() {
        ArrayList<Topic> topicList = new ArrayList<>();
        when(topicRepository.findAll()).thenReturn(topicList);
        List<Topic> actualAll = topicServiceImpl.getAll();
        assertSame(topicList, actualAll);
        assertTrue(actualAll.isEmpty());
        verify(topicRepository).findAll();
    }

    @Test
    void testGetAll2() {
        when(topicRepository.findAll()).thenThrow(new IllegalArgumentException("foo"));
        assertThrows(IllegalArgumentException.class, () -> topicServiceImpl.getAll());
        verify(topicRepository).findAll();
    }

    @Test
    void testCreate() {
        Topic topic = new Topic();
        topic.setId(123L);
        topic.setName("Name");
        when(topicRepository.save(any())).thenReturn(topic);

        Topic topic1 = new Topic();
        topic1.setId(123L);
        topic1.setName("Name");
        topicServiceImpl.create(topic1);
        verify(topicRepository).save(any());
        assertEquals(123L, topic1.getId().longValue());
        assertEquals("Name", topic1.getName());
        assertTrue(topicServiceImpl.getAll().isEmpty());
    }

    @Test
    void testCreate2() {
        when(topicRepository.save(any())).thenThrow(new IllegalArgumentException("foo"));

        Topic topic = new Topic();
        topic.setId(123L);
        topic.setName("Name");
        assertThrows(IllegalArgumentException.class, () -> topicServiceImpl.create(topic));
        verify(topicRepository).save(any());
    }

    @Test
    void testDelete() {
        doNothing().when(topicRepository).deleteById(any());
        topicServiceImpl.delete(123L);
        verify(topicRepository).deleteById(any());
        assertTrue(topicServiceImpl.getAll().isEmpty());
    }

    @Test
    void testDelete2() {
        doThrow(new IllegalArgumentException("foo")).when(topicRepository).deleteById(any());
        assertThrows(IllegalArgumentException.class, () -> topicServiceImpl.delete(123L));
        verify(topicRepository).deleteById(any());
    }

    @Test
    void testUpdate() {
        Topic topic = new Topic();
        topic.setId(123L);
        topic.setName("Name");
        Optional<Topic> ofResult = Optional.of(topic);

        Topic topic1 = new Topic();
        topic1.setId(123L);
        topic1.setName("Name");
        when(topicRepository.save(any())).thenReturn(topic1);
        when(topicRepository.findById(any())).thenReturn(ofResult);

        Topic topic2 = new Topic();
        topic2.setId(123L);
        topic2.setName("Name");
        assertSame(topic1, topicServiceImpl.update(topic2));
        verify(topicRepository).save(any());
        verify(topicRepository).findById(any());
    }

    @Test
    void testUpdate2() {
        Topic topic = new Topic();
        topic.setId(123L);
        topic.setName("Name");
        Optional<Topic> ofResult = Optional.of(topic);
        when(topicRepository.save(any())).thenThrow(new IllegalArgumentException("foo"));
        when(topicRepository.findById(any())).thenReturn(ofResult);

        Topic topic1 = new Topic();
        topic1.setId(123L);
        topic1.setName("Name");
        assertThrows(IllegalArgumentException.class, () -> topicServiceImpl.update(topic1));
        verify(topicRepository).save(any());
        verify(topicRepository).findById(any());
    }

    @Test
    void testUpdate3() {
        Topic topic = new Topic();
        topic.setId(123L);
        topic.setName("Name");
        when(topicRepository.save(any())).thenReturn(topic);
        when(topicRepository.findById(any())).thenReturn(Optional.empty());

        Topic topic1 = new Topic();
        topic1.setId(123L);
        topic1.setName("Name");
        assertThrows(IllegalArgumentException.class, () -> topicServiceImpl.update(topic1));
        verify(topicRepository).findById(any());
    }
}

