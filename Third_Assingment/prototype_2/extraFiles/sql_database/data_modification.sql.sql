SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";
SET SQL_SAFE_UPDATES = 0;

-- The authnetication password for the new user Mike is:
-- Mike = ThisTheFirstPassword

-- Update Role permission for a function
UPDATE `DB`.`Permissions`
SET `Permission` = '2'
WHERE (`Function` = 'print');

-- Delete Role permission for a function
DELETE FROM `DB`.`Permissions`
WHERE (`Function` = 'createUser ')
AND (`Permission` = '2');

-- Insert Role Permission for a function
INSERT INTO `DB`.`Permissions` (`id_p`, `Function`, `Permission`) VALUES ('12', 'newPrintJob', '2');

-- Delete Cecilia from authenticating
DELETE FROM `DB`.`data` WHERE (`Username` = 'Cecilia ');

-- Update Cecilia's role
UPDATE `DB`.`data` SET `Role` = '3' WHERE (`Username` = 'Cecilia ');

-- Insert new user to authentication
INSERT INTO `data`(`Username`, `Password`, `Salt`,`Role`) VALUES
('Andrei', 'TzBql1WR9wZjN0LoKr2OBk2majc=', '4NWwJULan8U=','1');
COMMIT;