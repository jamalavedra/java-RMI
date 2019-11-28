SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Table structure for table `data`
--

USE DB;
DROP TABLE IF EXISTS `data`;
CREATE TABLE IF NOT EXISTS `data` (
  `id_u` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(30) NOT NULL,
  `Password` varchar(64) NOT NULL,
  `Salt` varchar(64) NOT NULL,
   `Role` int(30)  NOT NULL,
    PRIMARY KEY (`id_u`)
);

--
-- Dumping data for table `data`
--

INSERT INTO `data` (`id_u`,`Username`, `Password`, `Salt`,`Role`) VALUES
(1,'Mike', 'TzBql1WR9wZjN0LoKr2OBk2majc=', '4NWwJULan8U=','1'), 
(2,'Cecilia', 'TzBql1WR9wZjN0LoKr2OBk2majc=', '4NWwJULan8U=','1'),
(3,'Erika', 'iubXIju+Hd+EOgIuivTx3RbRDoU=', '2MWwJULan8U=','2'),
(4,'Alice', 'mWrE8czs6KkOeP1WiMyn0NEnKGw=', '4NWSJULgn8U=','2'),
(5,'Bob', 'YNvbZBcchzXYRyRJBx5WkPmwxfo=', '4NWwJILan9U=','3'),
(6,'David', 'OPhte5nto3U+rJucbb3GUTGCSiI=', '4NWwFULan8X=','3');
COMMIT;

--
-- Table structure for table `Roles`
--

DROP TABLE IF EXISTS `Roles`;
CREATE TABLE IF NOT EXISTS `Roles` (
  `id_r` int(11) NOT NULL AUTO_INCREMENT,
  `Role_type` varchar(30) NOT NULL,
  `Role_code` int(30) NOT NULL,
   PRIMARY KEY (`id_r`),
   CONSTRAINT FK_UsersRoles FOREIGN KEY (Role_code)
   REFERENCES Roles(Role),
   CONSTRAINT FK_PermissionsRoles FOREIGN KEY (Role_code)
   REFERENCES Permissions(Permission)
)ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Roles`
--

INSERT INTO `Roles` (`id_r`, `Role_type`, `Role_code`) VALUES
(1, 'Admin', 1),
(2, 'PowerUser', 2),
(3, 'BasicUser', 3),
(4, 'Admin', 2),
(5, 'Admin', 3),
(6, 'PowerUser', 3),
(7, 'Maintainance', 4);
COMMIT;


--
-- Table structure for table `Permissions`
--

DROP TABLE IF EXISTS `Permissions`;
CREATE TABLE IF NOT EXISTS `Permissions` (
  `id_p` int(11) NOT NULL AUTO_INCREMENT,
  `Function` varchar(30) NOT NULL,
  `Permission` int(30) NOT NULL,
   PRIMARY KEY (`id_p`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Permissions`
--

INSERT INTO `Permissions` (`id_p`, `Function`, `Permission`) VALUES
(1, 'print', 1),
(2, 'print', 2),
(3, 'print', 3),
(4, 'queue', 1),
(5, 'queue', 2),
(6, 'queue', 3),
(7, 'topQueue', 1),
(8, 'topQueue', 2),
(9, 'restart', 1),
(10, 'restart', 2),
(11, 'restart', 4),
(12, 'start', 1),
(13, 'start', 4),
(14, 'stop', 1),
(15, 'stop', 4),
(16, 'status', 1),
(17, 'status', 4),
(18, 'readConfig', 1),
(19, 'readConfig', 4),
(20, 'setConfig', 1),
(21, 'setConfig', 4);
COMMIT;

