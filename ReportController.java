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
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import templeapp.practice.PrintReport;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class ReportController implements Initializable {
    
    final ObservableList ccombo = FXCollections.observableArrayList();
    Connection conn = null;

    @FXML
    private ComboBox<?> rep_cat;
    @FXML
    private DatePicker rep_from;
    @FXML
    private DatePicker rep_to;
    @FXML
    private TableColumn<ReportTable, String> rep_cl_date;
    @FXML
    private TableColumn<ReportTable, String> rep_cl_name;
    @FXML
    private TableColumn<ReportTable, String> rep_cl_am;
    @FXML
    private TableColumn<ReportTable, String> rep_cl_mode;
    @FXML
    private TableColumn<ReportTable, String> rep_cl_cat;
    @FXML
    private TableView<ReportTable> rep_table;

    ObservableList<ReportTable> rplist  = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cat_Fillcombo();
    }    

    @FXML
    private void print_report(ActionEvent event) {
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs;
            SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
            String a,b,c;
            a = (String)rep_cat.getSelectionModel().getSelectedItem();
            b = rep_from.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            c = rep_to.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
             String q="select member_name,don_date,amount,tr_mode,category_name from transaction where category_name='"+a+"' and don_date between '"+b+"' and '"+c+"'";
            rs=st.executeQuery(q);
            ArrayList<ArrayList<Object>> p = new ArrayList<ArrayList<Object>>();
             ArrayList<Object> item = new ArrayList<>();
            while (rs.next()) {
                item = new ArrayList<>();
                    item.add(0,rs.getString("member_name"));
                    item.add(1,rs.getString("amount"));
                    item.add(2,rs.getString("tr_mode"));
                    item.add(3,rs.getString("category_name"));
                    item.add(4,rs.getString("don_date"));
                    p.add(item);
                    rep_from.setValue(null);
                    rep_to.setValue(null);
                }
            int y = prno();
            String e = Integer.toString(y);
            String f = "insert into print values("+e+")";
            st.executeUpdate(f);
            
            PrintReport rpt = new PrintReport();
            rpt.getPDFReport(p);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Dialog Box");
            alert1.setHeaderText("Report generated");
            alert1.showAndWait();
        }
        catch (Exception e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("print error");
                alert1.showAndWait();
        }
        
    }

    @FXML
    private void search_report(ActionEvent event) {
        try{
            int i =0;
            for(i=0;i<rep_table.getItems().size();i++)
            {
                rep_table.getItems().clear();
            }
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs;
            String a,b,c;
            a = (String)rep_cat.getSelectionModel().getSelectedItem();
            b = rep_from.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            c = rep_to.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String q="select member_name,don_date,amount,tr_mode,category_name from transaction where category_name='"+a+"' and don_date between '"+b+"' and '"+c+"'";
            rs=st.executeQuery(q); 
            while(rs.next())
            {
                rplist.add(new ReportTable(rs.getString("don_date"),rs.getString("member_name"),rs.getString("amount"),rs.getString("tr_mode"),rs.getString("category_name")));
            }
            rep_cl_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            rep_cl_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            rep_cl_am.setCellValueFactory(new PropertyValueFactory<>("amount"));
            rep_cl_mode.setCellValueFactory(new PropertyValueFactory<>("mode"));
            rep_cl_cat.setCellValueFactory(new PropertyValueFactory<>("cat"));
        
        rep_table.setItems(rplist);
        }
        catch (Exception e) {
             Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("search error");
                alert1.showAndWait();
        }
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
            rep_cat.getItems().addAll(ccombo);
            ccombo.clear();
        }
        catch (Exception e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("cat_Fillcombo error");
                alert1.showAndWait();
        }
      
    }
    public int prno() {
        int f=0;
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs2;
            rs2= st.executeQuery("select COUNT(*) from print");
            while(rs2.next()) {
                f= Integer.parseInt(rs2.getString(1));
                f=f+1;
            } 
        }
        catch (Exception e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("prno error");
                alert1.showAndWait();
        }
        return f;
    }
    
}
