CREATE DATABASE  IF NOT EXISTS `pms` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pms`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: pms
-- ------------------------------------------------------
-- Server version	5.7.7-rc-log

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
-- Table structure for table `emddetail`
--

DROP TABLE IF EXISTS `emddetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emddetail` (
  `ProjId` int(10) NOT NULL,
  `SubProjId` int(10) DEFAULT NULL,
  `EmdAmount` decimal(15,2) NOT NULL,
  `EmdStartDate` datetime NOT NULL,
  `EmdEndDate` datetime NOT NULL,
  `EmdType` varchar(20) NOT NULL,
  `BGNumber` varchar(20) NOT NULL,
  `EmdPeriod` int(10) NOT NULL,
  `EmdExtensionDate` datetime NOT NULL,
  `EmdLedgerNumber` varchar(20) NOT NULL,
  `SubProjectEmd` varchar(20) DEFAULT '0',
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `EmdId` int(11) NOT NULL AUTO_INCREMENT,
  `EmdSubmitter` varchar(50),
  PRIMARY KEY (`EmdId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emddetail`
--

LOCK TABLES `emddetail` WRITE;
/*!40000 ALTER TABLE `emddetail` DISABLE KEYS */;
/*INSERT INTO `emddetail` VALUES (3,NULL,12.00,'2015-05-30 00:00:00','2015-05-30 00:00:00','Bank Guarantee','123',2,'2015-05-30 00:00:00','12','0','tkumar','2015-05-30 21:31:05',1),(3,5,12.00,'2015-05-30 00:00:00','2015-05-30 00:00:00','Bank Guarantee','123',2,'2015-05-30 00:00:00','12','0','tkumar','2015-05-30 21:31:43',2,'');*/
/*!40000 ALTER TABLE `emddetail` ENABLE KEYS */;
UNLOCK TABLES;

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
  `empAddress` varchar(100) DEFAULT NULL,
  `empGender` varchar(10) NOT NULL,
  `empMobNum` varchar(20) NOT NULL,
  `empMail` varchar(50) DEFAULT NULL,
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
/*INSERT INTO `employee` VALUES ('akumar','$2a$10$PZb8ORC45xFGG/kJ5wtiseot.0ikqTNe6CnqYWWRwh1FZmS5/nvRG','ajith','kumar','3 rd street','Male','9994254559','mail@mail.com','porkodi',1,'Admin'),('mkumar','$2a$10$U9C.H3FqG8.J6HUwoh9CO.qGLigYFh7UUWFvz/2uVS1G54ozAJ/oK','manager','kumar','3 rd street','Male','9994254559','mail@mail.com','9994254559',0,'Management'),('tkumar','$2a$10$FvLt6u7AoqnbVwj46Ohf7eoxmQj.j13vulC2eZkkdPWQqARL..Lra','tech','kumar','3 rd street','Male','9994254559','mail@mail.com','porkodi',1,'Technical');*/
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
  `AgreementNum` varchar(50),
  `CERNum` varchar(30) NOT NULL,
  `Amount` decimal(15,2) NOT NULL,
  `ContractorName` varchar(50) NOT NULL,
  `ContractorAliasName` varchar(50) NOT NULL,
  `ContractorAdd` tinytext NOT NULL,
  `AgreementValue` decimal(15,2) NOT NULL,
  `TenderValue` decimal(15,2) NOT NULL,
  `ContractorValue` decimal(15,2) NOT NULL,
  `ExcessInAmount` decimal(15,2) NOT NULL,
  `ExcessInPercentage` decimal(15,2) NULL,
  `LessInPercentage` decimal(15,2) NULL,
  `TenderDate` datetime NOT NULL,
  `AddSecurityDeposit` decimal(15,2) NOT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `AgreementDate` datetime DEFAULT NULL,
  `CommencementDate` datetime DEFAULT NULL,
  `CompletedDate` datetime DEFAULT NULL,
  `AgreementPeriod` int(10) DEFAULT NULL,
  PRIMARY KEY (`ProjId`),
  UNIQUE KEY `AliasProjName_UNIQUE` (`AliasProjName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*INSERT INTO `project` VALUES (3,'Life Insurance','LIC','12312','CER234',12345.00,'PSK','conpsk','mount road, chennai',12345.00,12345.00,12345.00,12.00,12.00,13.00,'2015-05-28 00:00:00','2015-05-03 00:00:00',1234.00,123.00,'tkumar','2015-05-29 22:22:53','2015-05-27 00:00:00','2015-05-27 00:00:00','2015-05-27 00:00:00',2);*/
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
  `SerialNumber` varchar(10) NOT NULL,
  `SubProjId` int(10) NULL,
  `WorkType` varchar(30) NOT NULL,
  `QuantityInFig` decimal(15,2) NULL,
  `QuantityInWords` varchar(50) NULL,
  `Description` text NOT NULL,
  `AliasDescription` varchar(100) NOT NULL,
  `RateInFig` decimal(15,2) NOT NULL,
  `RateInWords` varchar(50) NOT NULL,
  `Amount` decimal(15,2) NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `ProjDescId` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ProjDescId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE TABLE `datadescription` (
  `ProjDescId` int(10) NOT NULL,
  `SerialNumber` varchar(10) NOT NULL,
  `Amount` decimal(15,2) NULL,
  `Unit` varchar(30) NOT NULL,
  `Material` varchar(100) NOT NULL,
  `QuantityInFig` decimal(15,2) NULL,
  `RateInFig` decimal(15,2) NOT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `DataDescId` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`DataDescId`)
);

