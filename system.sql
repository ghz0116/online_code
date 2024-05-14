/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.5.40 : Database - online_code
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`online_code` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `online_code`;

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `course_id` int(11) NOT NULL AUTO_INCREMENT,
  `course_name` varchar(20) DEFAULT NULL,
  `course_cover` varchar(80) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `description` text,
  `teacher_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `course` */

insert  into `course`(`course_id`,`course_name`,`course_cover`,`start_date`,`end_date`,`description`,`teacher_id`) values (2,'计算机网络技术基础','2ef621a5-14e3-40ca-9e7f-4c0db380dc00.jpeg','2024-04-01','2024-04-18','你理解计算机网络运行原理吗？你想了解如何开发网络应用程序吗？你想深入探究计算机网络深层奥秘吗？你知道虚拟的网络世界危机四伏吗？……那么这门课程是你最佳选择！《计算机网络》课将带你认识计算机网络、了解网络应用程序开发技术、探索计算机网络深层机理、了解计算机网络安全威胁及其防护技术等内容。\n',70),(3,'C语言程序设计——快速入门与提高','7480a335-7b83-49e3-ac88-b33b8c5bc1e8.jpeg','2024-04-03','2024-04-25','本课程本着以“计算思维”素养培养和“计算技术”应用能力提高为总目标，用案例教学法教C语言，一个编程案例一个台阶的学习C语言编程，每周你都能感觉到计算机运用能力的提升。本课程适合初学者，也适合想提高编程能力的学习者。 \n\n\n',70),(4,'数据结构','99ae9345-a153-4dca-a026-a8f4c5d4ceda.jpeg','2024-04-03','2024-04-11','本课程本着以“计算思维”素养培养和“计算技术”应用能力提高为总目标，用案例教学法教C语言，一个编程案例一个台阶的学习C语言编程，每周你都能感觉到计算机运用能力的提升。本课程适合初学者，也适合想提高编程能力的学习者。 ',70),(5,'计算机网络','e7ad51ff-ee3a-4725-b987-079fbfc35edd.webp','2024-04-10','2024-04-25','你理解计算机网络运行原理吗？你想了解如何开发网络应用程序吗？你想深入探究计算机网络深层奥秘吗？你知道虚拟的网络世界危机四伏吗？……那么这门课程是你最佳选择！《计算机网络》课将带你认识计算机网络、了解网络应用程序开发技术、探索计算机网络深层机理、了解计算机网络安全威胁及其防护技术等内容。',70),(6,'离散数学','865a7c27-bb45-457e-a5a3-1da21801cad0.png','2024-04-01','2024-04-30','离散数学是计算机学科的经典核心基础课程。课程内容主要包括集合论，数理逻辑，关系理论，图论相关内容，为进一步学习计算机科学的基本理论和方法以及之后的专业课打下良好的基础。通过这门课程的学习，将会培养学生的抽象思维能力，逻辑推理能力，缜密概括能力以及分析和解决实际问题的能力。',70),(7,'操作系统','42d36bb2-d5c1-4f93-87b2-b7e4c4d6e278.png','2024-04-07','2024-04-24',' 本课程以“如何给一个拥有单核 CPU、内存、磁盘、键盘和显示器的基本计算机配备一个可实际运转的操作系统?”这一基本问题作为主线。本课程对一个基本操作系统中的六个基本模块，即 CPU 管理、内存管理、外设管理、磁盘管理与文件系统、用户接口和启动模块，以及这些模块之间的内在联系，进行了深入细致地原理教授、算法与数据结构设计,并借鉴开源操作系统 Linux 全面讲授了整个操作系统的代码实现。通过本系列课程的学习，学生可以体会一个完整的操作系统是如何良好运转的，据此培养学生设计和实现一个完整操作系统的能力，进而在未来从事计算机系统层面的工作时能应用这些能力和经验，最终逐渐形成计算机系统能力。同时本课程包含了进程设计与实现、虚存设计与实现、外设驱动与实现在内的12个配套实验，通过丰富的实践环节，使学生真正掌握设计、实现和分析完整操作系统的能力。',74),(8,'计算机组成原理','a3a36f96-955c-4d99-a2be-7792f2ca5d2c.jpeg','2024-04-03','2024-04-15','“计算机组成原理”是一门理论性、工程性、技术性和实践性都很强的核心专业基础课程，在计算机学科系列课程中处于承上启下的作用。 课程教学目标是通过相关的教学活动，帮助学生理解计算机基本组成部件(包括运算器、控制器、存储器、输入/输出)的结构、工作原理、内部运行机制和设计方法。加深学生对计算机软、硬件系统的整体化理解，建立硬件/软件协同的整机概念，并有效增强学生的计算机系统设计能力。课程学习为研究生考试、后续课程（如系统结构，并行编程、嵌入式系统、接口技术）的学习，参加IT企业招聘等都奠定了坚实的基础。',74),(9,'大学信息基础','f128fcd3-0a06-4b62-8841-f9a476d6f741.jpeg','2024-04-19','2024-04-30','本课程冠以“大学信息技术”之名，信息化、网络化、智能化是贯穿课程的主题，内容涵盖极其广泛，也形成了本课程的主要特色。信息化本身指的是信息表示方式与处理方式，课程相关章节较为深入地介绍了信息与信息技术的概念、计算机软硬件系统、媒体数字化、问题解决与程序设计、计算机通信与网络技术、信息安全等内容。作为信息化的公共基础设施，互联网已经成为人们获取信息、利用信息的主要方式，课程拓展视频和拓展阅读材料包含了互联网+、物联网、云计算、大数据等基于互联网的应用技术。',74),(10,'深度学习及其应用','90fdf73d-4c80-4c28-8c8c-ea281dc9a0ce.jpeg','2024-04-02','2024-04-24','掌握深度学习的基本原理、常用算法，并在此基础上应用于机器视觉、自然语言处理等相关领域，培养一定的分析和解决实际问题的能力。',74),(11,'网页设计基础','0f0c8916-eb14-4598-abe4-b2f2e7bfd83f.jpeg','2024-04-02','2024-04-16','在如今这个互联网+的时代，访问网站、浏览网页已成为每个人的家常便饭。学习网页制作、拥有自己的网站成为很多人都想具备的技能。 \n\n你是否经历过想找图书，却被书名中的众多技术名词弄得眼花缭乱，不知从何入手？\n\n你是否有过被动辄几百页的网页设计书籍吓退的经历？\n\n面对互联网上浩如烟海的网页设计相关讲解，你是否遇到过不知所云的困难？ \n\n如果你有过以上这些困惑，那么《网页设计基础》课程可以帮你解惑。 \n\n本课程聚焦于网页的背后是什么，具有网页呈现和网页源代码双视角。以“让自学成为可能”为教学理念，将概念、技术、实践、方法相融合，帮助学习者突破基本概念学习瓶颈、理清网页设计学习思路、掌握相关技术学习方法，指导学习者使用互联网资料支持学习，为持续学习奠定基础。 ',75),(12,'数字图像处理','c450013c-810c-4ed5-bd65-448c301b69bb.jpeg','2024-04-03','2024-04-10','随着无人机、无人驾驶、机器人、生成式人工智能等新一代信息技术的应用和发展，计算机视觉取代人工视觉成为趋势，数字图像处理是计算机视觉的基础课程。数字图像处理是将图像信号转换成数字信号并利用计算机对其进行处理的过程，主要内容包括人类视觉感知系统、图像获取与数字化、图像基本运算、图像变换、图像增强、图像复原、图像压缩编码、图像分割、彩色图像处理、图像表示与描述等原理和技术方法。\n\n针对数字图像处理课程概念多、内容抽象、学生入门较难的特点，本课程以实践为导向，以实际应用为目标，在讲解数字图像处理技术基础理论及算法原理的同时，特别注意如何用MATLAB软件编程实现一些图像处理的算法，使学生掌握图像处理的理论和方法，运用所学知识解决实际问题。\n\n通过学习本课程，学生能够深刻理解理论方法，并在MATLAB下编程实现算法，理论与应用并重，为将来从事相关领域专业工作打好基础。',75),(13,'数据库与商业智能','8dae9ff6-1dda-4f84-ad68-059ea6308e94.png','2024-04-01','2024-04-09','随着计算方法和信息技术的不断发展，大量数据的产生和收集导致信息爆炸，而信息可以产生价值，因而需要我们对这些数据进行实时和深层次的分析。商业智能通常被理解为将企业中现有的数据转化为知识，帮助企业做出明智的业务经营决策的工具。为了将数据转化为知识，需要利用数据仓库、联机分析处理（OLAP）工具和数据挖掘等技术。本课程将介绍这三方面技术背景，并通过一系列实验综合运用这些技术。学生只需要有基本的数据库知识，不需要掌握编程语言，通过课程学习引导学生对数据挖掘产生兴趣，并且逐渐培养学生的数据意识，对较为成熟的技术和方法具有相应的分析应用能力。并为以后深入学习打下基础。',75),(14,'大学计算机CAP','241b6b5d-3fd6-4786-b1e6-4411a3376239.png','2024-04-02','2024-04-25','计算机科学既是独立学科，也是所有专业研究的基础。本课程是西安交通大学面向理工类专业开设的“大学计算机”课程的部分基础知识，总计4周课程。这些内容也是目前全国各高校非计算机专业第一门计算机课程基本都会涉及的知识。提前用4周时间完成本课程的学习，可以帮助你在进入大学后能更加顺利的学习大学第一门计算机课程的其他内容。 课程包括：引言（走进计算机），信息表示与编码，软硬件系统基本原理，网络技术基础等四周内容，并为对常用办公软件不熟悉的同学提供了应用指导辅助教材。 课程第1讲中提到程序设计，这部分内容会在进入大学后学习。另外，对有关操作系统的详细介绍，也会在大学课程中进一步学习，作为先修课，这些内容本课程暂不涉及。',75),(15,'游戏引擎原理及应用','1baddda8-f918-4507-bc9e-0ce701cbb439.jpeg','2024-04-03','2024-04-23','游戏引擎是目前游戏开发的最重要手段，每一位有志于从事游戏开发的人员，都需要了解游戏引擎的原理，并熟练掌握至少一款主流游戏引擎的使用方法。\n该课程将系统讨论游戏引擎中的主要功能的实现原理，以及这些功能在Unity引擎中的使用方法。\n通过该课程的学习，除了可以系统了解游戏引擎原理之外，还将熟练掌握Unity游戏引擎的使用方法，并了解游戏开发的相关流程和技术特点。',73),(16,'信息安全','7f206332-af5a-43c8-991b-af90350e1b6f.png','2024-04-08','2024-04-30','本课程目标: 认识到信息安全构成国家安全的一个重要屏障，信息安全的主战场是网络安全，信息安全的核心理论是密码学，信息安全的实现要有综合的学科与技术：数学，物理，计算机科学，软件与硬件。其中，数学为密码学提供的高效安全的算法，是信息安全的基础，是本课程的一个核心内容。',73),(17,'算法设计与分析','9e9696a0-e437-4b20-ad77-692c128718ba.jpeg','2024-04-01','2024-04-30','本课程既讲授动态规划与分而治之等经典算法策略，又以产教融合方式邀请阿里副总裁，第四范式CEO、联合创始人、ACM国际大学生程序设计竞赛世界冠军等算法专家分享经验。无论您是零基础的初学者，还是在刷题中困惑的求职者，本课程都将通过详细剖析实例降低理解难度，通过归纳总结问题揭示算法本质，助您真正学懂算法！',73);

/*Table structure for table `course_evaluate` */

DROP TABLE IF EXISTS `course_evaluate`;

CREATE TABLE `course_evaluate` (
  `evaluate_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `course_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`evaluate_id`),
  KEY `novel_id` (`course_id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

