
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

LOCK TABLES `asset` WRITE;
/*!40000 ALTER TABLE `asset` DISABLE KEYS */;
INSERT INTO `asset` VALUES (1,0,'FooPlayer1',_binary '',_binary '\0','jpeck/local/userAI/1.0-SNAPSHOT/userAI-1.0-SNAPSHOT.jar!userAI.player1.FooPlayer',NULL,2),(2,0,'FooPlayer2',_binary '',_binary '\0','jpeck/local/userAI/1.0-SNAPSHOT/userAI-1.0-SNAPSHOT.jar!userAI.player2.FooPlayer2',NULL,3),(3,0,'NormalAssetForMember1',_binary '',_binary '\0','1/fooPlayer-1.0.0.RELEASE.jar!userAI.player1.FooPlayer',NULL,2),(4,0,'HexGaia-DeleteThisRecord',_binary '\0',_binary '\0','4/hexagen-1.0.0.RELEASE.jar!gamma.player.Simple_Gaia',NULL,4),(5,0,'RiskPlayer2',_binary '',_binary '','2/riskPlayer-1.0.0.RC1.jar!userAI.player2.risk.RiskPlayer2',NULL,3);
/*!40000 ALTER TABLE `asset` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `game_class` WRITE;
/*!40000 ALTER TABLE `game_class` DISABLE KEYS */;
INSERT INTO `game_class` VALUES (1,0,'Hexagen0a','0.0.0.A','gamma/gamma-main/1.0-SNAPSHOT/gamma-main-1.0-SNAPSHOT.jar!gamma.main.GammaLauncher','gamma/gamma-si/1.0-SNAPSHOT/gamma-si-1.0-SNAPSHOT.jar!gamma.games.hexagen.Hexagen','/docs/for/Hexagen0a',NULL,'2017-02-26 12:01:46.000000'),(2,3,'Hexagen0b','0.0.0.B','gamma/gamma-main/1.0-SNAPSHOT/gamma-main-1.0-SNAPSHOT.jar!gamma.main.GammaLauncher','gamma/gamma-si/1.0-SNAPSHOT/gamma-si-1.0-SNAPSHOT.jar!gamma.games.hexagen.Hexagen','/docs/for/Hexagen0b',NULL,'2017-02-26 12:01:46.000000'),(3,1,'HexagenJar0a','0.0.0.A','gamma/gamma-main/1.0-SNAPSHOT/gamma-main-1.0-SNAPSHOT.jar!gamma.main.GammaLauncher','gamma/gamma-si/1.0-SNAPSHOT/gamma-si-1.0-SNAPSHOT.jar!gamma.games.hexagen.Hexagen','docs/Hexagen',NULL,'2017-02-26 12:01:46.000000'),(4,0,'AiRisk','0.0.0.A','file:/c:/Data/Programs/java/airisk/target/classes!com.thegraid.AiRiskLauncher','file:/c:/Data/Programs/java/airisk/target/classes!com.thegraid.AiRisk','docs/AiRisk','self_attack','2017-01-29 12:12:49.000000');
/*!40000 ALTER TABLE `game_class` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `game_inst` WRITE;
/*!40000 ALTER TABLE `game_inst` DISABLE KEYS */;
INSERT INTO `game_inst` VALUES (2,1070,'Hex1',NULL,NULL,'2010-06-20 10:38:31.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,1,2,2),(3,0,'Player2s-vs-Player1s-3',NULL,NULL,'2010-12-03 16:55:46.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,2,1,1),(88,1,'OpenGame1-88',NULL,NULL,'2010-12-13 18:24:10.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,1,NULL,1),(89,4,'Player2b-vs-Player2s-89',NULL,NULL,'2010-12-13 21:06:41.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,2,1),(92,4,'Player2b-vs-Player2b-92',NULL,NULL,'2010-12-14 14:32:46.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,1,4,1),(94,0,'Player2b-vs-*Open*',NULL,NULL,'2010-12-14 14:34:53.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,NULL,1),(96,1,'OpenGame-96',NULL,NULL,'2010-12-14 14:41:32.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,2,4,1),(97,1,'OpenGame-97',NULL,'MyCode','2010-12-14 14:42:48.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,2,1),(100,1,'OpenName',NULL,NULL,'2010-12-14 16:01:09.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,2,1),(148,24,'Game-148-is-READY','https://null.thegraid.com:8443/gamma-web/GameControl/148',NULL,'2011-01-12 13:54:42.000000',NULL,NULL,'2022-08-08 11:21:08.000000',NULL,NULL,NULL,4,1,1),(149,1,'Player2b-vs-Player1s',NULL,NULL,'2011-01-12 13:56:42.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,1,1),(152,1,'Player2b-vs-Player1s',NULL,NULL,'2011-01-12 14:04:42.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,1,1),(153,2,'Player1s-vs-Player2b','https://game4.thegraid.com:8443/gamma-web/GameControl/153',NULL,'2011-01-12 14:14:42.000000','2012-10-02 22:11:59.000000',NULL,'2019-01-14 20:30:13.000000',NULL,NULL,NULL,1,4,1),(154,610,'Player1s-vs-Player2b','https://game5.thegraid.com:8443/gamma-web/GameControl/154',NULL,'2011-01-12 14:24:42.000000',NULL,NULL,'2022-08-16 12:41:00.000000',NULL,NULL,NULL,1,4,1),(155,1,'Player2b-vs-Player1s',NULL,NULL,'2011-01-12 14:34:42.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,1,1),(156,2,'Player2s-vs-*Open*',NULL,NULL,'2011-01-12 14:44:42.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,2,4,1),(157,3,'Player2s-vs-Player2b',NULL,NULL,'2011-01-12 14:54:42.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,2,4,1),(158,1,'Player2s-vs-Player2b',NULL,NULL,'2011-01-12 15:04:42.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,2,4,1),(159,1,'Player2b-vs-Player2s',NULL,NULL,'2011-01-12 15:14:42.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,2,1),(160,2,'A2RiskPlayer-vs-A2RiskPlayer',NULL,NULL,'2011-01-12 15:24:42.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,5,5,4),(161,2,'A2RiskPlayer-vs-A2RiskPlayer',NULL,NULL,'2011-01-12 15:34:42.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,5,5,4),(162,1,'Player2b-vs-Player1s',NULL,NULL,'2011-01-12 15:44:42.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,1,1),(163,0,'Player2b-vs-Player1s',NULL,NULL,'2011-01-12 16:50:34.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,1,1),(164,1,'Player2b-vs-Player1s',NULL,NULL,'2011-01-12 16:55:19.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,1,1),(165,0,'Player2s-vs-*Open*',NULL,NULL,'2012-05-29 22:54:42.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,2,NULL,1),(166,0,'Player2b-vs-*Open*',NULL,NULL,'2016-12-13 12:34:53.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,NULL,1),(167,0,'newgame',NULL,NULL,'2016-12-13 12:35:12.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,4,NULL,1),(168,0,'newPlayer2sGame',NULL,NULL,'2016-12-17 11:08:21.000000',NULL,NULL,'2017-01-29 12:08:27.000000',NULL,NULL,NULL,2,NULL,1);
/*!40000 ALTER TABLE `game_inst` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `game_player` WRITE;
/*!40000 ALTER TABLE `game_player` DISABLE KEYS */;
INSERT INTO `game_player` VALUES (26,3,'A',-1,2,1),(27,4,'B',-1,2,2),(28,0,'A',-1,3,2),(36,0,'A',-1,94,4),(38,0,'A',-1,96,2),(39,0,'A',-1,97,4),(42,0,'A',-1,100,4),(99,4,'A',7,148,4),(101,4,'A',-2,149,4),(106,14,'A',24,152,4),(107,26,'B',24,152,1),(108,2,'A',2,153,1),(109,6,'A',4,154,1),(110,8,'B',9,154,4),(111,0,'A',-1,155,4),(112,0,'A',-1,89,4),(113,0,'B',-1,89,2),(114,3,'B',2,153,4),(115,0,'A',-1,88,1),(116,0,'A',-1,92,1),(117,0,'B',-1,92,4),(118,0,'B',-1,96,4),(119,0,'A',-1,156,2),(120,0,'A',-1,157,2),(121,0,'B',-1,157,4),(122,0,'A',-1,158,2),(123,0,'A',-1,159,4),(124,0,'A',-1,160,5),(125,0,'A',-1,161,5),(126,2,'B',1,159,2),(127,0,'B',-1,100,2),(128,4,'B',3,158,4),(129,1,'B',-2,156,4),(130,0,'B',-1,97,2),(131,0,'A',-1,164,4),(132,4,'B',7,148,1),(134,0,'A',-1,165,2),(135,0,'A',-1,166,4),(136,0,'A',-1,167,4),(137,0,'A',-1,168,2);
/*!40000 ALTER TABLE `game_player` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `jhi_user` WRITE;
/*!40000 ALTER TABLE `jhi_user` DISABLE KEYS */;
INSERT INTO `jhi_user` VALUES (3,'user2','$2a$10$DzJe6TsyRi4ngB2za5U0SeT1ZrSoGplHpjReYXeuC2TKeIBfYs9na',NULL,NULL,'user2@test.com',NULL,_binary '\0','en','OeWb5dOrWdp86AIAYDC7',NULL,'anonymousUser','2022-08-24 04:43:49',NULL,'anonymousUser','2022-08-24 04:43:49'),(4,'user4','$2a$10$xw/djvZenarPcNxgOjr5BeU7w2IDIlGsJSXpaz2nhPfGDmd7jZjAO',NULL,NULL,'user4@test.com',NULL,_binary '\0','en','dY5ldRiSW7C5pY0NmkMs',NULL,'anonymousUser','2022-08-24 04:44:20',NULL,'anonymousUser','2022-08-24 04:44:20');
-- (1,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','',_binary '','en',NULL,NULL,'system',NULL,NULL,'system',NULL)
-- (2,'user1','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','',_binary '','en',NULL,NULL,'system',NULL,NULL,'system',NULL),
UPDATE `jhi_user` SET `login` = 'user1' WHERE (`id` = '2');
/*!40000 ALTER TABLE `jhi_user` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `jhi_user_authority` WRITE;
/*!40000 ALTER TABLE `jhi_user_authority` DISABLE KEYS */;
INSERT INTO `jhi_user_authority` VALUES (3,'ROLE_USER'),(4,'ROLE_USER');
-- (1,'ROLE_ADMIN'),(1,'ROLE_USER'),(2,'ROLE_USER'),
/*!40000 ALTER TABLE `jhi_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `player` WRITE;
/*!40000 ALTER TABLE `player` DISABLE KEYS */;
INSERT INTO `player` VALUES (1,2,'Player1s',3,0,'2010-11-12 00:00:00.000000','2010-11-12 00:00:00.000000','https://game4.thegraid.com:8445/map',1,1,2),(2,0,'Player2s',2,0,NULL,NULL,'https://game4.thegraid.com:8445/map',1,2,3),(3,0,'Player2x',1,0,NULL,NULL,'https://game4.thegraid.com:8445/map',2,2,3),(4,0,'Player2b',1,500,'2010-12-01 00:00:00.000000','2010-12-01 00:00:00.000000','https://game4.thegraid.com:8445/map',1,2,3),(5,0,'A2RiskPlayer',1,0,NULL,NULL,'airisk/riskPlayer.html',4,5,3),(6,0,'ANewHexPlayer',15,0,NULL,NULL,'gammaFlex/GammaFlex.html',1,2,3),(7,0,'TempPlayer',16,0,NULL,NULL,'gammaFlex/GammaFlex.html',1,1,2);
/*!40000 ALTER TABLE `player` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;