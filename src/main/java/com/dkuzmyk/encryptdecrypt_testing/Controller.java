package com.dkuzmyk.encryptdecrypt_testing;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    // encryption string
    private final String SS = "SomeCryptoValueH";

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label fileLabel;
    @FXML
    private Label fileLabel2;

    private File file;
    private logic logic;

    public void getInput(ActionEvent actionEvent) {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        final FileChooser fileChooser = new FileChooser();

        /*fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XLS Files (*.xls)", "*.xls")
        );*/

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            System.out.println("Fetching data.");
            fileLabel.setText("File: " +file.getAbsolutePath());
            // set file as file to process for encryption/decryption
            logic.setInputPath(new File(file.getAbsolutePath()));
            String ext = file.getName().substring(file.getName().length()-4);
            logic.setFileName(new File(file.getName().replace(ext, "")));
        }
    }

    public void encrypt(ActionEvent actionEvent) throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException {
        logic.encrypt();
    }

    public void decrypt(ActionEvent actionEvent) throws InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        logic.decrypt();
    }

    public void getOutputFolder(ActionEvent actionEvent) {
        DirectoryChooser dc = new DirectoryChooser();
        dc.setTitle("Output path");
        Stage stage = (Stage) mainPane.getScene().getWindow();
        File selectedDir = dc.showDialog(stage);
        if(selectedDir != null) {
            String path = selectedDir.getAbsolutePath();
            logic.setOutputPath(new File(path));
            fileLabel2.setText("Output: " + path);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logic = new logic(SS);
    }
}