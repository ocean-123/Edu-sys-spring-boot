package com.education.service;

import com.education.entity.Course;
import com.education.repository.CourseRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService  implements  course{
    @Autowired
    private CourseRepo courseRepository;


    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }
    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }


    @Override
    public  Long getTotalNumberOfCourses(){

        return  courseRepository.count();
    }
}
