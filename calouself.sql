-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 14, 2024 at 06:15 PM
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
('IT2N1cItd', 'Formal Suit Blazer', 'Blazer', 'L', 150, 'approved', 'SEtH097MQ'),
('IT4JcYAMC', 'Lightweight Windbreaker', 'Jacket', 'M', 48, 'approved', 'SEZheZTKT'),
('IT5mwE5Jo', 'Hooded Sweatshirt', 'Hoodie', 'XL', 45, 'approved', 'SEtH097MQ'),
('IT8OuQmWz', 'Plaid Flannel Shirt', 'Shirt', 'M', 28, 'approved', 'SEgrAS8d2'),
('ITaVhWz2f', 'Cotton Cargo Pants', 'Pants', 'XL', 35, 'approved', 'SEZheZTKT'),
('ITEEgHWgk', 'Classic White Shirt', 'Shirt', 'M', 25, 'approved', 'SEa9YT6ZR'),
('ITeN33ium', 'Woolen Winter Sweater', 'Sweater', 'M', 50, 'approved', 'SEgrAS8d2'),
('ITerOytnt', 'Graphic Print T-Shirt', 'Shirt', 'M', 22, 'approved', 'SEtH097MQ'),
('ITgOn7JUW', 'Comfy Lounge Sweatpants', 'Pants', 'M', 32, 'approved', 'SEZheZTKT'),
('ITGxQAikU', 'Sports Running Shorts', 'Shorts', 'M', 18, 'approved', 'SEgrAS8d2'),
('IThSInj0a', 'Basic Crew Neck Tee', 'Shirt', 'S', 15, 'approved', 'SEZheZTKT'),
('ITJYRTfnD', 'Vintage Denim Jacket', 'Jacket', 'L', 80, 'approved', 'SEgrAS8d2'),
('ITk76vCL4', 'Slim Fit Jeans', 'Pants', 'L', 40, 'approved', 'SEa9YT6ZR'),
('ITKqjfFuu', 'Summer Floral Dress', 'Dress', 'S', 35, 'approved', 'SEa9YT6ZR'),
('ITLQfJrrh', 'Silk Party Gown', 'Dress', 'S', 120, 'approved', 'SEtH097MQ'),
('ITn6TPnME', 'Oversized Graphic Hoodie', 'Hoodie', 'L', 55, 'approved', 'SEZheZTKT'),
('ITpLwAI8B', 'Casual Polo T-Shirt', 'Shirt', 'L', 20, 'approved', 'SEa9YT6ZR'),
('ITUZFTXa7', 'Waterproof Raincoat', 'Jacket', 'M', 60, 'approved', 'SEtH097MQ'),
('ITwGujW00', 'Black Leather Jacket', 'Jacket', 'XL', 100, 'approved', 'SEa9YT6ZR'),
('ITyaNGKMn', 'Stretch Yoga Leggings', 'Pants', 'S', 30, 'approved', 'SEgrAS8d2');

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
('OF2rQam4Z', 'ITpLwAI8B', 'BUpXyjomD', 15, 'pending'),
('OF4uR0NOx', 'IThSInj0a', 'BUyINTmZT', 11, 'pending'),
('OF8fHg2QE', 'ITLQfJrrh', 'BUd3i0qL8', 85, 'pending'),
('OF9bfJI7a', 'ITGxQAikU', 'BUd3i0qL8', 15, 'pending'),
('OFbc7ZtFz', 'ITgOn7JUW', 'BUFwO7Ivx', 10, 'pending'),
('OFcpvdySe', 'ITerOytnt', 'BUyINTmZT', 22, 'pending'),
('OFcXIXMAH', 'IT5mwE5Jo', 'BUpXyjomD', 35, 'pending'),
('OFEiX2Cpq', 'ITyaNGKMn', 'BUd3i0qL8', 10, 'pending'),
('OFi2D0mQY', 'ITaVhWz2f', 'BUyINTmZT', 22, 'pending'),
('OFJaanQot', 'ITk76vCL4', 'BUpXyjomD', 15, 'pending'),
('OFL1SIWsi', 'ITUZFTXa7', 'BUFwO7Ivx', 50, 'pending'),
('OFl9VTDbE', 'ITJYRTfnD', 'BUpXyjomD', 55, 'pending'),
('OFnEymObK', 'IT4JcYAMC', 'BUFwO7Ivx', 16, 'pending'),
('OFNhSOV3H', 'ITwGujW00', 'BUFwO7Ivx', 66, 'pending'),
('OFvjoz1PM', 'ITeN33ium', 'BUpXyjomD', 34, 'pending'),
('OFvWdVwIY', 'IT2N1cItd', 'BUd3i0qL8', 90, 'pending'),
('OFy8m2S51', 'ITn6TPnME', 'BUyINTmZT', 11, 'pending');

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
('RERgQ21aC', 'Only Clothing', 'ITpwgDYZy', 'admin'),
('REVekA8hO', 'Only Clothing', 'IT553hQn8', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transaction_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `item_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
('BUd3i0qL8', 'frankie_star', 'frankie001&', '+62998877669', '+62998877669', 'buyer'),
('BUFwO7Ivx', 'john_doe', 'pass1234@', '+62812345678', '123 Main Street, Jakarta', 'buyer'),
('BUpXyjomD', 'bob_builder', 'buildit99#', '+62223344556', '12 Hammer Lane, Surabaya', 'buyer'),
('BUyINTmZT', 'clara_sky', 'clara@123', '+62334455667', '78 Skyview Blvd, Medan', 'buyer'),
('SEa9YT6ZR', 'danny_phantom', 'ghosty99^', '+62556677889', '21 Phantom Rd, Bali', 'seller'),
('SEgrAS8d2', 'alice_wonder', 'alice2023!', '+62876543212', '45 Wonderland Ave, Bandung', 'seller'),
('SEtH097MQ', 'emma_coder', 'emma#2024', '+62112233447', '33 Code Street, Yogyakarta', 'seller'),
('SEZheZTKT', 'harry_potter', 'expelliarmus*', '+62778899001', '+62778899001', 'seller');

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
('WL08tsU3D', 'IThSInj0a', 'BUpXyjomD'),
('WL0zCJJ6M', 'ITLQfJrrh', 'BUyINTmZT'),
('WL3bMODlJ', 'ITgOn7JUW', 'BUyINTmZT'),
('WL4Q6aOmW', 'ITKqjfFuu', 'BUyINTmZT'),
('WL6hyHQPy', 'ITk76vCL4', 'BUpXyjomD'),
('WL7f5m3Fx', 'IThSInj0a', 'BUFwO7Ivx'),
('WL9gbs6HH', 'ITeN33ium', 'BUpXyjomD'),
('WL9YFGHbW', 'ITerOytnt', 'BUFwO7Ivx'),
('WLazCyYBU', 'IT8OuQmWz', 'BUpXyjomD'),
('WLBDCxBi6', 'ITUZFTXa7', 'BUd3i0qL8'),
('WLcOfWfRZ', 'IT4JcYAMC', 'BUFwO7Ivx'),
('WLEYAODtx', 'IT2N1cItd', 'BUFwO7Ivx'),
('WLigj65fU', 'ITgOn7JUW', 'BUpXyjomD'),
('WLIrp4HIL', 'ITJYRTfnD', 'BUFwO7Ivx'),
('WLJ5NurYo', 'IT5mwE5Jo', 'BUFwO7Ivx'),
('WLjxfuaSm', 'ITEEgHWgk', 'BUyINTmZT'),
('WLKe3kLDx', 'ITyaNGKMn', 'BUyINTmZT'),
('WLKPZnw8C', 'ITyaNGKMn', 'BUd3i0qL8'),
('WLM7DMyes', 'ITn6TPnME', 'BUd3i0qL8'),
('WLmF3KwaN', 'ITaVhWz2f', 'BUyINTmZT'),
('WLod5m42r', 'ITerOytnt', 'BUd3i0qL8'),
('WLqQvonyz', 'ITn6TPnME', 'BUyINTmZT'),
('WLRf9zW3N', 'IT2N1cItd', 'BUd3i0qL8'),
('WLrmjlJwE', 'ITn6TPnME', 'BUpXyjomD'),
('WLVKh84LF', 'ITGxQAikU', 'BUpXyjomD'),
('WLyt1sUvy', 'ITwGujW00', 'BUFwO7Ivx');

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
