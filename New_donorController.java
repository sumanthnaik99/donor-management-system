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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class New_donorController implements Initializable {
    
    final ObservableList nd_combo = FXCollections.observableArrayList();
    Connection conn = null;

    @FXML
    private ComboBox<?> donor_combo;
    @FXML
    private TextField donor_fn;
    @FXML
    private TextField donor_ln;
    @FXML
    private TextField donor_add;
    @FXML
    private TextField donor_city;
    @FXML
    private TextField donor_state;
    @FXML
    private DatePicker donor_dob;
    @FXML
    private TextField donor_bg;
    @FXML
    private TextField donor_phno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cat_Fillcombo();
    }    

    @FXML
    private void add_donor(ActionEvent event) {
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs3;
            int g = mmno();
            String i,j,k,l,m,n,o,p,q,r,s,t=null;
            s= Integer.toString(g);
            i= (String)donor_combo.getSelectionModel().getSelectedItem();
            j= donor_fn.getText();
            k= donor_ln.getText();
            l= donor_add.getText();
            m= donor_city.getText();
            n= donor_state.getText();
            o= donor_dob.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            p= donor_bg.getText();
            q= donor_phno.getText();
            rs3=st.executeQuery("select category_id from category where category_name= '"+i+"'");
            while(rs3.next()) {
                t=rs3.getString("category_id");
            }
            r= "insert into member(member_id,first_name,last_name,address,city,state,dob,phno,category_id,blood_group) values("+s+",'"+j+"','"+k+"','"+l+"','"+m+"','"+n+"','"+o+"','"+q+"',"+t+",'"+p+"')";
            st.executeUpdate(r);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Dialog Box");
            alert1.setHeaderText("Entered successfully ");
            alert1.showAndWait();
            st.close();
            clear_new_donor();
            } 
        catch( SQLException E)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception Dialog");
            alert.setHeaderText("Exception");

            Exception ex = E;

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
    
    public int mmno() {
        int f=0;
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs2;
            rs2= st.executeQuery("select COUNT(*) from member");
            while(rs2.next()) {
                f= Integer.parseInt(rs2.getString(1));
                f=f+1;
            } 
        }
        catch (Exception e) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("new donor count error");
                alert1.showAndWait();
        }
        return f;
    }
    
    private void clear_new_donor()
    {
        donor_fn.setText(null);
        donor_ln.setText(null);
        donor_add.setText(null);
        donor_city.setText(null);
        donor_state.setText(null);
        donor_bg.setText(null);
        donor_phno.setText(null);
        donor_dob.setValue(null);
    }
    
    public void cat_Fillcombo()
    {
      try{
           conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs3;
            rs3= st.executeQuery("select category_name from category");
            while(rs3.next()) {
                nd_combo.add(rs3.getString("category_name"));
            }
            rs3.close();
            donor_combo.getItems().addAll(nd_combo);
            nd_combo.clear();
        }
        catch (Exception e) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("fill combo error");
                alert1.showAndWait();
        }
    }    
}
