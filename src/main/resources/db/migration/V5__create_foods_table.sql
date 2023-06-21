CREATE TABLE FOOD (
      foodID INT PRIMARY KEY AUTO_INCREMENT,
      foodName VARCHAR(255) NOT NULL,
      recipeID INT,
      description VARCHAR(255),
      type VARCHAR(255),
      measurementUnit VARCHAR(10),
      calories DOUBLE,
      fat DOUBLE,
      carbohydrates DOUBLE,
      protein DOUBLE,
      FOREIGN KEY (recipeID) REFERENCES Recipe(recipeID)
);
