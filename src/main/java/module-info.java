module com.ijse.gdse.carwash {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires java.desktop;
    requires java.mail;
    requires net.sf.jasperreports.core;

    opens com.ijse.gdse.carwash.dto.tm to javafx.base;
    opens com.ijse.gdse.carwash.controller to javafx.fxml;
    exports com.ijse.gdse.carwash;
}