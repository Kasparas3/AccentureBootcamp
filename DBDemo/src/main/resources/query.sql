PRAGMA foreign_keys = ON;

-- Select all courses.
    SELECT * FROM courses;

-- Select all students.
    SELECT * FROM students;

-- Select students older than 20.
    SELECT * FROM students WHERE age > 20;

-- Show student names together with course names.
    SELECT students.name, courses.course_name
    FROM students
    JOIN courses ON students.course_id = courses.course_id;

-- Count how many students are in each course.
    SELECT courses.course_name, COUNT(students.id) AS student_count
    FROM courses
    LEFT JOIN students ON students.course_id = courses.course_id
    GROUP BY courses.course_id, courses.course_name;

-- Update the first students age.
    UPDATE students SET age = 30 WHERE id = 1;

-- Check the update.
    SELECT * FROM students WHERE id = 1;

-- Move second student to a different course.
    UPDATE students SET course_id = 3 WHERE id = 2;

-- Check the update using JOIN.
    SELECT students.name, courses.course_name
    FROM students
    JOIN courses ON students.course_id = courses.course_id
    WHERE students.id = 2;

-- Delete one student.
    DELETE FROM students WHERE id = 5;

-- Check the final result.
    SELECT * FROM students;
