
package Configuracion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author dkred
 */

public class Conexion
{
    Connection con=null;
    String url ="jdbc:mysql://localhost:3306/mas_vida?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    String user="root";
    String pass ="";
    private static Conexion conexion;
    private Conexion()
    {
        this.con=null;
    }
    public static Conexion Obtener_Conexion()
    {
        if (conexion==null)
        {
            conexion=new Conexion();
        }
        return conexion;
    }
    public Connection Iniciar_Conexion()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,pass);
            
             System.out.println("conexion exitosa desde config");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("Error en la conexcion con la base de datos");
        }
        return con;
    }
}