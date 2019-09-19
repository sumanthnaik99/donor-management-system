/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temple_app;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class CategoryController implements Initializable {
    
    final ObservableList ccombo = FXCollections.observableArrayList();
    Connection conn = null;

    @FXML
    private TextField category_textfield;
    @FXML
    private ComboBox<?> category_combo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cat_Fillcombo();
    }    

    @FXML
    private void add_category(ActionEvent event) {
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            int g;
            g= ccno();
            String b,c,d;
            b= Integer.toString(g);
            c=category_textfield.getText();
            d="insert into category(category_id,category_name) values("+b+",'"+c+"')";
            st.executeUpdate(d);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Dialog box");
            alert1.setHeaderText("Entered Successfully");
            alert1.showAndWait();
        }
        catch (Exception e) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("add_category Exception");
                alert1.showAndWait();
        }
        category_combo.getItems().clear();
        cat_Fillcombo();
    }

    @FXML
    private void delete_category(ActionEvent event) {
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            String h=(String)category_combo.getSelectionModel().getSelectedItem();
            String q="delete from category where category_name='"+h+"'";
            st.executeUpdate(q);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Dialog Box");
            alert1.setHeaderText("Successfully deleted");
            alert1.showAndWait();
            } 
        catch (Exception e) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("delete_category Exception");
                alert1.showAndWait();
        }
        category_combo.getItems().clear();
        cat_Fillcombo();
    }
    
    public int ccno() {
        int f=0;
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs2;
            rs2= st.executeQuery("select COUNT(*) from category");
            while(rs2.next()) {
                f= Integer.parseInt(rs2.getString(1));
                f=f+1;
            } 
        }
        catch (Exception e) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("category count error");
                alert1.showAndWait();
        }
        return f;
    }
    
    public void cat_Fillcombo()
    {
      try{
           conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs3;
            rs3= st.executeQuery("select category_name from category");
            while(rs3.next()) {
                ccombo.add(rs3.getString("category_name"));
            }
            rs3.close();
            category_combo.getItems().addAll(ccombo);
            ccombo.clear();
        }
        catch (Exception e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("cat_Fillcombo error");
                alert1.showAndWait();
        }
      
    }
    
}
