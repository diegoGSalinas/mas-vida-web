package Modelo;

import Configuracion.Conexion;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BackupJob {
    
    public static void ejecutarBackup() {
        try {
            var conn = Conexion.Obtener_Conexion().Iniciar_Conexion();
            
            // Generar nombre de base de datos con fecha actual
            String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
            String nombreBD = "mas_vida_copia_" + fecha;
            
            // Preparar y ejecutar el procedimiento
            CallableStatement stmt = conn.prepareCall("{CALL copiar_base_datos_dinamico(?)}");
            stmt.setString(1, nombreBD);
            
            // Ejecutar y obtener el resultado
            stmt.execute();
            ResultSet rs = stmt.getResultSet();
            
            if (rs.next()) {
                System.out.println("✅ " + rs.getString("resultado"));
            }
            
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al ejecutar el backup: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        ejecutarBackup();
    }
}
