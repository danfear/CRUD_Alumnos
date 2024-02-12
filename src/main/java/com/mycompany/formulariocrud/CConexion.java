/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.formulariocrud;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Daniel
 */
public class CConexion {
    
    Connection conexion = null;
   
    String usuario = "root";
    String password = "root";
    String db = "alumnosdb";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+db;
    
    public Connection establecerConexion () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(cadena,usuario,password);
            //JOptionPane.showMessageDialog(null,"Conexión realizada con éxito");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos, error: " + e.toString());
        }
        return conexion;
    }
}
