package sportzone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE activo = 1";

        Connection con = ConexionBD.getInstancia().getConnection();

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto prod = new Producto(
                    rs.getInt("id_producto"),
                    rs.getInt("id_categoria"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getDouble("precio"),
                    rs.getInt("stock"),
                    rs.getString("talla"),
                    rs.getString("marca"),
                    rs.getString("imagen_url"),
                    rs.getBoolean("activo")
                );
                lista.add(prod);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }

        return lista;
    }

    public boolean insertarProducto(Producto prod) {
        String sql = "INSERT INTO productos (id_categoria, nombre, descripcion, precio, stock, talla, marca, imagen_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection con = ConexionBD.getInstancia().getConnection();

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, prod.getIdCategoria());
            ps.setString(2, prod.getNombre());
            ps.setString(3, prod.getDescripcion());
            ps.setDouble(4, prod.getPrecio());
            ps.setInt(5, prod.getStock());
            ps.setString(6, prod.getTalla());
            ps.setString(7, prod.getMarca());
            ps.setString(8, prod.getImagenUrl());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return false;
        }
    }
}