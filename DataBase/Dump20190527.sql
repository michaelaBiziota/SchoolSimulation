-- MySQL dump 10.13  Distrib 8.0.15, for Win64 (x86_64)
--
-- Host: localhost    Database: schoolsystem
-- ------------------------------------------------------
-- Server version	8.0.15

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `assignments`
--

DROP TABLE IF EXISTS `assignments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `assignments` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `submissiondate` date DEFAULT NULL,
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignments`
--

LOCK TABLES `assignments` WRITE;
/*!40000 ALTER TABLE `assignments` DISABLE KEYS */;
INSERT INTO `assignments` VALUES (1,'oop-exercises','2019-05-07'),(2,'web-application-exercises','2019-04-10'),(3,'mvc web application','2019-06-12'),(5,'jdbc individual project','2019-05-26'),(7,'spring','2019-06-15');
/*!40000 ALTER TABLE `assignments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `course` (
  `c_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `stream` char(30) DEFAULT NULL,
  PRIMARY KEY (`c_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'oop','java'),(2,'web-application','java'),(3,'sql','c#');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coursesandassignments`
--

DROP TABLE IF EXISTS `coursesandassignments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `coursesandassignments` (
  `c_id` int(11) NOT NULL,
  `a_id` int(11) NOT NULL,
  PRIMARY KEY (`c_id`,`a_id`),
  KEY `fk` (`a_id`),
  CONSTRAINT `fk` FOREIGN KEY (`a_id`) REFERENCES `assignments` (`a_id`),
  CONSTRAINT `fk2` FOREIGN KEY (`c_id`) REFERENCES `course` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coursesandassignments`
--

LOCK TABLES `coursesandassignments` WRITE;
/*!40000 ALTER TABLE `coursesandassignments` DISABLE KEYS */;
INSERT INTO `coursesandassignments` VALUES (2,1),(1,2),(3,2),(1,3),(3,5),(2,7);
/*!40000 ALTER TABLE `coursesandassignments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `roles` (
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('headmaster'),('student'),('trainer');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `schedule` (
  `schedule_date` date NOT NULL,
  `c_id` int(11) NOT NULL,
  PRIMARY KEY (`schedule_date`,`c_id`),
  KEY `fk1` (`c_id`),
  CONSTRAINT `fk1` FOREIGN KEY (`c_id`) REFERENCES `course` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES ('2019-05-12',1),('2019-05-13',1),('2019-05-14',1),('2019-05-15',2),('2019-05-14',3),('2019-05-15',3);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `user_type` char(30) DEFAULT NULL,
  `password` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `fkur` (`user_type`),
  CONSTRAINT `fkur` FOREIGN KEY (`user_type`) REFERENCES `roles` (`role`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Spyros','Mavros','trainer','$2a$10$SPjJw5jsKcn/DYP5pJrIg.TECog/GV9.bRB20oeB15m6vFKgThh36'),(4,'Kostas','Michalakos','student','$2a$10$iVFjUsgYsbqgBumP0rQcQOoGDsazCZBk03cwP0pmtmuy39FLVHvQa'),(5,'John','Papadopoulos','headmaster','$2a$10$BAdzMCAEMN6r2w7IoRkTt.Jl6TKpLlp/wMfRWmfg.fBk11CMrg6Jq'),(14,'panagiwtis','panou','student','$2a$10$iVFjUsgYsbqgBumP0rQcQOoGDsazCZBk03cwP0pmtmuy39FLVHvQa'),(17,'George','Pasparakis','trainer','$2a$10$SPjJw5jsKcn/DYP5pJrIg.TECog/GV9.bRB20oeB15m6vFKgThh36');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersandassignments`
--

DROP TABLE IF EXISTS `usersandassignments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `usersandassignments` (
  `user_id` int(11) NOT NULL,
  `a_id` int(11) NOT NULL,
  `mark` decimal(5,2) DEFAULT NULL,
  `submitted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`a_id`),
  KEY `fkaid` (`a_id`),
  CONSTRAINT `fkaid` FOREIGN KEY (`a_id`) REFERENCES `assignments` (`a_id`),
  CONSTRAINT `fkuid` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersandassignments`
--

LOCK TABLES `usersandassignments` WRITE;
/*!40000 ALTER TABLE `usersandassignments` DISABLE KEYS */;
INSERT INTO `usersandassignments` VALUES (4,1,NULL,1),(4,2,NULL,NULL),(4,5,NULL,NULL),(4,7,NULL,NULL),(14,1,NULL,NULL),(14,2,NULL,NULL),(14,3,NULL,1),(14,7,NULL,NULL);
/*!40000 ALTER TABLE `usersandassignments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userspercourse`
--

DROP TABLE IF EXISTS `userspercourse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `userspercourse` (
  `user_id` int(11) NOT NULL,
  `c_id` int(11) NOT NULL,
  PRIMARY KEY (`c_id`,`user_id`),
  KEY `fk_c_id` (`user_id`),
  CONSTRAINT `fk_c_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `fk_tr_id` FOREIGN KEY (`c_id`) REFERENCES `course` (`c_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userspercourse`
--

LOCK TABLES `userspercourse` WRITE;
/*!40000 ALTER TABLE `userspercourse` DISABLE KEYS */;
INSERT INTO `userspercourse` VALUES (1,1),(1,2),(4,2),(4,3),(14,1),(14,2),(17,2),(17,3);
/*!40000 ALTER TABLE `userspercourse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-27 20:03:06
