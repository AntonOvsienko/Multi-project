-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `doctor_appointment`
--

DROP TABLE IF EXISTS `doctor_appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_appointment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `case_record_id` int NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `complete` varchar(45) DEFAULT NULL,
  `name_staff_complete` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_id_case_records_idx` (`case_record_id`),
  CONSTRAINT `fk_id_case_records` FOREIGN KEY (`case_record_id`) REFERENCES `case_record` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_appointment`
--

LOCK TABLES `doctor_appointment` WRITE;
/*!40000 ALTER TABLE `doctor_appointment` DISABLE KEYS */;
INSERT INTO `doctor_appointment` VALUES (62,20,'Приём лекарств','','true','Аманда Янг(медсестра)'),(63,20,'Терапия','',NULL,NULL),(64,25,'Терапия','',NULL,NULL),(65,25,'Операция','',NULL,NULL),(66,25,'Приём лекарств','','true','Аманда Янг(медсестра)'),(67,27,'Терапия','',NULL,NULL),(68,18,'Терапия','','true','Макар Доценко(доктор)'),(69,18,'Подготовка к операции','','true','Аманда Янг(медсестра)'),(70,18,'Приём лекарств','','true','Аманда Янг(медсестра)'),(71,31,'Подготовка к операции','','true','Аманда Янг(медсестра)'),(72,31,'Операция','',NULL,NULL),(73,31,'Приём лекарств','',NULL,NULL),(96,40,'Приём лекарств','','true','Макар Доценко(доктор)'),(97,40,'Терапия','',NULL,NULL),(98,40,'Приём лекарств','',NULL,NULL),(99,27,'Приём лекарств','',NULL,NULL),(100,42,'Терапия','','true','Макар Доценко(доктор)'),(101,42,'Подготовка к операции','','true','Аманда Янг(медсестра)'),(102,42,'Операция','',NULL,NULL);
/*!40000 ALTER TABLE `doctor_appointment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-29 16:48:32
