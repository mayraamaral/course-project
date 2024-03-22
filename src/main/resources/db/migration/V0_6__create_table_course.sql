CREATE TABLE course (
    name VARCHAR(100) NOT NULL,
    code VARCHAR(10) UNIQUE NOT NULL,
    instructor_id INT NOT NULL,
    description VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATE NOT NULL,
    inactivated_at DATE,
    FOREIGN KEY (instructor_id) REFERENCES user(user_id)
);