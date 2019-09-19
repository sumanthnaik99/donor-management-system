/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temple_app;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import static temple_app.Update_donorController.LOCAL_DATE;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class TransactionsController implements Initializable {
    
    Connection conn = null;

    @FXML
    private TableView<ModelTable> tr_table;
    @FXML
    private TextField tr_id;
    @FXML
    private TextField tr_mn;
    @FXML
    private DatePicker tr_date;
    @FXML
    private TextField tr_amount;
    @FXML
    private TableColumn<ModelTable, String> tr_cl_id;
    @FXML
    private TableColumn<ModelTable, String> tr_cl_name;
    @FXML
    private TableColumn<ModelTable, String> tr_cl_date;
    @FXML
    private TableColumn<ModelTable, String> tr_cl_am;
    @FXML
    private TableColumn<ModelTable, String> tr_cl_mode;

    ObservableList<ModelTable> oblist  = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fetch();
    }    

    @FXML
    private void search_transactions(ActionEvent event) {
        try{
           conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs3;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String a = tr_id.getText();
            rs3= st.executeQuery("select member_name,don_date,amount from transaction where transaction_id="+a+"");
            while(rs3.next()) {
                tr_mn.setText(rs3.getString("member_name"));
                tr_amount.setText(rs3.getString("amount"));
                tr_date.setValue(LOCAL_DATE(df.format(rs3.getDate("don_date"))));
            } 
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception Dialog");
            alert.setHeaderText("Exception");

            Exception ex = e;

            // Create expandable Exception.
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String exceptionText = sw.toString();

            Label label = new Label("The exception stacktrace was:");

            TextArea textArea = new TextArea(exceptionText);
            textArea.setEditable(false);
            textArea.setWrapText(true);

            textArea.setMaxWidth(Double.MAX_VALUE);
            textArea.setMaxHeight(Double.MAX_VALUE);
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);

            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(label, 0, 0);
            expContent.add(textArea, 0, 1);

            // Set expandable Exception into the dialog pane.
            alert.getDialogPane().setExpandableContent(expContent);

            alert.showAndWait();
        }
    }

    @FXML
    private void update_transactions(ActionEvent event) {
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            String k,n,o,r,u;
            u = tr_id.getText();
            k= tr_mn.getText();
            n= tr_amount.getText();
            o=tr_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            r= "update transaction set member_name='"+k+"',don_date='"+o+"',amount="+n+" where transaction_id="+u+"";
            st.executeUpdate(r);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Dialog Box");
            alert1.setHeaderText("Entered successfully ");
            alert1.showAndWait();
            st.close();
            clear_transactions();
            for(int i=0;i<tr_table.getItems().size();i++)
            {
                tr_table.getItems().clear();
            }
            fetch();
            } 
        
        catch (Exception e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("Update transactions error");
                alert1.showAndWait();
        }
    }
    
    public void clear_transactions()
    {
        tr_id.setText(null);
        tr_mn.setText(null);
        tr_date.setValue(null);
        tr_amount.setText(null);
    }
    
    public void fetch()
    {
        try {
            conn = JavaConnectDb.ConnecrDb();
            ResultSet rs = conn.createStatement().executeQuery("select transaction_id,member_name,don_date,amount,tr_mode from transaction");
            while(rs.next())
            {
                oblist.add(new ModelTable(rs.getString("transaction_id"),rs.getString("member_name"),rs.getString("don_date"),rs.getString("amount"),rs.getString("tr_mode")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TransactionsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO
        tr_cl_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tr_cl_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tr_cl_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        tr_cl_am.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tr_cl_mode.setCellValueFactory(new PropertyValueFactory<>("mode"));
        
        tr_table.setItems(oblist);
    }
    
}
