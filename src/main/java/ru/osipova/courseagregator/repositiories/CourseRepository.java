package ru.osipova.courseagregator.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.osipova.courseagregator.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllBySchool(String school);
    List<Course> findAllByCategory(String category);
    List<Course> findAllBySchoolAndCategory(String school, String category);
    List<Course> findAllByNameContains(String name);
}
