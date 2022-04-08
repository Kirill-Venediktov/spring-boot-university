INSERT INTO school.GROUPS
    (group_id, group_name)
VALUES (default, 'first group'),
       (default, 'second group'),
       (default, 'third group');

INSERT INTO school.COURSES
    (course_id, course_name, course_description)
VALUES (default, 'MATH', 'qwerty'),
       (default, 'PHYSICS', 'asdfg'),
       (default, 'CHEMISTRY', 'zxcvb'),
       (default, 'MUSIC', 'ggfgtv');

INSERT INTO school.STUDENTS
    (student_id, group_id, first_name, last_name)
VALUES (default, 2, 'Ivan', 'Petrov'),
       (default, 3, 'Petr', 'Ivanov'),
       (default, 1, 'Sergey', 'Sidorov'),
       (default, 2, 'Vladimir', 'Putin'),
       (default, 3, 'Dmitri', 'Medvedev');

INSERT INTO school.ENROLLMENT
    (STUDENT_ID, COURSE_ID)
VALUES (1, 3),
       (1, 4),
       (2, 2),
       (2, 3),
       (3, 1),
       (3, 2),
       (3, 4),
       (4, 3),
       (5, 3),
       (4, 4);

