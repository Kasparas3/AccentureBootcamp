PRAGMA foreign_keys = ON;

-- Delete students first because students depends on courses.
DELETE FROM students;
DELETE FROM courses;

    INSERT INTO courses (course_id, course_name, credits)
    VALUES
        (1, 'Software engineering', 30),
        (2, 'Multimedia', 35),
        (3, 'UX/UI', 20);


    INSERT INTO students (id, name, email, age, course_id)
    VALUES
        (1, 'Bob', 'bob@gmail.com', 23, 1),
        (2, 'John', 'john@gmail.com', 18, 2),
        (3, 'Simon', 'simon@gmail.com', 20, 2),
        (4, 'Kevin', 'kevin@gmail.com', 19, 1),
        (5, 'Alex', 'alex@gmail.com', 20, 3);

