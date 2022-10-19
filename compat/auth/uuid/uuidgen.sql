DELIMITER //
CREATE TRIGGER setUUID BEFORE INSERT ON users
FOR EACH ROW BEGIN
	IF NEW.uuid IS NULL THEN
		SET NEW.uuid = UUID();
	END IF;
END; //
DELIMITER ;