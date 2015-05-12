-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: pms
-- ------------------------------------------------------
-- Server version	5.6.24-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `empId` varchar(30) NOT NULL,
  `empPassword` varchar(60) NOT NULL,
  `empFirstName` varchar(40) NOT NULL,
  `empLastName` varchar(40) NOT NULL,
  `empAddress` varchar(80) NOT NULL,
  `empGender` varchar(10) NOT NULL,
  `empMobNum` varchar(20) NOT NULL,
  `empMail` varchar(30) NOT NULL,
  `empMotherName` varchar(30) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  `empTeam` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('akumar','$2a$10$PZb8ORC45xFGG/kJ5wtiseot.0ikqTNe6CnqYWWRwh1FZmS5/nvRG','ajith','kumar','3 rd street','Male','9994254559','mail@mail.com','porkodi',1,'Admin'),('mkumar','$2a$10$U9C.H3FqG8.J6HUwoh9CO.qGLigYFh7UUWFvz/2uVS1G54ozAJ/oK','manager','kumar','3 rd street','Male','9994254559','mail@mail.com','9994254559',0,'Management'),('tkumar','$2a$10$FvLt6u7AoqnbVwj46Ohf7eoxmQj.j13vulC2eZkkdPWQqARL..Lra','tech','kumar','3 rd street','Male','9994254559','mail@mail.com','porkodi',1,'Technical');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `ProjId` int(10) NOT NULL AUTO_INCREMENT,
  `ProjName` text NOT NULL,
  `AliasProjName` varchar(50) NOT NULL,
  `AgreementNum` varchar(50) NOT NULL,
  `CERNum` varchar(30) NOT NULL,
  `Amount` numeric(15,2) NOT NULL,
  `ContractorName` varchar(50) NOT NULL,
  `ContractorAdd` tinytext NOT NULL,
  `AgreementValue` numeric(15,2) NOT NULL,
  `TenderValue` numeric(15,2) NOT NULL,
  `ContractorValue` numeric(15,2) NOT NULL,
  `ExcessInAmount` numeric(15,2) NOT NULL,
  `ExcessInPercentage` numeric(15,2) NOT NULL,
  `TenderDate` datetime NOT NULL,
  `EmdStartDate` datetime NOT NULL,
  `EmdEndDate` datetime NOT NULL,
  `EmdAmount` numeric(15,2) NOT NULL,
  `AgreementDate` datetime NULL,
  `CommencementDate` datetime NULL,
  `CompletedDate` datetime NULL,
  `AgreementPeriod` int(10) NULL,
  PRIMARY KEY (`ProjId`),
  UNIQUE KEY `AliasProjName_UNIQUE` (`AliasProjName`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'Life Insurance Corporation Building','LIC Building','12345','12345','2000000','john kontrac','12, metro boulevard, chennai','5000000','8000000','1000000','4000000','40','2015-04-24 00:00:00','2015-04-24 00:00:00','2015-04-24 00:00:00','2015-04-24 00:00:00',12),(2,'Central Prison','cp','original 123','12345','12345','ajith','123, north street','12345','12345','12345','123','12','2015-04-27 00:00:00','2015-04-27 00:00:00','2015-04-27 00:00:00','2015-04-27 00:00:00',12);
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projectdesc`
--

DROP TABLE IF EXISTS `projectdesc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projectdesc` (
  `ProjId` int(10) NOT NULL,
  `SubProjId` int(10) NOT NULL,
  `WorkType` varchar(30) NOT NULL,
  `QuantityInFig` int(30) NOT NULL,
  `QuantityInWords` varchar(50) NOT NULL,
  `Description` text NOT NULL,
  `AliasDescription` varchar(100) NOT NULL,
  `RateInFig` int(30) NOT NULL,
  `RateInWords` varchar(50) NOT NULL,
  `Amount` int(50) NOT NULL,
  `ProjDescId` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ProjDescId`),
  UNIQUE KEY `AliasDescription_UNIQUE` (`AliasDescription`),
  KEY `AliasSubProjName` (`SubProjId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projectdesc`
--

LOCK TABLES `projectdesc` WRITE;
/*!40000 ALTER TABLE `projectdesc` DISABLE KEYS */;
INSERT INTO `projectdesc` VALUES (2,1,'Main Work',0,'zero','description','mainDesc',0,'zero',0,1);
/*!40000 ALTER TABLE `projectdesc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subproject`
--

DROP TABLE IF EXISTS `subproject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subproject` (
  `SubProjId` int(10) NOT NULL AUTO_INCREMENT,
  `SubProjName` text NOT NULL,
  `AliasSubProjName` varchar(50) NOT NULL,
  `AgreementNum` varchar(50) NOT NULL,
  `CERNum` varchar(30) NOT NULL,
  `Amount` varchar(50) NOT NULL,
  `ContractorName` varchar(50) NOT NULL,
  `ContractorAdd` tinytext NOT NULL,
  `AgreementValue` varchar(50) NOT NULL,
  `TenderValue` varchar(50) NOT NULL,
  `ContractorValue` varchar(50) NOT NULL,
  `ExcessInAmount` varchar(50) NOT NULL,
  `ExcessInPercentage` varchar(30) NOT NULL,
  `TenderDate` datetime NOT NULL,
  `AgreementDate` datetime NULL,
  `CommencementDate` datetime NULL,
  `CompletedDate` datetime NULL,
  `AgreementPeriod` int(10) NULL,
  `ProjId` int(10) NOT NULL,
  PRIMARY KEY (`SubProjId`),
  UNIQUE KEY `AliasSubProjName_UNIQUE` (`AliasSubProjName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subproject`
--

LOCK TABLES `subproject` WRITE;
/*!40000 ALTER TABLE `subproject` DISABLE KEYS */;
INSERT INTO `subproject` VALUES (1,'cp flooring','cpf','1234','1224','2131','12312','1233','1231','12321','123213','1323','1233','2015-04-01 00:00:00','2015-04-01 00:00:00','2015-04-01 00:00:00','2015-04-01 00:00:00',12,2),(4,'cp flooring','cpf1','1234','1224','2131','12312','1233','1231','12321','123213','1323','1233','2015-04-01 00:00:00','2015-04-01 00:00:00','2015-04-01 00:00:00','2015-04-01 00:00:00',12,2);
/*!40000 ALTER TABLE `subproject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userroles`
--

DROP TABLE IF EXISTS `userroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userroles` (
  `empId` varchar(30) NOT NULL,
  `userRole` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userroles`
--

LOCK TABLES `userroles` WRITE;
/*!40000 ALTER TABLE `userroles` DISABLE KEYS */;
INSERT INTO `userroles` VALUES ('akumar','ROLE_USER'),('tkumar','ROLE_USER'),('mkumar','ROLE_USER');
/*!40000 ALTER TABLE `userroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `empId` int(8) NOT NULL,
  `empPassword` varchar(45) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-05 21:17:37
