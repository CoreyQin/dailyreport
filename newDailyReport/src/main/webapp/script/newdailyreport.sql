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

insert  into `employee`(`employee`,`team`,`project`,`role`,`email`,`active`) values ('Abby Li','Kronos Team5','State Sick Leave SU1(RFA 1000036)','QA','test@test.com.cn',1),('Caroline Zhang','Kronos Team5','SMS Back porting V7(RFA 1000029)','QA',NULL,1),('Carson Zhu','Kronos Team5','State Sick LeaveSU2(RFA 1000037)','Dev',NULL,1),('coreytest','Kronos Team5','coreytest','Dev','corey@corey.com.cn',0),('Donna Wu','Kronos Team5',NULL,NULL,NULL,1),('Holly Ren','Kronos Team5','State Sick Leave SU1(RFA 1000036)','QA',NULL,1),('Jonathan Feng','Kronos Team5',NULL,'Arch',NULL,1),('Josh Li','Kronos Team5',NULL,'TL','coreyqin@objectivasoftware.com',1),('Maria Deng','Kronos Team5','SMS Backporting V7(RFA 1000030)','QA',NULL,1),('Patrick Wang','Kronos Team5','State Sick Leave SU1(RFA 1000036)','Dev',NULL,1),('Quentin Qin','Kronos Team5','State Sick Leave SU1(RFA 1000036)','QA',NULL,1),('Stephanie Yang','Kronos Team5','State Sick Leave SU1(RFA 1000036)','Dev',NULL,1),('test','Kronos Team5','testProject222','Dev','',0),('Timothy Yang','Kronos Team5','Click Software SHA2 testing','Dev',NULL,1),('Victoria Chang','Kronos Team5','State Sick LeaveSU2(RFA 1000037)','Dev',NULL,1);

/*Table structure for table `plans_info` */

DROP TABLE IF EXISTS `plans_info`;

