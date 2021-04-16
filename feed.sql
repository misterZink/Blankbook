-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Värd: 127.0.0.1:3306
-- Tid vid skapande: 16 apr 2021 kl 16:11
-- Serverversion: 5.7.31
-- PHP-version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databas: `feed`
--

-- --------------------------------------------------------

--
-- Tabellstruktur `feed`
--

DROP TABLE IF EXISTS `feed`;
CREATE TABLE IF NOT EXISTS `feed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(255) NOT NULL,
  `hashtag` varchar(255) NOT NULL,
  `creator` varchar(45) NOT NULL,
  `datetime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=134 DEFAULT CHARSET=latin1;

--
-- Dumpning av Data i tabell `feed`
--

INSERT INTO `feed` (`id`, `message`, `hashtag`, `creator`, `datetime`) VALUES
(128, 'Hej Marcus', '', 'User1', '2021-04-16 09:51:38'),
(127, 'Hello', '#Test', 'User2', '2021-04-16 00:08:56'),
(126, 'Hej då', '', 'User1', '2021-04-15 14:20:30'),
(125, 'Hej', '', 'User1', '2021-04-15 14:20:26'),
(129, 'Hej marcus!', '##Test', 'User1', '2021-04-16 09:52:02'),
(130, 'Hej marcus', '#test', 'User1', '2021-04-16 09:52:16'),
(131, 'test', '#test', 'User1', '2021-04-16 09:52:36'),
(132, 'test', '#test', 'User1', '2021-04-16 09:53:29'),
(133, 'testa', '#testa', 'User2', '2021-04-16 09:54:51');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
