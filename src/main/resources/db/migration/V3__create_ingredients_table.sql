CREATE TABLE INGREDIENTS (
     ingredient_id INT PRIMARY KEY AUTO_INCREMENT,
     ingredient_name VARCHAR(255),
     ingredient_description VARCHAR(255),
     ingredient_type VARCHAR(255),
     measurement_unit VARCHAR(255),
     calories DOUBLE,
     fat DOUBLE,
     carbohydrates DOUBLE,
     proteins DOUBLE
);