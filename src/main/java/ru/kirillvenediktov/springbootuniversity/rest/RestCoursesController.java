package ru.kirillvenediktov.springbootuniversity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kirillvenediktov.springbootuniversity.dto.CourseDTO;
import ru.kirillvenediktov.springbootuniversity.exceptions.ChangesNotMadeException;
import ru.kirillvenediktov.springbootuniversity.service.CoursesService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestCoursesController {

    private final CoursesService coursesService;

    @Autowired
    public RestCoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping("/courses")
    public List<CourseDTO> getCourses() {
        return coursesService.getAllCoursesDTO();
    }

    @GetMapping("/courses/{id}")
    public CourseDTO getCourse(@PathVariable Long id) {
        return coursesService.getCourseDTO(id);
    }

    @RequestMapping(value = "/courses", method = {RequestMethod.POST, RequestMethod.PUT})
    public CourseDTO saveCourse(@RequestBody CourseDTO courseDTO) {
        coursesService.saveCourse(courseDTO);
        return courseDTO;
    }

    @DeleteMapping("/courses/{id}")
    public String deleteCourse(@PathVariable Long id) {
        if (coursesService.getCourse(id).getId() == null) {
            throw new ChangesNotMadeException("there is no course with id = " + id + " in database");
        }
        coursesService.deleteCourse(id);
        return "Course with id = " + id + " was deleted";
    }

}
