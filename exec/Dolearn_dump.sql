-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: i8a802.p.ssafy.io    Database: dolearn
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `uid` int unsigned DEFAULT NULL,
  `tid` int unsigned NOT NULL,
  `title` varchar(60) NOT NULL,
  `max_cnt` int NOT NULL,
  `content` varchar(600) NOT NULL,
  `summary` varchar(60) NOT NULL,
  `start_time` timestamp NOT NULL,
  `end_time` timestamp NOT NULL,
  `deadline` timestamp NOT NULL,
  `is_fixed` tinyint NOT NULL,
  `created_time` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_board_member1_idx` (`uid`),
  CONSTRAINT `fk_board_member1` FOREIGN KEY (`uid`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (2,8,5,'면접 봐주실 인사담당자 출신 강사님 계신가요??',3,'제가 5개 기업에 면접을 앞두고 있습니다. 너무 떨려서 사실 말도 안나와요... 긴장을 많이 하는 스타일인데 혹시 도움 주실 분들 계시면 많이 신청 부탁드립니다 ㅠㅠ','면접 봐주실 강사님 구합니다!!','2023-02-18 17:00:00','2023-02-18 19:00:00','2023-02-15 23:59:59',0,'2023-02-14 16:41:23'),(4,8,5,'Computer Sceience 학습',4,'운영체제와 WebSocket관련 진행 해주실분 구합니다..!','컴퓨터 전공자 구합니다...','2023-02-22 09:00:00','2023-02-22 11:00:00','2023-02-21 23:59:59',0,'2023-02-14 16:43:40'),(6,8,5,'유튜브 편집 피드백 해주실분..',2,'팩트폭력도 괜찮으니까 피드백 해주실 강사님... 제발 신청해주세요... 간절합니다..!','편집 피드백을 받고싶습니다..!','2023-03-02 07:00:00','2023-03-02 08:00:00','2023-02-28 23:59:59',0,'2023-02-14 16:45:26'),(11,1,0,'자바 알고리즘 공부 봐주실 분 구합니다.',5,'자바로 그래프, DP, 문자열 등 알고리즘 문제 풀이 봐주실 분 구합니다.\n문제풀이는 백준으로 진행할 계획이고, 백준 골드 이상 이신 분들만 신청해주시면 감사하겠습니다.\n같이 이번 상반기 코테 준비 열심히 해볼 예정있는 분들 많이 신청해주세요!!!','자바 알고리즘 알려주실 분 구합니다.','2023-02-14 18:00:00','2023-02-14 20:00:00','2023-02-14 18:00:00',1,'2023-02-14 17:09:14'),(12,1,0,'자바 알고리즘 공부 봐주실 분 구합니다',5,'자바로 그래프, DP, 문자열 등 알고리즘 문제 풀이 봐주실 분 구합니다.\n문제 풀이는 백준으로 진행할 계획이고, 백준 골드 이상이신 분들 신청해주시면 감사하겠습니다.\n같이 이번 상반기 코테 준비 열심히 해볼 예정있는 분들 많이 신청해주세요!!','자바 알고리즘 알려주실 분 구합니다','2023-02-14 18:00:00','2023-02-14 20:00:00','2023-02-14 18:00:00',1,'2023-02-14 17:15:04'),(13,1,0,'자바 알고리즘 공부 봐주실 분 구합니다',5,'자바로 그래프, DP, 문자열 등 알고리즘 문제 풀이 봐주실 분 구합니다.\n문제 풀이는 백준으로 진행할 계획이고, 백준 골드 이상이신 분들 신청해주시면 감사하겠습니다.\n같이 이번 상반기 코테 준비 열심히 해볼 예정있는 분들 많이 신청해주세요!!!','자바 알고리즘 알려주실 분 구합니다.','2023-02-14 18:00:00','2023-02-14 20:00:00','2023-02-14 18:00:00',1,'2023-02-14 17:27:11'),(14,3,0,'webRTC 테스트',2,'webRTC 테스트','webRTC 테스트 해보실 분','2023-02-14 22:00:00','2023-02-15 00:00:00','2023-02-14 22:00:00',1,'2023-02-14 21:32:56'),(15,10,5,'개발자 자기소개서 작성법 강의',5,'백엔드 개발자가 되기 위해 준비 중인 사람들을 위해 자기소개서 작성법을 강의해 줄 분 찾고 있습니다.\n대략 1시간 정도로 기본 문항 위주로 강의해 주시면 좋을 것 같습니다.','백엔드 개발자 취준생 자소서 작성법','2023-02-18 09:00:00','2023-02-18 10:00:00','2023-02-18 09:00:00',0,'2023-02-15 08:35:39'),(16,3,4,'시연영상 찍자',5,'들어오세요','들어오세요','2023-02-15 10:00:00','2023-02-15 12:00:00','2023-02-15 10:00:00',1,'2023-02-15 09:08:48');
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecture`
--