/*Data for the table `course_evaluate` */

insert  into `course_evaluate`(`evaluate_id`,`parent_id`,`create_time`,`course_id`,`user_id`,`content`) values (84,0,'2024-04-18 21:58:08',5,70,'这是老师开的课，谢谢大家'),(85,84,'2024-04-18 21:58:50',5,71,'老师您好，谢谢您'),(87,0,'2024-04-24 21:38:01',5,78,'今天又进步了，真开心'),(88,85,'2024-04-24 21:45:38',5,71,'题目好难呀'),(89,87,'2024-04-30 11:03:08',5,71,'真棒呀');

/*Table structure for table `course_user` */

DROP TABLE IF EXISTS `course_user`;

CREATE TABLE `course_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `course_user` */

insert  into `course_user`(`id`,`course_id`,`user_id`) values (5,5,71),(6,4,71),(7,2,71),(8,8,71),(9,15,71),(10,5,78),(11,5,80),(12,5,69),(13,5,79),(14,3,71),(15,6,71);

/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
  `question_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_name` varchar(40) DEFAULT NULL,
  `solution_num` int(11) DEFAULT '0',
  `pass_rate` double DEFAULT NULL,
  `degree` int(11) DEFAULT NULL,
  `question_detail` text,
  `course_id` int(11) DEFAULT NULL,
  `parameter_str` text,
  `tags_json` varchar(20) DEFAULT NULL,
  `md` text,
  PRIMARY KEY (`question_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `question` */

