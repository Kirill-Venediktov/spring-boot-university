package ru.kirillvenediktov.springbootuniversity.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.models.Student;
import ru.kirillvenediktov.springbootuniversity.service.RandomGenerator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.sql.DataSource;
import java.util.List;

public class DatabaseStartupFiller {

    private static final int BATCH_SIZE = 10;

    private final List<Group> groups;
    private final List<Student> students;
    private final List<Course> courses;

    private final EntityManagerFactory entityManagerFactory;
    private final DataSource dataSource;
    private final RandomGenerator randomGenerator = new RandomGenerator();

    @Value("${tableCreationScryptFilepath}")
    private Resource resource;

    public DatabaseStartupFiller(List<Group> groups, List<Student> students, List<Course> courses,
                                 LocalContainerEntityManagerFactoryBean entityManagerFactory, DataSource dataSource) {
        this.groups = groups;
        this.students = students;
        this.courses = courses;
        this.dataSource = dataSource;
        this.entityManagerFactory = entityManagerFactory.getObject();
    }

    public void dropAndCreateTables() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
    }


    private <T> void fill(List<T> elements) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            for (int i = 0; i < elements.size(); i++) {
                if (i % BATCH_SIZE == 0) {
                    em.flush();
                    transaction.commit();
                    transaction.begin();
                    em.clear();
                }
                em.persist(elements.get(i));

            }
        } catch (RuntimeException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.flush();
            transaction.commit();
            em.close();
        }
    }

    private <T> void update(T t) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        em.merge(t);
        em.flush();
        em.clear();
        em.getTransaction().commit();
    }

    public void fillTables() {
        dropAndCreateTables();
        fill(groups);
        fill(students);
        randomGenerator.assignCoursesForStudents(students, courses);
        update(students.get(0));

    }

}
