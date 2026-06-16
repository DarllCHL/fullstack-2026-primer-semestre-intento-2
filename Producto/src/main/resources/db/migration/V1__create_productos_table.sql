-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS db_productos_shop_now
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- Seleccionar la base
USE db_productos_shop_now;

-- Crear la tabla productos si no existe
CREATE TABLE IF NOT EXISTS productos (
                                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                         nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(500),
    precio DOUBLE NOT NULL,
    stock INT NOT NULL,
    activo BOOLEAN DEFAULT TRUE
    );
