DROP TABLE IF EXISTS school.courses CASCADE;
DROP TABLE IF EXISTS school.groups CASCADE;
DROP TABLE IF EXISTS school.students CASCADE;
DROP TABLE IF EXISTS school.enrollment;

CREATE TABLE IF NOT EXISTS school.courses
(
    course_id          BIGSERIAL UNIQUE,
    course_name        varchar(250) COLLATE pg_catalog."default"           NOT NULL,
    course_description character varying(250) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);

CREATE TABLE IF NOT EXISTS school.groups
(
    group_id   BIGSERIAL UNIQUE,
    group_name varchar(250) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
);

CREATE TABLE IF NOT EXISTS school.students
(
    student_id BIGSERIAL UNIQUE,
    group_id   BIGINT,
    first_name varchar(250) COLLATE pg_catalog."default" NOT NULL,
    last_name  varchar(250) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id),
    CONSTRAINT students_group_id_fkey FOREIGN KEY (group_id) REFERENCES school.groups (group_id) ON DELETE SET default
);

CREATE TABLE IF NOT EXISTS school.enrollment
(
    student_id BIGINT NOT NULL,
    course_id  BIGINT NOT NULL,
    CONSTRAINT enrollment_pkey PRIMARY KEY (student_id, course_id),
    CONSTRAINT enrollment_student_id_fkey FOREIGN KEY (student_id) REFERENCES school.students ON DELETE CASCADE,
    CONSTRAINT enrollment_course_id_fkey FOREIGN KEY (course_id) REFERENCES school.courses ON DELETE CASCADE
);
