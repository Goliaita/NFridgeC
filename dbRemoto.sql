-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: localhost    Database: dbremoto
-- ------------------------------------------------------
-- Server version	5.7.17-0ubuntu0.16.04.1

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
-- Table structure for table `catalogue`
--

DROP TABLE IF EXISTS `catalogue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `catalogue` (
  `nfcId` varchar(15) NOT NULL,
  `NomeProdotto` varchar(45) NOT NULL,
  `Category` varchar(60) DEFAULT NULL,
  `Produttore` varchar(45) NOT NULL,
  `Scadenza` date NOT NULL,
  PRIMARY KEY (`nfcId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `catalogue`
--

LOCK TABLES `catalogue` WRITE;
/*!40000 ALTER TABLE `catalogue` DISABLE KEYS */;
INSERT INTO `catalogue` VALUES ('04533182C22980','Acqua','Acqua','San Pellegrino','2015-09-04'),('04543182C22980','Fanta','Bibita','Coca Cola','2015-09-15'),('045AD61AB62880','Pecorino romano','Formaggio','Seppunisi','2015-11-17'),('045BD51AB62880','Yogurt','Yogurt','Yomo','2015-11-29'),('045CD61AB62880','Uova','Uova','Serio','2015-10-26'),('045DD61AB62880','Ketchup','Salse','Kraft','2015-11-05'),('0489DD7AC22980','Montepulciano','Vini','Cantine Carrisi','2015-10-03'),('048ADD7AC22980','Sottilette','Formaggio','Kraft','2015-10-15'),('048BDD7AC22980','CocaCola','Bibita','Coca Cola','2015-09-20'),('048CDD7AC22980','Latte','Latte','Perla','2015-09-28');
/*!40000 ALTER TABLE `catalogue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fridge`
--

DROP TABLE IF EXISTS `fridge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fridge` (
  `idFridge` int(11) NOT NULL,
  PRIMARY KEY (`idFridge`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fridge`
--

LOCK TABLES `fridge` WRITE;
/*!40000 ALTER TABLE `fridge` DISABLE KEYS */;
INSERT INTO `fridge` VALUES (10),(20),(30);
/*!40000 ALTER TABLE `fridge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fridge_has_catalogue`
--

DROP TABLE IF EXISTS `fridge_has_catalogue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fridge_has_catalogue` (
  `catalogue_nfcId` varchar(15) NOT NULL,
  `fridge_idFridge` int(11) NOT NULL,
  PRIMARY KEY (`catalogue_nfcId`,`fridge_idFridge`),
  KEY `fk_fridge_has_catalogue_fridge1_idx` (`fridge_idFridge`),
  CONSTRAINT `fk_fridge_has_catalogue_fridge1` FOREIGN KEY (`fridge_idFridge`) REFERENCES `fridge` (`idFridge`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fridge_has_catalogue`
--

LOCK TABLES `fridge_has_catalogue` WRITE;
/*!40000 ALTER TABLE `fridge_has_catalogue` DISABLE KEYS */;
INSERT INTO `fridge_has_catalogue` VALUES ('04533182C22980',10),('04543182C22980',10),('0489DD7AC22980',10),('048BDD7AC22980',10);
/*!40000 ALTER TABLE `fridge_has_catalogue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(45) NOT NULL,
  `Password` varchar(45) NOT NULL,
  PRIMARY KEY (`idUser`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Marco','ciao'),(2,'Cosimo','ciao'),(3,'Luca','ciao');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_fridge`
--

DROP TABLE IF EXISTS `user_has_fridge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_fridge` (
  `User_idUser` int(11) NOT NULL,
  `Fridge_idFridge` int(11) NOT NULL,
  PRIMARY KEY (`User_idUser`,`Fridge_idFridge`),
  KEY `fk_User_has_Fridfge_Fridge1_idx` (`Fridge_idFridge`),
  CONSTRAINT `fk_User_has_Fridfge_Fridge1` FOREIGN KEY (`Fridge_idFridge`) REFERENCES `fridge` (`idFridge`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Fridfge_User1` FOREIGN KEY (`User_idUser`) REFERENCES `user` (`idUser`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_fridge`
--

LOCK TABLES `user_has_fridge` WRITE;
/*!40000 ALTER TABLE `user_has_fridge` DISABLE KEYS */;
INSERT INTO `user_has_fridge` VALUES (1,10),(2,20),(3,30);
/*!40000 ALTER TABLE `user_has_fridge` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-28 21:57:50
