package ru.kirillvenediktov.springbootuniversity.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import ru.kirillvenediktov.springbootuniversity.dao.DatabaseStartupFiller;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.models.Student;
import ru.kirillvenediktov.springbootuniversity.service.SchoolDataGenerator;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@ComponentScan("ru.kirillvenediktov.springbootuniversity")
@AllArgsConstructor
public class SpringConfig {

    private final Environment env;

    @Bean
    @ConfigurationProperties(prefix = "datasource")
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSourceBuilder.url(env.getProperty("spring.datasource.url"));
        dataSourceBuilder.username(env.getProperty("spring.datasource.username"));
        dataSourceBuilder.password(env.getProperty("spring.datasource.password"));
        return dataSourceBuilder.build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
            = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"ru.kirillvenediktov.springbootuniversity.models"});
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean
    public SchoolDataGenerator dataGenerator() {
        return new SchoolDataGenerator();
    }

    @Bean
    public DatabaseStartupFiller databaseStartupFiller() {
        SchoolDataGenerator dataGenerator = dataGenerator();
        List<Group> groups = dataGenerator.getGroups();
        List<Student> students = dataGenerator.getStudents();
        List<Course> courses = dataGenerator.getCourses();
        return new DatabaseStartupFiller(groups, students, courses, entityManagerFactory(), dataSource());
    }

}
