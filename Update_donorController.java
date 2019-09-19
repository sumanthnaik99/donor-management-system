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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class Update_donorController implements Initializable {
    
    Connection conn = null;

    @FXML
    private TextField umem_id;
    @FXML
    private TextField udonor_dn;
    @FXML
    private TextField udonor_ln;
    @FXML
    private TextField udonor_add;
    @FXML
    private TextField udonor_city;
    @FXML
    private TextField udonor_state;
    @FXML
    private DatePicker udonor_dob;
    @FXML
    private TextField udonor_bg;
    @FXML
    private TextField udonor_phno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void search_donor(ActionEvent event) {
        try{
           conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs3;
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String a = umem_id.getText();
            rs3= st.executeQuery("select first_name,last_name,address,city,state,dob,blood_group,phno from member where member_id='"+a+"'");
            while(rs3.next()) {
                udonor_dn.setText(rs3.getString("first_name"));
                udonor_ln.setText(rs3.getString("last_name"));
                udonor_add.setText(rs3.getString("address"));
                udonor_city.setText(rs3.getString("city"));
                udonor_state.setText(rs3.getString("state"));
                udonor_dob.setValue(LOCAL_DATE(df.format(rs3.getDate("dob"))));
                udonor_bg.setText(rs3.getString("blood_group"));
                udonor_phno.setText(rs3.getString("phno"));
            } 
        }
        catch (Exception e) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("search_donor Exception");
                alert1.showAndWait();
        }
    }

    @FXML
    private void update_donor_details(ActionEvent event) {
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            String j,k,l,m,n,o,p,q,r,u;
            u = umem_id.getText();
            k= udonor_dn.getText();
            l= udonor_ln.getText();
            m= udonor_add.getText();
            n= udonor_city.getText();
            o=udonor_dob.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            p= udonor_state.getText();
            q= udonor_bg.getText();
            j= udonor_phno.getText();
            r= "update member set first_name='"+k+"',last_name='"+l+"',address='"+m+"',city='"+n+"',state='"+p+"',dob='"+o+"',phno='"+j+"',blood_group='"+q+"' where member_id="+u+"";
            st.executeUpdate(r);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Dialog Box");
            alert1.setHeaderText("Updated successfully ");
            alert1.showAndWait();
            st.close();
            clear_update();
            } 
        
        catch (Exception e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("update_donor Exception");
                alert1.showAndWait();
        }
    }
    
    public static final LocalDate LOCAL_DATE(String dateString)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(dateString, dtf);
        return ld;
    }
    
    private void clear_update()
    {
        umem_id.setText(null);
        udonor_dn.setText(null);
        udonor_ln.setText(null);
        udonor_add.setText(null);
        udonor_city.setText(null);
        udonor_state.setText(null);
        udonor_dob.setValue(null);
        udonor_bg.setText(null);
        udonor_phno.setText(null);
    }
    
}
