package com.ijse.gdse.carwash.controller;

import com.ijse.gdse.carwash.bo.BOFactory;
import com.ijse.gdse.carwash.bo.custom.BatchBO;
import com.ijse.gdse.carwash.bo.custom.CustomerBO;
import com.ijse.gdse.carwash.bo.custom.ProductBO;
import com.ijse.gdse.carwash.dao.DAOFactory;
import com.ijse.gdse.carwash.dao.custom.BatchDAO;
import com.ijse.gdse.carwash.dao.custom.ProductDAO;
import com.ijse.gdse.carwash.dto.BatchDTO;
import com.ijse.gdse.carwash.dto.ProductDTO;
import com.ijse.gdse.carwash.dto.tm.BatchTM;
import com.ijse.gdse.carwash.dao.custom.impl.BatchDAOImpl;
import com.ijse.gdse.carwash.dao.custom.impl.ProductDAOImpl;
import com.ijse.gdse.carwash.entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class BatchController implements Initializable {
    @FXML
    private AnchorPane BatchAnchor;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDeleteBatch;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSaveBatch;

    @FXML
    private Button btnUpdateBatch;

    @FXML
    private ComboBox<String> cmbProductId;

    @FXML
    private TableColumn<BatchTM, String> colBatchId;

    @FXML
    private TableColumn<BatchTM, String> colDate;

    @FXML
    private TableColumn<BatchTM, String> colPrice;

    @FXML
    private TableColumn<BatchTM, String> colProductId;

    @FXML
    private TableColumn<BatchTM, String> colQty;

    @FXML
    private Label lblBatchDate;

    @FXML
    private Label lblBatchId;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    private Label productQty;

    @FXML
    private TableView<BatchTM> tblBatch;

    BatchBO batchBO = (BatchBO) BOFactory.getInstance().getBO(BOFactory.BOType.BATCH);
    ProductBO productBO = (ProductBO) BOFactory.getInstance().getBO(BOFactory.BOType.PRODUCT);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        colBatchId.setCellValueFactory(new PropertyValueFactory<>("batchId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));

        try {
            refreshPage();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load booking id" + e.getMessage()).show();
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadProductId();
        loadNextBatchId();
        loadTableData();

        btnSaveBatch.setDisable(false);
        btnUpdateBatch.setDisable(true);
        btnDeleteBatch.setDisable(true);

        lblBatchDate.setText(LocalDate.now().toString());
        cmbProductId.getSelectionModel().clearSelection();
        productName.setText("");
        productPrice.setText("");
        productQty.setText("");
    }

    private void loadNextBatchId() throws SQLException, ClassNotFoundException {
        String nextBatchId = batchBO.getNext();
        lblBatchId.setText(nextBatchId);
    }

    private void loadProductId() throws SQLException, ClassNotFoundException {
        ArrayList<String> ProductId = ProductDAOImpl.getAllProductIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(ProductId);
        cmbProductId.setItems(observableList);
    }

    private void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<BatchDTO> batchDTOS = batchBO.getAll();
        ObservableList<BatchTM> batchTMS = FXCollections.observableArrayList();

        for (BatchDTO batchDTO : batchDTOS) {
            BatchTM batchTM = new BatchTM(
                    batchDTO.getBatchID(),
                    batchDTO.getDate(),
                    batchDTO.getQty(),
                    batchDTO.getPrice(),
                    batchDTO.getProductId()
            );
            batchTMS.add(batchTM);
        }
        tblBatch.setItems(batchTMS);
    }

    public void clickcmbProductId(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String selectedProductId = cmbProductId.getSelectionModel().getSelectedItem();
        Product productDTO = productBO.findById(selectedProductId);

        if (productDTO != null) {

            productName.setText(productDTO.getProductName());
            productQty.setText(productDTO.getQty());
            productPrice.setText(productDTO.getPrice());

        }
    }

    public void btnResetOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    public void btnDeleteBatchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String BatchId = lblBatchId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?",ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        if(optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES){
            boolean isDeleted = batchBO.delete(BatchId);
            if (isDeleted) {
                refreshPage();
                new Alert(Alert.AlertType.INFORMATION, "batch deleted...!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete ...!").show();
            }
        }
    }

    public void btnUpdateBatchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String BatchId = lblBatchId.getText();
        String Date = lblBatchDate.getText();
        String Qty = productQty.getText();
        String Price = productPrice.getText();
        String ProductId = cmbProductId.getSelectionModel().getSelectedItem();

        cmbProductId.setStyle(cmbProductId.getStyle() + ";-fx-border-color: #045ae6;");
        productName.setStyle(productName.getStyle() + ";-fx-border-color: #045ae6;");
        productQty.setStyle(productQty.getStyle() + ";-fx-border-color: #045ae6;");
        productPrice.setStyle(productPrice.getStyle() + ";-fx-border-color: #045ae6;");

        BatchDTO batchDTO = new BatchDTO(
                BatchId,
                Date,
                Qty,
                Price,
                ProductId
        );
        boolean isUpdate = batchBO.update(batchDTO);
        if(isUpdate) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, " update successfully").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to update....").show();
        }
    }

    public void btnSaveBatchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String BatchId = lblBatchId.getText();
        String ProductId = cmbProductId.getSelectionModel().getSelectedItem();
        String Date = lblBatchDate.getText();
        String Qty = productQty.getText();
        String Price = productPrice.getText();

        BatchDTO batchDTO = new BatchDTO(
                BatchId,
                Date,
                Qty,
                Price,
                ProductId
        );
        boolean isSaved = batchBO.save(batchDTO);
        if(isSaved) {
            refreshPage();
            new Alert(Alert.AlertType.INFORMATION, " saved successfully").show();
        }else{
            new Alert(Alert.AlertType.ERROR, "Fail to save....").show();
        }
    }

    public void onClicktable(MouseEvent mouseEvent) {
        BatchTM batchTM = tblBatch.getSelectionModel().getSelectedItem();
        if (batchTM != null) {
            lblBatchId.setText(batchTM.getBatchId());
            cmbProductId.setValue(batchTM.getProductId());
            productPrice.setText(batchTM.getPrice());
            productQty.setText(batchTM.getQty());
            lblBatchDate.setText(batchTM.getDate());

            btnSaveBatch.setDisable(true);
            btnUpdateBatch.setDisable(false);
            btnDeleteBatch.setDisable(false);
        }
    }

    public void clickBackbtn(ActionEvent actionEvent) {
        try {
            AnchorPane load = FXMLLoader.load(getClass().getResource("/view/ProductView.fxml"));
            BatchAnchor.getChildren().clear();
            BatchAnchor.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error loading the product...").show();
        }
    }
}
