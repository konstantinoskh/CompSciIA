package com.example.compsciia;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

public class FileDecryption {
    private static final String KEY = "konstantinos123456789123";


    public static void decryptFile() throws Exception {
        // read the encrypted contents of the text file into a byte array
        Path path = Paths.get("users.txt");
        byte[] encryptedFileContents = Files.readAllBytes(path);

        // create a secret key from the key string
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8),"AES");

        // create a cipher and initialize it with the secret key in decrypt mode
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // decrypt the encrypted file contents
        byte[] fileContents = cipher.doFinal(encryptedFileContents);

        // write the decrypted contents back to the file
        Files.write(path, fileContents);
    }
    public static SecretKey getKeyFromKeyGenerator() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(192);
        return keyGenerator.generateKey();
    }
}

