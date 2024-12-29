package nure.atrk.climate_control.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

@Service
public class BackupService {

    private static final String BACKUP_DIRECTORY = "backups/";

    public String createBackup() throws IOException {
        // Generate a timestamp for the backup file name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupFileName = BACKUP_DIRECTORY + "backup_" + timestamp + ".sql";

        // Create the backup directory if it doesn't exist
        File backupDir = new File(BACKUP_DIRECTORY);
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        // Form the command to execute
        String[] command = {
                "cmd.exe", "/c",
                String.format(
                        "mysqldump -u%s -p%s %s > %s",
                        "root",
                        "admin",
                        "climate_db",
                        backupFileName
                )
        };

        System.out.println("Executing command: " + String.join(" ", command));

        // Execute the command
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        try {
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // Encrypt the backup file
                encryptFile(backupFileName, backupFileName.replace(".sql", ".enc"));
                Files.delete(Paths.get(backupFileName));
                return "Backup created successfully at: " + backupFileName;
            } else {
                throw new IOException("Error occurred while creating backup. Exit code: " + exitCode);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Backup process was interrupted", e);
        }
    }

    public String restoreBackup(String backupFileName) throws IOException {
        // Check if the backup file exists
        File backupFile = new File(BACKUP_DIRECTORY + backupFileName);
        if (!backupFile.exists()) {
            throw new IOException("Backup file not found: " + backupFileName);
        }

        // Decrypt the backup file
        String decryptedBackupFileName = backupFileName.replace(".enc", ".sql");
        decryptFile(backupFile.getAbsolutePath(), BACKUP_DIRECTORY + decryptedBackupFileName);

        // Form the command to execute
        String[] command = {
                "cmd.exe", "/c",
                String.format(
                        "mysql -u%s -p%s %s < %s",
                        "root",
                        "admin",
                        "climate_db",
                        BACKUP_DIRECTORY + decryptedBackupFileName
                )
        };

        System.out.println("Executing command: " + String.join(" ", command));

        // Execute the command
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true); // Redirect errors to the standard output

        Process process = processBuilder.start();
        try {
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                // Delete the decrypted backup file
                Files.delete(Paths.get(BACKUP_DIRECTORY + decryptedBackupFileName));
                return "Backup restored successfully from: " + backupFileName;
            } else {
                InputStream errorStream = process.getErrorStream();
                Scanner scanner = new Scanner(errorStream).useDelimiter("\\A");
                String errorOutput = scanner.hasNext() ? scanner.next() : "";
                throw new IOException("Error occurred while restoring backup. Exit code: " + exitCode + ". Error: " + errorOutput);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Restore process was interrupted", e);
        }
    }

    private void encryptFile(String inputFile, String outputFile) throws IOException {
        try {
            SecretKey secretKey = null;
            File keyFile = new File(BACKUP_DIRECTORY + "secret.key");

            // Check if the secret key file exists
            if (keyFile.exists()) {
                byte[] keyBytes = Files.readAllBytes(Paths.get(BACKUP_DIRECTORY + "secret.key"));
                secretKey = new SecretKeySpec(keyBytes, "AES");
            } else {
                // Generate a new secret key
                KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
                keyGenerator.init(256, new SecureRandom());
                secretKey = keyGenerator.generateKey();

                // Save the secret key to a file (for decryption later)
                try (FileOutputStream keyOut = new FileOutputStream(BACKUP_DIRECTORY + "secret.key")) {
                    keyOut.write(secretKey.getEncoded());
                }
            }

            // Initialize the cipher
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Read the input file and encrypt it
            try (FileInputStream fis = new FileInputStream(inputFile);
                 FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    byte[] output = cipher.update(buffer, 0, bytesRead);
                    if (output != null) {
                        fos.write(output);
                    }
                }
                byte[] outputBytes = cipher.doFinal();
                if (outputBytes != null) {
                    fos.write(outputBytes);
                }
            }
        } catch (Exception e) {
            throw new IOException("Error occurred while encrypting the file", e);
        }
    }

    private void decryptFile(String inputFile, String outputFile) throws IOException {
        try {
            // Load the secret key from the file
            byte[] keyBytes = Files.readAllBytes(Paths.get(BACKUP_DIRECTORY + "secret.key"));
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

            // Initialize the cipher
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Read the encrypted file and decrypt it
            try (FileInputStream fis = new FileInputStream(inputFile);
                 FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    byte[] output = cipher.update(buffer, 0, bytesRead);
                    if (output != null) {
                        fos.write(output);
                    }
                }
                byte[] outputBytes = cipher.doFinal();
                if (outputBytes != null) {
                    fos.write(outputBytes);
                }
            }
        } catch (Exception e) {
            throw new IOException("Error occurred while decrypting the file", e);
        }
    }
}