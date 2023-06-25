CREATE TABLE RECIPE (
    recipeId INT PRIMARY KEY AUTO_INCREMENT,
    recipeName VARCHAR(255),
    cookingTime INT,
    difficulty VARCHAR(255),
    cuisine VARCHAR(255),
    isPublic BOOLEAN,
    created_by INT,
    FOREIGN KEY (created_by) REFERENCES REGISTERED_USERS(id)
);