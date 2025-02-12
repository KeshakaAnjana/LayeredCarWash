package com.ijse.gdse.carwash.controller;

import com.ijse.gdse.carwash.bo.BOFactory;
import com.ijse.gdse.carwash.bo.custom.BatchBO;
import com.ijse.gdse.carwash.bo.custom.ProductBO;
import com.ijse.gdse.carwash.bo.custom.SupplierBO;
import com.ijse.gdse.carwash.dto.ProductDTO;
import com.ijse.gdse.carwash.dto.tm.ProductTM;
import com.ijse.gdse.carwash.dao.custom.impl.SupplierDAOImpl;
import com.ijse.gdse.carwash.entity.Supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    
    @FXML
    private ComboBox cmbProductName;
    @FXML
    private AnchorPane Product;

    @FXML
    private Label ProductDatelbl;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnBatch;

    @FXML
    private Button btnDeleteProduct;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSaveProduct;

    @FXML
    private Button btnUpdateProduct;

    @FXML
    private TableColumn<ProductTM, String> colDate;

    @FXML
    private TableColumn<ProductTM, String> colPrice;

    @FXML
    private TableColumn<ProductTM, String> colProductId;

    @FXML
    private TableColumn<ProductTM, String> colProductName;

    @FXML
    private TableColumn<ProductTM, String> colQTY;

    @FXML
    private Label lblProductId;

    @FXML
    private TableView<ProductTM> tblProduct;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQTY;

    BatchBO batchBO = (BatchBO) BOFactory.getInstance().getBO(BOFactory.BOType.BATCH);
    ProductBO productBO = (ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);
    SupplierBO supplierBO = (SupplierBO) BOFactory.getInstance().getBO(BOFactory.BOType.SUPPLIER);

    public void initialize(URL url, ResourceBundle resourceBundle) {

        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qty"));


        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load vehicle id").show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {

        loadNextProductId();
        loadTableData();
        loadProductName();

        btnSaveProduct.setDisable(false);
        btnUpdateProduct.setDisable(true);
        btnDeleteProduct.setDisable(true);

        cmbProductName.getSelectionModel().clearSelection();
        ProductDatelbl.setText(LocalDate.now().toString());
        txtPrice.setText("");
        txtQTY.setText("");
    }

    private void loadNextProductId() throws SQLException, ClassNotFoundException {
        String nextProductId = productBO.getNext();
        lblProductId.setText(nextProductId);
    }

    private void loadProductName() throws SQLException, ClassNotFoundException {
        ArrayList<String> Status = SupplierDAOImpl.getAllSupplierStatus();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(Status);
        cmbProductName.setItems(observableList);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<ProductDTO> productDTOS = productBO.getAll();
        ObservableList<ProductTM> productTMS = FXCollections.observableArrayList();

        for (ProductDTO productDTO : productDTOS) {
            ProductTM productTM = new ProductTM(
                    productDTO.getProductId(),
                    productDTO.getProductName(),
                    productDTO.getDate(),
                    productDTO.getPrice(),
                    productDTO.getQty()
            );
            productTMS.add(productTM);
        }
        tblProduct.setItems(productTMS);
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void btnDeleteProductOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ProductId = lblProductId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {

            boolean isDeleted = productBO.delete(ProductId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, " deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete...!").show();
            }
        }
    }

    public void btnUpdateProductOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ProductId = lblProductId.getText();
        String ProductName = (String) cmbProductName.getSelectionModel().getSelectedItem();
        String Date = ProductDatelbl.getText();
        String Price = txtPrice.getText();
        String Qty = txtQTY.getText();

        cmbProductName.setStyle(cmbProductName.getStyle() + ";-fx-border-color: #045ae6;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #045ae6;");
        txtQTY.setStyle(txtQTY.getStyle() + ";-fx-border-color: #045ae6;");

        //String ProductNamePattern = "^[A-Za-z ]+$";
        String PricePattern = "^(\\d+(\\.\\d{1,2})?)$";
        String QtyPattern = "^(\\d+)$";

       // boolean isValidProductName = ProductName.matches(ProductNamePattern);
        boolean isValidPrice = Price.matches(PricePattern);
        boolean isValidQty = Qty.matches(QtyPattern);

