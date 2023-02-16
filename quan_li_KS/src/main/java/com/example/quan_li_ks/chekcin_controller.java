package com.example.quan_li_ks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

public class chekcin_controller implements Initializable {
    @FXML
    private Button checkin_btn_checkin;

    @FXML
    private Button checkin_btn_inhoadon;

    @FXML
    private Button checkin_btn_reset;

    @FXML
    private TextField checkin_email;

    @FXML
    private AnchorPane checkin_form;

    @FXML
    private TextField checkin_ho;

    @FXML
    private DatePicker checkin_ngayra;

    @FXML
    private DatePicker checkin_ngayvao;

    @FXML
    private Label checkin_number;

    @FXML
    private TextField checkin_sdt;

    @FXML
    private TextField checkin_ten;

    @FXML
    private AnchorPane closee;

    @FXML
    Button close_btn;

    @FXML
    private Label checkin_tongngay;

    @FXML
    private Label checkin_tongthanhtoan;

    @FXML
    private ComboBox<?> checkin_loaiphong;

    @FXML
    private ComboBox<?> checkin_sophong;


    // database toool
    Connection connect;
    PreparedStatement prepare;
    ResultSet resultSet;

    Statement statement;
    private double tt=0;

    public void hienThitongchiphi(){
        String tongngay=String.valueOf(getdata.tongNgay);
        checkin_tongngay.setText(tongngay);
        String selectItem= (String) checkin_sophong.getSelectionModel().getSelectedItem();
        String sqlsophongcanthanhtoan="SELECT * FROM  room WHERE soPhong='"+selectItem+"'";
        double tongthanhtoan=0;
        connect=database_connect.connectDatabase();
        try {
            prepare=connect.prepareStatement(sqlsophongcanthanhtoan);
            resultSet=prepare.executeQuery();
            while (resultSet.next()){
                tongthanhtoan=resultSet.getDouble("thanhThien");
                tt=tongthanhtoan;
            }
            float tongtien= (float) tongthanhtoan*getdata.tongNgay;
            checkin_tongthanhtoan.setText(String.valueOf(tongtien)+"VND");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void danhsachcacphong(){
        String danhsach="SELECT DISTINCT LoaiPhong FROM room WHERE TinhTrang='Phòng trống'";
        connect= database_connect.connectDatabase();
        try {
            ObservableList danhsachdata= FXCollections.observableArrayList();
            prepare=connect.prepareStatement(danhsach);
            resultSet=prepare.executeQuery();
            while (resultSet.next()){
                danhsachdata.add(resultSet.getString("LoaiPhong"));
            }
            checkin_loaiphong.setItems(danhsachdata);
            Sophonglistdata();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void Sophonglistdata(){
        String item= (String) checkin_loaiphong.getSelectionModel().getSelectedItem();
        String nhungphonghienco="SELECT * FROM room WHERE LoaiPhong='"+item+"' AND TinhTrang='Phòng trống' order by soPhong ASC";
        database_connect.connectDatabase();
        try {
            ObservableList listphong= FXCollections.observableArrayList();
            prepare=connect.prepareStatement(nhungphonghienco);
            resultSet=prepare.executeQuery();
            while (resultSet.next()){
                listphong.add(resultSet.getString("soPhong"));
            }
            checkin_sophong.setItems(listphong);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void tongNgay(){
        Alert alert;
        if(!(checkin_ngayra.getValue().isAfter(checkin_ngayvao.getValue()))){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi !");
            alert.setContentText("Ngày check-in, check-out không hợp lệ");
            alert.setHeaderText(null);
            alert.showAndWait();

        }else {
            getdata.tongNgay=checkin_ngayra.getValue().compareTo(checkin_ngayvao.getValue());
            hienThitongchiphi();
        }
    }
    public void hienThiNgay(){
        String tongngay11=String.valueOf(getdata.tongNgay);
        checkin_tongngay.setText(tongngay11);
        double tongthanhtoan=0;
    }


    public void customercheckIn() {
        String insertCustomerdata = "INSERT INTO custommer (customer_ID, hoadon, loaiphong, sophong, ho, ten, sdt, email, vao, ra) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        connect = database_connect.connectDatabase();
        try {
            String customernum = checkin_number.getText();
            String loai=(String) checkin_loaiphong.getSelectionModel().getSelectedItem();
            String so=(String) checkin_sophong.getSelectionModel().getSelectedItem();
            String ho1 = checkin_ho.getText();
            String ten1 = checkin_ten.getText();
            String sdt1 = checkin_sdt.getText();
            String mail = checkin_email.getText();
            String total=String.valueOf(checkin_tongthanhtoan);
            String vao1 = String.valueOf(checkin_ngayvao.getValue());
            String ra1 = String.valueOf(checkin_ngayra.getValue());
            Alert alert;

            if (customernum == null || ho1 == null || ten1 == null || sdt1 == null || mail == null || vao1 == null || ra1 == null||total==null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi !");
                alert.setContentText("Vui lòng nhập đầy đủ thông tin !");
                alert.setContentText(null);
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Thông báo !");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn thêm dữ liệu ?");
                Optional<ButtonType> optional = alert.showAndWait();
                if(optional.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(insertCustomerdata);
                    prepare.setString(1, customernum);
                    prepare.setString(2, String.valueOf(tt));
                    prepare.setString(3, loai);
                    prepare.setString(4, so);
                    prepare.setString(5, ho1);
                    prepare.setString(6, ten1);
                    prepare.setString(7, sdt1);
                    prepare.setString(8, mail);
                    prepare.setString(9, vao1);
                    prepare.setString(10, ra1);
                    prepare.executeUpdate();

                    String date1=String.valueOf(checkin_ngayvao.getValue());
                    String tong1=String.valueOf(tt);
                    String makhach=checkin_number.getText();

                    String hoadon="INSERT INTO customer_hoadon (customer_num, date1, total) VALUES (?, ?, ?)";
                    prepare=connect.prepareStatement(hoadon);
                    prepare.setString(1,makhach);
                    prepare.setString(2,date1);
                    prepare.setString(3,tong1);
                    prepare.executeUpdate();

                    String sqlupdateTrangThai="UPDATE room SET TinhTrang = 'Đã được đặt' WHERE (`soPhong` = '"+so+"')";
                    statement=connect.createStatement();
                    statement.executeUpdate(sqlupdateTrangThai);



                    alert= new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo !");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã cập nhật thông tin phòng thành công !");
                    alert.showAndWait();
                    resett();


                }else {
                    return;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ediabke(){
        String a= (String) checkin_loaiphong.getSelectionModel().getSelectedItem();
        System.out.println(a);
            checkin_sophong.setDisable(false);


    }
    public void resett(){
        checkin_ho.setText("");
        checkin_ten.setText("");
        checkin_sdt.setText("");
        checkin_email.setText("");
        checkin_loaiphong.getSelectionModel().clearSelection();
        checkin_sophong.getSelectionModel().clearSelection();

        checkin_tongngay.setText("---");
        checkin_tongthanhtoan.setText("0.00 VND");
    }

        public void layIDkhachtudata () {
            String customernum = "SELECT customer_ID FROM custommer";
            connect = database_connect.connectDatabase();
            try {
                prepare = connect.prepareStatement(customernum);
                resultSet = prepare.executeQuery();
                while (resultSet.next()) {
                    getdata.customerNum = resultSet.getInt("customer_ID");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        public void hienthiIDkhach () {
            layIDkhachtudata ();

            checkin_number.setText(String.valueOf(getdata.customerNum + 1));
        }


        public void Close () {
            Stage stage = (Stage) close_btn.getScene().getWindow();
            stage.close();
        }

        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
            hienthiIDkhach();
            danhsachcacphong();
            Sophonglistdata();


        }
    }

