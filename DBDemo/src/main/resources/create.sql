PRAGMA foreign_keys = ON;

-- Drop students first because students depends on courses.
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS courses;

-- TODO 1:
-- Create the courses table.
-- It must contain:
-- course_id   INTEGER primary key
-- course_name TEXT, required
-- credits     INTEGER, must be greater than 0

-- TODO 2:
-- Create the students table.
-- It must contain:
-- id        INTEGER primary key
-- name      TEXT, required
-- email     TEXT, required and unique
-- age       INTEGER, must be 18 or older
-- course_id INTEGER, required
-- course_id must be a foreign key that references courses(course_id).