insert  into `question`(`question_id`,`question_name`,`solution_num`,`pass_rate`,`degree`,`question_detail`,`course_id`,`parameter_str`,`tags_json`,`md`) values (20,'寻找两个正序数组的中位数',60,0.4167,3,'<p>给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。</p>\n<p>算法的时间复杂度应该为 O(log (m+n)) 。</p>\n<p>示例 1：</p>\n<pre><code>输入：nums1 = [1,3], nums2 = [2]\n输出：2.00000\n解释：合并数组 = [1,2,3] ，中位数 2\n</code></pre>\n<p>示例 2：</p>\n<pre><code>输入：nums1 = [1,2], nums2 = [3,4]\n输出：2.50000\n解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5\n</code></pre>\n<p>提示：</p>\n<ul>\n<li>nums1.length == m</li>\n<li>nums2.length == n</li>\n<li>0 &lt;= m &lt;= 1000</li>\n<li>0 &lt;= n &lt;= 1000</li>\n<li>1 &lt;= m + n &lt;= 2000</li>\n<li>-106 &lt;= nums1[i], nums2[i] &lt;= 106</li>\n</ul>\n',5,'[\"nums1\",\"nums2\"]','[\"数组\",\"二分查找\",\"分治\"]','<p>给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。</p>\r\n<p>算法的时间复杂度应该为 O(log (m+n)) 。</p>\r\n<p>示例 1：</p>\r\n<pre><code>输入：nums1 = [1,3], nums2 = [2]\r\n输出：2.00000\r\n解释：合并数组 = [1,2,3] ，中位数 2\r\n</code></pre>\r\n<p>示例 2：</p>\r\n<pre><code>输入：nums1 = [1,2], nums2 = [3,4]\r\n输出：2.50000\r\n解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5\r\n</code></pre>\r\n<p>提示：</p>\r\n<ul>\r\n<li>nums1.length == m</li>\r\n<li>nums2.length == n</li>\r\n<li>0 &lt;= m &lt;= 1000</li>\r\n<li>0 &lt;= n &lt;= 1000</li>\r\n<li>1 &lt;= m + n &lt;= 2000</li>\r\n<li>-106 &lt;= nums1[i], nums2[i] &lt;= 106</li>\r\n</ul>\r\n'),(28,'组合总和 Ⅳ',1,1,0,'<p>给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。请你从 nums 中找出并返回总和为 target 的元素组合的个数。</p>\n<p>题目数据保证答案符合 32 位整数范围。</p>\n<p>示例 1：</p>\n<p>输入：nums = [1,2,3], target = 4<br />\n输出：7<br />\n解释：<br />\n所有可能的组合为：<br />\n(1, 1, 1, 1)<br />\n(1, 1, 2)<br />\n(1, 2, 1)<br />\n(1, 3)<br />\n(2, 1, 1)<br />\n(2, 2)<br />\n(3, 1)<br />\n请注意，顺序不同的序列被视作不同的组合。<br />\n示例 2：</p>\n<p>输入：nums = [9], target = 3<br />\n输出：0</p>\n<p>提示：</p>\n<p>1 &lt;= nums.length &lt;= 200<br />\n1 &lt;= nums[i] &lt;= 1000<br />\nnums 中的所有元素 互不相同<br />\n1 &lt;= target &lt;= 1000</p>\n<p>进阶：如果给定的数组中含有负数会发生什么？问题会产生何种变化？如果允许负数出现，需要向题目中添加哪些限制条件？</p>\n',5,'[\"nums\",\"target\"]','[\"数组\",\"动态规划\"]','给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。请你从 nums 中找出并返回总和为 target 的元素组合的个数。\n\n题目数据保证答案符合 32 位整数范围。\n\n \n\n示例 1：\n\n输入：nums = [1,2,3], target = 4\n输出：7\n解释：\n所有可能的组合为：\n(1, 1, 1, 1)\n(1, 1, 2)\n(1, 2, 1)\n(1, 3)\n(2, 1, 1)\n(2, 2)\n(3, 1)\n请注意，顺序不同的序列被视作不同的组合。\n示例 2：\n\n输入：nums = [9], target = 3\n输出：0\n \n\n提示：\n\n1 <= nums.length <= 200\n1 <= nums[i] <= 1000\nnums 中的所有元素 互不相同\n1 <= target <= 1000\n \n\n进阶：如果给定的数组中含有负数会发生什么？问题会产生何种变化？如果允许负数出现，需要向题目中添加哪些限制条件？'),(29,'两数之和',10,0.2,4,'<p>给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。</p>\n<p>你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。</p>\n<p>你可以按任意顺序返回答案。</p>\n<p>示例 1：</p>\n<p>输入：nums = [2,7,11,15], target = 9<br />\n输出：[0,1]<br />\n解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。<br />\n示例 2：</p>\n<p>输入：nums = [3,2,4], target = 6<br />\n输出：[1,2]<br />\n示例 3：</p>\n<p>输入：nums = [3,3], target = 6<br />\n输出：[0,1]</p>\n<p>提示：</p>\n<p>2 &lt;= nums.length &lt;= 104<br />\n-109 &lt;= nums[i] &lt;= 109<br />\n-109 &lt;= target &lt;= 109<br />\n只会存在一个有效答案</p>\n<p>进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？</p>\n',5,'[\"nums\",\"target\"]','[\"数组\",\"哈希表\"]','给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。\n\n你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。\n\n你可以按任意顺序返回答案。\n\n \n\n示例 1：\n\n输入：nums = [2,7,11,15], target = 9\n输出：[0,1]\n解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。\n示例 2：\n\n输入：nums = [3,2,4], target = 6\n输出：[1,2]\n示例 3：\n\n输入：nums = [3,3], target = 6\n输出：[0,1]\n \n\n提示：\n\n2 <= nums.length <= 104\n-109 <= nums[i] <= 109\n-109 <= target <= 109\n只会存在一个有效答案\n \n\n进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？'),(30,'两数相加',9,0.3333,4,'<p>给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。</p>\n<p>请你将两个数相加，并以相同形式返回一个表示和的链表。</p>\n<p>你可以假设除了数字 0 之外，这两个数都不会以 0 开头。</p>\n<p>示例 1：<br />\n<img src=\"http://127.0.0.1:7999/question/fetch_img/9ec3b195-1f8d-4c4f-a61e-88760880d6ba.jpg\" alt=\"addtwonumber1 1.jpg\" /></p>\n<p>输入：l1 = [2,4,3], l2 = [5,6,4]<br />\n输出：[7,0,8]<br />\n解释：342 + 465 = 807.<br />\n示例 2：</p>\n<p>输入：l1 = [0], l2 = [0]<br />\n输出：[0]<br />\n示例 3：</p>\n<p>输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]<br />\n输出：[8,9,9,9,0,0,0,1]</p>\n<p>提示：</p>\n<p>每个链表中的节点数在范围 [1, 100] 内<br />\n0 &lt;= Node.val &lt;= 9<br />\n题目数据保证列表表示的数字不含前导零</p>\n',5,'[\"l1\",\"l2\"]','[\"递归\",\"链表\",\"数学\"]','给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。\n\n请你将两个数相加，并以相同形式返回一个表示和的链表。\n\n你可以假设除了数字 0 之外，这两个数都不会以 0 开头。\n\n \n\n示例 1：\n![addtwonumber1 1.jpg](http://127.0.0.1:7999/question/fetch_img/9ec3b195-1f8d-4c4f-a61e-88760880d6ba.jpg)\n\n输入：l1 = [2,4,3], l2 = [5,6,4]\n输出：[7,0,8]\n解释：342 + 465 = 807.\n示例 2：\n\n输入：l1 = [0], l2 = [0]\n输出：[0]\n示例 3：\n\n输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]\n输出：[8,9,9,9,0,0,0,1]\n \n\n提示：\n\n每个链表中的节点数在范围 [1, 100] 内\n0 <= Node.val <= 9\n题目数据保证列表表示的数字不含前导零');

