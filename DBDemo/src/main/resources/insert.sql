PRAGMA foreign_keys = ON;

-- Delete students first because students depends on courses.
DELETE FROM students;
DELETE FROM courses;

-- TODO 1:

    INSERT INTO courses (course_id, course_name, credits)
    VALUES
        (1, 'Software engineering', 30),
        (2, 'Multimedia', 35),
        (3, 'UX/UI', 20);

-- Insert 3 courses.
-- Remember:
-- course_id is a number.
-- course_name must be present.
-- credits must be greater than 0.

-- TODO 2:

    INSERT INTO students (id, name, email, age, course_id)
    VALUES
        (1, 'Bob', 'bob@gmail.com', 23, 1),
        (2, 'John', 'john@gmail.com', 18, 2),
        (3, 'Simon', 'simon@gmail.com', 20, 2),
        (4, 'Kevin', 'kevin@gmail.com', 19, 1),
        (5, 'Alex', 'alex@gmail.com', 20, 3);


-- Insert 5 students.
-- Remember:
-- id is a number.
-- name must be present.
-- email must be unique.
-- age must be 18 or older.
-- course_id must exist in the courses table.