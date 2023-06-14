CREATE TABLE FOOD (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        protein DOUBLE(10, 2) NOT NULL,
        fat DOUBLE(10, 2) NOT NULL,
        carbohydrates DOUBLE(10, 2) NOT NULL,
        kcal INT NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;