--
-- Dumping data for table `projectdesc`
--

LOCK TABLES `projectdesc` WRITE;
/*!40000 ALTER TABLE `projectdesc` DISABLE KEYS */;
/*INSERT INTO `projectdesc` VALUES (3,5,'Main Work',123.00,'one two three','desc work','LICF1MW1',123.00,'312',122.00,'tkumar','2015-05-30 16:05:49',2);*/
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
  `AgreementNum` varchar(50),
  `CERNum` varchar(30) NOT NULL,
  `Amount` decimal(15,2) NOT NULL,
  `ContractorName` varchar(50) NOT NULL,
  `ContractorAliasName` varchar(50) NOT NULL,
  `ContractorAdd` tinytext NOT NULL,
  `AgreementValue` decimal(15,2) NOT NULL,
  `TenderValue` decimal(15,2) NOT NULL,
  `ContractorValue` decimal(15,2) NOT NULL,
  `ExcessInAmount` decimal(15,2) NOT NULL,
  `ExcessInPercentage` decimal(15,2) NULL,
  `LessInPercentage` decimal(15,2) NULL,
  `SubAddSecurityDeposit` decimal(15,2) NOT NULL,
  `TenderDate` datetime NOT NULL,
  `AgreementDate` datetime DEFAULT NULL,
  `CommencementDate` datetime DEFAULT NULL,
  `CompletedDate` datetime DEFAULT NULL,
  `AgreementPeriod` int(10) DEFAULT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `ProjId` int(10) NOT NULL,
  PRIMARY KEY (`SubProjId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subproject`
--

LOCK TABLES `subproject` WRITE;
/*!40000 ALTER TABLE `subproject` DISABLE KEYS */;
/*INSERT INTO `subproject` VALUES (5,'LIC 1st floor','LICF1','12212','3131',3123.00,'psk','conpsk','mt road',3123.00,3213.00,12321.00,31.00,32.00,32.00,12312.00,'2015-05-30 00:00:00','2015-05-30 00:00:00','2015-05-30 00:00:00','2015-05-30 00:00:00',1,'tkumar','2015-05-30 16:00:47',3);*/
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
/*INSERT INTO `userroles` VALUES ('akumar','ROLE_USER'),('tkumar','ROLE_USER'),('mkumar','ROLE_USER');*/
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

-- Dump completed on 2015-05-30 21:59:24
