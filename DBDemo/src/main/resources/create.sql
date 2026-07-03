PRAGMA foreign_keys = ON;

-- Drop students first because students depends on courses.
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS courses;

    CREATE TABLE courses (
        course_id INTEGER primary key ,
        course_name TEXT NOT NULL ,
        credits INTEGER CHECK ( credits > 0 )
    );

    CREATE TABLE students (
        id INTEGER primary key ,
        name TEXT NOT NULL ,
        email TEXT NOT NULL UNIQUE ,
        age INTEGER CHECK ( age >= 18 ) ,
        course_id INTEGER NOT NULL ,
        FOREIGN KEY (course_id)
                          REFERENCES courses(course_id)
    );
