CREATE DATABASE  IF NOT EXISTS `donation_tracker` /*!40100 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `donation_tracker`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: donation_tracker
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `campaign`
--

DROP TABLE IF EXISTS `campaign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `campaign` (
  `campaignID` int NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `description` text,
  `goalAmount` double DEFAULT NULL,
  `currentAmount` double DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `category` varchar(20) NOT NULL,
  PRIMARY KEY (`campaignID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
INSERT INTO `campaign` VALUES (1,'Testing','This is the end of the line for you',10000,0,'Live','2023-03-31 12:45:23','2023-03-31 12:49:55','Health'),(2,'dasdasd','gsdfgdsfgsdfg',25000,0,'Active','2023-04-01 08:29:16','2023-04-01 08:29:16','Other'),(3,'Save Lions','Lions in africa',100000,0,'Active','2023-04-01 08:41:08','2023-04-01 08:41:08','Animal Wellfare'),(4,'dvsdvas','dfgdfgdf',25,0,'Active','2023-04-01 08:42:41','2023-04-01 08:42:41','Hunger & Poverty'),(5,'dfgsdfg','dfasfasd',25,0,'Active','2023-04-01 08:43:25','2023-04-01 08:43:25','Education'),(6,'255','tdhfghfdghfgh',25,0,'Active','2023-04-01 08:45:33','2023-04-01 08:45:33','Environment'),(7,'dasdasda','sdfasdfsadf',25,0,'Active','2023-04-01 08:48:09','2023-04-01 08:48:09','Funeral & Memorial'),(8,'asdasdas','fdsfsdfsdf',14,0,'Active','2023-04-01 08:51:23','2023-04-01 08:51:23','Disaster Relief'),(9,'dasdasdasd','fdsfasdfsad',123,0,'Active','2023-04-01 09:02:05','2023-04-01 09:02:05','Funeral & Memorial');
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userdetails`
--

DROP TABLE IF EXISTS `userdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `userdetails` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(45) NOT NULL,
  `userType` varchar(20) NOT NULL,
  `fullName` varchar(100) DEFAULT NULL,
  `orgName` varchar(45) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `resetCode` int DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userdetails`
--

LOCK TABLES `userdetails` WRITE;
/*!40000 ALTER TABLE `userdetails` DISABLE KEYS */;
INSERT INTO `userdetails` VALUES (1,'chisea','chisea@juventus.com','1234chisea','Fundraiser','Chisea',NULL,NULL,4043),(2,'havertz123','kai@chelsea.com','havertz123','Contributor','Kai Havertz',NULL,NULL,7203),(4,'aud','aud@chelsea.com','aud123','Contributor','AUD RICHMAN',NULL,NULL,7939),(5,'kante','kante@chelsea.com','kante123','Contributor','NGoloKante',NULL,NULL,1618),(6,'tuchel','tuchel@chelsea.com','tuchel123','Fundraiser','Thomas Tuchel',NULL,NULL,1408),(7,'dasdasd','asdasd','123','Contributor','sadas',NULL,NULL,4955),(8,'drdeath','butcher@supes.com','billy123','Fundraiser','Billy Butcher',NULL,NULL,6711);
/*!40000 ALTER TABLE `userdetails` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-01 22:32:37
