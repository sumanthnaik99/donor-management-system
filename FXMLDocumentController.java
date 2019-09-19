/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temple_app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author hp
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private BorderPane border_pane;
    @FXML
    private VBox bor;
    @FXML
    private Button category_button;
    @FXML
    private Button new_donor_button;
    @FXML
    private Button update_donor_button;
    @FXML
    private Button donation_button;
    @FXML
    private Button transactions_button;
    @FXML
    private Button report_button;
    @FXML
    private Button change_password_button;
    @FXML
    private Button logout_button;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void show_category(MouseEvent event) throws IOException {
        Parent category = FXMLLoader.load(getClass().getResource("/temple_app/category.fxml"));
        border_pane.setCenter(category);
    }

    @FXML
    private void show_new_donor(MouseEvent event) throws IOException {
        
        Parent new_donor = FXMLLoader.load(getClass().getResource("/temple_app/new_donor.fxml"));
        border_pane.setCenter(new_donor);
    }

    @FXML
    private void category_mouse_exited(MouseEvent event) {
        category_button.setStyle("-fx-background-color: #346285");
    }

    @FXML
    private void category_mouse_entered(MouseEvent event) {
        category_button.setStyle("-fx-background-color: #c3d1e8");
    }

    @FXML
    private void new_donor_mouse_exited(MouseEvent event) {
        new_donor_button.setStyle("-fx-background-color: #346285");
    }

    @FXML
    private void new_donor_mouse_entered(MouseEvent event) {
        new_donor_button.setStyle("-fx-background-color: #c3d1e8");
    }

    @FXML
    private void update_donor_mouse_exited(MouseEvent event) {
        update_donor_button.setStyle("-fx-background-color: #346285");
    }

    @FXML
    private void update_donor_mouse_entered(MouseEvent event) {
        update_donor_button.setStyle("-fx-background-color: #c3d1e8");
    }

    @FXML
    private void donation_mouse_exited(MouseEvent event) {
        donation_button.setStyle("-fx-background-color: #346285");
    }

    @FXML
    private void donation_mouse_entered(MouseEvent event) {
        donation_button.setStyle("-fx-background-color: #c3d1e8");
    }

    @FXML
    private void transactions_mouse_exited(MouseEvent event) {
        transactions_button.setStyle("-fx-background-color: #346285");
    }

    @FXML
    private void transactions_mouse_entered(MouseEvent event) {
        transactions_button.setStyle("-fx-background-color: #c3d1e8");
    }

    @FXML
    private void report_mouse_exited(MouseEvent event) {
        report_button.setStyle("-fx-background-color: #346285");
    }

    @FXML
    private void report_mouse_entered(MouseEvent event) {
        report_button.setStyle("-fx-background-color: #c3d1e8");
    }

    @FXML
    private void change_password_mouse_exited(MouseEvent event) {
        change_password_button.setStyle("-fx-background-color: #346285");
    }

    @FXML
    private void change_password_mouse_entered(MouseEvent event) {
        change_password_button.setStyle("-fx-background-color: #c3d1e8");
    }

    @FXML
    private void logout_mouse_exited(MouseEvent event) {
        logout_button.setStyle("-fx-background-color: #346285");
    }

    @FXML
    private void logout_mouse_entered(MouseEvent event) {
        logout_button.setStyle("-fx-background-color: #c3d1e8");
    }

    @FXML
    private void show_update_donor(MouseEvent event) throws IOException {
        Parent update_donor = FXMLLoader.load(getClass().getResource("/temple_app/update_donor.fxml"));
        border_pane.setCenter(update_donor);
    }

    @FXML
    private void show_donation(MouseEvent event) throws IOException {
        Parent donations = FXMLLoader.load(getClass().getResource("/temple_app/donations.fxml"));
        border_pane.setCenter(donations);
    }

    @FXML
    private void show_transactions(MouseEvent event) throws IOException {
        Parent transactions = FXMLLoader.load(getClass().getResource("/temple_app/transactions.fxml"));
        border_pane.setCenter(transactions);
    }

    @FXML
    private void show_report(MouseEvent event) throws IOException {
        Parent report = FXMLLoader.load(getClass().getResource("/temple_app/report.fxml"));
        border_pane.setCenter(report);
    }

    @FXML
    private void show_change_password(MouseEvent event) throws IOException {
        Parent change_password = FXMLLoader.load(getClass().getResource("/temple_app/change_password.fxml"));
        border_pane.setCenter(change_password);
    }


    @FXML
    private void show_login(ActionEvent event) throws IOException{
        Parent login_parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene login_scene = new Scene(login_parent);
        Stage login_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        login_stage.hide();
        login_stage.setScene(login_scene);
        login_stage.show();
    }
}
