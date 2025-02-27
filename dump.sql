-- MySQL dump 10.13  Distrib 8.0.40, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: hermes
-- ------------------------------------------------------
-- Server version	11.6.2-MariaDB-ubu2404

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `devices`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `devices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(50) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `firebase_token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrfbri1ymrwywdydc4dgywe1bt` (`user_id`),
  CONSTRAINT `FKrfbri1ymrwywdydc4dgywe1bt` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--


--
-- Table structure for table `feedback`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `label` int(11) NOT NULL,
  `text` text NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpwwmhguqianghvi1wohmtsm8l` (`user_id`),
  CONSTRAINT `FKpwwmhguqianghvi1wohmtsm8l` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--


--
-- Table structure for table `groups`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `groups` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKagnoryn9aja2wap923iar8oav` (`group_id`,`user_id`),
  KEY `FK4cygfv5el2o2v3hbkdkscfw5q` (`user_id`),
  CONSTRAINT `FK4cygfv5el2o2v3hbkdkscfw5q` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `groups`
--


--
-- Table structure for table `notifications`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `group_name` varchar(255) NOT NULL,
  `group_id` bigint(20) NOT NULL,
  `score` double NOT NULL,
  `text` varchar(4096) NOT NULL,
  `text_id` bigint(20) NOT NULL,
  `topic` varchar(30) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9y21adhxn0ayjhfocscqox7bh` (`user_id`),
  CONSTRAINT `FK9y21adhxn0ayjhfocscqox7bh` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--


--
-- Table structure for table `topics`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics`
--

INSERT INTO `topics` (`id`, `name`) VALUES (1,'ARTS');
INSERT INTO `topics` (`id`, `name`) VALUES (2,'ARTS & CULTURE');
INSERT INTO `topics` (`id`, `name`) VALUES (3,'BLACK VOICES');
INSERT INTO `topics` (`id`, `name`) VALUES (4,'BUSINESS');
INSERT INTO `topics` (`id`, `name`) VALUES (5,'COLLEGE');
INSERT INTO `topics` (`id`, `name`) VALUES (6,'COMEDY');
INSERT INTO `topics` (`id`, `name`) VALUES (7,'CRIME');
INSERT INTO `topics` (`id`, `name`) VALUES (8,'CULTURE & ARTS');
INSERT INTO `topics` (`id`, `name`) VALUES (9,'DIVORCE');
INSERT INTO `topics` (`id`, `name`) VALUES (10,'EDUCATION');
INSERT INTO `topics` (`id`, `name`) VALUES (11,'ENTERTAINMENT');
INSERT INTO `topics` (`id`, `name`) VALUES (12,'ENVIRONMENT');
INSERT INTO `topics` (`id`, `name`) VALUES (13,'FIFTY');
INSERT INTO `topics` (`id`, `name`) VALUES (14,'FOOD & DRINK');
INSERT INTO `topics` (`id`, `name`) VALUES (15,'GOOD NEWS');
INSERT INTO `topics` (`id`, `name`) VALUES (16,'GREEN');
INSERT INTO `topics` (`id`, `name`) VALUES (17,'HEALTHY LIVING');
INSERT INTO `topics` (`id`, `name`) VALUES (18,'HOME & LIVING');
INSERT INTO `topics` (`id`, `name`) VALUES (19,'IMPACT');
INSERT INTO `topics` (`id`, `name`) VALUES (20,'LATINO VOICES');
INSERT INTO `topics` (`id`, `name`) VALUES (21,'MEDIA');
INSERT INTO `topics` (`id`, `name`) VALUES (22,'MONEY');
INSERT INTO `topics` (`id`, `name`) VALUES (23,'PARENTING');
INSERT INTO `topics` (`id`, `name`) VALUES (24,'PARENTS');
INSERT INTO `topics` (`id`, `name`) VALUES (25,'POLITICS');
INSERT INTO `topics` (`id`, `name`) VALUES (26,'QUEER VOICES');
INSERT INTO `topics` (`id`, `name`) VALUES (27,'RELIGION');
INSERT INTO `topics` (`id`, `name`) VALUES (28,'SCIENCE');
INSERT INTO `topics` (`id`, `name`) VALUES (29,'SPORTS');
INSERT INTO `topics` (`id`, `name`) VALUES (30,'STYLE');
INSERT INTO `topics` (`id`, `name`) VALUES (31,'STYLE & BEAUTY');
INSERT INTO `topics` (`id`, `name`) VALUES (32,'TASTE');
INSERT INTO `topics` (`id`, `name`) VALUES (33,'TECH');
INSERT INTO `topics` (`id`, `name`) VALUES (34,'THE WORLDPOST');
INSERT INTO `topics` (`id`, `name`) VALUES (35,'TRAVEL');
INSERT INTO `topics` (`id`, `name`) VALUES (36,'U.S. NEWS');
INSERT INTO `topics` (`id`, `name`) VALUES (37,'WEDDINGS');
INSERT INTO `topics` (`id`, `name`) VALUES (38,'WEIRD NEWS');
INSERT INTO `topics` (`id`, `name`) VALUES (39,'WELLNESS');
INSERT INTO `topics` (`id`, `name`) VALUES (40,'WOMEN');
INSERT INTO `topics` (`id`, `name`) VALUES (41,'WORLD NEWS');
INSERT INTO `topics` (`id`, `name`) VALUES (42,'WORLDPOST');

--
-- Table structure for table `topics_preferences`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topics_preferences` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `value` double NOT NULL,
  `topic_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKbpp1yf1arl81707wspwk3ifx1` (`topic_id`,`user_id`),
  KEY `FKiclb5ckulre1q7b2mxud9enk1` (`user_id`),
  CONSTRAINT `FKiclb5ckulre1q7b2mxud9enk1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKnyl25gxrx65ijdmr92km752i2` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topics_preferences`
--


--
-- Table structure for table `users`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `first_name` varchar(64) NOT NULL,
  `last_name` varchar(64) DEFAULT NULL,
  `phone_number` varchar(20) NOT NULL,
  `profile_image_path` varchar(255) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9q63snka3mdh91as4io72espi` (`phone_number`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_uca1400_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-26 10:50:14
