/*
SQLyog Community Edition- MySQL GUI v6.05
Host - 5.1.30-community : Database - newdailyreport
*********************************************************************
Server version : 5.1.30-community
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `newdailyreport`;

USE `newdailyreport`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `employee` */

DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `employee` varchar(100) NOT NULL,
  `team` varchar(50) DEFAULT NULL,
  `project` varchar(100) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL COMMENT '1:active; 0:inactive',
  PRIMARY KEY (`employee`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `employee` */

insert  into `employee`(`employee`,`team`,`project`,`role`,`email`,`active`) values ('Abby Li','Kronos Team5','State Sick Leave SU1(RFA 1000036)','Dev',NULL,1),('Caroline Zhang','Kronos Team5','SMS Back porting V7(RFA 1000029)','QA',NULL,1),('Carson Zhu','Kronos Team5','State Sick LeaveSU2(RFA 1000037)','Dev',NULL,1),('Donna Wu','Kronos Team5',NULL,NULL,NULL,1),('Holly Ren','Kronos Team5','State Sick Leave SU1(RFA 1000036)','QA',NULL,1),('Jonathan Feng','Kronos Team5',NULL,'Arch',NULL,1),('Josh Li','Kronos Team5',NULL,'TL','coreyqin@objectivasoftware.com',0),('Maria Deng','Kronos Team5','SMS Backporting V7(RFA 1000030)','QA',NULL,1),('Patrick Wang','Kronos Team5','State Sick Leave SU1(RFA 1000036)','Dev',NULL,1),('Quentin Qin','Kronos Team5','State Sick Leave SU1(RFA 1000036)','QA',NULL,1),('Stephanie Yang','Kronos Team5','State Sick Leave SU1(RFA 1000036)','Dev',NULL,1),('Timothy Yang','Kronos Team5','Click Software SHA2 testing','Dev',NULL,1),('Victoria Chang','Kronos Team5','State Sick LeaveSU2(RFA 1000037)','Dev',NULL,1);

/*Table structure for table `plans_info` */

DROP TABLE IF EXISTS `plans_info`;

CREATE TABLE `plans_info` (
  `employee` varchar(100) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `project` varchar(100) DEFAULT NULL,
  `plans` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `plans_info` */

insert  into `plans_info`(`employee`,`date`,`project`,`plans`) values ('Jonathan Feng','2015-11-30','-','asdf'),('Abby Li','2015-12-01','Click Software SHA2 testing','asdfasdf');

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `project` varchar(100) NOT NULL,
  `team` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`project`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `project` */

insert  into `project`(`project`,`team`) values ('Click Software SHA2 testing','Kronos Team5'),('SMS Back porting V7(RFA 1000029)','Kronos Team5'),('SMS Backporting V7(RFA 1000030)','Kronos Team5'),('State Sick Leave SU1(RFA 1000036)','Kronos Team5'),('State Sick LeaveSU2(RFA 1000037)','Kronos Team5');

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

insert  into `task_info`(`employee`,`project`,`role`,`date`,`task_desc`,`hours`,`eta`) values ('Jonathan Feng','-','Arch','2015-11-30','asdfasdfasasdf',3,'done'),('Abby Li','Click Software SHA2 testing','QA','2015-12-01','asdf',3,'dd');

/*Table structure for table `team` */

DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `team` varchar(50) NOT NULL,
  PRIMARY KEY (`team`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `team` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
