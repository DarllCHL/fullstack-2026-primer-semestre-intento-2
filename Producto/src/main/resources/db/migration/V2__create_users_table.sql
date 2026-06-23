USE db_productos_shop_now;

DROP TABLE IF EXISTS `user`; -- Borramos la versión vacía anterior

CREATE TABLE `user` (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(255) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(50) NOT NULL
);