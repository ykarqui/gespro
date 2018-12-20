-- MySQL dump 10.13  Distrib 5.7.22, for Linux (x86_64)
--
-- Host: localhost    Database: gespro
-- ------------------------------------------------------
-- Server version	5.7.22-0ubuntu0.17.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sprint_task`
--

DROP TABLE IF EXISTS `sprint_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sprint_task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `created` datetime DEFAULT NULL,
  `estimate` int(11) DEFAULT NULL,
  `modified` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `list_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `FKilq29q5f5jdao275l2hmxv8ms` (`list_id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sprint_task`
--

LOCK TABLES `sprint_task` WRITE;
/*!40000 ALTER TABLE `sprint_task` DISABLE KEYS */;
INSERT INTO `sprint_task` VALUES (11,'2018-12-20 15:09:22',12,'2018-12-20 15:09:22','Learn TS','Medium',1),(15,'2018-12-20 15:11:03',3,'2018-12-20 15:11:16','Add css','Low',3),(9,'2018-12-20 15:08:47',7,'2018-12-20 15:08:47','Documentation','Medium',1),(12,'2018-12-20 15:09:34',1,'2018-12-20 16:05:36','Meeting','High',3),(13,'2018-12-20 15:10:02',1,'2018-12-20 15:10:02','Evaluate progress','Medium',1),(18,'2018-12-20 16:10:26',1,'2018-12-20 16:10:26','Meeting dev team','Medium',1),(19,'2018-12-20 16:13:04',2,'2018-12-20 16:13:04','Make all estimations','Low',1),(20,'2018-12-20 17:27:51',2,'2018-12-20 17:29:31','Final','Low',4);
/*!40000 ALTER TABLE `sprint_task` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-20 17:37:13
