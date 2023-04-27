CREATE TABLE IF NOT EXISTS `processors` (
    `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(100) NOT NULL UNIQUE,
    `manufacturer_name` varchar(100) DEFAULT NULL,
    `socket` varchar(100) DEFAULT NULL,
    `release_date` int(11) DEFAULT NULL,
    `number_of_cores` int(11) DEFAULT NULL,
    `number_of_threads` int(11) DEFAULT NULL,
    `base_clock_speed` int(11) DEFAULT NULL,
    `boost_clock_speed` int(11) DEFAULT NULL,
    `retail_price` int(11) DEFAULT NULL,
    `additional_info` varchar(1000) DEFAULT NULL
)ENGINE=InnoDB DEFAULT CHARSET=UTF8;