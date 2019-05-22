-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 178.128.213.130    Database: comic
-- ------------------------------------------------------
-- Server version	5.7.23

CREATE DATABASE  IF NOT EXISTS `comic`;
USE `comic`;

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `user_id` int(11) NOT NULL,
  `permission` int(11) DEFAULT '1',
  `fb_share_url` varchar(500) DEFAULT NULL,
  `fb_share_count` int(11) DEFAULT NULL,
  `fb_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'Avengers',13,1,NULL,NULL,NULL),(2,'Tom and Jerry',13,2,NULL,NULL,NULL),(3,'Spiderman',12,1,NULL,NULL,NULL),(4,'Starwars',12,2,NULL,NULL,NULL),(5,'Gone with wind',13,2,NULL,NULL,NULL);
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(200) DEFAULT NULL,
  `key_value` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (1,'smtp.host','smtp.gmail.com'),(2,'smtp.port','587'),(3,'smtp.user','paulcody8290'),(4,'smtp.password','12345678aA@'),(5,'smtp.from.address','paulcody8290@gmail.com'),(6,'smtp.from.name','Comic App'),(7,'smtp.subject.email_validate','[Comic app] Validate email address'),(8,'smtp.subject.reset_password','[Comic app] Reset password'),(9,'smtp.content.email_validate','Please click on the link below to verify your email address: </br> <a href=\"@@link@@\">Click here</a>'),(10,'smtp.content.reset_password','Your new password is: @@password@@'),(11,'smtp.content.type','HTML'),(12,'smtp.transport.strategy','SMTP_TLS'),(13,'server.web.homepage','http://localhost:4200/#/test/test1'),(15,'server.api.active_user','http://127.0.0.1:8290/user/active/'),(16,'server.session_time_out','30'),(17,'server.session_expire','1440'),(18,'server.web.validate_email','http://localhost:4200/#/auth/email-active/?token=@@token@@'),(19,'server.secret.login_key','logincomic@123'),(20,'server.secret.active_key','validatecomic@123');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(200) NOT NULL COMMENT 'email',
  `password` varchar(200) NOT NULL,
  `first_name` varchar(200) DEFAULT NULL,
  `last_name` varchar(200) DEFAULT NULL,
  `group_id` int(11) NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '0',
  `facebook_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'test@gmail.com','731A524117B7FCCA6C9BA99DFE8CFAE0','nguyen1','Van AAAAAABBBB',1,1,NULL),(8,'test2@gmail.com','123445','nguyen','Van',1,0,NULL),(9,'test3@gmail.com','123445','nguyen1','Van AAAAAA',1,0,NULL),(10,'paullennon1982@gmail.com','123456','Ha','Dat',1,0,NULL),(12,'test4@gmail.com','BB4FCA9715AD938F2C8DB65A1E15BFC6','nguyen','Van B',1,0,NULL),(13,'tarrega1982@yahoo.com','C4CA4238A0B923820DCC509A6F75849B','Ha','van Dat',3,1,'10156612817862295'),(14,'trucphuongsg@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','HNC5126 TRUC PHUONG','HNC5126 TRUC PHUONG',1,0,NULL),(15,'test10@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','haha','hehehe',1,0,NULL),(16,'anh.phan@ascendcorp.com','C4CA4238A0B923820DCC509A6F75849B','Phan','Anh',1,0,NULL),(17,'phanvietanh90@gmail.com','2732DFB634B061076FC4462D2703BE11','Viet','Anh',1,0,NULL),(18,'test11@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','demo','test',1,0,NULL),(19,'test12@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','demo','test',1,0,NULL),(20,'test13@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','Viet','Anh',1,0,NULL),(21,'dat.ha@effecttdb.com','C4CA4238A0B923820DCC509A6F75849B','Ha','Dat',3,0,NULL),(22,'test14@gmail.com','123445','nguyen1','Van AAAAAABBBBCCCCCCC',1,0,NULL),(23,'test15@gmail.com','F59BD65F7EDAFB087A81D4DCA06C4910','Viet','Anh',1,0,NULL),(24,'test16@gmail.com','F59BD65F7EDAFB087A81D4DCA06C4910','Viet','Anh',1,0,NULL),(25,'test17@gmail.com','F59BD65F7EDAFB087A81D4DCA06C4910','Viet','Anh',1,0,NULL),(26,'test18@gmail.com','F59BD65F7EDAFB087A81D4DCA06C4910','Viet','Anh',1,0,NULL),(27,'test19@gmail.com','51596103F385101CE16BCC58B3C3B85B','nguyen1','Van AAAAAABBBBCCCCCCC',1,0,NULL),(28,'test20@gmail.com','51596103F385101CE16BCC58B3C3B85B','Viet','Anh',1,0,NULL),(29,'test21@gmail.com','51596103F385101CE16BCC58B3C3B85B','Viet','Anh',1,0,NULL),(30,'test22@gmail.com','F59BD65F7EDAFB087A81D4DCA06C4910','nguyen1','Van AAAAAABBBBCCCCCCC',1,0,NULL),(31,'test23@gmail.com','1234567','Viet','Anh',1,0,NULL),(32,'test24@gmail.com','25D55AD283AA400AF464C76D713C07AD','nguyen111111111','Van AAAAAABBBBCCCCCCC',1,0,NULL),(33,'test25@gmail.com','FCEA920F7412B5DA7BE0CF42B8C93759','Viet','Anh',1,0,NULL),(34,'test26@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','viet anh','phan',3,0,'10205045474691883'),(35,'dat.ha.1@dat.vn','202CB962AC59075B964B07152D234B70','Ha','Van Dat',3,0,NULL),(36,'dat.ha.2@dat.vn','C4CA4238A0B923820DCC509A6F75849B','Ha Van ','Dat',2,0,NULL),(37,'test27@gmail.com','FCEA920F7412B5DA7BE0CF42B8C93759','Viet','Anh',1,0,NULL),(38,'test28@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','Viet','Anh',1,0,'1021806374657875');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_group` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
INSERT INTO `user_group` VALUES (1,'student',NULL),(2,'fresher',NULL),(3,'profesional',NULL);
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chapter_id` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

--
-- Table structure for table `chapter`
--

DROP TABLE IF EXISTS `chapter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chapter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sub_topic`
--

DROP TABLE IF EXISTS `sub_topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `topic_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

-- Dump completed on 2018-11-13 23:45:01
