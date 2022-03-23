-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 23, 2022 at 09:20 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `conconvhealth`
--

-- --------------------------------------------------------

--
-- Table structure for table `medication`
--

CREATE TABLE `medication` (
  `ID_med` int(11) NOT NULL,
  `NAME_med` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `medication`
--

INSERT INTO `medication` (`ID_med`, `NAME_med`) VALUES
(1, 'Bavencio'),
(8, 'Doliprane'),
(4, 'Focalin'),
(2, 'Methadone'),
(3, 'Tazorac'),
(5, 'Valium'),
(7, 'Vitamin C'),
(6, 'Vitamin E');

-- --------------------------------------------------------

--
-- Table structure for table `medication_list`
--

CREATE TABLE `medication_list` (
  `ID_pat` int(11) NOT NULL,
  `ID_med` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `dosing_times` int(11) NOT NULL,
  `next_taking` datetime DEFAULT NULL,
  `last_taking` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `medication_list`
--

INSERT INTO `medication_list` (`ID_pat`, `ID_med`, `qty`, `dosing_times`, `next_taking`, `last_taking`) VALUES
(2, 7, 2, 4, '2022-03-23 04:05:22', '2022-03-23 00:05:22'),
(2, 5, 1, 1, '2022-03-23 13:35:30', '2022-03-23 13:34:30'),
(3, 6, 2, 4, '2022-03-19 00:00:00', NULL),
(3, 3, 1, 2, '2022-03-19 00:00:00', NULL),
(2, 3, 1, 24, '2022-03-24 11:54:59', '2022-03-23 11:54:59');

-- --------------------------------------------------------

--
-- Table structure for table `user_category`
--

CREATE TABLE `user_category` (
  `ID_cat` int(11) NOT NULL,
  `NAME_cat` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_category`
--

INSERT INTO `user_category` (`ID_cat`, `NAME_cat`) VALUES
(1, 'Caregiver'),
(2, 'Patient');

-- --------------------------------------------------------

--
-- Table structure for table `user_information`
--

CREATE TABLE `user_information` (
  `ID_user` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `CAT_user` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user_information`
--

INSERT INTO `user_information` (`ID_user`, `username`, `password`, `CAT_user`) VALUES
(1, 'admin', 'admin', 1),
(2, 'John Doe', 'doedoe', 2),
(3, 'Sandra Smith', 'sandra', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `medication`
--
ALTER TABLE `medication`
  ADD PRIMARY KEY (`ID_med`),
  ADD UNIQUE KEY `NAME_med` (`NAME_med`);

--
-- Indexes for table `medication_list`
--
ALTER TABLE `medication_list`
  ADD KEY `med_fk` (`ID_med`),
  ADD KEY `patcat` (`ID_pat`);

--
-- Indexes for table `user_category`
--
ALTER TABLE `user_category`
  ADD PRIMARY KEY (`ID_cat`);

--
-- Indexes for table `user_information`
--
ALTER TABLE `user_information`
  ADD PRIMARY KEY (`ID_user`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `cat_fk` (`CAT_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `medication`
--
ALTER TABLE `medication`
  MODIFY `ID_med` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `user_category`
--
ALTER TABLE `user_category`
  MODIFY `ID_cat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `user_information`
--
ALTER TABLE `user_information`
  MODIFY `ID_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `medication_list`
--
ALTER TABLE `medication_list`
  ADD CONSTRAINT `med_fk` FOREIGN KEY (`ID_med`) REFERENCES `medication` (`ID_med`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `pat` FOREIGN KEY (`ID_pat`) REFERENCES `user_information` (`ID_user`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `user_information`
--
ALTER TABLE `user_information`
  ADD CONSTRAINT `cat_fk` FOREIGN KEY (`CAT_user`) REFERENCES `user_category` (`ID_cat`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
