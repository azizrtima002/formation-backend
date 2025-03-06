package tn.esprit.projetpi.repos;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.projetpi.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}