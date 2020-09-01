-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 01, 2020 at 05:24 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.3.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `economic`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `id` int(11) NOT NULL,
  `username` varchar(200) DEFAULT NULL,
  `password` varchar(60) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `username`, `password`, `role_id`, `email`) VALUES
(1, 'admin12', '$2a$10$7wty7EhEUZWveWBKkYG2iujxqzYjJwovcu8inI/PqIgzdPFc17LTO', 1, 'admin12@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`, `parent_id`, `deleted_at`) VALUES
(1, 'Staples', NULL, NULL),
(2, 'Snacks', NULL, NULL),
(3, 'Fruits & Vegetables', NULL, NULL),
(4, 'Breakfast & Cereal', NULL, NULL),
(5, 'Food', 1, NULL),
(6, 'Tea', 3, NULL),
(7, 'Milk', 3, NULL),
(8, 'Chicken', 4, NULL),
(9, 'Oils', 2, NULL),
(13, 'Olong', 3, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `sale` int(11) DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `category_id`, `number`, `sale`, `image`, `content`, `created_at`, `deleted_at`) VALUES
(1, 'Moong(1 kg)', 2, 1, 5, 10, 'of.png', NULL, '2020-08-31 04:09:24', NULL),
(3, 'Sunflower Oil(5 kg)', 10, 1, 5, 5, 'of1.png', NULL, '2020-08-31 06:03:37', NULL),
(4, 'Kabuli Chana(1 kg)', 3, 1, 6, 5, 'of2.png', NULL, '2020-08-31 07:43:59', NULL),
(5, 'Soya Chunks(1 kg)', 4, 1, 14, 10, 'of3.png', NULL, '2020-08-31 08:17:23', NULL),
(9, 'Lays(100 g', 1, 2, 12, 30, 'of4.png', NULL, '2020-09-01 01:58:20', NULL),
(10, 'Kurkure(100 g)', 1, 2, 2, 30, 'of5.png', NULL, '2020-09-01 02:15:47', NULL),
(12, 'Popcorn(250 g)', 4, 2, 5, 12, 'of6.png', NULL, '2020-09-01 02:23:46', NULL),
(13, 'Nuts(250 g)', 1, 2, 2, 2, 'of7.png', NULL, '2020-09-01 02:28:46', NULL),
(14, 'Sunflower Oil(5 kg)', 1, 1, 2, 2, 'of1.png', NULL, '2020-09-01 02:31:42', NULL),
(15, 'Banana(6 pcs)', 2, 3, 10, 5, 'of8.png', NULL, '2020-09-01 03:17:45', NULL),
(16, 'Onion(1 kg)', 1, 3, 5, 10, 'of9.png', NULL, '2020-09-01 03:18:13', NULL),
(17, 'Bitter Gourd(1 kg)', 2, 3, 10, 50, 'of10.png', NULL, '2020-09-01 03:18:46', NULL),
(18, 'Apples(1 kg)', 4, 3, 5, 10, 'of11.png', NULL, '2020-09-01 03:19:03', NULL),
(19, 'Honey(500 g)', 7, 4, 12, 5, 'of12.png', NULL, '2020-09-01 03:20:11', NULL),
(20, 'Chocos(250 g)', 5, 4, 10, 12, 'of13.png', NULL, '2020-09-01 03:21:05', NULL),
(21, 'Oats(1 kg)', 4, 4, 10, 6, 'of14.png', NULL, '2020-09-01 03:21:35', NULL),
(22, 'Bread(500 g)', 1, 4, 10, 20, 'of15.png', NULL, '2020-09-01 03:22:09', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
