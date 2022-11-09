-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3307
-- Generation Time: Nov 09, 2022 at 03:28 AM
-- Server version: 10.4.22-MariaDB
-- PHP Version: 8.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gogrocery`
--

-- --------------------------------------------------------

--
-- Table structure for table `processed food`
--

CREATE TABLE `processed food` (
  `FoodId` varchar(50) NOT NULL,
  `FoodName` varchar(50) NOT NULL,
  `FoodPrice` int(11) NOT NULL,
  `ExpiredDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `processed food`
--

INSERT INTO `processed food` (`FoodId`, `FoodName`, `FoodPrice`, `ExpiredDate`) VALUES
('PF001', 'Spam', 50000, '2022-11-10'),
('PF002', 'Cheddar cheese', 40000, '2022-11-24'),
('PF003', 'Star Cereal', 20000, '2022-12-23');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `TransactionID` varchar(50) NOT NULL,
  `GroceryId` varchar(50) NOT NULL,
  `Quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`TransactionID`, `GroceryId`, `Quantity`) VALUES
('TR944', 'PF003', 10);

-- --------------------------------------------------------

--
-- Table structure for table `unprocessed food`
--

CREATE TABLE `unprocessed food` (
  `FoodId` varchar(50) NOT NULL,
  `FoodName` varchar(50) NOT NULL,
  `FoodPrice` int(11) NOT NULL,
  `FoodWeight` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `unprocessed food`
--

INSERT INTO `unprocessed food` (`FoodId`, `FoodName`, `FoodPrice`, `FoodWeight`) VALUES
('FF001', 'Californian Beef', 80000, 1),
('FF002', 'Local Tomatoes', 30000, 1),
('FF003', 'Egg', 12000, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `processed food`
--
ALTER TABLE `processed food`
  ADD PRIMARY KEY (`FoodId`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`TransactionID`);

--
-- Indexes for table `unprocessed food`
--
ALTER TABLE `unprocessed food`
  ADD PRIMARY KEY (`FoodId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
