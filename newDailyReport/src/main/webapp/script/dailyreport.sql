/*
SQLyog Community Edition- MySQL GUI v6.05
Host - 5.1.30-community : Database - dailyreport
*********************************************************************
Server version : 5.1.30-community
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `dailyreport`;

USE `dailyreport`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `plans_info` */

DROP TABLE IF EXISTS `plans_info`;

CREATE TABLE `plans_info` (
  `employee` varchar(100) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `project` varchar(100) DEFAULT NULL,
  `plans` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `plans_info` */

/*Table structure for table `project_info` */

DROP TABLE IF EXISTS `project_info`;

CREATE TABLE `project_info` (
  `employee` varchar(100) NOT NULL,
  `project` varchar(50) DEFAULT NULL,
  `role` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`employee`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `project_info` */

insert  into `project_info`(`employee`,`project`,`role`) values ('Abby Li','State Sick Leave SU1(RFA 1000036)','Dev'),('Caroline Zhang','SMS Back porting V7(RFA 1000029)','QA'),('Carson Zhu','State Sick LeaveSU2(RFA 1000037)','Dev'),('Donna Wu\r\n\r\n\r\n\r\nCarson Zhu\r\nVictoria Chang  \r\n\r\nPatrick Wang\r\nAbby Li\r\nQuentin Qin\r\n\r\n\r\nHolly Ren\r\n\r','State Sick LeaveSU2(RFA 1000037)','Dev'),('Holly Ren','State Sick Leave SU1(RFA 1000036)','QA'),('Jonathan Feng',NULL,'Arch'),('Josh Li',NULL,'TL'),('Maria Deng','SMS Backporting V7(RFA 1000030)','QA'),('Patrick Wang','State Sick Leave SU1(RFA 1000036)','Dev'),('Quentin Qin','State Sick Leave SU1(RFA 1000036)','QA'),('Stephanie Yang','State Sick Leave SU1(RFA 1000036)','Dev'),('Timothy Yang','Click Software SHA2 testing','Dev'),('Victoria Chang','State Sick LeaveSU2(RFA 1000037)','Dev');

/*Table structure for table `task_info` */

DROP TABLE IF EXISTS `task_info`;

CREATE TABLE `task_info` (
  `employee` varchar(100) DEFAULT NULL,
  `project` varchar(100) DEFAULT NULL,
  `role` varchar(10) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `task_desc` text,
  `hours` float DEFAULT NULL,
  `eta` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `task_info` */

/*Table structure for table `team_member` */

DROP TABLE IF EXISTS `team_member`;

CREATE TABLE `team_member` (
  `employee` varchar(100) NOT NULL,
  `team` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`employee`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `team_member` */

insert  into `team_member`(`employee`,`team`) values ('Abby Li','Kronos Team5'),('Caroline Zhang','Kronos Team5'),('Carson Zhu','Kronos Team5'),('Donna Wu','Kronos Team5'),('Holly Ren','Kronos Team5'),('Jonathan Feng','Kronos Team5'),('Josh Li','Kronos Team5'),('Maria Deng','Kronos Team5'),('Patrick Wang','Kronos Team5'),('Quentin Qin','Kronos Team5'),('Stephanie Yang','Kronos Team5'),('Timothy Yang','Kronos Team5'),('Victoria Chang','Kronos Team5');

/*Table structure for table `team_project` */

DROP TABLE IF EXISTS `team_project`;

CREATE TABLE `team_project` (
  `team` varchar(100) DEFAULT NULL,
  `project` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `team_project` */

insert  into `team_project`(`team`,`project`) values ('Kronos Team5','Click Software SHA2 testing'),('Kronos Team5','SMS Backporting V7(RFA 1000030)'),('Kronos Team5','SMS Back porting V7(RFA 1000029)'),('Kronos Team5','State Sick LeaveSU2(RFA 1000037)'),('Kronos Team5','State Sick Leave SU1(RFA 1000036)');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
