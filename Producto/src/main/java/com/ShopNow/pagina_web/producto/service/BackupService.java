package com.ShopNow.pagina_web.producto.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class BackupService {

    public void createBackup() {
        String dumpPath = "C:/laragon/bin/mysql/mysql-8.0.30-winx64/bin/mysqldump";

        String dbName = "db_tienda_shop_now_productos";
        String dbUser = "root";
        String dbPass = "1234";
        String savePath = "C:/backups/backup_productos.sql";

        // Crea la carpeta automáticamente si no existe
        File backupDir = new File("C:/backups");
        if (!backupDir.exists()) {
            backupDir.mkdirs();
            System.out.println("📁 Carpeta de backups creada en: " + backupDir.getAbsolutePath());
        }

        String command = String.format("%s -u %s %s --databases %s -r %s",
                dumpPath,
                dbUser,
                dbPass.isEmpty() ? "" : "-p" + dbPass,
                dbName,
                savePath);

        try {
            Process process = Runtime.getRuntime().exec(command);
            int processComplete = process.waitFor();
            if (processComplete == 0) {
                System.out.println("✅ Backup creado con éxito en: " + savePath);
            } else {
                System.err.println("❌ Fallo al crear el backup");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}