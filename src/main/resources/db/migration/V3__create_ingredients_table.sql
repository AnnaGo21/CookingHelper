CREATE TABLE INGREDIENTS (
     IngredientID INT PRIMARY KEY,
     IngredientName VARCHAR(50) NOT NULL,
     IngredientDescription VARCHAR(255),
     IngredientType VARCHAR(50),
     MeasurementUnit VARCHAR(50),
     Calories DECIMAL(5, 2),
     Fat DECIMAL(5, 2),
     Carbohydrates DECIMAL(5, 2),
     Protein DECIMAL(5, 2)
);
