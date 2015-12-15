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
  `team` varchar(50) NOT NULL,
  `project` varchar(100) DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL COMMENT '1:active; 0:inactive',
  PRIMARY KEY (`employee`,`team`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `employee` */

insert  into `employee`(`employee`,`team`,`project`,`role`,`email`,`active`) values ('Abby Li','Kronos Team5','State Sick Leave SU1(RFA 1000036)','DEV','test@test.com',1),('Andrew Chang','Kronos Team3','Audit Report - convert to java','DEV','',1),('Ashley Geng','Kronos Team3','API Locale Policy Support','QA','',1),('Barry Fan','Kronos Team1&8','State Street Core SAML 2.0 Support(RFA-32016)(CENG-15829)','DEV','bfan@Objectivasoftware.com',1),('Byron Liu','Kronos Team1&8','Training','DEV','byronliu@Objectivasoftware.com',1),('Caroline Zhang','Kronos Team5','SMS Back porting V7(RFA 1000029)','QA',NULL,1),('Carson Zhu','Kronos Team5','State Sick LeaveSU2(RFA 1000037)','DEV',NULL,1),('Charles Qian','Kronos Team1&8','8.0 Stabilization (RFA-1000047)','DEV','charlesqian@Objectivasoftware.com',1),('Chester Zhang','Kronos Team1&8','Resources Support in  Team 3','DEV','chesterzhang@Objectivasoftware.com',1),('Christine Cai','Kronos Team1&8','LAM(RFA-32445)','QA','christinecai@Objectivasoftware.com',1),('Coby Guo','Kronos Team1&8','Cross Project Automation (UFT)','QA','cobyguo@Objectivasoftware.com',1),('coreytest','Kronos Team3','coreytest_project','DEV','',1),('coreytest','Kronos Team5','coreytest_project','DEV','',1),('Edmund Shi','Kronos Team1&8','Resources Support in  Team 3','DEV','edmundshi@Objectivasoftware.com',1),('Eric Yao','Kronos Team1&8','State Street Core SAML 2.0 Support\n(RFA-32016)(CENG-15829)','ARCH','eyao@Objectivasoftware.com',1),('Eva Ren','Kronos Team3','API Update Action ','QA','',1),('Frida Yang','Kronos Team3','API Locale Policy Support','QA','',1),('Gavin Wang','Kronos Team3','API Locale Policy Support','DEV','',1),('Holly Ren','Kronos Team5','State Sick Leave SU1(RFA 1000036)','QA',NULL,1),('James Li','Kronos Team3','Rest Day Rule','QA','',1),('Jonathan Feng','Kronos Team5','-','ARCH','',1),('Josh Li','Kronos Team5',NULL,'TL','joshli@Objectivasoftware.com',1),('Julia Zhang','Kronos Team3','Shift tolerance - technical debt','QA','',1),('Karen Meng','Kronos Team3','Audit Report - convert to java','QA','',1),('Lisa Li','Kronos Team1&8','State Street Core SAML 2.0 Support\n(RFA-32016)(CENG-15829)','QA','lisali@Objectivasoftware.com',1),('Maria Deng','Kronos Team5','SMS Backporting V7(RFA 1000030)','QA',NULL,1),('Neal Chen','Kronos Team3','Shift tolerance - technical debt','DEV','',1),('Nolan Lu','Kronos Team3','Audit Report - convert to java','DEV','',1),('Patrick','Kronos Team3','Rest Day Rule','DEV','',1),('Patrick Wang','Kronos Team5','State Sick Leave SU1(RFA 1000036)','DEV',NULL,1),('Queenie Li','Kronos Team1&8','Resources Support in  Team 3','QA','queenieli@Objectivasoftware.com',1),('Quentin Qin','Kronos Team5','State Sick Leave SU1(RFA 1000036)','QA',NULL,1),('Rebacca Cheng','Kronos Team1&8','8.0 Stabilization (RFA-1000047)','QA','rcheng@Objectivasoftware.com',1),('Sabrina Liu','Kronos Team3','API Locale Policy Support','DEV','',1),('Scarlett Xiao','Kronos Team1&8','State Street Core SAML 2.0 Support\n(RFA-32016)(CENG-15829)','TL','sxiao@Objectivasoftware.com',1),('Stephanie Yang','Kronos Team5','State Sick Leave SU1(RFA 1000036)','DEV',NULL,1),('Timothy Yang','Kronos Team3','-','DEV','',1),('Timothy Yang','Kronos Team5','Click Software SHA2 testing','DEV',NULL,1),('Victoria Chang','Kronos Team5','State Sick LeaveSU2(RFA 1000037)','DEV',NULL,1);

/*Table structure for table `plans_info` */

DROP TABLE IF EXISTS `plans_info`;

CREATE TABLE `plans_info` (
  `employee` varchar(100) DEFAULT NULL,
  `team` varchar(50) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `project` varchar(100) DEFAULT NULL,
  `plans` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `plans_info` */

insert  into `plans_info`(`employee`,`team`,`date`,`project`,`plans`) values ('coreytest','Kronos Team3','2015-12-15','coreytest_project','cGxhbnMgZm9yIHRlYW0z'),('coreytest','Kronos Team5','2015-12-15','coreytest_project','cGxhbnMgZm9yIHRlYW01'),('coreytest','Kronos Team5','2015-12-15','coreytest_project_10','dGFzayBmb3IgcHJvamVjdCAxMCBpbiB0ZWFtNQ==');

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `project` varchar(100) NOT NULL,
  `team` varchar(100) NOT NULL,
  `level` int(2) DEFAULT NULL,
  PRIMARY KEY (`project`,`team`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `project` */

insert  into `project`(`project`,`team`,`level`) values ('-','Kronos Team5',10),('8.0 Stabilization (RFA-1000047)','Kronos Team1&8',10),('Click Software SHA2 testing','Kronos Team5',5),('coreytest_project','Kronos Team3',10),('coreytest_project','Kronos Team5',10),('coreytest_project_10','Kronos Team5',10),('Cross Project Automation (UFT)','Kronos Team1&8',10),('LAM(RFA-32445)','Kronos Team1&8',10),('Resources Support in  Team 3','Kronos Team1&8',10),('SMS Back porting V7(RFA 1000029)','Kronos Team5',10),('SMS Backporting V7(RFA 1000030)','Kronos Team5',10),('State Sick Leave SU1(RFA 1000036)','Kronos Team5',10),('State Sick LeaveSU2(RFA 1000037)','Kronos Team5',10),('State Street Core SAML 2.0 Support','Kronos Team1&8',10),('Training','Kronos Team1&8',9);

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

/*Data for the table `task_info` */

insert  into `task_info`(`employee`,`team`,`project`,`role`,`date`,`task_desc`,`hours`,`eta`) values ('coreytest','Kronos Team3','coreytest_project','DEV','2015-12-15','MTIzMTxicj5kZmE8YnI+dGFzayBmb3IgdGVhbTM=',2,'2'),('coreytest','Kronos Team5','coreytest_project','DEV','2015-12-15','dGFzayBmb3IgdGVhbTU=',2,'2'),('coreytest','Kronos Team5','coreytest_project_10','DM','2015-12-15','dGFzayBmb3IgcHJvamVjdCAxMCBpbiB0ZWFtNQ==',2,'2');

/*Table structure for table `team` */

DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `team` varchar(50) NOT NULL,
  PRIMARY KEY (`team`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `team` */

insert  into `team`(`team`) values ('Kronos Team1&8'),('Kronos Team3'),('Kronos Team5');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
