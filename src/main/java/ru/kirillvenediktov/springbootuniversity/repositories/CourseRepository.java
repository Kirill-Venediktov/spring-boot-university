package ru.kirillvenediktov.springbootuniversity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kirillvenediktov.springbootuniversity.models.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
