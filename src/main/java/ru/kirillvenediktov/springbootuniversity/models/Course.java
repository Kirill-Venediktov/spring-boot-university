package ru.kirillvenediktov.springbootuniversity.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses", schema = "school")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    @NonNull
    private Long id;

    @Column(name = "course_name")
    @NonNull
    private String courseName;

    @Column(name = "course_description")
    @NonNull
    private String courseDescription;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, mappedBy = "courses",
        fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<Student> students;

    public void addStudentToCourse(Student student) {
        if (students == null) {
            students = new LinkedHashSet<>();
        }
        students.add(student);
        student.addCourseToStudent(this);
    }

    public void removeStudentFromCourse(Student student) {
        students.remove(student);
        student.getCourses().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Course course = (Course) o;

        return id != null && id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, courseName, courseDescription);
    }

}
