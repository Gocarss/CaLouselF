-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 14, 2024 at 05:54 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `calouself`
--

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `item_id` varchar(255) NOT NULL,
  `item_name` varchar(255) NOT NULL,
  `item_category` varchar(255) NOT NULL,
  `item_size` varchar(255) NOT NULL,
  `item_price` double NOT NULL,
  `item_status` enum('pending','approved','declined','sold') NOT NULL,
  `seller_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`item_id`, `item_name`, `item_category`, `item_size`, `item_price`, `item_status`, `seller_id`) VALUES
('1', 'Saw III', 'Movies', 'SA', 40, 'sold', 'SE6blIbcj'),
('3', 'Jordan One', 'Footwear', '32', 120, 'approved', 'SE6blIbcj'),
('4', 'Nike short', 'Pants', 'M', 45, 'sold', 'SE6blIbcj'),
('ITCW2mrc6', 'NEW! Supreme Shirt', 'Shirt', 'L', 200, 'approved', 'SE6blIbcj');

-- --------------------------------------------------------

--
-- Table structure for table `offer`
--

CREATE TABLE `offer` (
  `offer_id` varchar(255) NOT NULL,
  `item_id` varchar(255) NOT NULL,
  `buyer_id` varchar(255) NOT NULL,
  `offer_price` double NOT NULL,
  `offer_status` enum('accept','decline','pending') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `offer`
--

INSERT INTO `offer` (`offer_id`, `item_id`, `buyer_id`, `offer_price`, `offer_status`) VALUES
('OFGX0ip4t', '4', '1', 100, 'accept'),
('OFvrdVMNc', '3', '1', 50, 'pending');

-- --------------------------------------------------------

--
-- Table structure for table `reason`
--

CREATE TABLE `reason` (
  `reason_id` varchar(255) NOT NULL,
  `reason_text` varchar(255) NOT NULL,
  `item_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `reason`
--

INSERT INTO `reason` (`reason_id`, `reason_text`, `item_id`, `user_id`) VALUES
('RE0tMs5NC', 'Hell naw', '3', 'SE6blIbcj'),
('REgRxKLj1', 'You think you cooking lil bro', '1', 'SE6blIbcj'),
('RESzzacLQ', 'Wtf is that name', 'ITJfboq5a', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transaction_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `item_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transaction_id`, `user_id`, `item_id`) VALUES
('TRFLK3XiZ', 'SE6blIbcj', '4'),
('TRwWMKECu', '1', '1');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `role` enum('seller','buyer') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `username`, `password`, `phone_number`, `address`, `role`) VALUES
('1', 'Hugo', '123', '123', '123', 'buyer'),
('BUdfz6zW6', 'Hugooo1', 'Hugo1234567@', '+62123456789', 'ASdwapoQW', 'buyer'),
('BUEGw4Mu7', 'faldian', 'faldianchandra@', '+62123456789', 'HAHAHA', 'buyer'),
('BUpdXX5sc', 'Hugo04', 'Hugo1234567@', '+62987654321', 'Jl. Kenanga Indah VII, Blok HG 38', 'buyer'),
('SE6blIbcj', 'SellerTest', 'SellerTest@', '+62123456789', 'Testing', 'seller');

-- --------------------------------------------------------

--
-- Table structure for table `wishlist`
--

CREATE TABLE `wishlist` (
  `wishlist_id` varchar(255) NOT NULL,
  `item_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `wishlist`
--

INSERT INTO `wishlist` (`wishlist_id`, `item_id`, `user_id`) VALUES
('WLiCmm3do', 'ITCW2mrc6', '1'),
('WLldCq9SZ', '3', '1');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`item_id`),
  ADD KEY `item_ibfk_1` (`seller_id`);

--
-- Indexes for table `offer`
--
ALTER TABLE `offer`
  ADD PRIMARY KEY (`offer_id`),
  ADD KEY `item_id` (`item_id`),
  ADD KEY `buyer_id` (`buyer_id`);

--
-- Indexes for table `reason`
--
ALTER TABLE `reason`
  ADD PRIMARY KEY (`reason_id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transaction_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `item_id` (`item_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `wishlist`
--
ALTER TABLE `wishlist`
  ADD PRIMARY KEY (`wishlist_id`),
  ADD KEY `item_id` (`item_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `item`
--
ALTER TABLE `item`
  ADD CONSTRAINT `item_ibfk_1` FOREIGN KEY (`seller_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE;

--
-- Constraints for table `offer`
--
ALTER TABLE `offer`
  ADD CONSTRAINT `offer_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`),
  ADD CONSTRAINT `offer_ibfk_2` FOREIGN KEY (`buyer_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`);

--
-- Constraints for table `wishlist`
--
ALTER TABLE `wishlist`
  ADD CONSTRAINT `wishlist_ibfk_1` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`),
  ADD CONSTRAINT `wishlist_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
