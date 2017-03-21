-- MySQL dump 10.13  Distrib 5.5.19, for Win64 (x86)
--
-- Host: localhost    Database: test_db
-- ------------------------------------------------------
-- Server version	5.5.19

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
-- Table structure for table `databasechangelog`
--

DROP TABLE IF EXISTS `databasechangelog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangelog`
--

LOCK TABLES `databasechangelog` WRITE;
/*!40000 ALTER TABLE `databasechangelog` DISABLE KEYS */;
INSERT INTO `databasechangelog` VALUES ('create user table','Nikolay','src/db/liquibase/1.0/2015-12-17-init.xml','2017-03-21 18:13:27',1,'EXECUTED','7:5e8f3cf9d8cdb620972975b5f080ae2c','createTable','',NULL,'3.4.1',NULL,NULL),('1382006649218-6','Nikolay (generated)','src/db/liquibase/1.0/2015-12-17-init.xml','2017-03-21 18:13:28',2,'EXECUTED','7:2c3caacaf9595553d9280039803357e1','createIndex','',NULL,'3.4.1',NULL,NULL),('rename password column','Nikolay','src/db/liquibase/1.0/2015-12-17-init.xml','2017-03-21 18:13:29',3,'EXECUTED','7:955770fb42569ca372ef0dced24a9c99','addColumn, sql, dropColumn, renameColumn','',NULL,'3.4.1',NULL,NULL),('create table roles','Nikolay','src/db/liquibase/1.0/2015-12-21-authentication.xml','2017-03-21 18:13:29',4,'EXECUTED','7:f73f9a2c1ee488942f5d24decdcf6e87','createTable','',NULL,'3.4.1',NULL,NULL),('create table users_roles','Nikolay','src/db/liquibase/1.0/2015-12-21-authentication.xml','2017-03-21 18:13:29',5,'EXECUTED','7:2d468a4fe88c94095ed1eec77f530bb5','createTable','',NULL,'3.4.1',NULL,NULL),('create users_roles unique index','Nikolay','src/db/liquibase/1.0/2015-12-21-authentication.xml','2017-03-21 18:13:29',6,'EXECUTED','7:89734675b487e93f957715006130fa2a','createIndex','',NULL,'3.4.1',NULL,NULL),('add user locale','Nikolay','src/db/liquibase/1.0/2016-09-12-add-user-locale.xml','2017-03-21 18:13:30',7,'EXECUTED','7:675a70acbfa67bc6941c9279615b9149','addColumn','',NULL,'3.4.1',NULL,NULL),('create table products','Nikolay','src/db/liquibase/1.0/2016-10-19-add-product.xml','2017-03-21 18:13:30',8,'EXECUTED','7:18a0c6d6d0d6ed028f504448d68b4536','createTable','',NULL,'3.4.1',NULL,NULL),('add deleted column to product','Nikolay','src/db/liquibase/1.0/2016-10-19-add-product.xml','2017-03-21 18:13:31',9,'EXECUTED','7:360165389d3a55ee5c4b6c11691ae5e2','addColumn, addNotNullConstraint, addDefaultValue','',NULL,'3.4.1',NULL,NULL),('add default user','Nikolay','src/db/liquibase/1.0/2016-10-27-add-default-user.xml','2017-03-21 18:13:31',10,'EXECUTED','7:bddce50b2c06b29c949cee4ea7fba187','sql','',NULL,'3.4.1',NULL,NULL),('insert default roles','Nikolay','src/db/liquibase/1.0/2016-10-27-add-default-user.xml','2017-03-21 18:13:31',11,'EXECUTED','7:df5ee64dc89f05eac90ebbe083061d40','sql','',NULL,'3.4.1',NULL,NULL),('link default roles and users','Nikolay','src/db/liquibase/1.0/2016-10-27-add-default-user.xml','2017-03-21 18:13:31',12,'EXECUTED','7:6b32c4a001edcba97f652bf6b2bf0bae','sql','',NULL,'3.4.1',NULL,NULL),('change password','Nikolay','src/db/liquibase/1.0/2016-10-27-add-default-user.xml','2017-03-21 18:13:31',13,'EXECUTED','7:dc8696a045fd2bcdb9a33318486b7272','sql','',NULL,'3.4.1',NULL,NULL),('create job type','Nikolay','src/db/liquibase/1.0/2016-add-job-type.xml','2017-03-21 18:13:31',14,'EXECUTED','7:3b7aa8b7706799bb60be4c247ed411f1','createTable','',NULL,'3.4.1',NULL,NULL),('add deleted column to job_type','Nikolay','src/db/liquibase/1.0/2016-add-job-type.xml','2017-03-21 18:13:32',15,'EXECUTED','7:994413cfd419fe2d4443d9d4dcc659f8','addColumn, addNotNullConstraint','',NULL,'3.4.1',NULL,NULL),('create job sub type','Nikolay','src/db/liquibase/1.0/2016-10-25-add-job-subtype.xml','2017-03-21 18:13:33',16,'EXECUTED','7:2431b864435b9369fbd6d43866ae4bc5','createTable, addForeignKeyConstraint','',NULL,'3.4.1',NULL,NULL),('add deleted column to job_subtype','Nikolay','src/db/liquibase/1.0/2016-10-25-add-job-subtype.xml','2017-03-21 18:13:33',17,'EXECUTED','7:571613fcfdb16ae7151549a6e319ac84','addColumn, addNotNullConstraint','',NULL,'3.4.1',NULL,NULL),('create job sub type cost','Nikolay','src/db/liquibase/1.0/2016-10-31-add-job-subtype-cost.xml','2017-03-21 18:13:34',18,'EXECUTED','7:dec5c48b3c0fc5f3292a49992373c223','createTable, addForeignKeyConstraint (x2)','',NULL,'3.4.1',NULL,NULL),('add deleted column to job_subtype_cost','Nikolay','src/db/liquibase/1.0/2016-10-31-add-job-subtype-cost.xml','2017-03-21 18:13:36',19,'EXECUTED','7:a529f9efe1ac394babe06c50fe1c9cbc','addColumn, addNotNullConstraint','',NULL,'3.4.1',NULL,NULL),('create job table','Nikolay','src/db/liquibase/1.0/2016-11-07-add-job.xml','2017-03-21 18:13:37',20,'EXECUTED','7:bc8b1c31e411b188934de5d586e95198','createTable, addForeignKeyConstraint (x2)','',NULL,'3.4.1',NULL,NULL),('add external column external_id to products','Nikolay','src/db/liquibase/1.0/2016-12-05-add-external-columns.xml','2017-03-21 18:13:37',21,'EXECUTED','7:ee7f89606679be381e224b47366a0382','addColumn','',NULL,'3.4.1',NULL,NULL),('add external column external_id to job type','Nikolay','src/db/liquibase/1.0/2016-12-05-add-external-columns.xml','2017-03-21 18:13:37',22,'EXECUTED','7:ccb316767d16d36a99738fab960138a0','addColumn','',NULL,'3.4.1',NULL,NULL),('add external column external_id to job subtype','Nikolay','src/db/liquibase/1.0/2016-12-05-add-external-columns.xml','2017-03-21 18:13:38',23,'EXECUTED','7:b9beff9f98808a4c366ca1392c358307','addColumn','',NULL,'3.4.1',NULL,NULL),('add external column external_code to products','Nikolay','src/db/liquibase/1.0/2016-12-05-add-external-columns.xml','2017-03-21 18:13:38',24,'EXECUTED','7:9143a11d840bc4b65b8d0866706767df','addColumn','',NULL,'3.4.1',NULL,NULL),('add external column external_code to job type','Nikolay','src/db/liquibase/1.0/2016-12-05-add-external-columns.xml','2017-03-21 18:13:38',25,'EXECUTED','7:152be949e3487b610fc68849393f1c6e','addColumn','',NULL,'3.4.1',NULL,NULL),('add external column external_code to job subtype','Nikolay','src/db/liquibase/1.0/2016-12-05-add-external-columns.xml','2017-03-21 18:13:39',26,'EXECUTED','7:b473db10516de478d2e42717b9bb0a8a','addColumn','',NULL,'3.4.1',NULL,NULL),('add complete_date index to job','Nikolay','src/db/liquibase/1.0/2016-12-27-add-complete-date-index.xml','2017-03-21 18:13:39',27,'EXECUTED','7:9aec6e244f88bb01dcc2f13bab59abf8','createIndex','',NULL,'3.4.1',NULL,NULL),('add use_in_salary_report column','Nikolay','src/db/liquibase/1.0/2017-01-15-add-use_in_salary_report.xml','2017-03-21 18:13:39',28,'EXECUTED','7:8005cd62a3a63f3222e45772a82e0b01','addColumn','',NULL,'3.4.1',NULL,NULL),('modify number column','Nikolay','src/db/liquibase/1.0/2017-01-15-modify_number_type.xml','2017-03-21 18:13:40',29,'EXECUTED','7:d1ef65a2ef0216c79b38a7bde818a552','modifyDataType','',NULL,'3.4.1',NULL,NULL),('create progress table','Nikolay','src/db/liquibase/1.0/2017-01-18-add-progress.xml','2017-03-21 18:13:40',30,'EXECUTED','7:9657448b43c52121f7bc299029c18116','createTable','',NULL,'3.4.1',NULL,NULL),('add-report_group','Nikolay','src/db/liquibase/1.0/2017-01-29-add-report_group.xml','2017-03-21 18:13:40',31,'EXECUTED','7:f964b137160eaf3ee32512ff5d6a2b9f','addColumn','',NULL,'3.4.1',NULL,NULL),('create settings','Nikolay','src/db/liquibase/1.0/2017-03-10-create-settings.xml','2017-03-21 18:13:41',32,'EXECUTED','7:616a3a3fb6bbe48c779ffaebfb16b77e','createTable','',NULL,'3.4.1',NULL,NULL),('add user version','Nikolay','src/db/liquibase/1.0/2017-03-20-add-version.xml','2017-03-21 18:13:41',33,'EXECUTED','7:74ab8825f36be726fe22533bd2e78170','addColumn','',NULL,'3.4.1',NULL,NULL),('add user version init version','Nikolay','src/db/liquibase/1.0/2017-03-20-add-version.xml','2017-03-21 18:13:41',34,'EXECUTED','7:05d5d5f65f89c231afdf969566589e25','sql','',NULL,'3.4.1',NULL,NULL),('create sent entity table','Nikolay','src/db/liquibase/1.0/2017-03-20-create-sent-entity.xml','2017-03-21 18:13:41',35,'EXECUTED','7:6580f8d3050b28ff0defe49473e12d67','createTable','',NULL,'3.4.1',NULL,NULL),('add web column to users','Nikolay','src/db/liquibase/1.0/2017-03-21-add-web-user-attribute.xml','2017-03-21 18:13:42',36,'EXECUTED','7:4fd1ea7773ae0782d5a66b0eb2cd3e1b','addColumn','',NULL,'3.4.1',NULL,NULL);
/*!40000 ALTER TABLE `databasechangelog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,'\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job`
--

DROP TABLE IF EXISTS `job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `number` int(11) DEFAULT NULL,
  `complete_date` datetime NOT NULL,
  `creation_date` datetime NOT NULL,
  `job_type_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `job_to_job_type_FK` (`job_type_id`),
  KEY `job_to_product_FK` (`product_id`),
  KEY `job_complete_date` (`complete_date`),
  CONSTRAINT `job_to_job_type_FK` FOREIGN KEY (`job_type_id`) REFERENCES `job_type` (`id`),
  CONSTRAINT `job_to_product_FK` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job`
--

LOCK TABLES `job` WRITE;
/*!40000 ALTER TABLE `job` DISABLE KEYS */;
/*!40000 ALTER TABLE `job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_subtype`
--

DROP TABLE IF EXISTS `job_subtype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_subtype` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `job_type_id` bigint(20) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `external_id` varchar(255) DEFAULT NULL,
  `external_code` varchar(255) DEFAULT NULL,
  `use_in_salary_report` bit(1) DEFAULT b'1',
  `report_group` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `job_subtype_to_job_type_FK` (`job_type_id`),
  CONSTRAINT `job_subtype_to_job_type_FK` FOREIGN KEY (`job_type_id`) REFERENCES `job_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_subtype`
--

LOCK TABLES `job_subtype` WRITE;
/*!40000 ALTER TABLE `job_subtype` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_subtype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_subtype_cost`
--

DROP TABLE IF EXISTS `job_subtype_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_subtype_cost` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cost` double NOT NULL,
  `job_subtype_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `job_subtype_cost_to_job_subtype_FK` (`job_subtype_id`),
  KEY `job_subtype_cost_to_product_FK` (`product_id`),
  CONSTRAINT `job_subtype_cost_to_job_subtype_FK` FOREIGN KEY (`job_subtype_id`) REFERENCES `job_subtype` (`id`),
  CONSTRAINT `job_subtype_cost_to_product_FK` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_subtype_cost`
--

LOCK TABLES `job_subtype_cost` WRITE;
/*!40000 ALTER TABLE `job_subtype_cost` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_subtype_cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_type`
--

DROP TABLE IF EXISTS `job_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `order_number` int(11) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `external_id` varchar(255) DEFAULT NULL,
  `external_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_type`
--

LOCK TABLES `job_type` WRITE;
/*!40000 ALTER TABLE `job_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `external_id` varchar(255) DEFAULT NULL,
  `external_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `progress`
--

DROP TABLE IF EXISTS `progress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `progress` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `state` varchar(255) NOT NULL,
  `value` int(11) NOT NULL,
  `last_update` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progress`
--

LOCK TABLES `progress` WRITE;
/*!40000 ALTER TABLE `progress` DISABLE KEYS */;
/*!40000 ALTER TABLE `progress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_REGULAR');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sent_entity`
--

DROP TABLE IF EXISTS `sent_entity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sent_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `send_date` datetime NOT NULL,
  `entity_id` bigint(20) NOT NULL,
  `action_type` varchar(20) NOT NULL,
  `entity_type` varchar(20) NOT NULL,
  `sent_version` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sent_entity`
--

LOCK TABLES `sent_entity` WRITE;
/*!40000 ALTER TABLE `sent_entity` DISABLE KEYS */;
/*!40000 ALTER TABLE `sent_entity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `settings`
--

DROP TABLE IF EXISTS `settings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `settings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `settings`
--

LOCK TABLES `settings` WRITE;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) NOT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `password` varchar(1000) DEFAULT NULL,
  `locale` varchar(10) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  `web` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UQ__users__login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'root','root','cm9vdA==','ru_RU',0,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  UNIQUE KEY `users__roles_idx` (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-21 18:13:44
