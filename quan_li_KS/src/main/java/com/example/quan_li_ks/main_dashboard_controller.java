package com.example.quan_li_ks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class main_dashboard_controller implements Initializable {

    double x=0;
    double y=0;

    @FXML
    private Label BDK_SoPhongBook;

    @FXML
    private Label BDK_ThuNhapHomNay;

    @FXML
    private Label BDK_TongThuNhap;

    @FXML
    private AreaChart<?, ?> BDK_chart;

    @FXML
    private Button BangDieuKHien_BTN;

    @FXML
    private TableColumn<?, ?> KH_cot_checkout;

    @FXML
    private TableColumn<?, ?> KH_cot_chekcin;

    @FXML
    private TableColumn<?, ?> KH_cot_hovaten;

    @FXML
    private TableColumn<?, ?> KH_cot_khachHang;

    @FXML
    private TableColumn<?, ?> KH_cot_sodienthoai;

    @FXML
    private TableColumn<?, ?> KH_cot_thanhtoan;

    @FXML
    private AnchorPane KH_panel;

    @FXML
    private TextField KH_search_khachhang;

    @FXML
    private TableView<?> KH_table_khachHang;

    @FXML
    private Button KhachHang_BTN;

    @FXML
    private ComboBox<String> NhapTT_LoaiPhong;

    @FXML
    private AnchorPane NhapTT_Panel;

    @FXML
    private Button NhapTT_btn_capnhat;

    @FXML
    private Button NhapTT_btn_checkin;

    @FXML
    private Button NhapTT_btn_clear;

    @FXML
    private Button NhapTT_btn_them;

    @FXML
    private Button NhapTT_btn_xoa;

    @FXML
    private TableColumn<roomData, String> NhapTT_cot_Tinhtrang;

    @FXML
    private TableColumn<roomData, String> NhapTT_cot_giatien;

    @FXML
    private TableColumn<roomData, String> NhapTT_cot_loaiPhong;

    @FXML
    private TableColumn<roomData, String> NhapTT_cot_phong;

    @FXML
    private TextField NhapTT_phongSo;

    @FXML
    private TextField NhapTT_search;

    @FXML
    private TableView<roomData> NhapTT_table;

    @FXML
    private TextField NhapTT_thanhtien;

    @FXML
    private ComboBox<String> NhapTT_tinhTrang;

    @FXML
    private Button PhongTrong_BTN;

    @FXML
    private AnchorPane bangdieukhienPanel;

    @FXML
    private Button close_btn;

    @FXML
    private Label label_xinchao;

    @FXML
    private Label label_xinchao1;

    @FXML
    private Label label_xinchao11;

    @FXML
    private Label label_xinchao111;

    @FXML
    private Label label_xinchao12;

    @FXML
    private Label label_xinchao2;

    @FXML
    private Button logout;

    @FXML
    private Button thu_nho_btn;

    private Connection connect;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;


    public void themDuLieu(){
        String sql="INSERT INTO room (soPhong, LoaiPhong, TinhTrang, thanhThien) VALUES (?,?,?,?)";
        connect=database_connect.connectDatabase();
        try {
            String phongso = NhapTT_phongSo.getText();
            String loaiPhong= (String) NhapTT_LoaiPhong.getSelectionModel().getSelectedItem();
            String tinhtrang= (String) NhapTT_tinhTrang.getSelectionModel().getSelectedItem();
            String thanhtien= NhapTT_thanhtien.getText();

            Alert alert;

            if(phongso.isEmpty()||loaiPhong.isEmpty()||tinhtrang.isEmpty()||thanhtien.isEmpty()){
                alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thiếu thông tin !!");
                alert.setHeaderText("Thiếu thông tin check-in !!!");
                alert.setContentText("Vui lòng nhập đầy đủ thông tin !!!");
                alert.showAndWait();
            }else {
                String checknumroom="SELECT soPhong FROM room WHERE soPhong='"+phongso+"'";

                preparedStatement=connect.prepareStatement(checknumroom);
                resultSet=preparedStatement.executeQuery();
                if(resultSet.next()){
                    alert= new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi rồi má ơi !");
                    alert.setHeaderText(null);
                    alert.setContentText("Số phòng này đã tồn tại !");
                    alert.showAndWait();
                }else {
                    preparedStatement= connect.prepareStatement(sql);
                    preparedStatement.setString(1,phongso);
                    preparedStatement.setString(2,loaiPhong);
                    preparedStatement.setString(3,tinhtrang);
                    preparedStatement.setString(4,thanhtien);
                    preparedStatement.executeUpdate();

                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("thông báo thông tin");
                    alert.setHeaderText(null);
                    alert.setContentText("Thông tin được cập nhật thành công");
                    HienDataLenBang();
                    alert.showAndWait();


                    xoadulieu();
                }




            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String type[]={"Giường đơn", "Giường đôi", "Giường ba", "Giường tình yêu","Bồng lai tiên cảnh","Phòng Vua"};
    public void LoaiPhongks(){
        List<String> listdata= new ArrayList<>();
        for (String data: type) {
            listdata.add(data);
        }
        ObservableList<String> list1= FXCollections.observableList(listdata);
        NhapTT_LoaiPhong.setItems(list1);
    }
    private String type2[]={"Phòng trống", "Đang bảo trì", "Có ma ám", "Đã được đặt trước"};
    public void tinhtrangphong(){
        List<String> listdata1= new ArrayList<>();
        for (String data: type2) {
            listdata1.add(data);
        }
        ObservableList<String> list1= FXCollections.observableList(listdata1);
        NhapTT_tinhTrang.setItems(list1);
    }

    public ObservableList<roomData> phongSanCodataTable(){
        ObservableList<roomData> listdata= FXCollections.observableArrayList();
        String sql ="SELECT * FROM room";
        connect= database_connect.connectDatabase();
        try {
            roomData roomData1;
            preparedStatement= connect.prepareStatement(sql);
            resultSet= preparedStatement.executeQuery();
            while (resultSet.next()){
                roomData1= new roomData(resultSet.getInt("soPhong"),resultSet.getString("LoaiPhong"),resultSet.getString("TinhTrang"),resultSet.getDouble("thanhThien"));
                listdata.add(roomData1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return listdata;
    }

    private ObservableList<roomData> roomDatalist;
    public void HienDataLenBang(){
        roomDatalist=phongSanCodataTable();
        NhapTT_cot_phong.setCellValueFactory(new PropertyValueFactory<>("phongso"));
        NhapTT_cot_loaiPhong.setCellValueFactory(new PropertyValueFactory<>("LoaiPhong"));
        NhapTT_cot_Tinhtrang.setCellValueFactory(new PropertyValueFactory<>("TinhTrang"));
        NhapTT_cot_giatien.setCellValueFactory(new PropertyValueFactory<>("thanhTien"));

        NhapTT_table.setItems(roomDatalist);

    }

    public void xoadulieu(){

        NhapTT_phongSo.setText("");
        NhapTT_LoaiPhong.getSelectionModel().clearSelection();
        NhapTT_tinhTrang.getSelectionModel().clearSelection();
        NhapTT_thanhtien.setText("");

    }

    @FXML
    public void closestage1(ActionEvent event)
    {
        Stage stage= (Stage) close_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void minimizestage(ActionEvent event)
    {
        Stage stage= (Stage) close_btn.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    private AnchorPane title_Drag;

    @FXML
    public void dagbar(MouseEvent event){
        Stage stage= (Stage) title_Drag.getScene().getWindow();
        stage.setX(event.getScreenX()-x);
        stage.setY(event.getScreenY()-y);
    }


    @FXML
    public void clickbar(MouseEvent event){
        x=event.getSceneX();
        y=event.getSceneY();
    }
    public void updateData(){
        String loai1= (String) NhapTT_LoaiPhong.getSelectionModel().getSelectedItem();
        String tinhtrang1=(String) NhapTT_tinhTrang.getSelectionModel().getSelectedItem();
        String giaphong= NhapTT_thanhtien.getText();
        String soPhong=NhapTT_phongSo.getText();

        String sql="UPDATE room SET LoaiPhong = '"+loai1+"', TinhTrang = '"+tinhtrang1+"', `thanhThien` = '"+giaphong+"' WHERE soPhong = '"+soPhong+"'";
        connect=database_connect.connectDatabase();

        try{
            Alert alert ;
            if(loai1==null||tinhtrang1==null||giaphong==null||soPhong==null){
               alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Lỗi !");
               alert.setContentText("Vui lòng nhập đầy đủ thông tin !");
               alert.setHeaderText(null);
               alert.showAndWait();
            }else {
                preparedStatement= connect.prepareStatement(sql);
                preparedStatement.executeUpdate();
                alert= new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo !");
                alert.setHeaderText(null);
                alert.setContentText("Đã cập nhật thông tin phòng thành công !");
                alert.showAndWait();
                HienDataLenBang();
                xoadulieu();

            }




        }catch (Exception e){
            e.printStackTrace();
        }



    }
    public void xoadulieudatabase(){

        String soPhong=NhapTT_phongSo.getText();

        String sql="DELETE FROM room WHERE soPhong='"+soPhong+"'";
        connect=database_connect.connectDatabase();

        try{
            Alert alert;
            if(soPhong.isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi !");
                alert.setContentText("Vui lòng nhập đầy đủ thông tin !");
                alert.setContentText(null);
                alert.showAndWait();

            }else {

                alert= new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Thông báo !");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn xóa dữ liệu ?");
                Optional<ButtonType> optional= alert.showAndWait();
                if(optional.get().equals(ButtonType.OK)){
                    statement= connect.createStatement();
                    statement.executeUpdate(sql);
                    alert= new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo !");
                    alert.setHeaderText(null);
                    alert.setContentText("Đã cập nhật thông tin phòng thành công !");
                    alert.showAndWait();
                    HienDataLenBang();
                    xoadulieu();
                }
                HienDataLenBang();
                xoadulieu();
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void checkIN(){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("check-in.fxml"));
            Stage stage1 = new Stage();
            Scene scene= new Scene(root);
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    x=event.getSceneX();
                    y=event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage1.setX(event.getScreenX()-x);
                    stage1.setY(event.getScreenY()-y);
                }
            });
            stage1.initStyle(StageStyle.TRANSPARENT);
            stage1.setScene(scene);
            stage1.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
















    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoaiPhongks();
        tinhtrangphong();
        HienDataLenBang();



        logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("wtf ");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Thông báo !");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có muốn đăng xuất không ?");
                Optional<ButtonType> optional= alert.showAndWait();
                DBultil.change_scence(event,"dang-nhap1.fxml",null,null,null);
            }
        });
    }
    public void set_textxinchao(String gmail){
        label_xinchao.setText("Xin chào : "+gmail);
    }
    public void selectData(){
        roomData roomData1= NhapTT_table.getSelectionModel().getSelectedItem();
        int num= NhapTT_table.getSelectionModel().getSelectedIndex();
        if((num-1)<-1){
            return;
        }
        NhapTT_phongSo.setText(String.valueOf(roomData1.getPhongso()));
        NhapTT_thanhtien.setText(String.valueOf(roomData1.getThanhTien()));
    }


}
