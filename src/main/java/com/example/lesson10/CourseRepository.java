package com.example.lesson10;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CourseRepository extends CrudRepository<Course, Long> {
    Course findByTitle(String title);
    ArrayList<Course> findAllByCredit(int credit);
    ArrayList<Course> findAllByTitleContainingIgnoreCase(String search);
    ArrayList<Course> findAllByCreditBetween(int min, int max);
    Long countAllByInstructor(String instructor);
}
