CREATE TABLE IF NOT EXISTS `users` (
    `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email` varchar(100) NOT NULL UNIQUE,
    `password` varchar(100) NOT NULL,
    `name` varchar(100) NOT NULL,
    `role` varchar(100) DEFAULT NULL,
    `is_disabled` tinyint(1) DEFAULT 0
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
