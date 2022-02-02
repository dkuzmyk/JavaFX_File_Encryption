module com.spray.dkuzmyk.encryptdecrypt_testing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.dkuzmyk.encryptdecrypt_testing to javafx.fxml;
    exports com.dkuzmyk.encryptdecrypt_testing;
}