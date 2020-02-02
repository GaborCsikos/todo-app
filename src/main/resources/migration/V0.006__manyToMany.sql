CREATE TABLE IF NOT EXISTS GOAL(
ID INT PRIMARY KEY AUTO_INCREMENT,
DESCRIPTION VARCHAR(128)
);


INSERT INTO GOAL (ID, DESCRIPTION)
VALUES (1, 'World domination');


CREATE TABLE IF NOT EXISTS TODO_GOAL(
ID INT PRIMARY KEY AUTO_INCREMENT,
TODO_ID INT,
GOAL_ID INT
);

INSERT INTO TODO_GOAL (ID, TODO_ID, GOAL_ID)
VALUES (1, 1,1);

ALTER TABLE TODO_GOAL
ADD CONSTRAINT FK_TODO_GOAL FOREIGN KEY (TODO_ID) REFERENCES TODO(ID);

ALTER TABLE TODO_GOAL
ADD CONSTRAINT FK_GOAL_TODO FOREIGN KEY (GOAL_ID) REFERENCES GOAL(ID);