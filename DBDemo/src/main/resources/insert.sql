PRAGMA foreign_keys = ON;

-- Delete students first because students depends on courses.
DELETE FROM students;
DELETE FROM courses;

-- TODO 1:
-- Insert 3 courses.
-- Remember:
-- course_id is a number.
-- course_name must be present.
-- credits must be greater than 0.

-- TODO 2:
-- Insert 5 students.
-- Remember:
-- id is a number.
-- name must be present.
-- email must be unique.
-- age must be 18 or older.
-- course_id must exist in the courses table.