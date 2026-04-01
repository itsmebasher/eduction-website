package com.education.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Question model.
 */
public class QuestionTest {
    private Question question;

    @Before
    public void setUp() {
        question = new Question("Q1", "QUIZ-1", "What is Java?", "MULTIPLE_CHOICE");
        question.addOption("A programming language");
        question.addOption("A coffee brand");
        question.addOption("An island");
        question.setCorrectAnswer("A programming language");
    }

    @Test
    public void testQuestionCreation() {
        assertEquals("Q1", question.getId());
        assertEquals("QUIZ-1", question.getQuizId());
        assertEquals("What is Java?", question.getQuestionText());
        assertEquals("MULTIPLE_CHOICE", question.getQuestionType());
        assertEquals(3, question.getOptions().size());
    }

    @Test
    public void testCorrectAnswer() {
        assertEquals("A programming language", question.getCorrectAnswer());
    }

    @Test
    public void testAddOptions() {
        Question q = new Question("Q2", "QUIZ-1", "True or False?", "TRUE_FALSE");
        assertEquals(0, q.getOptions().size());
        q.addOption("True");
        q.addOption("False");
        assertEquals(2, q.getOptions().size());
    }
}
