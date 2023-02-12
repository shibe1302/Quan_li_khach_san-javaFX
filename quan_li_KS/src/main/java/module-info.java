module com.example.quan_li_ks {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.quan_li_ks to javafx.fxml;
    exports com.example.quan_li_ks;
    requires java.sql;
}