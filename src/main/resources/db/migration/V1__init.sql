CREATE TABLE `user_`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `access_token` varchar(255) NOT NULL,
    `email`        varchar(255) NOT NULL,
    `username`     varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uq_user_email` (`email`)
);

CREATE TABLE `endpoint`
(
    `check_interval`  int    DEFAULT NULL,
    `created_at`      datetime(6) DEFAULT NULL,
    `id`              bigint        NOT NULL AUTO_INCREMENT,
    `last_checked_at` datetime(6) DEFAULT NULL,
    `user_id`         bigint DEFAULT NULL,
    `name`            varchar(50)   NOT NULL,
    `url`             varchar(2048) NOT NULL,
    PRIMARY KEY (`id`),
    KEY               `fk_endpoint_user_id` (`user_id`),
    CONSTRAINT `fk_endpoint_user` FOREIGN KEY (`user_id`) REFERENCES `user_` (`id`)
);

CREATE TABLE `monitoring_result`
(
    `checked_at`  datetime(6) DEFAULT NULL,
    `endpoint_id` bigint,
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `payload`     text,
    `status_code` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY           `fk_monitoring_result_endpoint_id` (`endpoint_id`),
    CONSTRAINT `fk_monitoring_result_endpoint` FOREIGN KEY (`endpoint_id`) REFERENCES `endpoint` (`id`)
);
