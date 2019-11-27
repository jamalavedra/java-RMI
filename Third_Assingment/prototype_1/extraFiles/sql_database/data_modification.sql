SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

-- The authnetication password for the new user Andrei is:
-- Andrei = ThisTheFirstPassword

-- Update David's permissions
UPDATE `users`
SET  print = true, queue = true, topQueue = true, start = false, stop = false, restart = true, status = false, readConfig = false, setConfig = false
WHERE Username = 'David';

-- Delete Cecilia from authenticating
DELETE FROM `users`
WHERE Username = 'Cecilia';

-- Delete Cecilia from being authorized
DELETE FROM `data`
WHERE Username = 'Cecilia';

-- Insert a new user  to the authorization
INSERT INTO `users` (`Username`, `print`, `queue`, `topQueue`, `start`, `stop`, `restart`, `status`, `readConfig`, `setConfig`) VALUES
('Andrei', true, false, false, false, false, false, false, false, false);

-- Insert new user to authentication
INSERT INTO `data`(`Username`, `Password`, `Salt`) VALUES
('Andrei', 'TzBql1WR9wZjN0LoKr2OBk2majc=', '4NWwJULan8U=');
COMMIT;
