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
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author hp
 */
public class DonationsController implements Initializable {
    
    ObservableList<String> lists = FXCollections.observableArrayList("1-month", "6-months", "1-year");
    String mode = null;
    String[] possibleWords ={"","","","","","","","","",""};
    Connection conn = null;

    @FXML
    private TextField donations_mn;
    @FXML
    private ComboBox<String> donations_combo;
    @FXML
    private DatePicker donations_date;
    @FXML
    private TextField donations_amount;
    @FXML
    private CheckBox donations_cheque;
    @FXML
    private TextField donations_cno;
    @FXML
    private TextField donations_bank;
    @FXML
    private CheckBox donations_cash;
    @FXML
    private CheckBox donations_online;
    @FXML
    private TextField donations_mer;
    @FXML
    private TextField donations_rno;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        donations_combo.getItems().addAll(lists);
        abc();
        TextFields.bindAutoCompletion(donations_mn, possibleWords);
    }    

    @FXML
    private void save_donation(ActionEvent event) {
        try{
           conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs3;
            ResultSet rs4;
            int p = dono();
            String a,b=null,c,u,e,f,g,h,i,j,k,l,r,s,t=null;
            l = donations_mn.getText();//member_name
            a = l.substring(0, l.indexOf("("));
            rs3=st.executeQuery("select member_id from member where first_name= '"+a+"'");
            while(rs3.next()) {
                b=rs3.getString("member_id");
            }
            c= Integer.toString(p); //donor_id
            u=(String)donations_combo.getSelectionModel().getSelectedItem();//combo
            e=donations_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));//date
            f= donations_amount.getText();//amount
            if(donations_cno.getText().equals(""))
            {
                g="0";
            }
            else
            {
                g= donations_cno.getText();
            }
            if(donations_bank.getText().equals(""))
            {
                i="0";
            }
            else
            {
                i= donations_bank.getText();
            }
            if(donations_mer.getText().equals(""))
            {
                j="0";
            }
            else
            {
                j= donations_mer.getText();
            }
            if(donations_rno.getText().equals(""))
            {
                k="0";
            }
            else
            {
                k= donations_rno.getText();
            }
            r = "insert into donor values("+c+","+b+",'"+u+"','"+e+"',"+f+",'"+g+"','"+i+"','"+j+"',"+k+")";
            st.executeUpdate(r);
            rs4 = st.executeQuery("select category_name from category where category_id=(select category_id from member where member_id="+b+")");
            while(rs4.next()) {
                t=rs4.getString("category_name");
            }
            int z = trno();
            h = Integer.toString(z);
            s = "insert into transaction values("+h+",'"+a+"','"+e+"',"+f+",'"+mode+"','"+t+"','"+c+"')";
            st.executeUpdate(s);
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Dialog Box");
            alert1.setHeaderText("Entered successfully ");
            alert1.showAndWait();
            st.close();
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
    
    public int dono() {
        int f=0;
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs2;
            rs2= st.executeQuery("select COUNT(*) from donor");
            while(rs2.next()) {
                f= Integer.parseInt(rs2.getString(1));
                f=f+1;
            } 
        }
        catch (Exception e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("dono count error");
                alert1.showAndWait();
        }
        return f;
    }
    
    public int trno() {
        int f=0;
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs2;
            rs2= st.executeQuery("select COUNT(*) from transaction");
            while(rs2.next()) {
                f= Integer.parseInt(rs2.getString(1));
                f=f+1;
            } 
        }
        catch (Exception e) {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Exception");
                alert1.setHeaderText("trno count error");
                alert1.showAndWait();
        }
        return f;
    }
    
    public void abc()
    {
        int i = 0;
        try{
            conn = JavaConnectDb.ConnecrDb();
            Statement st= conn.createStatement();
            ResultSet rs3;
            rs3= st.executeQuery("select first_name,phno from member");
            while(rs3.next()) {
                String ct= rs3.getString("first_name");
                String it= rs3.getString("phno");
                String bt = ct+"(";
                String gt = bt+it;
                String ht = gt+")";
                possibleWords[i]=ht;
                i++;
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
    
}
