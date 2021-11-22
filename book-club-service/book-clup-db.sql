-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bookend-book-club
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `accident_file_keys`
--

DROP TABLE IF EXISTS `accident_file_keys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accident_file_keys` (
  `industrial_accident_id` bigint NOT NULL,
  `file_keys_id` bigint NOT NULL,
  UNIQUE KEY `UK_rcl1t6r1sc77lsuk0ohw5o8bp` (`file_keys_id`),
  KEY `FKf5bjoqh1glhm9a9okqxcd6jhp` (`industrial_accident_id`),
  CONSTRAINT `FKf5bjoqh1glhm9a9okqxcd6jhp` FOREIGN KEY (`industrial_accident_id`) REFERENCES `industrial_accidents` (`id`),
  CONSTRAINT `FKr7npp8l87lu4qhni9owpwi60e` FOREIGN KEY (`file_keys_id`) REFERENCES `file_keys` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accident_file_keys`
--

LOCK TABLES `accident_file_keys` WRITE;
/*!40000 ALTER TABLE `accident_file_keys` DISABLE KEYS */;
/*!40000 ALTER TABLE `accident_file_keys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answers`
--

DROP TABLE IF EXISTS `answers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `answer` varchar(255) DEFAULT NULL,
  `pic_byte` longblob,
  `form_id` bigint DEFAULT NULL,
  `template_id` bigint NOT NULL,
  `inspection_id` bigint NOT NULL,
  `option_id` bigint DEFAULT NULL,
  `question_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfswncf52whus8abtircrwxlwo` (`form_id`),
  KEY `FKo020m5hraeec53dmfeqi74m6l` (`template_id`),
  KEY `FK823n0sp2bh7x1v33fyw5g6ee4` (`inspection_id`),
  KEY `FKdi3wbmqfwtg2jxsthoegabrff` (`option_id`),
  KEY `FK3erw1a3t0r78st8ty27x6v3g1` (`question_id`),
  CONSTRAINT `FK3erw1a3t0r78st8ty27x6v3g1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `FK823n0sp2bh7x1v33fyw5g6ee4` FOREIGN KEY (`inspection_id`) REFERENCES `inspections` (`id`),
  CONSTRAINT `FKdi3wbmqfwtg2jxsthoegabrff` FOREIGN KEY (`option_id`) REFERENCES `question_options` (`id`),
  CONSTRAINT `FKfswncf52whus8abtircrwxlwo` FOREIGN KEY (`form_id`) REFERENCES `forms` (`id`),
  CONSTRAINT `FKo020m5hraeec53dmfeqi74m6l` FOREIGN KEY (`template_id`) REFERENCES `form_templates` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answers`
--

LOCK TABLES `answers` WRITE;
/*!40000 ALTER TABLE `answers` DISABLE KEYS */;
/*!40000 ALTER TABLE `answers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cities_towns`
--

DROP TABLE IF EXISTS `cities_towns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cities_towns` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `belonging_city_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqd5dvvwrr71s5jn06b2nuf85o` (`belonging_city_id`),
  CONSTRAINT `FKqd5dvvwrr71s5jn06b2nuf85o` FOREIGN KEY (`belonging_city_id`) REFERENCES `cities_towns` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cities_towns`
--

LOCK TABLES `cities_towns` WRITE;
/*!40000 ALTER TABLE `cities_towns` DISABLE KEYS */;
/*!40000 ALTER TABLE `cities_towns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `club_member`
--

DROP TABLE IF EXISTS `club_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `club_member` (
  `club_id` bigint NOT NULL,
  `member_id` bigint NOT NULL,
  KEY `FKaslyu9v3wjfiy6f6jfr7um51e` (`member_id`),
  KEY `FKlk4on7d67rl1vwmn25205ujon` (`club_id`),
  CONSTRAINT `FKaslyu9v3wjfiy6f6jfr7um51e` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
  CONSTRAINT `FKlk4on7d67rl1vwmn25205ujon` FOREIGN KEY (`club_id`) REFERENCES `clubs` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `club_member`
--

LOCK TABLES `club_member` WRITE;
/*!40000 ALTER TABLE `club_member` DISABLE KEYS */;
INSERT INTO `club_member` VALUES (1,76),(1,76),(1,76),(1,75);
/*!40000 ALTER TABLE `club_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clubs`
--

DROP TABLE IF EXISTS `clubs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clubs` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `clubName` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isPrivate` bit(1) NOT NULL,
  `owner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKingvac0oict70phb3fk57pqu1` (`owner_id`),
  CONSTRAINT `FKingvac0oict70phb3fk57pqu1` FOREIGN KEY (`owner_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clubs`
--

LOCK TABLES `clubs` WRITE;
/*!40000 ALTER TABLE `clubs` DISABLE KEYS */;
INSERT INTO `clubs` VALUES (1,'Book-Club-g5572riwwkk','testdesc',_binary '',75);
/*!40000 ALTER TABLE `clubs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `companies` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `e_company_type` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `department_id` bigint DEFAULT NULL,
  `city_town_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKipp835bgxuewi6ngkmg3ua1qb` (`department_id`),
  KEY `FKo6ubvowvvmdj20l0tx5n9j1` (`city_town_id`),
  CONSTRAINT `FKipp835bgxuewi6ngkmg3ua1qb` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`),
  CONSTRAINT `FKo6ubvowvvmdj20l0tx5n9j1` FOREIGN KEY (`city_town_id`) REFERENCES `cities_towns` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_companysgknos`
--

DROP TABLE IF EXISTS `company_companysgknos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company_companysgknos` (
  `company_id` bigint NOT NULL,
  `companysgknos` varchar(255) DEFAULT NULL,
  KEY `FKjob0pd9t4a3wqwy9slmb3uvsc` (`company_id`),
  CONSTRAINT `FKjob0pd9t4a3wqwy9slmb3uvsc` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_companysgknos`
--

LOCK TABLES `company_companysgknos` WRITE;
/*!40000 ALTER TABLE `company_companysgknos` DISABLE KEYS */;
/*!40000 ALTER TABLE `company_companysgknos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departments`
--

DROP TABLE IF EXISTS `departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `name` varchar(255) DEFAULT NULL,
  `city_id` bigint DEFAULT NULL,
  `company_id` bigint NOT NULL,
  `manager_id` bigint DEFAULT NULL,
  `town_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKose7loe3ct64e84vnvydy55eq` (`city_id`),
  KEY `FKoq64wrpwbvd4lq19c3qyxykl0` (`company_id`),
  KEY `FKia71ute4clfbt6u2auufvw1bv` (`manager_id`),
  KEY `FKor4vcg84g7r57leg5nl69rlx` (`town_id`),
  CONSTRAINT `FKia71ute4clfbt6u2auufvw1bv` FOREIGN KEY (`manager_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKoq64wrpwbvd4lq19c3qyxykl0` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
  CONSTRAINT `FKor4vcg84g7r57leg5nl69rlx` FOREIGN KEY (`town_id`) REFERENCES `cities_towns` (`id`),
  CONSTRAINT `FKose7loe3ct64e84vnvydy55eq` FOREIGN KEY (`city_id`) REFERENCES `cities_towns` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments`
--

LOCK TABLES `departments` WRITE;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departments_belonging_departments`
--

DROP TABLE IF EXISTS `departments_belonging_departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departments_belonging_departments` (
  `department_id` bigint NOT NULL,
  `belonging_departments_id` bigint NOT NULL,
  UNIQUE KEY `UK_cukpnmbrh992rxs2q82udm1jy` (`belonging_departments_id`),
  KEY `FKlw8iepj60frt4h4vr40ul34n6` (`department_id`),
  CONSTRAINT `FKjbv97fimao3ha7eebkcx83ktc` FOREIGN KEY (`belonging_departments_id`) REFERENCES `departments` (`id`),
  CONSTRAINT `FKlw8iepj60frt4h4vr40ul34n6` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments_belonging_departments`
--

LOCK TABLES `departments_belonging_departments` WRITE;
/*!40000 ALTER TABLE `departments_belonging_departments` DISABLE KEYS */;
/*!40000 ALTER TABLE `departments_belonging_departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departments_parents`
--

DROP TABLE IF EXISTS `departments_parents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `departments_parents` (
  `belonging_departments_id` bigint NOT NULL,
  `parents_id` bigint NOT NULL,
  KEY `FK55a0arf8cu1vg10qt1xjigik4` (`parents_id`),
  KEY `FKpjmvxvpxupuxcn8dsa69wursh` (`belonging_departments_id`),
  CONSTRAINT `FK55a0arf8cu1vg10qt1xjigik4` FOREIGN KEY (`parents_id`) REFERENCES `departments` (`id`),
  CONSTRAINT `FKpjmvxvpxupuxcn8dsa69wursh` FOREIGN KEY (`belonging_departments_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments_parents`
--

LOCK TABLES `departments_parents` WRITE;
/*!40000 ALTER TABLE `departments_parents` DISABLE KEYS */;
/*!40000 ALTER TABLE `departments_parents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_keys`
--

DROP TABLE IF EXISTS `file_keys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file_keys` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `file_key` varchar(255) DEFAULT NULL,
  `latitude` varchar(255) DEFAULT NULL,
  `longtitude` varchar(255) DEFAULT NULL,
  `size` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_keys`
--

LOCK TABLES `file_keys` WRITE;
/*!40000 ALTER TABLE `file_keys` DISABLE KEYS */;
/*!40000 ALTER TABLE `file_keys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_file_keys`
--

DROP TABLE IF EXISTS `form_file_keys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form_file_keys` (
  `form_id` bigint NOT NULL,
  `file_keys_id` bigint NOT NULL,
  UNIQUE KEY `UK_986fuqy0etdr23waoltc9qhf8` (`file_keys_id`),
  KEY `FK6ptqx8wrgibe3h7djy345dn1a` (`form_id`),
  CONSTRAINT `FK6ptqx8wrgibe3h7djy345dn1a` FOREIGN KEY (`form_id`) REFERENCES `forms` (`id`),
  CONSTRAINT `FK8mjbbj05v816gic2swj4xd7yo` FOREIGN KEY (`file_keys_id`) REFERENCES `file_keys` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_file_keys`
--

LOCK TABLES `form_file_keys` WRITE;
/*!40000 ALTER TABLE `form_file_keys` DISABLE KEYS */;
/*!40000 ALTER TABLE `form_file_keys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `form_templates`
--

DROP TABLE IF EXISTS `form_templates`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `form_templates` (
  `id` bigint NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `version` int NOT NULL DEFAULT '1',
  `content` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `previous_version_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKevd3haqg4x5x93ha15a55l534` (`previous_version_id`),
  CONSTRAINT `FKevd3haqg4x5x93ha15a55l534` FOREIGN KEY (`previous_version_id`) REFERENCES `form_templates` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `form_templates`
--

LOCK TABLES `form_templates` WRITE;
/*!40000 ALTER TABLE `form_templates` DISABLE KEYS */;
/*!40000 ALTER TABLE `form_templates` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forms`
--

DROP TABLE IF EXISTS `forms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `submission_date` datetime(6) DEFAULT NULL,
  `template_id` bigint NOT NULL,
  `inspection_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs9w5t6qaslevkiqmifiqltj4y` (`template_id`),
  KEY `FKpwnv2s7vccecpp63a1bhlhfv9` (`inspection_id`),
  CONSTRAINT `FKpwnv2s7vccecpp63a1bhlhfv9` FOREIGN KEY (`inspection_id`) REFERENCES `inspections` (`id`),
  CONSTRAINT `FKs9w5t6qaslevkiqmifiqltj4y` FOREIGN KEY (`template_id`) REFERENCES `form_templates` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forms`
--

LOCK TABLES `forms` WRITE;
/*!40000 ALTER TABLE `forms` DISABLE KEYS */;
/*!40000 ALTER TABLE `forms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1),(1),(1),(1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `industrial_accident_forms`
--

DROP TABLE IF EXISTS `industrial_accident_forms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `industrial_accident_forms` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `file1_id` bigint DEFAULT NULL,
  `file10_id` bigint DEFAULT NULL,
  `file11_id` bigint DEFAULT NULL,
  `file12_id` bigint DEFAULT NULL,
  `file13_id` bigint DEFAULT NULL,
  `file14_id` bigint DEFAULT NULL,
  `file15_id` bigint DEFAULT NULL,
  `file16_id` bigint DEFAULT NULL,
  `file17_id` bigint DEFAULT NULL,
  `file18_id` bigint DEFAULT NULL,
  `file19_id` bigint DEFAULT NULL,
  `file2_id` bigint DEFAULT NULL,
  `file20_id` bigint DEFAULT NULL,
  `file21_id` bigint DEFAULT NULL,
  `file22_id` bigint DEFAULT NULL,
  `file23_id` bigint DEFAULT NULL,
  `file24_id` bigint DEFAULT NULL,
  `file25_id` bigint DEFAULT NULL,
  `file3_id` bigint DEFAULT NULL,
  `file4_id` bigint DEFAULT NULL,
  `file5_id` bigint DEFAULT NULL,
  `file6_id` bigint DEFAULT NULL,
  `file7_id` bigint DEFAULT NULL,
  `file8_id` bigint DEFAULT NULL,
  `file9_id` bigint DEFAULT NULL,
  `form1_id` bigint DEFAULT NULL,
  `form2_id` bigint DEFAULT NULL,
  `form3_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg13dxev8pgy40pqbwj121ang9` (`file1_id`),
  KEY `FKonfdy0l86bipe1u25k7xyfpe8` (`file10_id`),
  KEY `FK16yoofq67wry0eiprjbn8xxfa` (`file11_id`),
  KEY `FKflauit6o5hyr3bkw9kc6t23w2` (`file12_id`),
  KEY `FK1ijbxpbv68qsjfcw08pbb99ef` (`file13_id`),
  KEY `FKcufvu2it3yb7h7sguw1k8etq` (`file14_id`),
  KEY `FKjxa67o6w8uyg7y8fcu2lspl3x` (`file15_id`),
  KEY `FKo2xu7m1etqydvqj73rscl53pt` (`file16_id`),
  KEY `FKqd4e9kpvleox9sbbphpipvfd8` (`file17_id`),
  KEY `FKh4wloldaw7ik9cin3na2x3ubo` (`file18_id`),
  KEY `FK9rb36dytwgnj9rn4auomly9kj` (`file19_id`),
  KEY `FKhg3f81otvftyqgwr9m3b98vk1` (`file2_id`),
  KEY `FKnfsg9uk0ak25oga1kqh85wfvy` (`file20_id`),
  KEY `FKptvhqcv5qy4fivd7y56pwgw4c` (`file21_id`),
  KEY `FKp0b25cogkyvuw40gr5496ggfl` (`file22_id`),
  KEY `FK7i9ntlvccp4msar9i20dejojp` (`file23_id`),
  KEY `FKbn6wdmh1n45rh7a6ytlmsw3l1` (`file24_id`),
  KEY `FK2t2g92y6cr1vpmt31goqymqrk` (`file25_id`),
  KEY `FKny4oix8q0ww5315a1ml1ni6ci` (`file3_id`),
  KEY `FKlnry96deuta528xm2u28ql4hm` (`file4_id`),
  KEY `FKak49xmqg53i7l9e9bq0yq2diq` (`file5_id`),
  KEY `FKcj1t52i672wpl9s9k9l54d47g` (`file6_id`),
  KEY `FK2y2mw30tmks42qcya5nx526b0` (`file7_id`),
  KEY `FKcg9j0nlb34x6479o9pdcc19pf` (`file8_id`),
  KEY `FK2ykt71jcwwhndop9s4obcwf0d` (`file9_id`),
  KEY `FK5hx4l5bnwhaddh5n9xn3lxkql` (`form1_id`),
  KEY `FKmtbopowo48g9s8m48ds5mm6en` (`form2_id`),
  KEY `FK958vacdr3s1pcm0yiy712a7w9` (`form3_id`),
  CONSTRAINT `FK16yoofq67wry0eiprjbn8xxfa` FOREIGN KEY (`file11_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FK1ijbxpbv68qsjfcw08pbb99ef` FOREIGN KEY (`file13_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FK2t2g92y6cr1vpmt31goqymqrk` FOREIGN KEY (`file25_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FK2y2mw30tmks42qcya5nx526b0` FOREIGN KEY (`file7_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FK2ykt71jcwwhndop9s4obcwf0d` FOREIGN KEY (`file9_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FK5hx4l5bnwhaddh5n9xn3lxkql` FOREIGN KEY (`form1_id`) REFERENCES `forms` (`id`),
  CONSTRAINT `FK7i9ntlvccp4msar9i20dejojp` FOREIGN KEY (`file23_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FK958vacdr3s1pcm0yiy712a7w9` FOREIGN KEY (`form3_id`) REFERENCES `forms` (`id`),
  CONSTRAINT `FK9rb36dytwgnj9rn4auomly9kj` FOREIGN KEY (`file19_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKak49xmqg53i7l9e9bq0yq2diq` FOREIGN KEY (`file5_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKbn6wdmh1n45rh7a6ytlmsw3l1` FOREIGN KEY (`file24_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKcg9j0nlb34x6479o9pdcc19pf` FOREIGN KEY (`file8_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKcj1t52i672wpl9s9k9l54d47g` FOREIGN KEY (`file6_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKcufvu2it3yb7h7sguw1k8etq` FOREIGN KEY (`file14_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKflauit6o5hyr3bkw9kc6t23w2` FOREIGN KEY (`file12_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKg13dxev8pgy40pqbwj121ang9` FOREIGN KEY (`file1_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKh4wloldaw7ik9cin3na2x3ubo` FOREIGN KEY (`file18_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKhg3f81otvftyqgwr9m3b98vk1` FOREIGN KEY (`file2_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKjxa67o6w8uyg7y8fcu2lspl3x` FOREIGN KEY (`file15_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKlnry96deuta528xm2u28ql4hm` FOREIGN KEY (`file4_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKmtbopowo48g9s8m48ds5mm6en` FOREIGN KEY (`form2_id`) REFERENCES `forms` (`id`),
  CONSTRAINT `FKnfsg9uk0ak25oga1kqh85wfvy` FOREIGN KEY (`file20_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKny4oix8q0ww5315a1ml1ni6ci` FOREIGN KEY (`file3_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKo2xu7m1etqydvqj73rscl53pt` FOREIGN KEY (`file16_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKonfdy0l86bipe1u25k7xyfpe8` FOREIGN KEY (`file10_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKp0b25cogkyvuw40gr5496ggfl` FOREIGN KEY (`file22_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKptvhqcv5qy4fivd7y56pwgw4c` FOREIGN KEY (`file21_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKqd4e9kpvleox9sbbphpipvfd8` FOREIGN KEY (`file17_id`) REFERENCES `file_keys` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `industrial_accident_forms`
--

LOCK TABLES `industrial_accident_forms` WRITE;
/*!40000 ALTER TABLE `industrial_accident_forms` DISABLE KEYS */;
/*!40000 ALTER TABLE `industrial_accident_forms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `industrial_accidents`
--

DROP TABLE IF EXISTS `industrial_accidents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `industrial_accidents` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `accident_date` datetime(6) DEFAULT NULL,
  `companysgksicil_no` varchar(255) DEFAULT NULL,
  `did_getisgtraining` bit(1) NOT NULL,
  `e_accident_type` varchar(255) DEFAULT NULL,
  `lost_limb` varchar(255) DEFAULT NULL,
  `lost_time` bigint DEFAULT NULL,
  `personnelfname` varchar(255) DEFAULT NULL,
  `personnellname` varchar(255) DEFAULT NULL,
  `personnelsgksicil_no` varchar(255) DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  `industrial_accident_forms_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2dbmwuttlr6nw14o0wi08iuo8` (`company_id`),
  KEY `FKo2pim61rxhp6knhw6omp24ij6` (`industrial_accident_forms_id`),
  CONSTRAINT `FK2dbmwuttlr6nw14o0wi08iuo8` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
  CONSTRAINT `FKo2pim61rxhp6knhw6omp24ij6` FOREIGN KEY (`industrial_accident_forms_id`) REFERENCES `industrial_accident_forms` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `industrial_accidents`
--

LOCK TABLES `industrial_accidents` WRITE;
/*!40000 ALTER TABLE `industrial_accidents` DISABLE KEYS */;
/*!40000 ALTER TABLE `industrial_accidents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inspections`
--

DROP TABLE IF EXISTS `inspections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inspections` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `completion_date` datetime(6) DEFAULT NULL,
  `e_status` varchar(255) DEFAULT NULL,
  `inspection_type` varchar(255) DEFAULT NULL,
  `termination_time` datetime(6) DEFAULT NULL,
  `text` varchar(1000) DEFAULT NULL,
  `unsuitability` tinyint(1) DEFAULT '0',
  `company_id` bigint DEFAULT NULL,
  `department_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh2r4u7ofyxjs66kmpkridxceb` (`company_id`),
  KEY `FKom8gua7s7r0vt9u60ifp3qi0c` (`department_id`),
  KEY `FKhk8s3k7i9wryon90bfeh8c81s` (`user_id`),
  CONSTRAINT `FKh2r4u7ofyxjs66kmpkridxceb` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
  CONSTRAINT `FKhk8s3k7i9wryon90bfeh8c81s` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKom8gua7s7r0vt9u60ifp3qi0c` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inspections`
--

LOCK TABLES `inspections` WRITE;
/*!40000 ALTER TABLE `inspections` DISABLE KEYS */;
/*!40000 ALTER TABLE `inspections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inspections_file_keys`
--

DROP TABLE IF EXISTS `inspections_file_keys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inspections_file_keys` (
  `inspection_id` bigint NOT NULL,
  `file_keys_id` bigint NOT NULL,
  PRIMARY KEY (`inspection_id`,`file_keys_id`),
  UNIQUE KEY `UK_95aiewop0ea12ddby9kg5lgoa` (`file_keys_id`),
  CONSTRAINT `FK14a4bplpspitc2mlx0x5on11w` FOREIGN KEY (`file_keys_id`) REFERENCES `file_keys` (`id`),
  CONSTRAINT `FKh5r4a6phpi49s2muaxu229ukv` FOREIGN KEY (`inspection_id`) REFERENCES `inspections` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inspections_file_keys`
--

LOCK TABLES `inspections_file_keys` WRITE;
/*!40000 ALTER TABLE `inspections_file_keys` DISABLE KEYS */;
/*!40000 ALTER TABLE `inspections_file_keys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invitations`
--

DROP TABLE IF EXISTS `invitations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invitations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `club_id` bigint DEFAULT NULL,
  `invitedPerson_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbbp2c5f2otl9x284vpt5q88kj` (`club_id`),
  KEY `FK95d0khpv6f0uebvxse4beppl4` (`invitedPerson_id`),
  CONSTRAINT `FK95d0khpv6f0uebvxse4beppl4` FOREIGN KEY (`invitedPerson_id`) REFERENCES `members` (`id`),
  CONSTRAINT `FKbbp2c5f2otl9x284vpt5q88kj` FOREIGN KEY (`club_id`) REFERENCES `clubs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invitations`
--

LOCK TABLES `invitations` WRITE;
/*!40000 ALTER TABLE `invitations` DISABLE KEYS */;
/*!40000 ALTER TABLE `invitations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `new_area` varchar(255) DEFAULT NULL,
  `new_row_id` bigint DEFAULT NULL,
  `old_row_id` bigint DEFAULT NULL,
  `old_value` varchar(255) DEFAULT NULL,
  `target_column` varchar(255) DEFAULT NULL,
  `target_table` varchar(255) DEFAULT NULL,
  `txt_area` varchar(1500) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKefuitn33qkpy6nonixjyyu3u0` (`user_id`),
  CONSTRAINT `FKefuitn33qkpy6nonixjyyu3u0` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `id` bigint NOT NULL,
  `userName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES (75,'ekrem'),(76,'test3');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `non_conformity_file_keys`
--

DROP TABLE IF EXISTS `non_conformity_file_keys`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `non_conformity_file_keys` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `e_file_key_type` int DEFAULT NULL,
  `file_key` varchar(255) DEFAULT NULL,
  `nonconformityreport_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgleqenajaadkp1djtei09io40` (`nonconformityreport_id`),
  CONSTRAINT `FKgleqenajaadkp1djtei09io40` FOREIGN KEY (`nonconformityreport_id`) REFERENCES `non_conformity_reports` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `non_conformity_file_keys`
--

LOCK TABLES `non_conformity_file_keys` WRITE;
/*!40000 ALTER TABLE `non_conformity_file_keys` DISABLE KEYS */;
/*!40000 ALTER TABLE `non_conformity_file_keys` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `non_conformity_reports`
--

DROP TABLE IF EXISTS `non_conformity_reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `non_conformity_reports` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `completion_time` datetime(6) DEFAULT NULL,
  `e_status` varchar(255) DEFAULT NULL,
  `manager_text` varchar(1000) DEFAULT NULL,
  `note` varchar(1000) DEFAULT NULL,
  `termination_time` datetime(6) DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  `department_id` bigint DEFAULT NULL,
  `inspection_form_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKnioverphku4k84cu8vmt0fr68` (`company_id`),
  KEY `FKiu45pyudax84atoemuoxjiodq` (`department_id`),
  KEY `FK88iyym1wpr86v4tw44u9k41es` (`inspection_form_id`),
  KEY `FKksm2kst957b9ieghrr5gcg30q` (`user_id`),
  CONSTRAINT `FK88iyym1wpr86v4tw44u9k41es` FOREIGN KEY (`inspection_form_id`) REFERENCES `forms` (`id`),
  CONSTRAINT `FKiu45pyudax84atoemuoxjiodq` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`),
  CONSTRAINT `FKksm2kst957b9ieghrr5gcg30q` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKnioverphku4k84cu8vmt0fr68` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `non_conformity_reports`
--

LOCK TABLES `non_conformity_reports` WRITE;
/*!40000 ALTER TABLE `non_conformity_reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `non_conformity_reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `non_conformity_reports_attachments`
--

DROP TABLE IF EXISTS `non_conformity_reports_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `non_conformity_reports_attachments` (
  `non_conformity_report_id` bigint NOT NULL,
  `attachments_id` bigint NOT NULL,
  PRIMARY KEY (`non_conformity_report_id`,`attachments_id`),
  KEY `FKg3qs004miyoxlbsirycn7rq6h` (`attachments_id`),
  CONSTRAINT `FKfnakr97ux57oeyp3cteicty3x` FOREIGN KEY (`non_conformity_report_id`) REFERENCES `non_conformity_reports` (`id`),
  CONSTRAINT `FKg3qs004miyoxlbsirycn7rq6h` FOREIGN KEY (`attachments_id`) REFERENCES `file_keys` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `non_conformity_reports_attachments`
--

LOCK TABLES `non_conformity_reports_attachments` WRITE;
/*!40000 ALTER TABLE `non_conformity_reports_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `non_conformity_reports_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `non_conformity_reports_inspection_attachments`
--

DROP TABLE IF EXISTS `non_conformity_reports_inspection_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `non_conformity_reports_inspection_attachments` (
  `non_conformity_report_id` bigint NOT NULL,
  `inspection_attachments_id` bigint NOT NULL,
  PRIMARY KEY (`non_conformity_report_id`,`inspection_attachments_id`),
  KEY `FKrlqa4ay05mhpghq9wv6rh532y` (`inspection_attachments_id`),
  CONSTRAINT `FK7ah2f5tdg45u71nwm2rpdq5qx` FOREIGN KEY (`non_conformity_report_id`) REFERENCES `non_conformity_reports` (`id`),
  CONSTRAINT `FKrlqa4ay05mhpghq9wv6rh532y` FOREIGN KEY (`inspection_attachments_id`) REFERENCES `file_keys` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `non_conformity_reports_inspection_attachments`
--

LOCK TABLES `non_conformity_reports_inspection_attachments` WRITE;
/*!40000 ALTER TABLE `non_conformity_reports_inspection_attachments` DISABLE KEYS */;
/*!40000 ALTER TABLE `non_conformity_reports_inspection_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `non_conformity_reports_nonconformity_messages`
--

DROP TABLE IF EXISTS `non_conformity_reports_nonconformity_messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `non_conformity_reports_nonconformity_messages` (
  `non_conformity_report_id` bigint NOT NULL,
  `nonconformity_messages_id` bigint NOT NULL,
  UNIQUE KEY `UK_a8ajcpfw94eqkafjjm2lb359b` (`nonconformity_messages_id`),
  KEY `FK3waq7bsym5k9j925o2tjlrdr1` (`non_conformity_report_id`),
  CONSTRAINT `FK3waq7bsym5k9j925o2tjlrdr1` FOREIGN KEY (`non_conformity_report_id`) REFERENCES `non_conformity_reports` (`id`),
  CONSTRAINT `FKqavuic7115m3y54hg7k98ubei` FOREIGN KEY (`nonconformity_messages_id`) REFERENCES `nonconformity_message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `non_conformity_reports_nonconformity_messages`
--

LOCK TABLES `non_conformity_reports_nonconformity_messages` WRITE;
/*!40000 ALTER TABLE `non_conformity_reports_nonconformity_messages` DISABLE KEYS */;
/*!40000 ALTER TABLE `non_conformity_reports_nonconformity_messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nonconformity_message`
--

DROP TABLE IF EXISTS `nonconformity_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nonconformity_message` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `note` varchar(255) DEFAULT NULL,
  `note_time` datetime(6) DEFAULT NULL,
  `non_conformity_report_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9xs3rt5cmqapt9kh7855sh2mx` (`non_conformity_report_id`),
  KEY `FKgcwg283ut7si8qnsw8uj68skt` (`user_id`),
  CONSTRAINT `FK9xs3rt5cmqapt9kh7855sh2mx` FOREIGN KEY (`non_conformity_report_id`) REFERENCES `non_conformity_reports` (`id`),
  CONSTRAINT `FKgcwg283ut7si8qnsw8uj68skt` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nonconformity_message`
--

LOCK TABLES `nonconformity_message` WRITE;
/*!40000 ALTER TABLE `nonconformity_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `nonconformity_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `one_signal_push_notification`
--

DROP TABLE IF EXISTS `one_signal_push_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `one_signal_push_notification` (
  `id` bigint NOT NULL,
  `id_one_signal` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `one_signal_push_notification`
--

LOCK TABLES `one_signal_push_notification` WRITE;
/*!40000 ALTER TABLE `one_signal_push_notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `one_signal_push_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post_club_member`
--

DROP TABLE IF EXISTS `post_club_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post_club_member` (
  `post_club_id` bigint NOT NULL,
  `post_member_id` bigint NOT NULL,
  KEY `FKr9ut5l7sl9mn17jrx1kda6bi9` (`post_member_id`),
  KEY `FKjfj6tgo8oofygs8mvwvruaty4` (`post_club_id`),
  CONSTRAINT `FKjfj6tgo8oofygs8mvwvruaty4` FOREIGN KEY (`post_club_id`) REFERENCES `clubs` (`id`),
  CONSTRAINT `FKr9ut5l7sl9mn17jrx1kda6bi9` FOREIGN KEY (`post_member_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post_club_member`
--

LOCK TABLES `post_club_member` WRITE;
/*!40000 ALTER TABLE `post_club_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `post_club_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posts`
--

DROP TABLE IF EXISTS `posts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `club_id` bigint NOT NULL,
  `writer_id` bigint DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhj8dylgk572syl0gsa8of997a` (`club_id`),
  KEY `FKe5jhra8umbkca10l382395xtj` (`writer_id`),
  CONSTRAINT `FKe5jhra8umbkca10l382395xtj` FOREIGN KEY (`writer_id`) REFERENCES `members` (`id`),
  CONSTRAINT `FKhj8dylgk572syl0gsa8of997a` FOREIGN KEY (`club_id`) REFERENCES `clubs` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posts`
--

LOCK TABLES `posts` WRITE;
/*!40000 ALTER TABLE `posts` DISABLE KEYS */;
INSERT INTO `posts` VALUES (1,'texttexttexsfsdfsdfdt','title',1,75,NULL),(2,'texttexttexsfsdfsdfdt','title',1,75,NULL),(3,'texttexttexsfsdfsdfdt','title',1,75,NULL),(4,'texttexttexsfsdfsdfdt','title',1,75,NULL),(5,'texttexttexsfsdfsdfdt','title',1,76,NULL);
/*!40000 ALTER TABLE `posts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `push_notification_id`
--

DROP TABLE IF EXISTS `push_notification_id`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `push_notification_id` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `push_player_id` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjg72uy8bw0424nw5b5urj2gxd` (`user_id`),
  CONSTRAINT `FKjg72uy8bw0424nw5b5urj2gxd` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `push_notification_id`
--

LOCK TABLES `push_notification_id` WRITE;
/*!40000 ALTER TABLE `push_notification_id` DISABLE KEYS */;
/*!40000 ALTER TABLE `push_notification_id` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_options`
--

DROP TABLE IF EXISTS `question_options`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_options` (
  `id` bigint NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `version` int NOT NULL DEFAULT '1',
  `option_text` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `previous_version_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKce77ko0e5klj6i1re7ew1f7ve` (`previous_version_id`),
  CONSTRAINT `FKce77ko0e5klj6i1re7ew1f7ve` FOREIGN KEY (`previous_version_id`) REFERENCES `question_options` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_options`
--

LOCK TABLES `question_options` WRITE;
/*!40000 ALTER TABLE `question_options` DISABLE KEYS */;
/*!40000 ALTER TABLE `question_options` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_options_question`
--

DROP TABLE IF EXISTS `question_options_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question_options_question` (
  `options_id` bigint NOT NULL,
  `question_id` bigint NOT NULL,
  KEY `FKfhsga2t7nsws6refhgycfkdp0` (`question_id`),
  KEY `FKl2tyhl72esvxxclyiqf1tnun2` (`options_id`),
  CONSTRAINT `FKfhsga2t7nsws6refhgycfkdp0` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `FKl2tyhl72esvxxclyiqf1tnun2` FOREIGN KEY (`options_id`) REFERENCES `question_options` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_options_question`
--

LOCK TABLES `question_options_question` WRITE;
/*!40000 ALTER TABLE `question_options_question` DISABLE KEYS */;
/*!40000 ALTER TABLE `question_options_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questions`
--

DROP TABLE IF EXISTS `questions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `questions` (
  `id` bigint NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `version` int NOT NULL DEFAULT '1',
  `max_selection` int DEFAULT NULL,
  `min_selection` int DEFAULT NULL,
  `placeholder_text` varchar(255) DEFAULT NULL,
  `question_text` varchar(255) DEFAULT NULL,
  `question_type` varchar(255) DEFAULT NULL,
  `previous_version_id` bigint DEFAULT NULL,
  `template_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK28agd98h1sc0cuf8ldbfe056a` (`previous_version_id`),
  KEY `FKls1nurjikonm48wg13onwjw9h` (`template_id`),
  CONSTRAINT `FK28agd98h1sc0cuf8ldbfe056a` FOREIGN KEY (`previous_version_id`) REFERENCES `questions` (`id`),
  CONSTRAINT `FKls1nurjikonm48wg13onwjw9h` FOREIGN KEY (`template_id`) REFERENCES `form_templates` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questions`
--

LOCK TABLES `questions` WRITE;
/*!40000 ALTER TABLE `questions` DISABLE KEYS */;
/*!40000 ALTER TABLE `questions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `confirmation_token` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKj8rfw4x0wjjyibfqq566j4qng` (`user_id`),
  CONSTRAINT `FKj8rfw4x0wjjyibfqq566j4qng` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `email` varchar(50) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `f_name` varchar(255) DEFAULT NULL,
  `l_name` varchar(255) DEFAULT NULL,
  `password` varchar(120) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `usersgkno` varchar(255) DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  `department_id` bigint DEFAULT NULL,
  `manager_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  KEY `FKin8gn4o1hpiwe6qe4ey7ykwq7` (`company_id`),
  KEY `FKsbg59w8q63i0oo53rlgvlcnjq` (`department_id`),
  KEY `FK5p1ci5btqfwvtaqx5n2wxi182` (`manager_id`),
  CONSTRAINT `FK5p1ci5btqfwvtaqx5n2wxi182` FOREIGN KEY (`manager_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKin8gn4o1hpiwe6qe4ey7ykwq7` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`),
  CONSTRAINT `FKsbg59w8q63i0oo53rlgvlcnjq` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'bookend-book-club'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-07-06 19:58:14
