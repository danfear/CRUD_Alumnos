/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.formulariocrud;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Daniel
 */
public class CAlumnos {
    
    int codigo;
    String nombre;
    String apellido;
    String sexo;
    String edad;
    String grado;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }
    
    public void InsertarAlumno(JTextField paramNombre, JTextField paramApellido, JTextField paramSexo, JTextField paramEdad, JTextField paramGrado) {
        setNombre(paramNombre.getText());
        setApellido(paramApellido.getText());
        setSexo(paramSexo.getText());
        setEdad(paramEdad.getText());
        setGrado(paramGrado.getText());
        
        CConexion objetoConexion = new CConexion();
        
        String consulta ="insert into Alumnos(nombres,apellidos,sexo,edad,grado) values(?,?,?,?,?);";
        
        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            
            cs.setString(1, getNombre());
            cs.setString(2, getApellido());
            cs.setString(3, getSexo());
            cs.setString(4, getEdad());
            cs.setString(5, getGrado());
            
            cs.execute();
            
            JOptionPane.showConfirmDialog(null, "alumno ingresado exitosamente");
            
        }catch (Exception e){
            JOptionPane.showConfirmDialog(null, "El alumno no se ha ingresado correctamente: "+e.toString());
        }
    }
    
    public void MostrarAlumno(JTable paramTabla) {
        
        CConexion objetoConexion = new CConexion();
        DefaultTableModel modelo = new DefaultTableModel();
        
        TableRowSorter<TableModel> ordenarTabla= new TableRowSorter<TableModel> (modelo);
        paramTabla.setRowSorter(ordenarTabla);
        
        String sentenciaSQL="";
        
        modelo.addColumn("Id");
        modelo.addColumn("Nombres");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Sexo");
        modelo.addColumn("Edad");
        modelo.addColumn("Grado");
        
        paramTabla.setModel(modelo);
        
        sentenciaSQL = "select * from Alumnos;";
        
        String[] datos = new String[6];
        Statement st;
        
        try {
            st = objetoConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sentenciaSQL);
            while(rs.next()) {
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                
                modelo.addRow(datos);
            }
            
            paramTabla.setModel(modelo);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al presentar los registros, error: " + e.toString());
        }
    }
    
    public void SeleccionarAlumno(JTable paramTabla, JTextField paramId, JTextField paramNombre, JTextField paramApellido, JTextField paramSexo, JTextField paramEdad, JTextField paramGrado) {
        
        try {
            int fila = paramTabla.getSelectedRow();
            if (fila>=0){
                paramId.setText(paramTabla.getValueAt(fila, 0).toString());
                paramNombre.setText(paramTabla.getValueAt(fila, 1).toString());
                paramApellido.setText(paramTabla.getValueAt(fila, 2).toString());
                paramSexo.setText(paramTabla.getValueAt(fila, 3).toString());
                paramEdad.setText(paramTabla.getValueAt(fila, 4).toString());
                paramGrado.setText(paramTabla.getValueAt(fila, 5).toString());
            }
            else{
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error de selección, error: " + e.toString());
        }
        
    }
    
    public void ModificarAlumno(JTextField paramId, JTextField paramNombre, JTextField paramApellido, JTextField paramSexo, JTextField paramEdad, JTextField paramGrado){
        setCodigo(Integer.parseInt(paramId.getText()));
        setNombre(paramNombre.getText());
        setApellido(paramApellido.getText());
        setSexo(paramSexo.getText());
        setEdad(paramEdad.getText());
        setGrado(paramGrado.getText());
        
        CConexion objetoConexion = new CConexion();
        
        String consulta ="UPDATE Alumnos SET alumnos.nombres = ?, alumnos.apellidos = ?, alumnos.sexo = ?, alumnos.edad = ?, alumnos.grado = ? WHERE alumnos.id = ?;";
        
        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            
            cs.setString(1, getNombre());
            cs.setString(2, getApellido());
            cs.setString(3, getSexo());
            cs.setString(4, getEdad());
            cs.setString(5, getGrado());
            cs.setInt(6, getCodigo());
            
            cs.execute();
            
            JOptionPane.showConfirmDialog(null, "Modificación exitosa");
            
        }catch (SQLException e){
            JOptionPane.showConfirmDialog(null, "No ha sido posible modificar el registro, error: "+e.toString());
        }
    }
    
    public void EliminarAlumno(JTextField paramId){
        setCodigo(Integer.parseInt(paramId.getText()));
        
         CConexion objetoConexion = new CConexion();
        
        String consulta = "DELETE FROM Alumnos WHERE alumnos.id=?;";
        
        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            
            cs.setInt(1, getCodigo());
            cs.execute();
            
            JOptionPane.showConfirmDialog(null, "Eliminación exitosa");
            
        }catch (SQLException e){
            JOptionPane.showConfirmDialog(null, "No ha sido posible eliminar el registro, error: "+e.toString());
        }
    }
    

    
}
