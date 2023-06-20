CREATE TABLE Recipes (
     RecipeID INT PRIMARY KEY,
     RecipeName VARCHAR(100) NOT NULL,
     CookingTime INT,
     Servings INT,
     Difficulty VARCHAR(50),
     Cuisine VARCHAR(50),
     IsPublic BIT(1) NOT NULL
);
