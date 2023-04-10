module com.example.questifyit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.logging.log4j;


    opens com.example.questifyit to javafx.fxml;
    exports com.example.questifyit;

}