-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3308
-- Generation Time: Apr 24, 2023 at 02:42 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `donation_tracker`
--

-- --------------------------------------------------------

--
-- Table structure for table `campaign`
--

CREATE TABLE `campaign` (
  `campaignID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` text DEFAULT NULL,
  `goalAmount` double DEFAULT NULL,
  `currentAmount` double DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `createdAt` timestamp NULL DEFAULT current_timestamp(),
  `updatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `category` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `campaign`
--

INSERT INTO `campaign` (`campaignID`, `userID`, `title`, `description`, `goalAmount`, `currentAmount`, `status`, `createdAt`, `updatedAt`, `category`) VALUES
(15, 5, 'Raising Money for Saving Koalas', 'This is a critical effort to preserve one of the world\'s most iconic and beloved animals. These adorable creatures are facing extinction due to habitat loss, bushfires, and climate change. The funds raised will go towards the rescue, rehabilitation, and long-term conservation of koalas and their habitats. By supporting this cause, we can help provide essential medical care, support wildlife sanctuaries, and fund scientific research to better understand and protect these fascinating animals. Every donation can make a difference and help ensure that future generations will have the chance to appreciate the unique and valuable contributions of koalas to our planet.', 100000, 95000, 'Active', '2023-04-14 00:00:08', '2023-04-20 06:41:13', 'Animal Wellfare'),
(17, 5, 'Building Sustainable Homes for Low-Income Families', 'We\'re on a mission to build sustainable, energy-efficient homes for low-income families. Our homes will reduce energy costs and promote sustainable living, while providing families with a safe and comfortable place to live.', 5000000, 160000, 'Active', '2023-04-14 00:05:44', '2023-04-20 06:47:30', 'Community'),
(18, 5, 'Education for All', 'Our mission is to provide education to underprivileged children in rural areas who lack access to quality education. With your donation, we can provide school supplies, books, and teachers to make a lasting impact on the lives of these children and help them reach their full potential. Join us in our efforts to make education accessible to all.', 40000, 303000, 'Suspended', '2023-04-14 00:07:39', '2023-04-20 02:20:40', 'Education'),
(19, 5, 'Team Seas - Cleaning up the Ocean, One Pound at a Time', 'Team Seas is a collaborative effort by environmental organizations, social media influencers, and concerned individuals to clean up the ocean by removing one pound of trash at a time. The campaign aims to raise awareness about the devastating impact of plastic waste on our oceans and marine life, while also funding research and development of new technologies to combat this issue. By joining forces, Team Seas hopes to create a global movement towards a cleaner, healthier ocean for all.', 10000000, 335000, 'Active', '2023-04-14 00:08:16', '2023-04-21 00:32:08', 'Environment'),
(20, 5, 'Help Bring Clean Water to a Rural Village', 'Join us in our mission to bring clean and safe drinking water to a rural village in Africa. Lack of access to clean water is a major health issue for the people in this community, and with your help, we can make a difference. Our team has a plan to build a new well and filtration system that will provide access to clean water for generations to come.', 420000, 207000, 'Active', '2023-04-14 00:10:08', '2023-04-17 03:18:48', 'Community'),
(21, 4, 'Curing Cancer', 'we want to cure cancer', 50000, 7200, 'Active', '2023-04-16 00:33:01', '2023-04-20 02:09:54', 'Medical');

-- --------------------------------------------------------

--
-- Table structure for table `donation`
--

CREATE TABLE `donation` (
  `donationID` int(11) NOT NULL,
  `campaignID` int(11) NOT NULL,
  `userID` int(11) NOT NULL,
  `amount` double NOT NULL,
  `method` varchar(45) NOT NULL,
  `createdAt` timestamp NOT NULL DEFAULT current_timestamp(),
  `paymentStatus` varchar(255) DEFAULT 'Completed'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `donation`
--

INSERT INTO `donation` (`donationID`, `campaignID`, `userID`, `amount`, `method`, `createdAt`, `paymentStatus`) VALUES
(2, 15, 4, 5000, 'Debit or Credit Card', '2023-04-14 02:26:49', 'Refunded'),
(3, 17, 4, 10000, 'Debit or Credit Card', '2023-04-14 02:29:27', 'Refunded'),
(4, 20, 4, 25000, 'Debit or Credit Card', '2023-04-14 02:40:45', 'Completed'),
(5, 20, 4, 14000, 'Debit or Credit Card', '2023-04-14 03:03:24', 'Completed'),
(6, 20, 4, 2000, 'Debit or Credit Card', '2023-04-14 03:08:02', 'Completed'),
(7, 18, 4, 20000, 'Debit or Credit Card', '2023-04-14 03:09:02', 'Completed'),
(8, 15, 5, 2500, 'Debit or Credit Card', '2023-04-14 03:10:17', 'Completed'),
(9, 17, 5, 50000, 'Debit or Credit Card', '2023-04-14 03:13:07', 'Completed'),
(10, 20, 5, 25000, 'Debit or Credit Card', '2023-04-14 03:14:55', 'Completed'),
(11, 18, 5, 55000, 'Debit or Credit Card', '2023-04-14 03:21:02', 'Completed'),
(12, 19, 5, 52000, 'Debit or Credit Card', '2023-04-14 03:23:22', 'Completed'),
(13, 15, 5, 15000, 'Debit or Credit Card', '2023-04-14 03:25:13', 'Completed'),
(14, 19, 4, 15000, 'Debit or Credit Card', '2023-04-14 03:29:46', 'Completed'),
(15, 18, 5, 82000, 'MFS', '2023-04-14 03:33:40', 'Completed'),
(16, 18, 5, 80000, 'Debit or Credit Card', '2023-04-14 03:35:21', 'Completed'),
(17, 15, 5, 60000, 'Debit or Credit Card', '2023-04-14 03:55:34', 'Completed'),
(18, 19, 5, 69000, 'Debit or Credit Card', '2023-04-14 03:58:28', 'Completed'),
(19, 19, 5, 10000, 'Debit or Credit Card', '2023-04-14 03:59:41', 'Completed'),
(20, 19, 4, 51000, 'Debit or Credit Card', '2023-04-14 04:02:04', 'Completed'),
(21, 19, 5, 69000, 'Debit or Credit Card', '2023-04-14 04:04:06', 'Completed'),
(22, 19, 5, 69000, 'Bank Transfer', '2023-04-14 04:05:05', 'Completed'),
(23, 20, 5, 72000, 'MFS', '2023-04-14 06:54:18', 'Completed'),
(24, 15, 4, 5000, 'Debit or Credit Card', '2023-04-14 08:53:23', 'Completed'),
(25, 15, 4, 5000, 'Debit or Credit Card', '2023-04-14 09:00:23', 'Completed'),
(26, 18, 5, 58000, 'Debit or Credit Card', '2023-04-15 22:52:38', 'Completed'),
(27, 18, 5, 8000, 'Debit or Credit Card', '2023-04-15 23:28:34', 'Completed'),
(28, 17, 5, 110000, 'MFS', '2023-04-15 23:28:59', 'Completed'),
(29, 21, 4, 7200, 'Debit or Credit Card', '2023-04-16 00:33:17', 'Completed'),
(31, 20, 4, 69000, 'Debit or Credit Card', '2023-04-17 03:18:48', 'Completed'),
(32, 15, 5, 5000, 'Debit or Credit Card', '2023-04-17 10:05:20', 'Completed'),
(33, 15, 4, 2500, 'Debit or Credit Card', '2023-04-20 06:36:45', 'Refunded');

-- --------------------------------------------------------

--
-- Table structure for table `userdetails`
--

CREATE TABLE `userdetails` (
  `userID` int(11) NOT NULL,
  `userName` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(45) NOT NULL,
  `userType` varchar(20) NOT NULL,
  `fullName` varchar(100) DEFAULT NULL,
  `orgName` varchar(45) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `resetCode` int(11) DEFAULT NULL,
  `aboutMe` varchar(255) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `userdetails`
--

INSERT INTO `userdetails` (`userID`, `userName`, `email`, `password`, `userType`, `fullName`, `orgName`, `address`, `resetCode`, `aboutMe`, `phone`, `image`) VALUES
(1, 'chisea', 'chisea@juventus.com', '1234chisea', 'Fundraiser', 'Chisea', NULL, NULL, 4043, '', '0', ''),
(2, 'havertz123', 'kai@chelsea.com', 'havertz123', 'Contributor', 'Kai Havertz', NULL, NULL, 7203, '', '0', ''),
(4, 'aud', 'aud123@gmail.com', 'aud123', 'Contributor', 'AUD', 'givingTree', 'dhaka, bangladesh', 7939, 'New Bio Tesing 1, 2, 3...', '01234567890', ''),
(5, 'kante', 'kante@chelsea.com', 'kante123', 'Contributor', 'NGoloKante', NULL, NULL, 1618, '', '0', ''),
(6, 'tuchel', 'tuchel@chelsea.com', 'tuchel123', 'Fundraiser', 'Thomas Tuchel', NULL, NULL, 1408, '', '0', ''),
(7, 'dasdasd', 'asdasd', '123', 'Contributor', 'sadas', NULL, NULL, 4955, '', '0', ''),
(8, 'drdeath', 'butcher@supes.com', 'billy123', 'Fundraiser', 'Billy Butcher', NULL, NULL, 6711, '', '0', ''),
(9, 'finaluser', 'finaluser@gmail.com', 'finaluser123', 'Fundraiser', 'Final User', NULL, NULL, 7382, '', '0', ''),
(10, 'usertesting', 'usertesting@gmail.com', 'testing123', 'Fundraiser', 'Test User', NULL, NULL, 3871, '', '0', ''),
(11, 'chisea1', 'chisea@italy.com', 'chisea123', 'FundRaiser', 'Chisea Italy', NULL, NULL, NULL, NULL, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `campaign`
--
ALTER TABLE `campaign`
  ADD PRIMARY KEY (`campaignID`),
  ADD KEY `fk_campaign_userID` (`userID`);

--
-- Indexes for table `donation`
--
ALTER TABLE `donation`
  ADD PRIMARY KEY (`donationID`),
  ADD KEY `fk_donation_campaign_idx` (`campaignID`),
  ADD KEY `fk_donation_user_idx` (`userID`);

--
-- Indexes for table `userdetails`
--
ALTER TABLE `userdetails`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `campaign`
--
ALTER TABLE `campaign`
  MODIFY `campaignID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `donation`
--
ALTER TABLE `donation`
  MODIFY `donationID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `userdetails`
--
ALTER TABLE `userdetails`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `campaign`
--
ALTER TABLE `campaign`
  ADD CONSTRAINT `fk_campaign_userID` FOREIGN KEY (`userID`) REFERENCES `userdetails` (`userID`);

--
-- Constraints for table `donation`
--
ALTER TABLE `donation`
  ADD CONSTRAINT `fk_donation_campaign` FOREIGN KEY (`campaignID`) REFERENCES `campaign` (`campaignID`),
  ADD CONSTRAINT `fk_donation_user` FOREIGN KEY (`userID`) REFERENCES `userdetails` (`userID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
