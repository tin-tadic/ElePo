CREATE TABLE IF NOT EXISTS `users` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `email` varchar(100) NOT NULL UNIQUE,
    `password` varchar(100) NOT NULL,
    `username` varchar(100) NOT NULL,
    `role` varchar(100) DEFAULT NULL,
    `is_disabled` bit(1) NOT NULL DEFAULT 0
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;
