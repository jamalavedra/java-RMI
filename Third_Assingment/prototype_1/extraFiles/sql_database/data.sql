SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

-- Table structure for table `data`
--
USE data_security;
DROP TABLE IF EXISTS `data`;
CREATE TABLE IF NOT EXISTS `data` (
  `Username` varchar(30) NOT NULL,
  `Password` varchar(64) NOT NULL,
  `Salt` varchar(64) NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

USE data_security;
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `Username` varchar(30) NOT NULL,
  `print` BOOLEAN NOT NULL,
  `queue` BOOLEAN NOT NULL,
  `topQueue` BOOLEAN NOT NULL,
  `start` BOOLEAN NOT NULL,
  `stop` BOOLEAN NOT NULL,
  `restart` BOOLEAN NOT NULL,
  `status` BOOLEAN NOT NULL,
  `readConfig` BOOLEAN NOT NULL,
  `setConfig` BOOLEAN NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
--
-- Dumping data for table `data`
--

INSERT INTO `data` (`Username`, `Password`, `Salt`) VALUES
('Mike', 'TzBql1WR9wZjN0LoKr2OBk2majc=', '4NWwJULan8U='), --- ThisTheFirstPassword
('John', 'JcQnEVl0af8EwrPkmc6EGG+fLfs=', '2MWwJULan8U='), --- ThisTheSecondPassword
('Alice', 'BaN+yn8W0SYZiUkaOHSZxDKTLMg=', '4NWSJULgn8U='),--- ThisTheThirdPassword
('Bob', 'V1TtSe0GFYuHFJrA1l1lDIwdfKA=', '4NWwJILan9U='),  --- ThisTheFourthPassword
('Charlie', 'X40Xv/MntGoeqwif8hhFEOIrK8Q=', '4NWwFULan8X=');--- ThisTheFifthPassword
COMMIT;


INSERT INTO `users` (`Username`, `print`, `queue`, `topQueue`, `start`, `stop`, `restart`, `status`, `readConfig`, `setConfig`) VALUES
('Cecilia', true, true, true, false, false, true, false, false, false);
('Erika', true, false, false, false, false, false, false, false, false);
('Alice', true, true, true, true, true, true, true, true, true);
('Bob', false, false, false, true, true, true, true, true, true),
('David', true, false, false, false, false, flase, false, false, false);
COMMIT;
