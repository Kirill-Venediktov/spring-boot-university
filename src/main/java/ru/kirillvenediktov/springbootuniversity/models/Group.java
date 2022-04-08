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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "groups", schema = "school")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    @NonNull
    private Long id;

    @NonNull
    @Column(name = "group_name")
    private String groupName;

    @OneToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.DETACH, CascadeType.REFRESH},
        mappedBy = "group", fetch = FetchType.EAGER)
    private List<Student> students = new ArrayList<>();

    public void addStudentToGroup(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(student);
        student.setGroup(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Group group = (Group) o;

        return id != null && id.equals(group.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName);
    }

}
