package sportzone;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/tienda_deportiva?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 

    private static ConexionBD instancia;
    private Connection conexion;

    private ConexionBD() {
        try {
            Class.forName(DRIVER);
            this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("-> [OK] Conexion establecida con exito a MySQL.");
        } catch (ClassNotFoundException e) {
            System.err.println("-> [ERROR] No se encontro el driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("-> [ERROR] No se pudo conectar. Verifica que MySQL este activo en XAMPP.");
            e.printStackTrace();
        }
    }

    public static synchronized ConexionBD getInstancia() {
        if (instancia == null) {
            instancia = new ConexionBD();
        }
        return instancia;
    }

    public Connection getConnection() {
        try {
            if (this.conexion == null || this.conexion.isClosed()) {
                this.conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.err.println("-> [ERROR] Fallo al reabrir conexion: " + e.getMessage());
        }
        return this.conexion;
    }
}