CREATE TABLE course (
                        id BIGSERIAL PRIMARY KEY,
                        course_name VARCHAR(255) NOT NULL,
                        course_description TEXT,
                        category VARCHAR(100),
                        instructor VARCHAR(100),
                        created_date TIMESTAMP
);
