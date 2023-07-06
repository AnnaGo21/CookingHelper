CREATE TABLE FOOD (
      food_id INT PRIMARY KEY AUTO_INCREMENT,
      food_name VARCHAR(255),
      description VARCHAR(255),
      type VARCHAR(255),
      measurement_unit VARCHAR(255),
      calories DOUBLE,
      fat DOUBLE,
      carbohydrates DOUBLE,
      proteins DOUBLE
);