SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";
SET SQL_SAFE_UPDATES = 0;

-- Delete Bob from authenticating
DELETE FROM `DB`.`data` WHERE (`Username` = 'Bob ');

-- Update Cecilia's role
UPDATE `DB`.`data` SET `Role` = '4' WHERE (`Username` = 'George ');

-- ThisTheNinethPassword
-- ThisTheThenthPassword

-- Insert new user to authentication
INSERT INTO `data`(`Username`, `Password`, `Salt`,`Role`) VALUES
('Henry', 'Mpih7GRat97smL6ib/nD3976G7E=', '4NWwJULan8U=','3'),
('Ida', 'CZSTg0JsZrHKN7WZJfqB01M5Dxc=', '7SWwJULnn2Q=','2');
COMMIT;