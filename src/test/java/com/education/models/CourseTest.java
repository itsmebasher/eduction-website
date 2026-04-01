package com.education.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Course model.
 */
public class CourseTest {
    private Course course;

    @Before
    public void setUp() {
        course = new Course("1", "Java Basics", "Learn Java programming", "John Doe");
    }

    @Test
    public void testCourseCreation() {
        assertEquals("1", course.getId());
        assertEquals("Java Basics", course.getTitle());
        assertEquals("John Doe", course.getInstructor());
        assertEquals(0, course.getEnrolledStudents());
    }

    @Test
    public void testEnrollStudent() {
        course.enrollStudent();
        assertEquals(1, course.getEnrolledStudents());
        
        course.enrollStudent();
        assertEquals(2, course.getEnrolledStudents());
    }

    @Test
    public void testCourseUpdate() {
        course.setTitle("Advanced Java");
        assertEquals("Advanced Java", course.getTitle());
    }
}