DROP TABLE IF EXISTS `lecture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecture` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `bid` int unsigned NOT NULL,
  `start_realtime` timestamp NULL DEFAULT NULL,
  `end_realtime` timestamp NULL DEFAULT NULL,
  `created_time` timestamp NOT NULL,
  `member_cnt` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_lecture_board1_idx` (`bid`),
  CONSTRAINT `fk_lecture_board1` FOREIGN KEY (`bid`) REFERENCES `board` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecture`
--

LOCK TABLES `lecture` WRITE;
/*!40000 ALTER TABLE `lecture` DISABLE KEYS */;
INSERT INTO `lecture` VALUES (1,11,NULL,NULL,'2023-02-14 17:13:03',0),(2,12,NULL,NULL,'2023-02-14 17:16:46',0),(3,13,NULL,NULL,'2023-02-14 17:29:41',0),(4,14,NULL,NULL,'2023-02-14 21:52:57',0),(5,16,NULL,NULL,'2023-02-15 09:09:44',0);
/*!40000 ALTER TABLE `lecture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `info` varchar(3000) DEFAULT NULL,
  `point` int NOT NULL DEFAULT '0',
  `youtube` varchar(50) DEFAULT NULL,
  `instagram` varchar(50) DEFAULT NULL,
  `facebook` varchar(200) DEFAULT NULL,
  `blog` varchar(200) DEFAULT NULL,
  `join_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `refresh_token` varchar(500) DEFAULT NULL,
  `img_path` varchar(200) DEFAULT NULL,
  `img_url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (1,'박중원','bohodays0316@gmail.com','$2a$10$77Va/UMlHRDB1pQ/DVee1.I.xHwihKgxKnZZpqAujrHn6GGRP5aEq','노력하는 개발자 박중원입니다.',2300,'https://www.youtube.com/@hellossafy','https://www.instagram.com/hellossafy/','','','2023-02-14 16:28:02',NULL,'/var/dolearn/images/1749176411382131.jpg','/profile-img/1749176411382131.jpg'),(2,'김찬휘','cksgnlcjswoo@naver.com','$2a$10$tI2BTpnchyjeWqph9.GGd.cH8rU4eHoWxhOdJOsO2fSv7h17oRu/S','유학생 수업 / Lesson in English is welcomed!\n\n1️⃣ 기초강의\n\n파이썬 / Go / Javascript / Rust 기초\nReact 프론트엔드\n데이터베이스와 SQL\n2️⃣ 응용강의\n\n파이썬 중급 / 고급\n파이썬 백엔드 (Django/Flask/FastAPI) / Go 백엔드\n데이터사이언스(데이터 수집/분석/시각화, 머신러닝)\nTF-IDF, 토픽 모델링, 네트워크 분석 등\n파이썬 웹 크롤링 / 업무 자동화\n3️⃣ 취업대비\n\n개발자 자소서 첨삭, 취업 및 면접 컨설팅\n코딩테스트 대비 기초 알고리즘 및 자료구조 + 문제풀이\n프론트엔드 / 백엔드 포트폴리오 만들기',3200,'https://www.youtube.com/@hellossafy','hellossafy','hellossafy','https://cksgnlcjswo.tistory.com/','2023-02-14 16:28:02','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJja3NnbmxjanN3b29AbmF2ZXIuY29tIiwiaWF0IjoxNjc2NDE5NTMwLCJleHAiOjE2NzY1MDU5MzB9.R1dVjIvQ8CRVAb-QiZixK0BiinuFVpxeefHP-NlrYcY','/var/dolearn/images/1749144951945521.jpg','/profile-img/1749144951945521.jpg'),(3,'윤동균','ydg8732@naver.com','$2a$10$PhR/nsirnle73f2U7sElR.RCoywuJKAjZZtbHwghTQaEZUd5UCHtm','안녕하세요 윤동균입니다!! 잘 부탁드립니다',2100,'https://www.youtube.com/@hellossafy','hellossafy','hellossafy','https://goodteacher.tistory.com/','2023-02-14 16:28:43','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ5ZGc4NzMyQG5hdmVyLmNvbSIsImlhdCI6MTY3NjM3OTE2OCwiZXhwIjoxNjc2NDY1NTY4fQ.pdzWXvuu_UKSn5A6WSmcrU-0APOKBkkuPcaANkoAnuc','/var/dolearn/images/1749199209775866.jpg','/profile-img/1749199209775866.jpg'),(4,'정소영','jssafy88@gmail.com','$2a$10$AlhvCYRIkM6iwRFrbPC13Oa4byFpmdKAXn20p597zIckM0suOBzzy','자세한 수업계획서는 프로필 링크의 \"홈페이지\" 확인해주세요!\n유학생 수업 / Lesson in English is welcomed!\n\n1️⃣ 기초강의\n\n파이썬 / Go / Javascript / Rust 기초\nReact 프론트엔드\n데이터베이스와 SQL\n2️⃣ 응용강의\n\n파이썬 중급 / 고급\n파이썬 백엔드 (Django/Flask/FastAPI) / Go 백엔드\n데이터사이언스(데이터 수집/분석/시각화, 머신러닝)\nTF-IDF, 토픽 모델링, 네트워크 분석 등\n파이썬 웹 크롤링 / 업무 자동화\n3️⃣ 취업대비\n\n개발자 자소서 첨삭, 취업 및 면접 컨설팅\n코딩테스트 대비 기초 알고리즘 및 자료구조 + 문제풀이\n프론트엔드 / 백엔드 포트폴리오 만들기',1800,'https://www.youtube.com/@hellossafy','hellossafy','hellossafy','https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp','2023-02-14 16:28:51','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqc3NhZnk4OEBnbWFpbC5jb20iLCJpYXQiOjE2NzYzNjMzMzEsImV4cCI6MTY3NjQ0OTczMX0.Hwmw2xhwC6w2JIxIxl4H_FCpcwgdPbBdZsHiiaAXUyk','/var/dolearn/images/1749275807774291.jpg','/profile-img/1749275807774291.jpg'),(5,'정경호','kyungho@gmail.com','$2a$10$qFkekoyt.tK0fjP87Sn22eoT7l0jDu9FAYjpKIOE3bpPbQ0ngWbRu','정경호입니다.',1500,'https://www.youtube.com/@hellossafy','https://www.instagram.com/jstar_allallj/','','','2023-02-14 16:29:36',NULL,'/var/dolearn/images/1749334793401280.jpg','/profile-img/1749334793401280.jpg'),(6,'김정원','1wjddnjs0528@gmail.com','$2a$10$eO0kxgC8yYvz272LhlEuE.uc4NMF4z2jQ32LPRVRpIiFIxoNvPF3C','?[ 데이터분석 툴 ] \n- Python\n    - pandas 및 numpy 라이브러리를 활용하여 원하는 데이터 형식으로의 정제 및 가공 가능\n    - Matplotlib, Seaborn, Pydeck과 같은 시각화 라이브러리를 사용하여 데이터 유형에 알맞게 데이터 시각화를 할 수 있음\n    - 머신러닝 모델 생성 및 검증까지의 일련의 과정을 코드로 구현 가능\n- SQL\n    - 기본적인 문법(생성, 조회, 업데이트, 삭제, 조인, 서브쿼리, 집계) 구사 가능\n- R/SAS/SPSS\n    - 여러 가설검정(모수/비모수적 검정방법) 실행 및 결과 해석 가능\n\n?[ 시각화 툴 ] \n- Tableau\n    - Tableau Desktop을 활용하여 데이터의 적절한 전처리를 할 수 있음\n    - 계산된 필드 및 필터 옵션 등을 활용하여 원하는 유형으로 데이터 시각화 가능\n- Qgis\n    - 공간 정보 파일(.shp, .geojson)을 활용해 원하는 지도 생',1600,'https://www.youtube.com/@hellossafy','leo.rookie','hellossafy','https://hackmd.io/?nav=overview','2023-02-14 16:29:41',NULL,'/var/dolearn/images/1749223315118633.jpg','/profile-img/1749223315118633.jpg'),(7,'김광석','kwangsuk@gmail.com','$2a$10$dzfMk4vXBgPcorGZxEl96OiSSMcJUmpPgLTYnzBEfpTYeP3Ypq41u','',1550,'https://www.youtube.com/@hellossafy','https://www.instagram.com/lilyiu_/','','','2023-02-14 16:30:12','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrd2FuZ3N1a0BnbWFpbC5jb20iLCJpYXQiOjE2NzY0MjcwOTYsImV4cCI6MTY3NjUxMzQ5Nn0.6iiTHDcdepb8t8jMdBU_-rfg5Ew8EnFxqrPcsgKkJeM','/var/dolearn/images/1749384782165155.png','/profile-img/1749384782165155.png'),(8,'민초현','chohyeon9708@gmail.com','$2a$10$qP9zNIGjZUvc0LPMpmx77e.REI6T9CWd5CN9tI0Y6ai0kwXVfifH.','?‍♂️대전에 하나뿐인 엔드리스풀 ?\n바닥에 설치된 거울을 보며 배울수있는\n수영 PT 1:1 2:1 개인레슨 전문 풀장입니다\n수업이 어떻게 진행되는지 궁금하신 분들은\n인스타그램 endless_pool_dh 에서 확인해주세요!',2800,'https://www.youtube.com/@hellossafy','hellossafy','hellossafy','https://blog.naver.com/angly97','2023-02-14 16:30:18','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjaG9oeWVvbjk3MDhAZ21haWwuY29tIiwiaWF0IjoxNjc2NDIwMDA5LCJleHAiOjE2NzY1MDY0MDl9.0wUMldbiJEE7opgTIlbGMVhBgs1JGPszYJogvnDWwYo','/var/dolearn/images/1749250116292425.jpg','/profile-img/1749250116292425.jpg'),(9,'방종철','admin@gmail.com','$2a$10$KzhyyG4kGGvmI1N7Bsg6HO.fB5pC.1nBl1jOXAEloDzLzPyuoT.q6','',100,'','','','','2023-02-14 16:32:16',NULL,'/var/dolearn/images/1749477970662485.png','/profile-img/1749477970662485.png'),(10,'서지윤','jeey0124@gmail.com','temp','헤헤헤헤헿',2600,'https://www.youtube.com/@hellossafy','hellossafy','hellossafy','https://goodteacher.tistory.com/','2023-02-15 08:32:17','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqZWV5MDEyNEBnbWFpbC5jb20iLCJpYXQiOjE2NzY0MTc1MzYsImV4cCI6MTY3NjUwMzkzNn0.ZGMppXzBEBbekkA4jBwkRc-cgcAj-Bmlxoy8t_UgEcE','/var/dolearn/images/1805770857453475.png','/profile-img/1805770857453475.png');
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_board`
--

DROP TABLE IF EXISTS `member_board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_board` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `uid` int unsigned DEFAULT NULL,
  `bid` int unsigned NOT NULL,
  `member_type` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_member_board_member1_idx` (`uid`),
  KEY `fk_member_board_board1_idx` (`bid`),
  CONSTRAINT `fk_member_board_board1` FOREIGN KEY (`bid`) REFERENCES `board` (`id`),
  CONSTRAINT `fk_member_board_member1` FOREIGN KEY (`uid`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_board`
--

LOCK TABLES `member_board` WRITE;
/*!40000 ALTER TABLE `member_board` DISABLE KEYS */;
INSERT INTO `member_board` VALUES (2,8,2,'학생'),(4,8,4,'학생'),(6,8,6,'학생'),(11,1,11,'학생'),(12,7,11,'강사'),(13,2,11,'강사'),(14,3,11,'강사'),(15,6,11,'강사'),(16,8,11,'학생'),(17,4,11,'학생'),(18,1,12,'학생'),(19,8,12,'강사'),(20,1,13,'학생'),(21,6,13,'학생'),(22,2,13,'강사'),(23,3,13,'학생'),(24,8,13,'학생'),(25,5,13,'강사'),(26,9,13,'강사'),(27,7,13,'강사'),(28,4,13,'학생'),(29,1,2,'학생'),(30,3,14,'학생'),(31,1,14,'강사'),(32,2,14,'학생'),(33,10,2,'학생'),(34,10,15,'학생'),(35,4,15,'학생'),(36,3,16,'학생'),(37,4,16,'학생'),(38,6,16,'학생'),(39,8,16,'학생'),(42,1,16,'학생'),(43,2,16,'강사');
/*!40000 ALTER TABLE `member_board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member_lecture`
--

DROP TABLE IF EXISTS `member_lecture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member_lecture` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `uid` int unsigned DEFAULT NULL,
  `lid` int unsigned NOT NULL,
  `member_type` varchar(10) NOT NULL,
  `evaluate_status` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_member_lecture_member_idx` (`uid`),
  KEY `fk_member_lecture_lecture1_idx` (`lid`),
  CONSTRAINT `fk_member_lecture_lecture1` FOREIGN KEY (`lid`) REFERENCES `lecture` (`id`),
  CONSTRAINT `fk_member_lecture_member` FOREIGN KEY (`uid`) REFERENCES `member` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member_lecture`
--

LOCK TABLES `member_lecture` WRITE;
/*!40000 ALTER TABLE `member_lecture` DISABLE KEYS */;
INSERT INTO `member_lecture` VALUES (2,8,1,'학생',0),(4,7,1,'강사',0),(6,8,2,'강사',0),(7,1,3,'학생',0),(8,6,3,'학생',0),(9,3,3,'학생',0),(10,8,3,'학생',0),(11,4,3,'학생',0),(12,2,3,'강사',0),(13,3,4,'학생',1),(14,2,4,'학생',1),(15,1,4,'강사',0),(16,3,5,'학생',0),(17,4,5,'학생',1),(18,6,5,'학생',1),(19,8,5,'학생',1),(20,1,5,'학생',1),(21,2,5,'강사',0);
/*!40000 ALTER TABLE `member_lecture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `rid` int unsigned NOT NULL,
  `bid` int unsigned NOT NULL,
  `content` varchar(100) NOT NULL,
  `is_checked` tinyint NOT NULL,
  `type` varchar(20) NOT NULL,
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `check_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_message_member2_idx` (`rid`),
  KEY `fk_message_board_idx` (`bid`),
  CONSTRAINT `fk_message_board` FOREIGN KEY (`bid`) REFERENCES `board` (`id`),
  CONSTRAINT `fk_message_member1` FOREIGN KEY (`rid`) REFERENCES `member` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,1,11,'',1,'confirm','2023-02-14 17:13:03','2023-02-14 17:25:20'),(2,8,11,'',1,'confirm','2023-02-14 17:13:03','2023-02-14 17:16:32'),(3,4,11,'',0,'confirm','2023-02-14 17:13:03',NULL),(4,7,11,'',0,'confirm','2023-02-14 17:13:03',NULL),(5,1,12,'',1,'confirm','2023-02-14 17:16:46','2023-02-14 17:25:19'),(6,8,12,'',1,'confirm','2023-02-14 17:16:46','2023-02-15 09:15:46'),(7,1,13,'',0,'confirm','2023-02-14 17:29:41',NULL),(8,6,13,'',0,'confirm','2023-02-14 17:29:41',NULL),(9,3,13,'',1,'confirm','2023-02-14 17:29:41','2023-02-14 21:28:01'),(10,8,13,'',1,'confirm','2023-02-14 17:29:41','2023-02-15 09:15:47'),(11,4,13,'',0,'confirm','2023-02-14 17:29:41',NULL),(12,2,13,'',0,'confirm','2023-02-14 17:29:41',NULL),(13,3,14,'',1,'confirm','2023-02-14 21:52:57','2023-02-15 09:06:51'),(14,2,14,'',0,'confirm','2023-02-14 21:52:57',NULL),(15,1,14,'',0,'confirm','2023-02-14 21:52:57',NULL),(16,3,16,'',0,'confirm','2023-02-15 09:09:44',NULL),(17,4,16,'',0,'confirm','2023-02-15 09:09:44',NULL),(18,6,16,'',0,'confirm','2023-02-15 09:09:44',NULL),(19,8,16,'',1,'confirm','2023-02-15 09:09:44','2023-02-15 09:15:50'),(21,2,16,'',0,'confirm','2023-02-15 09:09:44',NULL);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-15 13:01:35
