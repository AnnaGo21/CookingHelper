CREATE TABLE FOOD (
      foodID INT PRIMARY KEY AUTO_INCREMENT,
      foodName VARCHAR(255),
      description VARCHAR(255),
      type VARCHAR(255),
      measurementUnit VARCHAR(255),
      calories DOUBLE,
      fat DOUBLE,
      carbohydrates DOUBLE,
      proteins DOUBLE
);