package com.example.quan_li_ks;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;
import java.io.IOException;

public class DBultil {
    private static double x1=0;
    private static double y1=0;

    public static void setstage(){
        Parent root1 =null;
        try {

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void change_scence(ActionEvent event,String fmxlFile,String title,String user, String gmail){
        Parent root = null;
        if(user!=null){
            try{
                FXMLLoader loader = new FXMLLoader(DBultil.class.getResource(fmxlFile));
                root= loader.load();
                main_dashboard_controller main_dashboard_controller= loader.getController();
                main_dashboard_controller.set_textxinchao(gmail);
            }catch (Exception e){
                System.out.println(e.getMessage());

            }
        }else {
            try {
                root = FXMLLoader.load(DBultil.class.getResource(fmxlFile));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        Stage stage=new Stage();

        //Stage stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.initStyle(StageStyle.TRANSPARENT);

        stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        root.setOnMousePressed(MouseEvent ->{
            x1= MouseEvent.getSceneX();
            y1=MouseEvent.getSceneY();
        });

        stage.setScene(new Scene(root));
        stage.setResizable(false);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);

        stage.show();


    }



    public static void signUp(ActionEvent event,String user, String password, String gmail){
        Connection connection= null;
        PreparedStatement psInsert= null;
        PreparedStatement Checkuserexist= null;
        ResultSet resultSet = null;

        try {
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/login-sign-up-ks","root","");
            Checkuserexist= connection.prepareStatement("SELECT * FROM user_data WHERE user_name=?");
            Checkuserexist.setString(1,user);
            resultSet= Checkuserexist.executeQuery();

            if(resultSet.isBeforeFirst()) {
                System.out.println("Tai khoan da ton tai !");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Tai khoan nay da ton tai !");
                alert.show();
            }else {
                psInsert= connection.prepareStatement("INSERT INTO user_data ( user_name, pwd) VALUES (?,?)");
                psInsert.setString(1,user);
                psInsert.setString(2,password);
                psInsert.executeUpdate();
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Thông báo");
                alert1.setHeaderText("Đăng kí tài khoản thành công !");
                alert1.setContentText("Vui lòng đăng nhập !");
                alert1.showAndWait();

                change_scence(event,"dang-nhap1.fxml","Chao mung den voi app quan li khach san shibe !",user,user);

            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(resultSet!=null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(Checkuserexist!=null){
                try {
                    Checkuserexist.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert!=null){
                try {
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }
    public static void login (ActionEvent event,String username, String password){
        Connection connection= null;
        PreparedStatement preparedStatement =null;
        ResultSet resultSet = null;
        try {
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/login-sign-up-ks","root","");
            preparedStatement= connection.prepareStatement("select pwd from user_data where user_name=?");
            preparedStatement.setString(1,username);
            resultSet=preparedStatement.executeQuery();

            if(!resultSet.isBeforeFirst()){
                System.out.println("khong tim thay tai khoan !");
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setContentText("khong tim thay tai khoan !");
                alert.show();
            } else {
                while (resultSet.next()){
                    String passwordnhandc= resultSet.getString("pwd");
                    if(passwordnhandc.equals(password)){
                        change_scence(event,"home.fxml","hello shibe !",username,username);

                    }else {
                        System.out.println("sai mk!");
                        Alert alert= new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Mat khau khong chinh xac !");
                        alert.show();
                    }
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        } finally {
            if(resultSet!=null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(preparedStatement!=null){
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection!=null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

        }

    }

}
