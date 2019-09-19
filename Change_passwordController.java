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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class Change_passwordController implements Initializable {
    
    Connection conn = null;

    @FXML
    private CheckBox cp_sp;
    @FXML
    private PasswordField cp_op;
    @FXML
    private PasswordField cp_np;
    @FXML
    private PasswordField cp_rp;

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void change(ActionEvent event) {
        String a = (String)cp_op.getText();
        String b = (String)cp_np.getText();
        String c = (String)cp_rp.getText();
        String d = null;
        try {
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs3;
            rs3= st.executeQuery("select password from login where username='admin'");
            while(rs3.next()) {
                d= rs3.getString("password");
            }
            if(d.equals(a))
            {
                if(b.equals(c))
                {
                    String e = "update login set password='"+c+"' where username='admin'";
                    st.executeUpdate(e);
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Dialog Box");
                alert1.setHeaderText("Password changed");
                alert1.showAndWait();
                }
            }
            clearpassword();
        }
        catch (Exception e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("Change password error");
                alert1.showAndWait();
        }
    }
    
    public void clearpassword()
    {
        cp_op.setText(null);
        cp_np.setText(null);
        cp_rp.setText(null);
    }

    @FXML
    private void show_password(ActionEvent event) {
    }
    
}