/*Table structure for table `question_answer` */

DROP TABLE IF EXISTS `question_answer`;

CREATE TABLE `question_answer` (
  `answer_id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `language` varchar(20) DEFAULT NULL,
  `code_file` varchar(60) DEFAULT NULL,
  `correct` tinyint(1) DEFAULT NULL,
  `cost_time` bigint(20) DEFAULT NULL,
  `submit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`answer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=265 DEFAULT CHARSET=utf8;

/*Data for the table `question_answer` */

insert  into `question_answer`(`answer_id`,`question_id`,`user_id`,`language`,`code_file`,`correct`,`cost_time`,`submit_time`) values (180,20,69,'Java','32b37eee-fe15-4eb1-b00b-7ac806f093d9.txt',1,98,'2024-04-18 21:56:55'),(181,20,69,'Java','a1cb14d1-a39f-4ec4-b05f-cc13fa63d62b.txt',0,0,'2024-04-18 21:57:18'),(182,20,69,'Java','d7932449-ece2-4e7a-b2ff-d5de326b378e.txt',0,0,'2024-04-18 21:57:35'),(183,20,69,'JavaScript','937719b8-dbfd-4b8e-97db-d82e15308e58.txt',0,0,'2024-04-18 22:11:28'),(184,20,69,'Java','47bcc160-632b-43b1-bcf6-775b2eac7f39.txt',1,93,'2024-04-19 14:30:15'),(185,20,69,'Java','2058ec17-4d63-45c9-a6db-a92a8bc977db.txt',1,83,'2024-04-19 14:43:23'),(186,20,69,'Java','7dea643d-b6ca-4c4f-a632-f3fb1ee428ca.txt',1,83,'2024-04-19 14:46:13'),(187,20,69,'Java','7a7ea68c-a162-4622-8051-397bd8645469.txt',1,113,'2024-04-19 14:53:53'),(188,20,71,'Java','7062ab8c-0f92-4b2f-ad16-ca2322ba1aca.txt',1,85,'2024-04-19 14:53:58'),(189,20,69,'JavaScript','e27f2196-43d8-44ef-af1a-3bfdcded836c.txt',1,83,'2024-04-19 14:54:31'),(190,20,69,'JavaScript','35ca75c3-7c29-4ea7-97d9-8b059456f952.txt',1,76,'2024-04-19 14:56:04'),(191,20,78,'JavaScript','a08f5c3a-1568-4d13-9284-48e69f1f462f.txt',1,72,'2024-04-19 14:56:05'),(192,20,78,'JavaScript','8bc25260-ba28-4747-b20c-2be2f59fc37b.txt',1,70,'2024-04-19 14:56:07'),(193,20,78,'JavaScript','3df14029-f316-4af7-abd3-1fd028849ea7.txt',0,0,'2024-04-19 14:56:17'),(194,20,69,'JavaScript','3f6d7a73-f298-4f34-9d5f-ceaceabc4638.txt',1,94,'2024-04-19 14:56:32'),(195,20,69,'Java','b3345883-19b8-4604-9e5c-a033ffd8c9f9.txt',1,86,'2024-04-19 15:01:43'),(196,20,69,'Java','0d19c41e-1386-41b0-9dfe-0cadac7e1e35.txt',1,84,'2024-04-19 15:04:33'),(197,20,69,'JavaScript','44969c54-9b5c-4875-94fe-d6bdd5036507.txt',1,71,'2024-04-19 15:04:53'),(198,20,79,'Python','8ff91b4f-5a56-4023-af15-1560babb89af.txt',0,0,'2024-04-20 15:12:35'),(199,20,71,'Python','ca787ed5-b26c-44ca-adad-e76a2817a3a0.txt',0,0,'2024-04-20 15:13:41'),(200,20,71,'Python','c879ef3c-1fbd-4738-a50b-2deb09721cf8.txt',0,0,'2024-04-20 15:14:11'),(201,20,71,'Python','e3e71276-cd8e-4961-8b90-01855007bf05.txt',0,0,'2024-04-20 15:14:46'),(202,20,79,'Python','c2d16b80-f87e-44a7-81b4-6299b013d95f.txt',1,62,'2024-04-20 15:18:09'),(203,20,79,'Python','60cd7b2e-dd1c-455f-a4cf-d237b26168fc.txt',0,0,'2024-04-20 15:22:40'),(204,20,79,'Python','3e798376-96b9-4316-bd7a-7e1961221aec.txt',1,61,'2024-04-20 15:22:57'),(205,20,80,'Python','1372287b-98ee-4607-945e-1c44c202d811.txt',0,0,'2024-04-20 15:23:05'),(206,20,71,'Python','287d2e8e-9f75-4356-8a77-e1d1231fe5f3.txt',1,58,'2024-04-20 15:23:15'),(207,20,71,'Java','f135fc08-9e48-4e60-a01e-9594643b6efe.txt',1,84,'2024-04-21 17:23:18'),(208,20,71,'Java','01c0cdee-d170-4c9c-bff6-1cf098e7a410.txt',0,0,'2024-04-21 17:24:17'),(209,20,71,'Java','a39370f1-f454-40b2-b65b-424fd512fc0b.txt',1,89,'2024-04-21 17:25:35'),(210,20,71,'Java','c05db835-d4ea-4fac-b9f3-c856be69db73.txt',0,0,'2024-04-21 17:25:45'),(211,20,71,'JavaScript','3c1c7ba0-a1c2-4177-9976-4a7d44ae9973.txt',0,0,'2024-04-21 17:27:52'),(212,20,71,'JavaScript','8cb0accc-3e81-49b5-bc8f-f653440776f7.txt',0,0,'2024-04-21 17:28:48'),(213,20,71,'JavaScript','61498129-32b5-4961-b8f6-6aa9e2e03062.txt',0,0,'2024-04-21 17:29:03'),(214,20,71,'JavaScript','caa68a31-63cf-46a2-bd2b-6147423b590f.txt',0,0,'2024-04-21 17:35:42'),(215,20,71,'JavaScript','e415a735-ec7f-47e1-bd90-b054f31fc793.txt',0,0,'2024-04-21 17:37:23'),(216,20,71,'JavaScript','64047fd7-f4e3-4a95-98a6-7620245bd5c7.txt',0,0,'2024-04-21 17:37:50'),(217,20,71,'JavaScript','5e88cb9e-6a4f-4f47-ac83-3364a18112f6.txt',0,0,'2024-04-21 17:42:26'),(218,20,71,'JavaScript','3674d1d0-390c-4ca8-93e7-e631fbfea0a4.txt',0,0,'2024-04-21 17:43:39'),(219,20,71,'JavaScript','93b7823d-9c1c-4a1a-9966-b83e1a40ca35.txt',0,0,'2024-04-21 17:44:37'),(220,20,71,'JavaScript','3de2397a-ff7d-43f9-b958-1cf2192020e0.txt',0,0,'2024-04-21 17:46:55'),(221,20,71,'Java','eab9bb48-3040-4135-b7c1-12afb4ea8d0a.txt',1,86,'2024-04-21 17:49:39'),(222,20,71,'Java','b997bc20-65a9-46d6-a1a3-97abed6651cf.txt',0,0,'2024-04-21 17:50:06'),(223,20,71,'Java','9a7882de-11c2-4fb0-abd9-476daaffec37.txt',0,0,'2024-04-21 17:54:50'),(224,20,71,'JavaScript','2e540cf8-72ea-4bbc-8c43-6c58f42915eb.txt',0,0,'2024-04-21 23:44:56'),(225,20,71,'Java','61129611-d305-4bfc-86d2-900410aceab0.txt',0,0,'2024-04-22 10:40:57'),(226,20,71,'Java','bfc41cdb-9bb2-42f7-8cbf-5cc719b81b79.txt',0,0,'2024-04-22 10:41:39'),(227,20,71,'Java','688e7511-2f8a-45f4-8adb-41ac78f34cf7.txt',0,0,'2024-04-22 10:42:09'),(228,20,71,'Java','375a3516-6a19-4703-b05e-ef104e452218.txt',0,0,'2024-04-22 10:42:31'),(229,20,71,'Java','059c78a6-0b51-4275-b68f-1816d2c63f6c.txt',0,0,'2024-04-22 10:42:57'),(230,20,71,'Java','1836f223-8be5-4766-b9c0-53ded9dcc540.txt',0,0,'2024-04-22 10:57:52'),(231,20,71,'Java','b5a11a5b-9da4-4594-8da7-73d8df26f8b4.txt',0,0,'2024-04-24 12:34:02'),(232,28,71,'Python','7855a420-5594-42b6-b206-dc2cf1bf88f2.txt',1,200,'2024-04-24 12:58:04'),(233,29,71,'Java','00a9a675-b3fb-4e89-be81-00b5d1f066e2.txt',0,0,'2024-04-24 12:59:00'),(234,29,71,'Java','97130def-e877-4aed-b4c2-fb8278f40b19.txt',0,0,'2024-04-24 12:59:33'),(235,29,71,'Java','8ace6953-e922-4a25-9b0a-8a981d6592df.txt',0,0,'2024-04-24 13:03:40'),(236,29,71,'Java','98955238-a1c7-475a-b2ef-eafdfcf6d690.txt',0,0,'2024-04-24 13:07:07'),(237,29,71,'Java','67223616-2f3e-4c9e-b43f-c11d80dfec32.txt',0,0,'2024-04-24 13:07:50'),(238,29,71,'Java','7b38c820-a59d-46ec-92f7-cfd7828a8fa7.txt',0,0,'2024-04-24 13:08:10'),(239,29,71,'Java','445fb555-26d6-40d5-857c-4b0f09422e0d.txt',0,0,'2024-04-24 13:08:20'),(240,29,71,'Java','01f0fef3-afd2-469c-8a36-18c7dfbc61f7.txt',0,0,'2024-04-24 13:09:15'),(241,29,71,'Java','37db62fc-6aba-490d-8c0d-0ce50c9dcfdb.txt',1,86,'2024-04-24 13:09:17'),(242,29,71,'Java','460a8a4d-75ae-4828-8458-5da82f269efd.txt',1,84,'2024-04-24 13:09:27'),(243,30,71,'Python','cea49c2c-07f2-4357-9d52-c4e1a2d1711b.txt',0,0,'2024-04-24 13:11:14'),(244,30,71,'Python','f17a7854-ec2e-4e92-b791-a91328fd1dc3.txt',0,0,'2024-04-24 13:12:01'),(245,30,71,'Python','a7a03a3a-0c0a-467b-af02-30dd705543c2.txt',0,0,'2024-04-24 13:13:41'),(246,30,71,'Python','879d4263-aa8b-4e02-bb4c-fa6d4a82f415.txt',0,0,'2024-04-24 13:14:52'),(247,30,71,'Python','fa5af585-9e88-455a-be77-f6404bedc68a.txt',0,0,'2024-04-24 13:14:54'),(248,30,71,'Java','f3dfd6ba-3404-44fb-aadd-e1f6e4d6fd3b.txt',0,0,'2024-04-24 13:15:29'),(249,30,70,'Python','bd27fc58-24d8-49b1-9089-69471fa199ea.txt',1,69,'2024-04-24 13:19:31'),(250,30,71,'Python','95f43c3b-3e2b-4fda-8bdf-b8d762aa32a0.txt',1,66,'2024-04-24 19:17:50'),(251,30,71,'Python','b177a6f5-fe82-491e-b6ad-37a0824912dc.txt',1,64,'2024-04-24 20:22:48'),(252,20,71,'Java','cd51139e-8d08-450e-932f-0d8b272d056b.txt',1,89,'2024-04-24 21:43:09'),(253,20,71,'Python','df3cafa8-bd85-4192-beb5-b938dad234b0.txt',1,68,'2024-04-24 21:43:31'),(254,20,71,'Python','c022c48e-e42a-4e0f-8898-3cc43eb678ee.txt',1,68,'2024-04-24 21:43:38'),(255,20,71,'Python','bd1bdf14-a7b9-48b6-97a2-1bb79fee8f72.txt',0,0,'2024-04-24 21:43:52'),(256,20,71,'Python','60eebd2c-879c-43b7-afa7-c3e3a9cb24ef.txt',0,0,'2024-04-24 22:36:13'),(257,20,71,'Python','c5a4da6c-14c5-48db-94eb-7483dc0593d9.txt',1,93,'2024-04-25 13:04:59'),(258,20,71,'Python','f077f13f-dbaa-4af7-ab6a-c80de587cc8f.txt',0,0,'2024-04-25 13:05:12'),(259,20,71,'Python','36dfa967-2a13-468e-8c67-3c3f397da0b3.txt',1,67,'2024-04-25 13:05:55'),(260,20,71,'Python','0af46cda-c753-41f7-8e51-4fa2e7c06be4.txt',1,71,'2024-04-25 13:59:57'),(261,20,71,'Python','e3ac85e1-6bc2-4ca3-b8ea-aaccfed1eeeb.txt',0,0,'2024-04-25 14:00:06'),(262,28,71,'Python','4496b2ed-06cd-425b-bcaa-eecaaef0017d.txt',1,246,'2024-04-30 11:03:40'),(263,28,71,'Python','2fe5d839-631a-4deb-b82b-80fd16575a81.txt',0,0,'2024-04-30 11:03:52'),(264,28,71,'Python','1ab1030b-16d7-4f81-8ab3-269d3e420c0f.txt',1,92,'2024-04-30 11:04:42');

/*Table structure for table `question_favorite` */

DROP TABLE IF EXISTS `question_favorite`;

CREATE TABLE `question_favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `question_favorite` */

insert  into `question_favorite`(`id`,`user_id`,`question_id`) values (7,70,28),(8,70,30),(9,71,29),(10,71,28);

/*Table structure for table `question_pre_code` */

DROP TABLE IF EXISTS `question_pre_code`;

CREATE TABLE `question_pre_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) DEFAULT NULL,
  `type` varchar(20) DEFAULT NULL,
  `code` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

/*Data for the table `question_pre_code` */

insert  into `question_pre_code`(`id`,`question_id`,`type`,`code`) values (16,23,'Java','asdsd'),(17,24,'Java','class Solution {\n    public double findMedianSortedArrays(int[] nums1, int[] nums2) {\n\n \n\n    }\n}'),(18,24,'JavaScript','/**\n * @param {number[]} nums1\n * @param {number[]} nums2\n * @return {number}\n */\nvar findMedianSortedArrays = function(nums1, nums2) {\n\n\n};'),(19,25,'Java','class Solution {\n    public double findMedianSortedArrays(int[] nums1, int[] nums2) {\n\n \n\n    }\n}'),(20,25,'JavaScript','/**\n * @param {number[]} nums1\n * @param {number[]} nums2\n * @return {number}\n */\nvar findMedianSortedArrays = function(nums1, nums2) {\n\n\n};'),(27,26,'Java','class Solution {\n    public double findMedianSortedArrays(int[] nums1, int[] nums2) {\n\n \n    }\n    \n}'),(28,26,'JavaScript','/**\n * @param {number[]} nums1\n * @param {number[]} nums2\n * @return {number}\n */\nvar findMedianSortedArrays = function(nums1, nums2) {\n\n\n};'),(31,0,'Java','sadasd'),(39,20,'Java','class Solution {\n    double findMedianSortedArrays(std::vector<int>& nums1, std::vector<int>& nums2) {\n\n\n \n\n    }\n}'),(40,20,'JavaScript','/**\n * @param {number[]} nums1\n * @param {number[]} nums2\n * @return {number}\n */\nvar findMedianSortedArrays = function(nums1, nums2) {\n\n\n};'),(41,20,'Python','def findMedianSortedArrays(nums1, nums2):\n   '),(42,20,'C++','#include <iostream>\n#include <vector>\n#include <algorithm>\n\nclass Solution {\npublic:\n    double findMedianSortedArrays(vector<int>& nums1, vector<int>& nums2) {\n      \n      \n    }\n}'),(43,28,'Python','def combinationSum4(nums, target):\n        \"\"\"\n        :type nums: List[int]\n        :type target: int\n        :rtype: int\n        \"\"\"'),(44,28,'Java','class Solution {\n    public int combinationSum4(int[] nums, int target) {\n\n    }\n}'),(45,29,'Java','class Solution {\n    public int[] twoSum(int[] nums, int target) {\n\n    }\n}'),(46,29,'Python','def twoSum(nums, target):\n        \"\"\"\n        :type nums: List[int]\n        :type target: int\n        :rtype: List[int]\n        \"\"\"'),(47,30,'Java','/**\n * Definition for singly-linked list.\n * public class ListNode {\n *     int val;\n *     ListNode next;\n *     ListNode() {}\n *     ListNode(int val) { this.val = val; }\n *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }\n * }\n */\nclass Solution {\n    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {\n\n    }\n}'),(48,30,'Python','  def addTwoNumbers(l1, l2):\n        \"\"\"\n        :type l1: ListNode\n        :type l2: ListNode\n        :rtype: ListNode\n        \"\"\"');

/*Table structure for table `question_test_case` */

DROP TABLE IF EXISTS `question_test_case`;

CREATE TABLE `question_test_case` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question_id` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `parameter_map` text,
  `result` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

/*Data for the table `question_test_case` */

insert  into `question_test_case`(`id`,`question_id`,`name`,`parameter_map`,`result`) values (14,23,'sd','{\"as\":\"asd\"}','ad'),(15,24,'case1','{\"nums1\":\" [1,3]\",\"nums2\":\" [2]\"}','2.0'),(16,24,'case2','{\"nums1\":\" [1,2]\",\"nums2\":\"[3,4]\"}','2.5'),(17,25,'case1','{\"nums1\":\" [1,3]\",\"nums2\":\" [2]\"}','2.0'),(18,25,'case2','{\"nums1\":\" [1,2]\",\"nums2\":\"[3,4]\"}','2.5'),(25,26,'case1','{\"nums1\":\" [1,3]\",\"nums2\":\" [2]\"}','2.0'),(26,26,'case2','{\"nums1\":\" [1,2]\",\"nums2\":\"[3,4]\"}','2.5'),(29,0,'aaa','{\"aaaa\":\"a\"}','a'),(34,20,'case1','{\"nums1\":\" [1,3]\",\"nums2\":\" [2]\"}','2.0'),(35,20,'case2','{\"nums1\":\" [1,2]\",\"nums2\":\"[3,4]\"}','2.5'),(36,28,'case1','{\"nums\":\"[1,2,3]\",\"target\":\"4\"}','7'),(37,28,'case2','{\"nums\":\"[9]\",\"target\":\"3\"}','0'),(38,29,'case1','{\"nums\":\"[2,7,11,15]\",\"target\":\"9\"}','[0,1]'),(39,29,'case2','{\"nums\":\"[3,2,4]\",\"target\":\"6\"}','[1,2]'),(40,29,'case3','{\"nums\":\"[3,3]\",\"target\":\"6\"}','[0,1]'),(41,30,'case1','{\"l1\":\"[2,4,3]\",\"l2\":\"[5,6,4]\"}','[7,0,8]'),(42,30,'case2','{\"l1\":\"[0]\",\"l2\":\"[0]\"}','[0]'),(43,30,'case3','{\"l1\":\"[9,9,9,9,9,9,9]\",\"l2\":\"[9,9,9,9]\"}','[8,9,9,9,0,0,0,1]');

/*Table structure for table `tags` */

DROP TABLE IF EXISTS `tags`;

CREATE TABLE `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `tags` */

insert  into `tags`(`id`,`name`) values (1,'递归'),(2,'链表'),(3,'数学');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(10) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `head_portrait` varchar(50) DEFAULT NULL,
  `identity` int(11) DEFAULT NULL COMMENT '1.学生；2.老师',
  `telephone` varchar(20) DEFAULT NULL,
  `gender` int(11) DEFAULT '0',
  `birthday` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`real_name`,`username`,`password`,`email`,`head_portrait`,`identity`,`telephone`,`gender`,`birthday`) values (69,'张三','zhangsan','$2a$10$EtQJH.xGDeHbfnImBKO.aOOEWjsVX7xluB2xtHRAfz1wvpjhtpto.','123456789@qq.com','27f664de-3ef0-45ed-a6af-3db78088a414.jpg',1,NULL,1,'2024-04-09'),(70,'李老师','lilaoshi','$2a$10$EtQJH.xGDeHbfnImBKO.aOOEWjsVX7xluB2xtHRAfz1wvpjhtpto.','123456789@qq.com','ccace9f3-bc25-4658-a5f8-1374154bd29c.webp',2,'123456789',0,'2024-04-10'),(71,'高和召','ghz666','$2a$10$EtQJH.xGDeHbfnImBKO.aOOEWjsVX7xluB2xtHRAfz1wvpjhtpto.','123456789@qq.com','5586aa44-10d1-477e-a9dc-b864da4cd3d4.jpg',1,'12345678910',0,'1994-04-01'),(73,'张老师','zhanglaoshi','$2a$10$EtQJH.xGDeHbfnImBKO.aOOEWjsVX7xluB2xtHRAfz1wvpjhtpto.','123456789@qq.com','f5621bde-d6e1-4323-8a6a-69f6bfb64428.webp',2,'123456789',0,'2024-04-01'),(74,'王老师','wanglaoshi','$2a$10$EtQJH.xGDeHbfnImBKO.aOOEWjsVX7xluB2xtHRAfz1wvpjhtpto.','123456789@qq.com','8650499c-9ba6-4f27-a267-5e4ebec299c4.webp',2,'123456789',1,'2024-04-09'),(75,'赵老师','zhaolaoshi','$2a$10$EtQJH.xGDeHbfnImBKO.aOOEWjsVX7xluB2xtHRAfz1wvpjhtpto.','123456789@qq.com','db8b869a-4599-451a-8da8-b1418fcbb549.webp',2,'123456789',1,'2024-04-07'),(76,'孙老师','sunlaoshi','$2a$10$EtQJH.xGDeHbfnImBKO.aOOEWjsVX7xluB2xtHRAfz1wvpjhtpto.','123456789@qq.com','null',2,'123456789',0,NULL),(78,'李四','lisi','$2a$10$EtQJH.xGDeHbfnImBKO.aOOEWjsVX7xluB2xtHRAfz1wvpjhtpto.','123456789@qq.com','da28140b-40f5-494a-a68b-0685fda7d45f.jpg',1,'123456789',1,'2024-04-08'),(79,'王五','wangwu','$2a$10$EtQJH.xGDeHbfnImBKO.aOOEWjsVX7xluB2xtHRAfz1wvpjhtpto.','123456789@qq.com','1bd8c012-76b8-4be0-91a2-ce044d9155a9.jpg',1,'123456789',1,'2024-04-08'),(80,'赵六','zhaoliu','$2a$10$EtQJH.xGDeHbfnImBKO.aOOEWjsVX7xluB2xtHRAfz1wvpjhtpto.','123456789@qq.com','169aff26-7e46-4b1e-a37b-ace37b9d4622.jpg',1,'123456789',0,'2024-04-08');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
