-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 13, 2024 at 04:45 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_travel`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `id` int(11) NOT NULL,
  `journey_id` int(11) DEFAULT NULL,
  `customer_name` varchar(100) DEFAULT NULL,
  `customer_email` varchar(100) DEFAULT NULL,
  `tickets_booked` int(11) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `sale_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`id`, `journey_id`, `customer_name`, `customer_email`, `tickets_booked`, `total_price`, `sale_date`) VALUES
(1, 1, 'deni', 'deni@gmail.com', 20, 10000000, '2024-07-07'),
(2, 1, 'deni', 'deni23@gmail.com', 6, 3000000, '2024-07-07'),
(3, 2, 'daniell', 'daniel@gmail.com', 20, 4000000, '2024-07-07'),
(4, 2, 'rena', 'rena@gmail.com', 5, 1000000, '2024-07-07');

-- --------------------------------------------------------

--
-- Table structure for table `journeys`
--

CREATE TABLE `journeys` (
  `id` int(11) NOT NULL,
  `route` varchar(100) NOT NULL,
  `schedule` varchar(100) NOT NULL,
  `price` double NOT NULL,
  `available_tickets` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `journeys`
--

INSERT INTO `journeys` (`id`, `route`, `schedule`, `price`, `available_tickets`) VALUES
(1, 'Jakarta - Surabaya', '2023-12-01 10:00', 500000, 4),
(2, 'Jakarta - Bandung', '2023-12-01 12:00', 200000, 5),
(3, 'Jakarta - Yogyakarta', '2023-12-02 08:00', 400000, 40),
(4, 'Semarang - Jogja', '2024-12-01 10.00', 50, 20),
(8, 'Semarang - Jogja', '2024-12-01 10.00', 50000, 10);

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `id` int(11) NOT NULL,
  `journey_id` int(11) DEFAULT NULL,
  `sale_date` date DEFAULT NULL,
  `tickets_sold` int(11) DEFAULT NULL,
  `total_price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`id`, `journey_id`, `sale_date`, `tickets_sold`, `total_price`) VALUES
(1, 1, '2023-12-01', 5, 2500000),
(2, 2, '2023-12-01', 3, 600000),
(3, 3, '2023-12-02', 4, 1600000);

-- --------------------------------------------------------

--
-- Table structure for table `travel`
--

CREATE TABLE `travel` (
  `id` int(11) NOT NULL,
  `origin` varchar(100) NOT NULL,
  `destination` varchar(100) NOT NULL,
  `schedule` varchar(100) NOT NULL,
  `price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `travel`
--

INSERT INTO `travel` (`id`, `origin`, `destination`, `schedule`, `price`) VALUES
(2, 'CityC', 'CityD', '2024-07-02 09:00:00', 150),
(3, 'CityE', 'CityF', '2024-07-03 10:00:00', 200);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'admin', 'adminpassword', 'admin'),
(2, 'user1', 'user1password', 'user'),
(3, 'user2', 'user2password', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `journey_id` (`journey_id`);

--
-- Indexes for table `journeys`
--
ALTER TABLE `journeys`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`id`),
  ADD KEY `journey_id` (`journey_id`);

--
-- Indexes for table `travel`
--
ALTER TABLE `travel`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookings`
--
ALTER TABLE `bookings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `journeys`
--
ALTER TABLE `journeys`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `sales`
--
ALTER TABLE `sales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `travel`
--
ALTER TABLE `travel`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `bookings_ibfk_1` FOREIGN KEY (`journey_id`) REFERENCES `journeys` (`id`);

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`journey_id`) REFERENCES `journeys` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
