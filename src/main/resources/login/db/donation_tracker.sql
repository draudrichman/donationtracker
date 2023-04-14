
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
  `userID` int NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` text,
  `goalAmount` double DEFAULT NULL,
  `currentAmount` double DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `category` varchar(20) NOT NULL,
  PRIMARY KEY (`campaignID`),
  KEY `fk_campaign_userID` (`userID`),
  CONSTRAINT `fk_campaign_userID` FOREIGN KEY (`userID`) REFERENCES `userdetails` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign`
--

LOCK TABLES `campaign` WRITE;
/*!40000 ALTER TABLE `campaign` DISABLE KEYS */;
INSERT INTO `campaign` VALUES (15,5,'Raising Money for Saving Koalas','This is a critical effort to preserve one of the world\'s most iconic and beloved animals. These adorable creatures are facing extinction due to habitat loss, bushfires, and climate change. The funds raised will go towards the rescue, rehabilitation, and long-term conservation of koalas and their habitats. By supporting this cause, we can help provide essential medical care, support wildlife sanctuaries, and fund scientific research to better understand and protect these fascinating animals. Every donation can make a difference and help ensure that future generations will have the chance to appreciate the unique and valuable contributions of koalas to our planet.',100000,82500,'Active','2023-04-14 00:00:08','2023-04-14 03:55:34','Animal Wellfare'),(17,5,'Building Sustainable Homes for Low-Income Families','We\'re on a mission to build sustainable, energy-efficient homes for low-income families. Our homes will reduce energy costs and promote sustainable living, while providing families with a safe and comfortable place to live.',5000000,50000,'Active','2023-04-14 00:05:44','2023-04-14 03:13:07','Community'),(18,5,'Education for All','Our mission is to provide education to underprivileged children in rural areas who lack access to quality education. With your donation, we can provide school supplies, books, and teachers to make a lasting impact on the lives of these children and help them reach their full potential. Join us in our efforts to make education accessible to all.',40000,237000,'Active','2023-04-14 00:07:39','2023-04-14 03:35:21','Education'),(19,5,'Team Seas - Cleaning up the Ocean, One Pound at a Time','Team Seas is a collaborative effort by environmental organizations, social media influencers, and concerned individuals to clean up the ocean by removing one pound of trash at a time. The campaign aims to raise awareness about the devastating impact of plastic waste on our oceans and marine life, while also funding research and development of new technologies to combat this issue. By joining forces, Team Seas hopes to create a global movement towards a cleaner, healthier ocean for all.',10000000,335000,'Active','2023-04-14 00:08:16','2023-04-14 04:05:05','Environment'),(20,5,'Help Bring Clean Water to a Rural Village','Join us in our mission to bring clean and safe drinking water to a rural village in Africa. Lack of access to clean water is a major health issue for the people in this community, and with your help, we can make a difference. Our team has a plan to build a new well and filtration system that will provide access to clean water for generations to come.',420000,138000,'Active','2023-04-14 00:10:08','2023-04-14 06:54:18','Community');
/*!40000 ALTER TABLE `campaign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donation`
--

DROP TABLE IF EXISTS `donation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `donation` (
  `donationID` int NOT NULL AUTO_INCREMENT,
  `campaignID` int NOT NULL,
  `userID` int NOT NULL,
  `amount` double NOT NULL,
  `method` varchar(45) NOT NULL,
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`donationID`),
  KEY `fk_donation_campaign_idx` (`campaignID`),
  KEY `fk_donation_user_idx` (`userID`),
  CONSTRAINT `fk_donation_campaign` FOREIGN KEY (`campaignID`) REFERENCES `campaign` (`campaignID`),
  CONSTRAINT `fk_donation_user` FOREIGN KEY (`userID`) REFERENCES `userdetails` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donation`
--

LOCK TABLES `donation` WRITE;
/*!40000 ALTER TABLE `donation` DISABLE KEYS */;
INSERT INTO `donation` VALUES (2,15,4,5000,'Debit or Credit Card','2023-04-14 02:26:49'),(3,17,4,10000,'Debit or Credit Card','2023-04-14 02:29:27'),(4,20,4,25000,'Debit or Credit Card','2023-04-14 02:40:45'),(5,20,4,14000,'Debit or Credit Card','2023-04-14 03:03:24'),(6,20,4,2000,'Debit or Credit Card','2023-04-14 03:08:02'),(7,18,4,20000,'Debit or Credit Card','2023-04-14 03:09:02'),(8,15,5,2500,'Debit or Credit Card','2023-04-14 03:10:17'),(9,17,5,50000,'Debit or Credit Card','2023-04-14 03:13:07'),(10,20,5,25000,'Debit or Credit Card','2023-04-14 03:14:55'),(11,18,5,55000,'Debit or Credit Card','2023-04-14 03:21:02'),(12,19,5,52000,'Debit or Credit Card','2023-04-14 03:23:22'),(13,15,5,15000,'Debit or Credit Card','2023-04-14 03:25:13'),(14,19,4,15000,'Debit or Credit Card','2023-04-14 03:29:46'),(15,18,5,82000,'MFS','2023-04-14 03:33:40'),(16,18,5,80000,'Debit or Credit Card','2023-04-14 03:35:21'),(17,15,5,60000,'Debit or Credit Card','2023-04-14 03:55:34'),(18,19,5,69000,'Debit or Credit Card','2023-04-14 03:58:28'),(19,19,5,10000,'Debit or Credit Card','2023-04-14 03:59:41'),(20,19,4,51000,'Debit or Credit Card','2023-04-14 04:02:04'),(21,19,5,69000,'Debit or Credit Card','2023-04-14 04:04:06'),(22,19,5,69000,'Bank Transfer','2023-04-14 04:05:05'),(23,20,5,72000,'MFS','2023-04-14 06:54:18');
/*!40000 ALTER TABLE `donation` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userdetails`
--

LOCK TABLES `userdetails` WRITE;
/*!40000 ALTER TABLE `userdetails` DISABLE KEYS */;
INSERT INTO `userdetails` VALUES (1,'chisea','chisea@juventus.com','1234chisea','Fundraiser','Chisea',NULL,NULL,4043),(2,'havertz123','kai@chelsea.com','havertz123','Contributor','Kai Havertz',NULL,NULL,7203),(4,'aud','aud@chelsea.com','aud123','Contributor','AUD RICHMAN',NULL,NULL,7939),(5,'kante','kante@chelsea.com','kante123','Contributor','NGoloKante',NULL,NULL,1618),(6,'tuchel','tuchel@chelsea.com','tuchel123','Fundraiser','Thomas Tuchel',NULL,NULL,1408),(7,'dasdasd','asdasd','123','Contributor','sadas',NULL,NULL,4955),(8,'drdeath','butcher@supes.com','billy123','Fundraiser','Billy Butcher',NULL,NULL,6711),(9,'finaluser','finaluser@gmail.com','finaluser123','Fundraiser','Final User',NULL,NULL,7382),(10,'usertesting','usertesting@gmail.com','testing123','Fundraiser','Test User',NULL,NULL,3871);
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

-- Dump completed on 2023-04-14 14:33:30
