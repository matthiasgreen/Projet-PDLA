DROP TABLE IF EXISTS users;
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM("USER_IN_NEED", "VOLUNTEER", "VALIDATOR") NOT NULL,
    UNIQUE(username)
);

DROP TABLE IF EXISTS posts;
CREATE TABLE posts (
    id INT PRIMARY KEY AUTO_INCREMENT,

    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,

    type ENUM("OFFER", "MISSION") NOT NULL,
    status ENUM("PENDING", "VALIDATED", "REFUSED", "DONE") NOT NULL,
    refusal_reason TEXT,
    location VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

DROP TABLE IF EXISTS reviews;
CREATE TABLE reviews (
    author_id INT NOT NULL,
    target_id INT NOT NULL,
    content TEXT NOT NULL,
    rating INT NOT NULL,

    PRIMARY KEY (author_id, target_id),
    FOREIGN KEY (author_id) REFERENCES users(id),
    FOREIGN KEY (target_id) REFERENCES users(id)
);