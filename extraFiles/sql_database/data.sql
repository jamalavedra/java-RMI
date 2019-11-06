-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Nov 13, 2018 at 04:36 PM
-- Server version: 5.7.23
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `passwordlogins`
--

-- --------------------------------------------------------

--
-- Table structure for table `data`
--
USE data_security;
DROP TABLE IF EXISTS `data`;
CREATE TABLE IF NOT EXISTS `data` (
  `Username` varchar(30) NOT NULL,
  `Password` varchar(64) NOT NULL,
  `Salt` varchar(64) NOT NULL
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `data`
--

INSERT INTO `data` (`Username`, `Password`, `Salt`) VALUES
('Mike', 'TzBql1WR9wZjN0LoKr2OBk2majc=', '4NWwJULan8U='),
('John', 'JcQnEVl0af8EwrPkmc6EGG+fLfs=', '2MWwJULan8U='),
('Alice', 'BaN+yn8W0SYZiUkaOHSZxDKTLMg=', '4NWSJULgn8U='),
('Bob', 'V1TtSe0GFYuHFJrA1l1lDIwdfKA=', '4NWwJILan9U='),
('Charlie', 'X40Xv/MntGoeqwif8hhFEOIrK8Q=', '4NWwFULan8X=');
COMMIT;
