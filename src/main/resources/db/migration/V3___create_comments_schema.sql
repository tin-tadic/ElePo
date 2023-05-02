CREATE TABLE IF NOT EXISTS `comments` (
    `id` bigint(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `text` varchar(1000) NOT NULL,
    `processor_id` bigint(11) NOT NULL,
    `user_id` bigint(11) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    CONSTRAINT `FK_comment_processor` FOREIGN KEY (`processor_id`) REFERENCES `processors` (`id`),
    CONSTRAINT `FK_comment_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;