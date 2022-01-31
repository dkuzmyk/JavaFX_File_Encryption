package com.dkuzmyk.encryptdecrypt_testing;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;


public class logic {
    private String inputPath;
    private String outputPath;
    private String fileName;

    private SecretKeySpec k;
    private Cipher c;
    private byte[] key;

    logic(String crypto_string){
        try{
            key = crypto_string.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            k = new SecretKeySpec(key, "AES");

            //k = new SecretKeySpec((crypto_string.getBytes(StandardCharsets.UTF_8)), "AES");

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void encrypt() throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
        c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE,k);
        // read the file as stream of bytes
        FileInputStream in = new FileInputStream(inputPath);
        // create an array of bytes to hold the file
        byte[] inputBytes = new byte[(int) inputPath.length()];
        // read in the file
        in.read(inputBytes);
        // encrypt
        byte[] enc = c.doFinal(inputBytes);
        System.out.println(Arrays.toString(enc));
        System.out.println(outputPath+"\\"+fileName+".ENC");
        // create the output stream to write the encrypted file
        FileOutputStream out = new FileOutputStream(outputPath+"\\"+fileName+".ENC");
        // endocde to base64 and write
        out.write(Base64.getEncoder().encode(enc));

        in.close();
        out.close();

    }

    public void decrypt() throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE,k, new IvParameterSpec(new byte[16]));
        // read the file as stream of bytes
        FileInputStream in = new FileInputStream(inputPath);
        // create an array of bytes to hold the file
        byte[] inputBytes = new byte[(int) inputPath.length()];
        // read in the file
        in.read(inputBytes);
        System.out.println(inputBytes);
        // encrypt
        byte[] enc = c.doFinal(Base64.getDecoder().decode(inputBytes));
        System.out.println(Arrays.toString(enc));
        System.out.println(outputPath+"\\"+fileName+".xls");
        // create the output stream to write the encrypted file
        FileOutputStream out = new FileOutputStream(outputPath+"\\"+fileName+"_decrypted.xls");
        // write
        out.write(enc);

        in.close();
        out.close();
    }

}
