CREATE TABLE INGREDIENTS (
     ingredientID INT PRIMARY KEY AUTO_INCREMENT,
     ingredientName VARCHAR(255),
     ingredientDescription VARCHAR(255),
     ingredientType VARCHAR(255),
     measurementUnit VARCHAR(255),
     calories DOUBLE,
     fat DOUBLE,
     carbohydrates DOUBLE,
     proteins DOUBLE
);