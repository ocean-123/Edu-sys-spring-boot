package com.education.service;

import com.education.entity.Course;

import java.util.List;

public interface course {


    void deleteCourse(Long id);
     Course saveCourse(Course course);
  List<Course> getAllCourses();

    Long getTotalNumberOfCourses();
}
