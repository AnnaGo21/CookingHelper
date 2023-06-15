CREATE TABLE Recipes (
     RecipeID INT PRIMARY KEY,
     RecipeName VARCHAR(100) NOT NULL,
     Instructions TEXT,
     PreparationTime INT,
     CookingTime INT,
     Servings INT,
     Difficulty VARCHAR(50),
     Cuisine VARCHAR(50)
);