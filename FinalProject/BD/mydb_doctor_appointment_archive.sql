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
-- Table structure for table `doctor_appointment_archive`
--

DROP TABLE IF EXISTS `doctor_appointment_archive`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_appointment_archive` (
  `id` int NOT NULL AUTO_INCREMENT,
  `case_record_archive_id` int NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `name_staff_complete` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_doctor_appointment_archive_case_record_archive1_idx` (`case_record_archive_id`),
  CONSTRAINT `fk_doctor_appointment_archive_case_record_archive1` FOREIGN KEY (`case_record_archive_id`) REFERENCES `case_record_archive` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_appointment_archive`
--

LOCK TABLES `doctor_appointment_archive` WRITE;
/*!40000 ALTER TABLE `doctor_appointment_archive` DISABLE KEYS */;
INSERT INTO `doctor_appointment_archive` VALUES (90,22,'Приём лекарств','','Вася Петров(доктор)'),(91,22,'Терапия','','Вася Петров(доктор)'),(92,23,'Приём лекарств','','Аманда Янг(медсестра)'),(93,23,'Терапия','','Арсений Шульц(доктор)'),(94,23,'Приём лекарств','','Арсений Шульц(доктор)'),(95,24,'Терапия','','Макар Доценко(доктор)'),(96,24,'Приём лекарств','','Аманда Янг(медсестра)'),(97,25,'Терапия','','Макар Доценко(доктор)'),(98,25,'Подготовка к операции','','Аманда Янг(медсестра)'),(99,25,'Приём лекарств','','Аманда Янг(медсестра)');
/*!40000 ALTER TABLE `doctor_appointment_archive` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-09-29 16:48:35
