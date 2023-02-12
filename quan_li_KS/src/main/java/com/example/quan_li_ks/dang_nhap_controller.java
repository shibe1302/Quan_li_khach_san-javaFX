package com.example.quan_li_ks;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class dang_nhap_controller implements Initializable {
    @FXML
    Button loginBTN;

    @FXML
    TextField user1;

    @FXML
    PasswordField pwd1;

    @FXML
    Button signupBTN;


    @FXML
    Button thunho;

    @FXML
    Button phongto;


    @FXML
    public void closestage1(ActionEvent event)
    {
        Stage stage= (Stage) phongto.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void minimizestage(ActionEvent event)
    {
        Stage stage= (Stage) thunho.getScene().getWindow();
        stage.setIconified(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        loginBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBultil.login(event,user1.getText(),pwd1.getText());
            }
        });

        signupBTN.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBultil.change_scence(event,"dang-ki.fxml","Dang ki tai  khoan",null,null);
            }
        });

    }
}
