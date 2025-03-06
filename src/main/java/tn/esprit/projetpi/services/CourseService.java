package tn.esprit.projetpi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.projetpi.entities.Course;
import tn.esprit.projetpi.repos.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Method to get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Method to get a course by its ID
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    // Method to add a new course
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    // Method to update an existing course
    public Course updateCourse(Long id, Course courseDetails) {
        Optional<Course> courseOptional = courseRepository.findById(id);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            course.setName(courseDetails.getName());
            course.setDescription(courseDetails.getDescription());
            course.setFormation(courseDetails.getFormation());
            return courseRepository.save(course);
        }
        return null;  // or throw a custom exception if you prefer
    }

    // Method to delete a course by its ID
    public boolean deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