CREATE TABLE `plans_info` (
  `employee` varchar(100) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `project` varchar(100) DEFAULT NULL,
  `plans` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `plans_info` */

insert  into `plans_info`(`employee`,`date`,`project`,`plans`) values ('Abby Li','2015-12-07','-',''),('Abby Li','2015-12-07','Click Software SHA2 testing','MiBidWdzLCAKUGF5aW5nUGF5Q29kZSBpcyBub3QgZXhpc3Rpbmcgd2hlbiBSZXRyaWV2ZSBhbiBFbXBsb3ltZW50IFRlcm0gd2l0aCBzaW5nbGUgdmVyc2lvbiBhbmQgQWxsb3dNaW5tdW1XYWdlIGluIFhNTCBpcyB0cnVlLiAKUGF5aW5nUGF5Q29kZSBpcyBub3QgZXhpc3Rpbmcgd2hlbiBSZXRyaWV2ZSBhbiBFbXBsb3ltZW50IFRlcm0gd2l0aCBtdWx0aSB2ZXJzaW9uIGFuZCBBbGxvd01pbm11bVdhZ2UgaW4gWE1MIGlzIHRydWUuCg=='),('Caroline Zhang','2015-12-07','Click Software SHA2 testing',''),('Abby Li','2015-12-09','Click Software SHA2 testing','YXNkZg=='),('Abby Li','2015-12-09','SMS Back porting V7(RFA 1000029)','c2RmYXNkZiA='),('Abby Li','2015-12-10','Click Software SHA2 testing','YXNkZg=='),('Caroline Zhang','2015-12-10','SMS Back porting V7(RFA 1000029)','YXNkZg==');

/*Table structure for table `project` */

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `project` varchar(100) NOT NULL,
  `team` varchar(100) DEFAULT NULL,
  `level` int(2) DEFAULT NULL,
  PRIMARY KEY (`project`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `project` */

insert  into `project`(`project`,`team`,`level`) values ('Click Software SHA2 testing','Kronos Team5',4),('SMS Back porting V7(RFA 1000029)','Kronos Team5',10),('SMS Backporting V7(RFA 1000030)','Kronos Team5',10),('State Sick Leave SU1(RFA 1000036)','Kronos Team5',10),('State Sick LeaveSU2(RFA 1000037)','Kronos Team5',10),('testProject','Kronos Team5',6);

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

insert  into `task_info`(`employee`,`project`,`role`,`date`,`task_desc`,`hours`,`eta`) values ('Abby Li','-','QA','2015-12-07','YWE=',2,'2'),('Abby Li','-','QA','2015-12-07','YmI8YnI+YXNkZmFkZmFkZiEjQCMjIUBAIyM8YnI+YXNkZmEncyBhZnNkZg==',2,'2'),('Abby Li','Click Software SHA2 testing','QA','2015-12-07','NC4gVXBkYXRlIHRoZSByZXBvcnQgdGVzdCBjYXNlIGFuZCBzY2VuYXJpbyBmb2xsb3cgUXVlbnRpbidzIHJldmlldzEwMCUpLg==',2,'2'),('Abby Li','Click Software SHA2 testing','QA','2015-12-07','My4gVXBkYXRlIHRoZSBYTUwgQVBJIHRlc3QgY2FzZSBhbmQgc2NlbmFyaW8gd2l0aCBDYXJzb24ncyBuZXcgc3dpdGNoMTAwJSku',2,'2'),('Abby Li','Click Software SHA2 testing','QA','2015-12-07','Mi4gVGVzdCAgWE1MIEFQSSBmdW5jdGlvbiBvbiAgcmZhMTAwMDAzNy1qbDEoMjAlKS4=',2,'2'),('Caroline Zhang','Click Software SHA2 testing','QA','2015-12-07','MS4gU01TIFFGIGZvciBSRkEgMTAwMDAyOSB0cm91YmxlIHNob290aW5nIEluc3RhbGwvVW5pbnN0YWxsIHRlc3RpbmcuIC0tLS0tLTEwMCU8YnI+MS4xICBJbnN0YWxsZWQgdGhlIDcuMC4wUlRNIGFuZCA3LjAuMTAgb24gY2VuZzk2ODktcGoxIHRvIHZlcmlmeSB0aGUgRXJyb3IgTG9nIGZlYXR1cmUgb24gNy4wLjEwKCM4NCkuPGJyPjEuMiAgVW5pbnN0YWxsZWQgdGhlIDcuMC4xMCBvbiBjZW5nOTY4OS1wajEgdG8gdmVyaWZ5IHRoZSBlcnJvciBsb2cgb24gNy4wLjBSVE0uPGJyPjEuMyAgVXBncmFkZSB0ZXN0aW5nIG9uIGNlbmc2NDQ4LCB3aGljaCBmcm9tIDcuMC45IHRvIDcuMC4xMC48YnI+',2,'2'),('Abby Li','Click Software SHA2 testing','QA','2015-12-09','YXNkZmFzZGY8YnI+YXNkZmFhPGJyPi8vXFxhc2RmYSdz',2,'2'),('Abby Li','Click Software SHA2 testing','QA','2015-12-09','YXNkZmE=',2,'2'),('Abby Li','SMS Back porting V7(RFA 1000029)','QA','2015-12-09','MTExMjxicj5hc2RmPGJyPmFzZGY=',2,'2'),('Abby Li','Click Software SHA2 testing','QA','2015-12-10','MTIzPGJyPmFzZGZhc2RmJ3Nhc2RmIGFzZGZhPGJyPkAjIyMkQCMjJA==',2,'2'),('Caroline Zhang','SMS Back porting V7(RFA 1000029)','QA','2015-12-10','YXNkZg==',2,'2');

/*Table structure for table `team` */

DROP TABLE IF EXISTS `team`;

CREATE TABLE `team` (
  `team` varchar(50) NOT NULL,
  PRIMARY KEY (`team`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `team` */

insert  into `team`(`team`) values ('Kronos Team5');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
