package ru.kirillvenediktov.springbootuniversity.enums;

public enum LoggerConstant {

    SUCCESSFUL_REMOVING_STRING("element successfully removed"),
    SUCCESSFUL_ADDITION_TO_COURSE_STRING("student successfully added to course"),
    DELETING_ELEMENT_INFO_STRING("deleting element with id {}"),
    COULD_NOT_DELETE_STUDENT("Couldn't delete student with id = "),
    DELETING_EXCEPTION_STRING("Exception during deleting element with id = {}"),
    REMOVING_STUDENT_FROM_COURSE("removing student with id {} from course with id {}"),
    REMOVING_STUDENT_FROM_COURSE_EXCEPTION("Error with student deleting operation. student id = {}, courseId = {}"),
    ADDITION_STUDENT_TO_COURSE_INFO_STRING("addition student with id = {} to course with id = {}"),
    STRING_GENERATED("string {} generated"),
    GROUP_NAME_GENERATION_EXCEPTION("failed to generate group name "),
    GROUP_NAME_GENERATED("group name {} generated"),
    FIRST_NAME_GENERATING_INFO_STRING("first name generating"),
    GENERATED_FIRST_NAME("generated first name = {}"),
    LAST_NAME_GENERATING_INFO_STRING("last name generating"),
    GENERATED_LAST_NAME("generated last name = {}"),
    COURSE_NAME_GENERATING_INFO_STRING("course name generating"),
    GENERATED_COURSE_NAME("generated course name = {}"),
    STUDENT_ADDITION_TO_GROUPS_INFO_STRING("student addition to groups"),
    GROUP_GENERATION_INFO_STRING("group generation"),
    GROUP_GENERATED("group {} generated"),
    COURSE_GENERATION_INFO_STRING("course generation"),
    COURSE_DESCRIPTION_GENERATION_EXCEPTION("failed to generate description of course"),
    COURSE_GENERATED("course {} generated"),
    STUDENT_GENERATION_INFO_STRING("student generation"),
    STUDENT_GENERATED("student {} generated"),
    COURSES_ASSIGMENT_INFO_STRING("student to courses assigment"),
    GENERATING_GROUPS_LIST_INFO_STRING("generating groups list"),
    GROUP_LIST_GENERATED("groups list {} generated"),
    GENERATING_COURSES_LIST_INFO_STRING("generating courses list"),
    COURSES_LIST_GENERATED("courses list {} generated"),
    GENERATING_STUDENTS_LIST_INFO_STRING("generating students list"),
    STUDENTS_LIST_GENERATED("students list {} generated"),
    BEFORE_ARRANGING("before arranging in groups: {}"),
    AFTER_ARRANGING("after arranging in groups: {}"),
    STUDENT_REGISTERING_INFO_STRING("student registering in groups"),
    STUDENT_REGISTERED_IN_GROUP_STRING("student {} registered in group {}"),
    CREATE_ELEMENT_IN_DB_STRING("create element = {} in DB"),
    UPDATE_ELEMENT_IN_DB_STRING("update element = {} in DB"),
    INVALID_VALUE("Invalid value = "),
    CHECKING_STUDENT_ADDED_TO_COURSE_INFO_STRING("checking for student with id = {} added to course with id = {}"),
    STUDENT_IS_ADDED("student is added = {}"),
    COURSE_ID(". Course id = "),
    FILLING_GROUPS("filling groups table"),
    FILLING_COURSES("filling courses table"),
    FILLING_STUDENTS_TABLE("filling students table"),
    FILLING_ENROLLMENT("filling enrollment table"),
    SCHOOL_DATA_GENERATION_START("generating data"),
    SCHOOL_DATA_GENERATION_FINISH("finishing generating data"),
    GENERATION_STRING_WITH_RANDOM_SIGNS("Generation String with random signs. Elements = {} "),
    THROWING_EXCEPTION("Throwing exception: = {}"),
    ELEMENT_IS_CREATED("Element is created. = {}"),
    ELEMENT_IS_UPDATED("Element is updated. = {}"),
    METHOD_STRING("Method: "),
    CHECKING_ELEMENT_WITH_ID("checking element with id = {}"),
    ELEMENT_IS_EXISTED("Element is existed = {}"),
    COURSE_STRING("Courses = {}"),
    STUDENT_STRING("Students = {}"),
    GROUPS_STRING("Groups = {}"),
    GETTING_GROUPS_WITH_STUDENT_COUNT("Getting groups with student count"),
    GETTING_COURSES("Getting courses related with student with id = {}"),
    REMOVING_STUDENTS_FROM_GROUP("Removing students from group with id = {}"),
    SUCCESSFUL_REMOVING_STUDENTS_FROM_GROUP("Removing successful. Are removed = {}"),
    GETTING_PAGE("Getting page. Inputs = {}"),
    PAGE_RECEIVED("Page = {} received"),
    GETTING_PAGE_NUMBERS("Getting page number for page = {}"),
    PAGE_NUMBERS_RECEIVED("page numbers = {} received"),
    METHOD_STARTED("Start of = {}"),
    METHOD_FINISH("Finish of = {}"),
    METHOD_RESULT("Result of method = {}");

    private final String value;

    LoggerConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
