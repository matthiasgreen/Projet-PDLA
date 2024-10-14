DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM("user", "volunteer", "validating_user") NOT NULL
);

DROP TABLE IF EXISTS posts;
CREATE TABLE posts (
    id INT PRIMARY KEY AUTO_INCREMENT,

    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,

    type ENUM("offer", "mission") NOT NULL,
    status ENUM("pending", "validated", "refused") NOT NULL,
    refusal_reason TEXT,
    location VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