//        if (!isValidProductName) {
//            System.out.println(txtProductName.getStyle());
//            txtProductName.setStyle(txtProductName.getStyle() + "-fx-border-color: red;");
//            System.out.println("Invalid Product Name!");
//        }
        if (!isValidPrice) {
            System.out.println(txtPrice.getStyle());
            txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid Price");
        }
        if (!isValidQty) {
            txtQTY.setStyle(txtQTY.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidPrice && isValidQty) {


            ProductDTO productDTO = new ProductDTO(
                    ProductId,
                    ProductName,
                    Date,
                    Price,
                    Qty
            );
            boolean isUpdate = productBO.update(productDTO);
            if (isUpdate) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, " update successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update product").show();
            }
        }
    }

    public void btnSaveProductOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String ProductId = lblProductId.getText();
        String ProductName = (String) cmbProductName.getSelectionModel().getSelectedItem();
        String Date = ProductDatelbl.getText();
        String Price = txtPrice.getText();
        String Qty = txtQTY.getText();

        //txtProductName.setStyle(txtProductName.getStyle() + ";-fx-border-color: #045ae6;");
        txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: #045ae6;");
        txtQTY.setStyle(txtQTY.getStyle() + ";-fx-border-color: #045ae6;");

       // String ProductNamePattern = "^[A-Za-z ]+$";
        String PricePattern = "^(\\d+(\\.\\d{1,2})?)$";
        String QtyPattern = "^(\\d+)$";

        //boolean isValidProductName = ProductName.matches(ProductNamePattern);
        boolean isValidPrice = Price.matches(PricePattern);
        boolean isValidQty = Qty.matches(QtyPattern);

//        if (!isValidProductName) {
//            System.out.println(txtProductName.getStyle());
//            txtProductName.setStyle(txtProductName.getStyle() + "-fx-border-color: red;");
//            System.out.println("Invalid Product Name!");
//        }
        if (!isValidPrice) {
            System.out.println(txtPrice.getStyle());
            txtPrice.setStyle(txtPrice.getStyle() + ";-fx-border-color: red;");
            System.out.println("Invalid Price");
        }
        if (!isValidQty) {
            txtQTY.setStyle(txtQTY.getStyle() + ";-fx-border-color: red;");
        }

        if ( isValidPrice && isValidQty) {
            ProductDTO productDTO = new ProductDTO(
                    ProductId,
                    ProductName,
                    Date,
                    Price,
                    Qty
            );
            boolean isSaved = productBO.save(productDTO);
            if (isSaved) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, " saved successfully").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save product").show();
            }
        }
    }
    public void onClickTable(MouseEvent mouseEvent) {
        ProductTM productTM = tblProduct.getSelectionModel().getSelectedItem();
        if (productTM != null) {
            lblProductId.setText(productTM.getProductId());
            cmbProductName.setValue(productTM.getProductName());
            txtPrice.setText(productTM.getPrice());
            txtQTY.setText(productTM.getQty());

            btnSaveProduct.setDisable(true);
            btnUpdateProduct.setDisable(false);
            btnDeleteProduct.setDisable(false);
        }
    }

    public void clickBackbtn(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainLayout.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) btnBack.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading the login screen.").show();
        }
    }

    public void clickBatch(ActionEvent actionEvent) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/BatchView.fxml"));
            Product.getChildren().clear();
            Product.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading the Batch...").show();
        }
    }

    public void clickcmbProductName(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedProductName = (String) cmbProductName.getSelectionModel().getSelectedItem();
        Supplier supplierDTO = supplierBO.findByName(selectedProductName);

        if (supplierDTO != null) {
            txtPrice.setText(supplierDTO.getUnitPrice());
            txtQTY.setText(supplierDTO.getQty());
        }
    }

}
