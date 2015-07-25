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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emddetail`
--

LOCK TABLES `emddetail` WRITE;
/*!40000 ALTER TABLE `emddetail` DISABLE KEYS */;
INSERT INTO `emddetail` VALUES (1,NULL,1200.00,'2015-07-11 00:00:00','2015-07-15 00:00:00','Bank Guarantee','18271278238',24,'2015-07-21 00:00:00','1020','0','tkumar','2015-07-04 15:13:22',1,'PSK');
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
  `itemNo` int(11) NOT NULL AUTO_INCREMENT,
  `itemType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`itemNo`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `itemcodes`
--

LOCK TABLES `itemcodes` WRITE;
/*!40000 ALTER TABLE `itemcodes` DISABLE KEYS */;
INSERT INTO `itemcodes` VALUES ('CEMENT','Bag',1,'MATERIAL'),('RIVER SAND','Bag',2,'MATERIAL'),('SEWING SAND','Bag',3,'MATERIAL'),('BRICKS','Bag',4,'MATERIAL'),('STEEL','Bag',5,'MATERIAL'),('BINDING WIRE','Bag',6,'MATERIAL'),('40 MM  METAL','Bag',7,'MATERIAL'),('20 MM METAL','Bag',8,'MATERIAL'),('CHIPS','Bag',9,'MATERIAL'),('EARTH FROM OUTSIDE','Bag',10,'MATERIAL'),('GRAVEL','Bag',11,'MATERIAL'),('QUARRY DUST','Bag',12,'MATERIAL'),('BOULDERS','Bag',13,'MATERIAL'),('WATER STOPPER','Bag',14,'MATERIAL'),('EXTRA FOR STAGEING 4.25 TO 6.5 M','Bag',15,'MATERIAL'),('EXTRA FOR STAGEING 4.25 TO 8.5 M','Bag',16,'MATERIAL'),('STRUCTURAL STEEL','Bag',17,'MATERIAL'),(' RR MASONARY','Bag',18,'MATERIAL'),('SOLID BLOCK 200 MM TK.','Bag',19,'MATERIAL'),('SOLID BLOCK 150 MM TK.','Bag',20,'MATERIAL'),('SOLID BLOCK 100 MM TK.','Bag',21,'MATERIAL'),('RMC M10','Bag',22,'MATERIAL'),('RMC M15','Bag',23,'MATERIAL'),('RMC M20','Bag',24,'MATERIAL'),('RMC M25','Bag',25,'MATERIAL'),('RMC M30','Bag',26,'MATERIAL'),('RMC M35','Bag',27,'MATERIAL'),('RMC M 40','Bag',28,'MATERIAL'),('ANTI-TERMINATE','Bag',29,'MATERIAL'),('FLY ASH CONC. BLOCK','Bag',30,'MATERIAL'),('ARPHITA MESH','Bag',31,'MATERIAL'),('CENTERING MATERIAL COST','Bag',32,'MATERIAL'),('COLUMN CENTERING MATRIAL COST','Bag',33,'MATERIAL'),('COLUMN CENTERING MATRIAL COST','Bag',34,'MATERIAL'),('CENTERING MATERIAL COST','Bag',35,'MATERIAL'),('AEROCON BLOCK','Bag',36,'MATERIAL'),('EROCON. BLOCK WORK IN C.M 1:6  ','Bag',37,'MATERIAL'),('FLY ASH','Bag',38,'MATERIAL'),('WATER PROOFING COMPOUND','Bag',39,'MATERIAL'),('MESH','Bag',40,'MATERIAL'),('WATERPROOFING MATERIAL & LABOUR (CICO)','Bag',41,'MATERIAL');
/*!40000 ALTER TABLE `itemcodes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pricedetail`
--

DROP TABLE IF EXISTS `pricedetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pricedetail` (
  `itemName` varchar(100) NOT NULL,
  `itemUnit` varchar(20) NOT NULL,
  `itemType` varchar(45) NOT NULL,
  `itemPrice` decimal(15,2) DEFAULT '0.00',
  `projectId` int(11) NOT NULL DEFAULT '0',
  `subProjectId` int(11) NOT NULL DEFAULT '0',
  `priceFeed` datetime NOT NULL,
  `entryId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`entryId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pricedetail`
--

LOCK TABLES `pricedetail` WRITE;
/*!40000 ALTER TABLE `pricedetail` DISABLE KEYS */;
INSERT INTO `pricedetail` VALUES ('CEMENT','BAG','MATERIAL',450.00,1,0,'2015-07-18 10:03:38',1),('RIVER SAND','BAG','MATERIAL',100.00,1,0,'2015-07-18 10:03:38',2),('SEWER SAND','BAG','MATERIAL',200.00,1,0,'2015-07-18 10:03:38',3),('MASON','1','LABOUR',200.00,1,0,'2015-07-18 10:03:38',4),('BINDING WIRE','Bag','Bag',560.00,1,0,'2015-07-20 00:00:00',5);
/*!40000 ALTER TABLE `pricedetail` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projdescitem`
--

LOCK TABLES `projdescitem` WRITE;
/*!40000 ALTER TABLE `projdescitem` DISABLE KEYS */;
INSERT INTO `projdescitem` VALUES (1,0,1,'SNO1','CEMENT','bag','200','500','100000',1),(1,0,1,'SNO1','RIVER SAND','cft','1000','100','100000',2),(1,0,1,'SNO1','SEWING SAND','cft','2000','200','400000',3),(1,0,2,'SER234','CEMENT','bag','122','301','36722',11),(1,0,4,'SER123','RIVER SAND','BAG','5','100.00','15',18),(1,0,4,'SER123','SEWER SAND','BAG','2','200.00','400',19),(1,0,4,'SER123','MASON','1','6','200.00','1200',20),(1,0,4,'SER123','CEMENT','BAG','5','450.00','2250',21);
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
  `CERNum` varchar(30) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'Madras Medical College','MMC','AGRN1234','CER1234',100000.00,'PSK','PSK','10 central street',150000.00,125000.00,200000.00,25000.00,NULL,10.00,'2015-07-03 00:00:00',100000.00,'tkumar','2015-07-03 23:03:53','2015-07-28 00:00:00','2015-07-29 00:00:00','2015-07-30 00:00:00',12,25000.00),(2,'Better Homes','BHG','AGRN1234','CER1234',100000.00,'AJ COMPANY','AJ','velachery',150000.00,125000.00,200000.00,25000.00,25.00,NULL,'2015-07-03 00:00:00',100000.00,'tkumar','2015-07-21 19:48:49','2015-07-21 00:00:00','2015-07-29 00:00:00','2015-07-30 00:00:00',12,25000.00);
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
  `QuantityInUnit` varchar(50) DEFAULT NULL,
  `Description` text NOT NULL,
  `AliasDescription` varchar(100) NOT NULL,
  `RateInFig` decimal(15,2) DEFAULT NULL,
  `Amount` decimal(15,2) DEFAULT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `ProjDescId` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ProjDescId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projectdesc`
--

LOCK TABLES `projectdesc` WRITE;
/*!40000 ALTER TABLE `projectdesc` DISABLE KEYS */;
INSERT INTO `projectdesc` VALUES (1,'SER234',NULL,'Main Work',12000.00,'twelve thousand','Earth excavation','EE2',12000.00,'twelve thousand',25000.00,'tkumar','2015-07-03 23:39:59',2),(1,'SER123',NULL,'Main Work',12000.00,'twelve thousand','Earth Excavation','EE3',12000.00,'twelve thousand',25000.00,'tkumar','2015-07-08 22:21:55',4);
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
  `CERNum` varchar(30) NOT NULL,
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

--
-- Table structure for table `itemtype`
--

DROP TABLE IF EXISTS `itemtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `itemtype` (
  `itemId` int(10) NOT NULL AUTO_INCREMENT primary key,
  `itemTypeName` varchar(20) NOT NULL)
ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping events for database 'pms'
--

LOCK TABLES `users` WRITE;
insert into itemtype values (1,'Material');
insert into itemtype values (2,'Labour');
insert into itemtype values (3,'Machinery');
insert into itemtype values (4,'Other');
UNLOCK TABLES;


--
-- Table structure for table `itemtype`
--

DROP TABLE IF EXISTS `emdtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emdtype` (
  `emdTypeId` int(10) NOT NULL AUTO_INCREMENT primary key,
  `emdTypeName` varchar(20) NOT NULL)
ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping data into emdtype table
--

LOCK TABLES `users` WRITE;
insert into emdtype values (1,'Bank Guarantee');
insert into emdtype values (2,'DD');
insert into emdtype values (3,'FD');
insert into emdtype values (4,'IVP');
insert into emdtype values (5,'KVP');
UNLOCK TABLES;

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

-- Dump completed on 2015-07-21 21:33:47
