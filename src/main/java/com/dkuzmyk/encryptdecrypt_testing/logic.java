package com.dkuzmyk.encryptdecrypt_testing;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;
import java.util.Base64;


public class logic {
    private File inputPath;
    private File outputPath;
    private File fileName;

    private Key k;
    private Cipher c;

    logic(String crypto_string){
        try{
            k = new SecretKeySpec(crypto_string.getBytes(), "AES");
            c = Cipher.getInstance("AES/CBC/PKCS5Padding");

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void setInputPath(File inputPath) {
        this.inputPath = inputPath;
    }

    public void setOutputPath(File outputPath) {
        this.outputPath = outputPath;
    }

    public void setFileName(File fileName) {
        this.fileName = fileName;
    }

    public void encrypt() throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
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
        //out.write(Base64.getEncoder().encode(enc));
        out.write(enc);
        in.close();
        out.close();
    }

    public void decrypt() throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        c.init(Cipher.DECRYPT_MODE,k);
        // read the file as stream of bytes
        FileInputStream in = new FileInputStream(inputPath);
        // create an array of bytes to hold the file
        byte[] inputBytes = new byte[(int) inputPath.length()];
        // read in the file
        in.read(inputBytes);
        System.out.println(inputBytes);
        // decrypt
        //byte[] enc = c.doFinal(Base64.getDecoder().decode(inputBytes));
        byte[] enc = c.doFinal(inputBytes);
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
