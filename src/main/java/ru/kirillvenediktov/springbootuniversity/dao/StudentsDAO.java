package ru.kirillvenediktov.springbootuniversity.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
@Transactional
public class StudentsDAO {

    @PersistenceContext
    private EntityManager em;

    public void deleteStudent(Student student) {
        em.remove(student);
    }

    public Set<Course> getStudentCourses(Student student) {
        return student.getCourses();
    }

    public List<Student> getAllStudents() {
        return em.createQuery("select s from Student s order by s.id", Student.class).getResultList();
    }

    public Student getStudent(Long studentId) {
        return em.find(Student.class, studentId);
    }

    public void createStudent(Student student) {
        em.persist(student);
    }

    public Student updateStudent(Student student) {
        return em.merge(student);
    }

}
