package sportzone;

import java.util.List;

public class SportZone {

    public static void main(String[] args) {
        System.out.println("Cargando tienda SportX...");

        // Obtenemos los productos desde MySQL en XAMPP
        ProductoDAO dao = new ProductoDAO();
        List<Producto> lista = dao.listarProductos();

        // Generamos y abrimos la página con el diseño SportX
        GeneradorWeb.construirYMostrarCatalogo(lista);
    }
}