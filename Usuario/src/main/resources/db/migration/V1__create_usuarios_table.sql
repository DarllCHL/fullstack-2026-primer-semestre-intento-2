-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS db_usuarios_shop_now
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- Seleccionar la base
USE db_usuarios_shop_now;

-- Crear la tabla usuarios si no existe
CREATE TABLE IF NOT EXISTS usuarios (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(50) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE
    );
