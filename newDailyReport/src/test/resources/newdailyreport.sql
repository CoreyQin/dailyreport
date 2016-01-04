create database if not exists `newdailyreport`;

USE `newdailyreport`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `employee` varchar(100) NOT NULL,
  `team` varchar(50) NOT NULL,
  `project` varchar(100) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL COMMENT '1:active; 0:inactive',
  PRIMARY KEY (`employee`,`team`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `plans_info` */

DROP TABLE IF EXISTS `plans_info`;

CREATE TABLE `plans_info` (
  `employee` varchar(100) DEFAULT NULL,
  `team` varchar(50) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `project` varchar(100) DEFAULT NULL,
  `plans` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `project` varchar(100) NOT NULL,
  `team` varchar(100) NOT NULL,
  `rfa` varchar(100) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `level` int(2) DEFAULT NULL,
  PRIMARY KEY (`project`,`team`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `task_info` */

DROP TABLE IF EXISTS `task_info`;

CREATE TABLE `task_info` (
  `employee` varchar(100) DEFAULT NULL,
  `team` varchar(50) DEFAULT NULL,
  `project` varchar(100) DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `task_desc` text,
  `hours` float DEFAULT NULL,
  `eta` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Table structure for table `team` */

DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `team` varchar(50) NOT NULL,
  PRIMARY KEY (`team`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
