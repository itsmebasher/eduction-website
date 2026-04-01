package com.education.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Quiz model.
 */
public class QuizTest {
    private Quiz quiz;

    @Before
    public void setUp() {
        quiz = new Quiz("1", "Java Fundamentals", "Learn Java basics", "COURSE-1", "INSTR-1");
    }

    @Test
    public void testQuizCreation() {
        assertEquals("1", quiz.getId());
        assertEquals("Java Fundamentals", quiz.getTitle());
        assertEquals(70, quiz.getPassingScore());
        assertEquals(0, quiz.getTotalQuestions());
    }

    @Test
    public void testQuizUpdate() {
        quiz.setTitle("Advanced Java");
        quiz.setPassingScore(80);
        quiz.setTotalQuestions(10);
        
        assertEquals("Advanced Java", quiz.getTitle());
        assertEquals(80, quiz.getPassingScore());
        assertEquals(10, quiz.getTotalQuestions());
    }

    @Test
    public void testQuizToString() {
        String str = quiz.toString();
        assertTrue(str.contains("Quiz"));
        assertTrue(str.contains("Java Fundamentals"));
    }
}
