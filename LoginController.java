/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temple_app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class LoginController implements Initializable {

    @FXML
    private TextField login_text;
    @FXML
    private PasswordField login_password;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void switch_to_dashboard(ActionEvent event) throws IOException {
        try{
            Connection conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            String sql= "Select * from login where username=? and password=?";
            PreparedStatement pst= conn.prepareStatement(sql);
            pst.setString(1,login_text.getText());
             pst.setString(2,login_password.getText());
             ResultSet rs= pst.executeQuery();
             if(rs.next()) {
                Parent dashboard_parent = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene dashboard_scene = new Scene(dashboard_parent);
                Stage dashboard_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                dashboard_stage.hide();
                dashboard_stage.setScene(dashboard_scene);
                dashboard_stage.show();  
             }
             else
             {
                Alert alert1 = new Alert(AlertType.INFORMATION);
                alert1.setTitle("Authentication Dialog");
                alert1.setHeaderText("Authentication failed!");
                alert1.showAndWait();
             }
        }
        catch(IOException | SQLException E)
        {
            Alert alert = new Alert(AlertType.ERROR);
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
    
}
