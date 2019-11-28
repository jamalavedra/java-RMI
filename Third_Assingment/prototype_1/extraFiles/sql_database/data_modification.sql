SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

-- The authnetication password for the new user Andrei is:
-- Andrei = ThisTheFirstPassword

-- Update David's permissions
UPDATE `users`
SET  print = false, queue = false, topQueue = false, start = true, stop = true, restart = true, status = true, readConfig = true, setConfig = true
WHERE Username = 'George';

-- Delete Cecilia from authenticating
DELETE FROM `users`
WHERE Username = 'Bob';

-- Delete Cecilia from being authorized
DELETE FROM `data`
WHERE Username = 'Bob';

-- Insert a new user  to the authorization
INSERT INTO `users` (`Username`, `print`, `queue`, `topQueue`, `start`, `stop`, `restart`, `status`, `readConfig`, `setConfig`) VALUES
('Henry', true, true, false, false, false, false, false, false, false),
('Ida', true, true, true, false, false, true, false, false, false);

-- ThisTheNinethPassword
-- ThisTheThenthPassword

-- Insert new user to authentication
INSERT INTO `data`(`Username`, `Password`, `Salt`) VALUES
('Henry', 'Mpih7GRat97smL6ib/nD3976G7E=', '4NWwJULan8U='),
('Ida', 'CZSTg0JsZrHKN7WZJfqB01M5Dxc=', '7SWwJULnn2Q=');
COMMIT;
