package ru.kirillvenediktov.springbootuniversity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kirillvenediktov.springbootuniversity.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
