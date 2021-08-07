CREATE DATABASE  IF NOT EXISTS `registrospese` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `registrospese`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: 127.0.0.1    Database: registrospese
-- ------------------------------------------------------
-- Server version	5.6.21

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
-- Table structure for table `spese`
--

DROP TABLE IF EXISTS `spese`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `spese` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  `categoria` varchar(45) NOT NULL,
  `esborso` int(11) NOT NULL,
  `data` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spese`
--

LOCK TABLES `spese` WRITE;
/*!40000 ALTER TABLE `spese` DISABLE KEYS */;
INSERT INTO `spese` VALUES (1,'supermecato','cibo',15,'2017-07-25'),(5,'sigarette','tabacchi',5,'2021-03-01'),(6,'maglietta sportiva','abbigliamento',15,'2021-02-02'),(8,'merenda','supermercato',4,'2021-03-11'),(9,'pizza','cibo',8,'2021-03-02'),(11,'ricarica telefonica','tabacchi',20,'2021-03-10'),(12,'jeans','abbigliamento',45,'2021-03-26'),(13,'libro c++','universita',25,'2021-03-03'),(14,'test','tabacchi',50,'2021-01-01'),(15,'quaderni','universita',10,'2021-03-05'),(16,'scarpe','abbigliamento',90,'2021-03-07'),(17,'spesa settimanale','supermercato',50,'2021-01-07'),(18,'lavaggio auto','auto',5,'2021-04-01'),(19,'revisione','auto',80,'2021-04-05');
/*!40000 ALTER TABLE `spese` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-08 19:08:55
