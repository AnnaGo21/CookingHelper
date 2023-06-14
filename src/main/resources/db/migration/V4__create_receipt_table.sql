CREATE TABLE RECEIPT (
      id INT AUTO_INCREMENT PRIMARY KEY,
      food_1_id INT NOT NULL,
      food_2_id INT NOT NULL,
      food_3_id INT NOT NULL,
      food_4_id INT NOT NULL,
      quantity INT NOT NULL,
      price DECIMAL(10, 2) NOT NULL,
      purchase_date DATE NOT NULL,
      FOREIGN KEY (food_1_id) REFERENCES FOOD(id),
      FOREIGN KEY (food_2_id) REFERENCES FOOD(id),
      FOREIGN KEY (food_3_id) REFERENCES FOOD(id),
      FOREIGN KEY (food_4_id) REFERENCES FOOD(id)
);