package com.example.quan_li_ks;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class dang_ki_controller implements Initializable {
    @FXML
    Button dangnhap;

    @FXML
    Button dangki;

    @FXML
    TextField userTextF;

    @FXML
    TextField passTextF;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        dangki.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!userTextF.getText().trim().isEmpty()&&!passTextF.getText().trim().isEmpty()){
                    DBultil.signUp(event,userTextF.getText(),passTextF.getText(),"");
                }else {
                    System.out.println("Nhap day du thong tin !");
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Làm ơn điền đầy đủ thông tin giùm tao !");
                    alert.show();
                }
            }
        });

        dangnhap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBultil.change_scence(event,"dang-nhap1.fxml","Dang nhap !",null,null);
            }
        });

    }


}
