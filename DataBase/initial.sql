-- Create database for d_gym
CREATE DATABASE IF NOT EXISTS d_gym
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

# start transaction;

-- Create syntax for TABLE 't_plan'
CREATE TABLE d_gym.t_plan (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `name` char(11) DEFAULT '',
                                `seq` int(11) DEFAULT '0',
                                `user_id` int(11) DEFAULT '0',
                                `plan_id` int(11) DEFAULT '0',
                                `plan_section_id` int(11) DEFAULT '0',
                                `sport_id` int(11) DEFAULT '0',
                                `value` double DEFAULT '0',
                                `times` int(11) DEFAULT '0',
                                `last_changed` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                `lastValue` int(11) DEFAULT '0',
                                `restTime` int(11) DEFAULT '0',
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `EveryInUser` (`seq`,`plan_id`,`plan_section_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=266 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 't_record'
CREATE TABLE d_gym.t_record (
                                  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                  `plan_name` char(11) DEFAULT '',
                                  `date` datetime DEFAULT NULL,
                                  `sport` char(11) DEFAULT '',
                                  `sport_unit` char(11) DEFAULT '',
                                  `cost_time` int(11) DEFAULT NULL,
                                  `times` int(11) DEFAULT NULL,
                                  `user_id` int(11) DEFAULT NULL,
                                  `value` double DEFAULT NULL,
                                  `record_id` int(11) DEFAULT '0',
                                  `record_section_id` int(11) DEFAULT '0',
                                  `total_time` int(11) DEFAULT '0',
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 't_sport'
CREATE TABLE d_gym.t_sport (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `name` char(11) DEFAULT NULL,
                                 `unit` char(11) DEFAULT NULL,
                                 `user_id` int(11) NOT NULL,
                                 `last_changed` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `name` (`name`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- Create syntax for TABLE 't_user'
CREATE TABLE d_gym.t_user (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `name` char(11) NOT NULL DEFAULT '',
                                `username` char(20) NOT NULL DEFAULT '',
                                `password` char(60) NOT NULL DEFAULT '',
                                `gender` tinyint(1) NOT NULL,
                                `age` int(11) NOT NULL,
                                PRIMARY KEY (`id`),
                                UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;


# INSERT INTO `d_gym.t_plan` (`id`, `name`, `seq`, `user_id`, `plan_id`, `plan_section_id`, `sport_id`, `value`, `times`, `last_changed`, `lastValue`, `restTime`)
# VALUES
#     (245, '胸肌训练', 4, 4, 0, 0, 0, 0, 0, '2021-12-15 10:31:10', 0, 0),
#     (246, '', 1, 4, 245, 0, 43, 0, 0, '2021-12-15 10:31:10', 0, 0),
#     (247, '', 1, 4, 245, 246, 0, 60, 3, '2021-12-16 00:14:49', 0, 60),
#     (248, '', 2, 4, 245, 246, 0, 90, 6, '2021-12-15 18:21:36', 0, 60),
#     (249, '', 3, 4, 245, 246, 0, 120, 10, '2021-12-15 10:31:10', 0, 120),
#     (250, '', 2, 4, 245, 0, 45, 0, 0, '2021-12-15 10:31:10', 0, 0),
#     (251, '', 1, 4, 245, 250, 0, 60, 12, '2021-12-15 10:31:10', 0, 60),
#     (252, '', 2, 4, 245, 250, 0, 60, 12, '2021-12-15 10:31:10', 0, 60),
#     (253, '', 3, 4, 245, 250, 0, 60, 12, '2021-12-15 10:31:10', 0, 60),
#     (254, '', 4, 4, 245, 250, 0, 60, 12, '2021-12-15 10:31:10', 0, 60),
#     (255, '', 3, 4, 245, 0, 44, 0, 0, '2021-12-15 10:31:10', 0, 0),
#     (256, '', 1, 4, 245, 255, 0, 10, 12, '2021-12-15 10:31:10', 0, 60),
#     (257, '', 2, 4, 245, 255, 0, 102, 12, '2021-12-16 11:02:25', 0, 60),
#     (258, '', 3, 4, 245, 255, 0, 10, 12, '2021-12-15 10:31:10', 0, 60),
#     (259, '', 4, 4, 245, 255, 0, 10, 12, '2021-12-15 10:31:10', 0, 60),
#     (260, '测试计划', 2, 4, 0, 0, 0, 0, 0, '2021-12-17 16:46:55', 0, 0),
#     (261, '', 1, 4, 260, 0, 34, 0, 0, '2021-12-17 16:46:55', 0, 0),
#     (262, '', 1, 4, 260, 261, 0, 11, 11, '2021-12-17 16:46:55', 0, 60),
#     (263, '', 2, 4, 260, 261, 0, 12, 12, '2021-12-17 16:46:55', 0, 15),
#     (264, '', 2, 4, 260, 0, 45, 0, 0, '2021-12-17 16:46:55', 0, 0),
#     (265, '', 1, 4, 260, 264, 0, 13, 13, '2021-12-17 16:46:55', 0, 120);
#
# INSERT INTO `d_gym.t_record` (`id`, `plan_name`, `date`, `sport`, `sport_unit`, `cost_time`, `times`, `user_id`, `value`, `record_id`, `record_section_id`, `total_time`)
# VALUES
#     (1, 'planname1', '2021-10-13 00:00:00', '', '', NULL, NULL, 4, NULL, NULL, NULL, 1003),
#     (2, '', '2021-10-13 00:00:00', 's1', 'u1', NULL, NULL, 4, NULL, 1, 0, NULL),
#     (3, '', '2021-10-13 00:00:00', '', '', 7, 5, 4, 11, 1, 2, NULL),
#     (4, '', '2021-10-13 00:00:00', '', '', 8, 6, 4, 22, 1, 2, NULL),
#     (5, '', '2021-10-13 00:00:00', 's2', 'u2', NULL, NULL, 4, NULL, 1, 0, NULL),
#     (6, '', '2021-10-13 00:00:00', '', '', NULL, 9, 4, 33, 1, 5, NULL),
#     (7, 'planname2', '2021-10-13 00:00:00', '', '', NULL, NULL, 4, NULL, NULL, NULL, 1002),
#     (8, '', '2021-10-13 00:00:00', 's3', 's3', NULL, NULL, 4, NULL, 7, 0, NULL),
#     (9, '', '2021-10-13 00:00:00', '', '', 9, 1, 4, 3, 7, 8, NULL),
#     (10, '', '2021-10-13 00:00:00', '', '', 10, 6, 4, 4, 7, 8, NULL);
#
#
# INSERT INTO `d_gym.t_sport` (`id`, `name`, `unit`, `user_id`, `last_changed`)
# VALUES
#     (33, '仰卧起坐', '个', 4, '2021-10-13 15:19:56'),
#     (34, '飞鸟', 'kg', 4, '2021-10-13 15:20:11'),
#     (35, '哑铃弯举', 'kg', 4, '2021-10-13 15:20:43'),
#     (43, '卧推', 'lb', 4, '2021-12-15 10:23:32'),
#     (44, '下斜交夹胸', 'lb', 4, '2021-12-15 10:24:01'),
#     (45, '上斜交夹胸', 'kg', 4, '2021-12-16 00:16:29');
#
#
# INSERT INTO `d_gym.t_user` (`id`, `name`, `username`, `password`, `gender`, `age`)
# VALUES
#     (4, 'user1', 'user1', 'password1', 0, 18),
#     (5, 'nametest', 'usertest1', 'pwdtest1', 1, 11),
#     (8, 'nametest2', 'usertest2', 'pwdtest2', 1, 11),
#     (9, 'nametest3', 'usertest3', 'pwdtest3', 1, 13),
#     (11, 'nametest4', 'usertest4', 'pwdtest4', 1, 14),
#     (20, 'As', 'Aaa', 'dddd', 1, 11),
#     (21, 'Aaa', 'As', 'aaa', 1, 11),
#     (27, 'nametest5', 'usertest5', '$2a$10$frwXWZKQ4MnIVvnNhTDoc.GGRn2IAtJpUl3BEtsBVCg/TAzyevuda', 1, 15);
#
# commit;