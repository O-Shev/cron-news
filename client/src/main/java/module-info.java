module com.leshkins.cronnews.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;
    requires spring.web;
    requires java.sql;

    requires com.fasterxml.jackson.databind;

    opens com.leshkins.cronnews.client to javafx.fxml;
    exports com.leshkins.cronnews.client;
}