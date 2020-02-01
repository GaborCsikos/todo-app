CREATE TABLE IF NOT EXISTS NOTE(
ID INT PRIMARY KEY AUTO_INCREMENT,
DESCRIPTION VARCHAR(256)
);


INSERT INTO NOTE (ID, DESCRIPTION)
VALUES (1, 'Must do today');

INSERT INTO NOTE (ID, DESCRIPTION)
VALUES (2, 'I need coffee');

ALTER TABLE NOTE
ADD COLUMN TODO_ID INT;

UPDATE NOTE
SET TODO_ID = 1;

ALTER TABLE NOTE
ADD CONSTRAINT FK_NOTE_TODO FOREIGN KEY (TODO_ID) REFERENCES TODO(ID)

