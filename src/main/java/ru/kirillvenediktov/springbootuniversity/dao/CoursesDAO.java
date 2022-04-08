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
public class CoursesDAO {

    @PersistenceContext
    EntityManager em;

    public Set<Course> getStudentCourses(Student student) {
        return student.getCourses();
    }

    public Set<Student> getStudentsOfCourse(Course course) {
        return course.getStudents();
    }

    public List<Course> getAllCourses() {
        return em.createQuery("select c from Course c order by c.id", Course.class).getResultList();
    }

    public Course getCourse(Long courseId) {
        return em.find(Course.class, courseId);
    }

    public void removeStudentFromCourse(Student student, Course course) {
        course.removeStudentFromCourse(student);
        em.merge(course);
    }

    public void addStudentToCourse(Student student, Course course) {
        course.addStudentToCourse(student);
        em.merge(course);
    }

    public void deleteCourse(Course course) {
        em.remove(course);
    }

    public void createCourse(Course course) {
        em.persist(course);
    }

    public Course updateCourse(Course course) {
        return em.merge(course);
    }

}
