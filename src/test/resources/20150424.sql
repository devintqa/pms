CREATE DATABASE  IF NOT EXISTS `pms` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pms`;
-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pms
-- ------------------------------------------------------
-- Server version	5.5.43-log

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
  `EmdSubmitter` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`EmdId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emddetail`
--

LOCK TABLES `emddetail` WRITE;
/*!40000 ALTER TABLE `emddetail` DISABLE KEYS */;
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
INSERT INTO `employee` VALUES ('akumar','$2a$10$5ypDVRuwAlFVuIcWOrkM1OrYgLuxjsFJ1otT.Ig3QY1YJ6a4d9OTG','admin','kumar','2nd street','Male','9999999999','admin@gmail.com','admin',1,'Admin'),('tkumar','$2a$10$mykTtgpJNJbFU5AVhmnuWOjdF2Gm/ze3Se4jRl/vaqqMCZT/Erv5y','technical','kumar','2nd street','Male','9999999998','tech@gmail.com','admin',1,'Technical');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `itemcodes`
--

DROP TABLE IF EXISTS `itemcodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemcodes` (
  `itemName` varchar(100) NOT NULL,
  `itemUnit` varchar(20) NOT NULL,
  `itemType` varchar(20) NOT NULL,
  `itemNo` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`itemNo`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemcodes`
--

LOCK TABLES `itemcodes` WRITE;
/*!40000 ALTER TABLE `itemcodes` DISABLE KEYS */;
INSERT INTO `itemcodes` VALUES
('CEMENT','BAG','Material',1),
('RIVER SAND','CFT','Material',2),
('SEWING SAND','CFT','Material',3),
('BRICKS','NO','Material',4),
('STEEL','MT','Material',5),
('MASON I','NO','Labour',6),
('MASON II','NO','Labour',7),
('CARPENTER I','NO','Labour',8),
('CARPENTER II','NO','Labour',9);
/*!40000 ALTER TABLE `itemcodes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projdescitem`
--

DROP TABLE IF EXISTS `projdescitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projdescitem` (
  `ProjId` int(11) DEFAULT NULL,
  `SubProjId` int(11) DEFAULT NULL,
  `ProjDescId` int(11) DEFAULT NULL,
  `ProjDescSerial` varchar(45) DEFAULT NULL,
  `ItemName` varchar(45) DEFAULT NULL,
  `ItemUnit` varchar(45) DEFAULT NULL,
  `ItemQty` varchar(45) DEFAULT NULL,
  `ItemPrice` varchar(45) DEFAULT NULL,
  `ItemCost` varchar(45) DEFAULT NULL,
  `DescItemId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`DescItemId`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projdescitem`
--

LOCK TABLES `projdescitem` WRITE;
/*!40000 ALTER TABLE `projdescitem` DISABLE KEYS */;
INSERT INTO `projdescitem` VALUES (3,0,3,'SER123','RIVER SAND','cft','45','42','1890',26),(3,0,3,'SER123','CEMENT','bag','100','250','25000',27),(3,0,3,'SER123','SEWING SAND','cft','201','250','50250',28);
/*!40000 ALTER TABLE `projdescitem` ENABLE KEYS */;
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
  `AgreementNum` varchar(50) DEFAULT NULL,
  `CERNum` varchar(30) NULL,
  `Amount` decimal(15,2) NOT NULL,
  `ContractorName` varchar(50) NOT NULL,
  `ContractorAliasName` varchar(50) NOT NULL,
  `ContractorAdd` tinytext NOT NULL,
  `AgreementValue` decimal(15,2) NOT NULL,
  `TenderValue` decimal(15,2) NOT NULL,
  `ContractorValue` decimal(15,2) NOT NULL,
  `ExcessInAmount` decimal(15,2) NOT NULL,
  `ExcessInPercentage` decimal(15,2) DEFAULT NULL,
  `LessInPercentage` decimal(15,2) DEFAULT NULL,
  `TenderDate` datetime NOT NULL,
  `AddSecurityDeposit` decimal(15,2) NOT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `AgreementDate` datetime DEFAULT NULL,
  `CommencementDate` datetime DEFAULT NULL,
  `CompletedDate` datetime DEFAULT NULL,
  `AgreementPeriod` int(10) DEFAULT NULL,
  `PerformanceGuarantee` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`ProjId`),
  UNIQUE KEY `AliasProjName_UNIQUE` (`AliasProjName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (4,'Madras Medical College','MMC','AN2123','1234CER',1000.00,'PSK','PSK','12 godown street',1500.00,1250.00,2000.00,2500.00,10.00,NULL,'2015-06-26 00:00:00',500.00,'tkumar','2015-06-26 09:44:34','2015-06-26 00:00:00','2015-06-30 00:00:00','2015-06-26 00:00:00',12,13);
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
  `SubProjId` int(10) DEFAULT NULL,
  `WorkType` varchar(30) NOT NULL,
  `QuantityInFig` decimal(15,2) DEFAULT NULL,
  `QuantityInWords` varchar(50) DEFAULT NULL,
  `Description` text NOT NULL,
  `AliasDescription` varchar(100) NOT NULL,
  `RateInFig` decimal(15,2) NOT NULL,
  `RateInWords` varchar(50) NOT NULL,
  `Amount` decimal(15,2) DEFAULT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `ProjDescId` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ProjDescId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projectdesc`
--

LOCK TABLES `projectdesc` WRITE;
/*!40000 ALTER TABLE `projectdesc` DISABLE KEYS */;
INSERT INTO `projectdesc` VALUES (4,'SER123',NULL,'Main Work',10.00,'ten','EARTH ESCAVATION','EE',1000.00,'Thousand',1000.00,'tkumar','2015-06-26 09:47:11',3);
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
  `AgreementNum` varchar(50) DEFAULT NULL,
  `CERNum` varchar(30) NULL,
  `Amount` decimal(15,2) NOT NULL,
  `ContractorName` varchar(50) NOT NULL,
  `ContractorAliasName` varchar(50) NOT NULL,
  `ContractorAdd` tinytext NOT NULL,
  `AgreementValue` decimal(15,2) NOT NULL,
  `TenderValue` decimal(15,2) NOT NULL,
  `ContractorValue` decimal(15,2) NOT NULL,
  `ExcessInAmount` decimal(15,2) NOT NULL,
  `ExcessInPercentage` decimal(15,2) DEFAULT NULL,
  `LessInPercentage` decimal(15,2) DEFAULT NULL,
  `SubAddSecurityDeposit` decimal(15,2) NOT NULL,
  `TenderDate` datetime NOT NULL,
  `AgreementDate` datetime DEFAULT NULL,
  `CommencementDate` datetime DEFAULT NULL,
  `CompletedDate` datetime DEFAULT NULL,
  `AgreementPeriod` int(10) DEFAULT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `SubPerformanceGuarantee` decimal(15,2) DEFAULT NULL,
  `ProjId` int(10) NOT NULL,
  PRIMARY KEY (`SubProjId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subproject`
--

LOCK TABLES `subproject` WRITE;
/*!40000 ALTER TABLE `subproject` DISABLE KEYS */;
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
INSERT INTO `userroles` VALUES ('akumar','ROLE_USER'),('tkumar','ROLE_USER');
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

--
-- Dumping events for database 'pms'
--

--
-- Dumping routines for database 'pms'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-07-01 20:11:16
