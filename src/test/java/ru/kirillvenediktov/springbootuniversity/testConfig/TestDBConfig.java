package ru.kirillvenediktov.springbootuniversity.testConfig;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.kirillvenediktov.springbootuniversity.dao.DatabaseStartupFiller;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.models.Student;
import ru.kirillvenediktov.springbootuniversity.service.SchoolDataGenerator;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

//@Configuration
//@EnableTransactionManagement
@ComponentScan("ru.kirillvenediktov.springbootuniversity")
@PropertySource("classpath:databaseTesting/testDB.properties")

@TestConfiguration
//@TestPropertySource("classpath:databaseTesting/testDB.properties")
public class TestDBConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSourceBuilder.url(env.getProperty("spring.datasource.url"));
        dataSourceBuilder.username(env.getProperty("spring.datasource.username"));
        dataSourceBuilder.password(env.getProperty("spring.datasource.password"));
        return dataSourceBuilder.build();
//
//
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("driver")));
//        dataSource.setUrl(Objects.requireNonNull(env.getProperty("url")));
//        dataSource.setUsername(Objects.requireNonNull(env.getProperty("user")));
//        dataSource.setPassword(Objects.requireNonNull(env.getProperty("password")));
//        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
            = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[]{"ru.kirillvenediktov.springbootuniversity.models"});

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
//        em.setJpaProperties(hibernateProperties());

        return em;
    }

//    private final Properties hibernateProperties() {
//        Properties hibernateProperties = new Properties();
//        hibernateProperties.setProperty(
//            "hibernate.hbm2ddl.auto", "update");
//        hibernateProperties.setProperty(
//            "hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        hibernateProperties.setProperty("hibernate.order_inserts", "true");
//        hibernateProperties.setProperty("hibernate.order_updates", "true");
//        hibernateProperties.setProperty("hibernate.batch_versioned_data", "true");
//        hibernateProperties.setProperty("hibernate.show_sql", "true");
//        return hibernateProperties;
//    }

//    @Bean
//    public PlatformTransactionManager hibernateTransactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setDataSource(dataSource());
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return transactionManager;
//    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }

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
