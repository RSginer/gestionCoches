/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestionCoches {

    Conexion c = new Conexion();

    public boolean crearTabla() {
        boolean respuesta = false;
        try {
            Connection c = this.crearConexion();
            PreparedStatement psCreaTabla;
            String crearTabla = "CREATE TABLE if not exists datos_coche (matricula varchar(10) NOT NULL,marca varchar(45) DEFAULT NULL,modelo  varchar(45) DEFAULT NULL,color varchar(45) DEFAULT NULL,año int(11) DEFAULT NULL,precio int(11) DEFAULT NULL,PRIMARY KEY (`matricula`)) ENGINE=InnoDB DEFAULT CHARSET=utf8";
            psCreaTabla = c.prepareStatement(crearTabla);
            psCreaTabla.execute();
            respuesta = true;
        } catch (SQLException ex) {
            Logger.getLogger(GestionCoches.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public ResultSet mostrarCoches() {
        String sql;
        PreparedStatement ps;
        ResultSet res = null;
        try {
            sql = "SELECT * FROM datos_coche";
            ps = this.c.nuevaConexion().prepareStatement(sql);
            res = ps.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(GestionCoches.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }

    public int cargarTablaCoches(File archivo) throws IOException {
        PreparedStatement ps;
        int numRows = 0;
        String sql = "INSERT INTO datos_coche VALUES(?,?,?,?,?,?)";
        String linea, matricula, modelo, marca, color;
        int año, precio;
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo.getPath()))) {
            while ((linea = lector.readLine()) != null) {
                String[] trozos = linea.split(":");
                matricula = trozos[0];
                modelo = trozos[1];
                marca = trozos[2];
                color = trozos[3];
                año = Integer.parseInt(trozos[4]);
                precio = Integer.parseInt(trozos[5]);
                try {
                    ps = this.crearConexion().prepareStatement(sql);
                    ps.setString(1, matricula);
                    ps.setString(2, marca);
                    ps.setString(3, modelo);
                    ps.setString(4, color);
                    ps.setInt(5, año);
                    ps.setInt(6, precio);
                    numRows = ps.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(GestionCoches.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionCoches.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numRows;
    }

    public Connection crearConexion() {
        return this.c.nuevaConexion();
    }
}
