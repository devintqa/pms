CREATE DATABASE  IF NOT EXISTS `pms` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `pms`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: pms
-- ------------------------------------------------------
-- Server version	5.6.26-log

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
-- Table structure for table `basedesc`
--

DROP TABLE IF EXISTS `basedesc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `basedesc` (
  `BaseDescId` int(10) NOT NULL AUTO_INCREMENT,
  `Category` varchar(25) NOT NULL,
  `WorkType` varchar(30) NOT NULL,
  `Metric` varchar(30) NOT NULL,
  `Quantity` decimal(15,2) NOT NULL,
  `PricePerQuantity` decimal(15,2) DEFAULT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `Description` text,
  `BaseDescription` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`BaseDescId`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `basedescitem`
--

DROP TABLE IF EXISTS `basedescitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `basedescitem` (
  `BaseDescId` int(11) DEFAULT NULL,
  `ItemName` varchar(45) DEFAULT NULL,
  `ItemUnit` varchar(45) DEFAULT NULL,
  `ItemType` varchar(45) DEFAULT NULL,
  `ItemQty` varchar(45) DEFAULT '1',
  `ItemPrice` varchar(45) DEFAULT NULL,
  `DescItemId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`DescItemId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `depositdetail`
--
DROP TABLE IF EXISTS `depositdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `depositdetail` (
  `ProjId` int(10) NOT NULL,
  `SubProjId` int(10) DEFAULT NULL,
  `DepositAmount` decimal(15,2) NOT NULL,
  `DepositStartDate` datetime NOT NULL,
  `DepositEndDate` datetime NOT NULL,
  `DepositType` varchar(20) NOT NULL,
  `depositTypeNumber` varchar(20) NOT NULL,
  `DepositStatus` varchar(20) NOT NULL,
  `DepositPeriod` int(10) NOT NULL,
  `DepositExtensionDate` datetime,
  `DepositLedgerNumber` varchar(20) NOT NULL,
  `SubProjectEmd` varchar(20) DEFAULT '0',
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `DepositId` int(11) NOT NULL AUTO_INCREMENT,
  `DepositSubmitter` varchar(50) DEFAULT NULL,
  `DepositDetail` varchar(30) NOT NULL,
  `DepositRecievedDate` datetime,
   `DepositRecievedNote` text NULL,

  PRIMARY KEY (`DepositId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `empRole` varchar(50) DEFAULT NULL,
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
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('akumar','$2a$10$5ypDVRuwAlFVuIcWOrkM1OrYgLuxjsFJ1otT.Ig3QY1YJ6a4d9OTG','admin','kumar','2nd street','Male','9999999999','admin@gmail.com','admin',1,'Admin'),('tkumar','$2a$10$mykTtgpJNJbFU5AVhmnuWOjdF2Gm/ze3Se4jRl/vaqqMCZT/Erv5y','technical','kumar','2nd street','Male','9999999998','tech@gmail.com','admin',1,'Technical');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `govestpricedetail`
--

DROP TABLE IF EXISTS `govpricedetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `govpricedetail` (
  `scheduledItemNumber` varchar(20) NOT NULL,
    `itemName` varchar(100) NOT NULL,
    `itemDescription` text NOT NULL,
    `itemUnit` varchar(20) NOT NULL,
    `itemType` varchar(45) NOT NULL,
    `itemPrice` decimal(15,2) DEFAULT '0.00',
    `priceFeed` datetime NOT NULL,
    `entryId` int(11) NOT NULL AUTO_INCREMENT,
    `active` int(1) DEFAULT '1',
    PRIMARY KEY (`entryId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

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

DROP TABLE IF EXISTS `pskpricedetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pskpricedetail` (
  `itemName` varchar(100) NOT NULL,
  `itemUnit` varchar(20) NOT NULL,
  `itemType` varchar(45) NOT NULL,
  `itemPrice` decimal(15,2) DEFAULT '0.00',
  `projectId` int(11) NOT NULL DEFAULT '0',
  `subProjectId` int(11) NOT NULL DEFAULT '0',
  `priceFeed` datetime NOT NULL,
  `entryId` int(11) NOT NULL AUTO_INCREMENT,
  `active` int(1) DEFAULT '1',
  PRIMARY KEY (`entryId`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `projdescitem`
--

DROP TABLE IF EXISTS `projdescitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projdescitem` (
  `ProjId` int(11) DEFAULT '0',
  `SubProjId` int(11) DEFAULT '0',
  `ProjDescId` int(11) DEFAULT '0',
  `ProjDescSerial` varchar(45) DEFAULT NULL,
  `ItemName` varchar(45) DEFAULT NULL,
  `ItemUnit` varchar(45) DEFAULT NULL,
  `ItemQty` varchar(45) DEFAULT '0',
  `ItemPrice` varchar(45) DEFAULT '0',
  `ItemCost` varchar(45) DEFAULT '0',
  `DescItemId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`DescItemId`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `ProjId` int(10) NOT NULL AUTO_INCREMENT,
  `ProjName` text NOT NULL,
  `MainProjId` int(10) DEFAULT 0,
  `AliasProjName` varchar(50) NOT NULL,
  `AgreementNum` varchar(50) DEFAULT NULL,
  `CERNum` varchar(30) DEFAULT NULL,
  `Amount` decimal(15,2) NOT NULL,
  `ContractorName` varchar(50) NOT NULL,
  `ContractorAliasName` varchar(50) NOT NULL,
  `ContractorAdd` tinytext NOT NULL,
  `AgreementValue` decimal(15,2) NOT NULL,
  `TenderValue` decimal(15,2) NOT NULL,
  `ExcessInAmount` decimal(15,2) NOT NULL,
  `ExcessInPercentage` decimal(15,2) DEFAULT NULL,
  `LessInPercentage` decimal(15,2) DEFAULT NULL,
  `TenderDate` datetime NOT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `AgreementDate` datetime DEFAULT NULL,
  `CommencementDate` datetime DEFAULT NULL,
  `CompletedDate` datetime DEFAULT NULL,
  `AgreementPeriod` int(10) DEFAULT NULL,
  `ProjectType` varchar(30) NOT NULL,
  `CompletionDateForBonus` datetime DEFAULT NULL,
  `workoutPercentage` decimal(15,2) NOT NULL,
  `workLocation` varchar(50) NOT NULL,
  PRIMARY KEY (`ProjId`),
  UNIQUE KEY `AliasProjName_UNIQUE` (`AliasProjName`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `Quantity` decimal(15,2) NOT NULL,
  `Metric` varchar(30) NOT NULL,
  `Description` text NOT NULL,
  `AliasDescription` varchar(180) NOT NULL,
  `PricePerQuantity` decimal(15,2) DEFAULT NULL,
  `TotalCost` decimal(15,2) DEFAULT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `ProjDescId` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ProjDescId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `govprojectdesc`
--

DROP TABLE IF EXISTS `govprojectdesc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `govprojectdesc` (
  `ProjId` int(10) NOT NULL,
  `SerialNumber` varchar(10) NOT NULL,
  `SubProjId` int(10) DEFAULT NULL,
  `WorkType` varchar(30) NOT NULL,
  `Quantity` decimal(15,2) NOT NULL,
  `Metric` varchar(30) NOT NULL,
  `Description` text NOT NULL,
  `AliasDescription` varchar(180) NOT NULL,
  `PricePerQuantity` decimal(15,2) DEFAULT NULL,
  `TotalCost` decimal(15,2) DEFAULT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `ProjDescId` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ProjDescId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `CERNum` varchar(30) DEFAULT NULL,
  `Amount` decimal(15,2) NOT NULL,
  `ContractorName` varchar(50) NOT NULL,
  `ContractorAliasName` varchar(50) NOT NULL,
  `ContractorAdd` tinytext NOT NULL,
  `AgreementValue` decimal(15,2) NOT NULL,
  `TenderValue` decimal(15,2) NOT NULL,
  `ExcessInAmount` decimal(15,2) NOT NULL,
  `ExcessInPercentage` decimal(15,2) DEFAULT NULL,
  `LessInPercentage` decimal(15,2) DEFAULT NULL,
  `TenderDate` datetime NOT NULL,
  `AgreementDate` datetime DEFAULT NULL,
  `CommencementDate` datetime DEFAULT NULL,
  `CompletedDate` datetime DEFAULT NULL,
  `AgreementPeriod` int(10) DEFAULT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `ProjId` int(10) NOT NULL,
  `SubProjectType` varchar(45) NOT NULL,
  `SubCompletionDateForBonus` datetime DEFAULT NULL,
  PRIMARY KEY (`SubProjId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-08-16 18:55:49

DROP TABLE IF EXISTS `fieldprojectdesc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fieldprojectdesc` (
  `ProjId` int(10) NOT NULL,
  `SerialNumber` varchar(10) NOT NULL,
  `SubProjId` int(10) DEFAULT NULL,
  `WorkType` varchar(30) NOT NULL,
  `Quantity` decimal(15,2) NOT NULL,
  `Metric` varchar(30) NOT NULL,
  `Description` text NOT NULL,
  `AliasDescription` varchar(100) NOT NULL,
  `PricePerQuantity` decimal(15,2) DEFAULT NULL,
  `TotalCost` decimal(15,2) DEFAULT NULL,
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `ProjDescId` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ProjDescId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `projdescitem`
--

DROP TABLE IF EXISTS `fieldprojdescitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fieldprojdescitem` (
  `ProjId` int(11) DEFAULT '0',
  `SubProjId` int(11) DEFAULT '0',
  `ProjDescId` int(11) DEFAULT '0',
  `ProjDescSerial` varchar(45) DEFAULT NULL,
  `ItemName` varchar(45) DEFAULT NULL,
  `ItemUnit` varchar(45) DEFAULT NULL,
  `ItemQty` varchar(45) DEFAULT '0',
  `ItemPrice` varchar(45) DEFAULT '0',
  `ItemCost` varchar(45) DEFAULT '0',
  `DescItemId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`DescItemId`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `govprojdescitem`
--

DROP TABLE IF EXISTS `govprojdescitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `govprojdescitem` (
  `ProjId` int(11) DEFAULT '0',
  `SubProjId` int(11) DEFAULT '0',
  `ProjDescId` int(11) DEFAULT '0',
  `ProjDescSerial` varchar(45) DEFAULT NULL,
  `ItemName` varchar(45) DEFAULT NULL,
  `ItemUnit` varchar(45) DEFAULT NULL,
  `ItemQty` varchar(45) DEFAULT '0',
  `ItemPrice` varchar(45) DEFAULT '0',
  `ItemCost` varchar(45) DEFAULT '0',
  `DescItemId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`DescItemId`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `teamRole`
--

DROP TABLE IF EXISTS `teamRole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `teamRole` (
  `teamRoleId` int(11) NOT NULL AUTO_INCREMENT,
  `teamName` varchar(100) NOT NULL,
  `roleName` varchar(100) NOT NULL,
  PRIMARY KEY (`teamRoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `authoriseProject`
--

DROP TABLE IF EXISTS `authoriseProject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authoriseProject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectId` varchar(100) NOT NULL,
  `teamName` varchar(100) NOT NULL,
  `empId` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;




--
-- Table structure for table `pmsmastertable`
--

DROP TABLE IF EXISTS `pmsmastertable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pmsmastertable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Type` varchar(100) NOT NULL,
  `Value` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `storeDetail`
--

DROP TABLE IF EXISTS `storeDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storeDetail` (
  `storeDetailId` int(10) NOT NULL AUTO_INCREMENT,
  `ProjId` int(10) NOT NULL,
  `itemType` varchar(10) NOT NULL,
  `itemName` varchar(30) NOT NULL,
  `supplierName` varchar(30) NOT NULL,
  `vehicleNo` varchar(30) NOT NULL,
  `quantityRecieved` varchar(30) NOT NULL,
  `recievedDate` datetime NOT NULL,
  `comments` varchar(200) NOT NULL,
  PRIMARY KEY (`storeDetailId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Dumping data for table `pmsmastertable`
--

LOCK TABLES `pmsmastertable` WRITE;
/*!40000 ALTER TABLE `pmsmastertable` DISABLE KEYS */;


INSERT INTO `pmsmastertable` (Type,Value) VALUES('team','Admin')
,('team','Account')
,('team','Management')
,('team','Purchase')
,('team','Technical')
,('team','Store')
,('team','Field')
,('projectType','PWD')
,('projectType','CPWD')
,('projectType','MRPL')
,('projectType','Private')
,('projectType','NPCC')
,('projectType','NBCC')
,('projectType','DAE')
,('projectType','NIT')
,('depositDetail','EMD')
,('depositDetail','ASD')
,('depositDetail','WHA')
,('depositDetail','SD')
,('depositDetail','PG')
,('depositStatus','Submitted')
,('depositStatus','Refunded')
,('metric','CUM')
,('metric','SQM')
,('metric','NOS')
,('metric','NO')
,('metric','RM')
,('metric','KG')
,('metric','QTL')
,('metric','MT')
,('depositType','Bank Guarantee')
,('depositType','DD')
,('depositType','FD')
,('depositType','IVP')
,('depositType','KVP')
,('itemType','Material')
,('itemType','Labour')
,('itemType','Machinery')
,('itemType','Other')
,('workLocation','Corporation')
,('workLocation','Rural');

/*!40000 ALTER TABLE `pmsmastertable` ENABLE KEYS */;
UNLOCK TABLES;
     -- -- -- --
-- Table structure for table `projectLeadDetail`
--
DROP TABLE IF EXISTS `projectLeadDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projectLeadDetail` (
  `projectId` int(11) NOT NULL default '0',
  `subProjectId` int(11) NOT NULL default '0',
  `leadDetailId` int(10) NOT NULL AUTO_INCREMENT,
  `material` varchar(100) NOT NULL,
  `sourceOfSupply` varchar(100) NOT NULL,
  `distance` decimal(15,2) default '0.0',
  `unit` varchar(30) NOT NULL,
  `cost` decimal(15,2) default '0.0',
  `ic` decimal(15,2) default '0.0',
  `leadCharges` decimal(15,2) default '0.0',
  `loadingUnloadingCharges` decimal(15,2) default '0.0',
  `total` decimal(15,2) default '0.0',
  `LastUpdatedBy` varchar(30) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `active` int(1) DEFAULT '1',
  primary key (`leadDetailId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `indent`
--

DROP TABLE IF EXISTS `indent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `indent` (
  `IndentId` int(11) NOT NULL AUTO_INCREMENT,
  `ProjId` int(10) NOT NULL,
  `StartDate` varchar(45) NOT NULL,
  `EndDate` varchar(45) NOT NULL,
  `LastUpdatedBy` varchar(45) NOT NULL,
  `LastUpdatedAt` datetime NOT NULL,
  `Status` varchar(64) NOT NULL DEFAULT 'NEW',
  PRIMARY KEY (`IndentId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Table structure for table `indentdesc`
--

DROP TABLE IF EXISTS `indentdesc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `indentdesc` (
  `IndentId` int(10) NOT NULL,
  `ProjDescId` int(10) NOT NULL,
  `Quantity` decimal(15,2) NOT NULL,
  `Metric` varchar(30) NOT NULL,
  `Comments` varchar(200) DEFAULT ' ',
  `IndentDescId` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IndentDescId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Table structure for table `indentdescitem`
--

DROP TABLE IF EXISTS `indentdescitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `indentdescitem` (
  `IndentDescId` int(11) DEFAULT '0',
  `ItemName` varchar(45) DEFAULT NULL,
  `ItemType` varchar(45) DEFAULT NULL,
  `ItemQty` varchar(45) DEFAULT '0',
  `ItemUnit` varchar(45) DEFAULT NULL,
  `IndentItemId` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IndentItemId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
--
-- Table structure for table `stockDetail`
--

DROP TABLE IF EXISTS `stockDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stockDetail` (
    `stockDetailId` int(11) NOT NULL AUTO_INCREMENT,
    `projectId` int(10) NOT NULL,
    `itemName` varchar(20) NOT NULL,
	`totalQuantity` varchar(20) NOT NULL,
    PRIMARY KEY (`stockDetailId`,`projectId`,`itemName`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `dispatchdetail`
--

DROP TABLE IF EXISTS `dispatchdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dispatchdetail` (
    `projectId` int(10) NOT NULL,
  `itemName` varchar(100) NOT NULL,
  `dispactchedDate` datetime NOT NULL,
  `fieldUser` varchar(100) NOT NULL,
  `quantity` varchar(100) NOT NULL,
  `dispatchId` int(11) NOT NULL AUTO_INCREMENT,
	`dispatchDesc` varchar(100) NOT NULL,
  PRIMARY KEY (`dispatchId`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



DROP TRIGGER IF EXISTS `SYNC_PROJECT_DESCRIPTION`;
DELIMITER $$
CREATE TRIGGER SYNC_PROJECT_DESCRIPTION AFTER DELETE on govprojectdesc
	FOR EACH ROW
		BEGIN
			DELETE FROM projectdesc WHERE (projectdesc.ProjId = old.ProjId) 
				AND 
				(projectdesc.Description = old.Description)
                AND
				(projectdesc.AliasDescription = old.AliasDescription);
END$$


