package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Conexion {
    public static Connection cnx = null;
    
    public static Connection conectar() throws  Exception{
        InputStream inputStream = 
                Conexion.class.getClassLoader().getResourceAsStream("properties/db.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            String user = properties.getProperty("user");
            String pwd = properties.getProperty("pwd");
            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            Class.forName(driver).newInstance();
            cnx = DriverManager.getConnection(url, user, pwd);            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error de conexión, revise xfa");
            System.out.println("error de conexion " + e.getMessage());
        }
        return cnx;
    }
    
    public void cerrar() throws Exception{
        if(cnx !=null){
            cnx.close();
        }
    }

    public static void main(String[] args) throws Exception {
        conectar();
        if(cnx!=null){
            System.out.println("todo bien !");
        }else{
            System.out.println("fijate el driver, conexión cerrada, etc....");
        }
    }
}
