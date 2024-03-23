CREATE TABLE feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    enrollment_id INT NOT NULL,
    rating INT NOT NULL,
    comment VARCHAR(200) NOT NULL,
    feedback_date DATE NOT NULL,
    FOREIGN KEY (enrollment_id) REFERENCES enrollment(enrollment_id)
);