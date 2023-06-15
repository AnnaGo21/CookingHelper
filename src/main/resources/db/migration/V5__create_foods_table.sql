CREATE TABLE Foods (
       FoodID INT PRIMARY KEY,
       FoodName VARCHAR(100) NOT NULL,
       RecipeID INT,
       FOREIGN KEY (RecipeID) REFERENCES Recipes(RecipeID)
);