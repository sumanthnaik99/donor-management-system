/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temple_app;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class JavaConnectDb {
     public static Connection ConnecrDb(){
        try{
           Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/temple","root","root123");
            return con;
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e);
        }
        return null;
    }
    
}
