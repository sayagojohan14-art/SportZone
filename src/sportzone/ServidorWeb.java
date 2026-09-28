package sportzone;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.awt.Desktop;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServidorWeb {

    private static final int PUERTO = 8080;
    private static final ProductoDAO dao = new ProductoDAO();

    public static void iniciar() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(PUERTO), 0);

            // Rutas de la tienda
            server.createContext("/", new CatalogoHandler());
            server.createContext("/guardar-producto", new GuardarProductoHandler());

            server.setExecutor(null);
            server.start();

            System.out.println("-> [OK] Servidor SportX corriendo en: http://localhost:" + PUERTO);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI("http://localhost:" + PUERTO));
            }

        } catch (Exception e) {
            System.err.println("-> [ERROR] No se pudo iniciar el servidor web: " + e.getMessage());
        }
    }

    // MANEJADOR PRINCIPAL: Genera el HTML con el diseño SportX dinámico
    static class CatalogoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Consulta los productos en tiempo real desde MySQL
            List<Producto> lista = dao.listarProductos();

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>\n");
            html.append("<html lang='es'>\n");
            html.append("<head>\n");
            html.append("  <meta charset='UTF-8' />\n");
            html.append("  <meta name='viewport' content='width=device-width, initial-scale=1.0' />\n");
            html.append("  <title>Tienda Deportiva SportX - ADSO</title>\n");
            html.append("  <style>\n");
            html.append("    :root {\n");
            html.append("      --bg: #07111f;\n");
            html.append("      --panel: #0f172a;\n");
            html.append("      --accent: #22c55e;\n");
            html.append("      --accent2: #38bdf8;\n");
            html.append("      --text: #f8fafc;\n");
            html.append("      --muted: #94a3b8;\n");
            html.append("      --card: #111827;\n");
            html.append("    }\n");
            html.append("    * { box-sizing: border-box; }\n");
            html.append("    body {\n");
            html.append("      margin: 0;\n");
            html.append("      font-family: 'Segoe UI', Arial, sans-serif;\n");
            html.append("      background: linear-gradient(135deg, var(--bg), #111827);\n");
            html.append("      color: var(--text);\n");
            html.append("    }\n");
            html.append("    header {\n");
            html.append("      background: rgba(7, 17, 31, 0.95);\n");
            html.append("      position: sticky;\n");
            html.append("      top: 0;\n");
            html.append("      z-index: 10;\n");
            html.append("      backdrop-filter: blur(10px);\n");
            html.append("      border-bottom: 1px solid #1f2937;\n");
            html.append("    }\n");
            html.append("    nav {\n");
            html.append("      display: flex;\n");
            html.append("      justify-content: space-between;\n");
            html.append("      align-items: center;\n");
            html.append("      padding: 16px 24px;\n");
            html.append("      max-width: 1200px;\n");
            html.append("      margin: auto;\n");
            html.append("    }\n");
            html.append("    .logo { font-size: 1.5rem; font-weight: 800; color: var(--accent2); }\n");
            html.append("    .nav-links a {\n");
            html.append("      color: var(--text);\n");
            html.append("      margin-left: 18px;\n");
            html.append("      text-decoration: none;\n");
            html.append("      font-size: 0.95rem;\n");
            html.append("      transition: color 0.3s;\n");
            html.append("    }\n");
            html.append("    .nav-links a:hover { color: var(--accent); }\n");
            html.append("    .hero {\n");
            html.append("      max-width: 1200px;\n");
            html.append("      margin: auto;\n");
            html.append("      display: grid;\n");
            html.append("      grid-template-columns: 1.1fr 0.9fr;\n");
            html.append("      gap: 24px;\n");
            html.append("      padding: 40px 24px 24px;\n");
            html.append("      align-items: center;\n");
            html.append("    }\n");
            html.append("    .hero-content h1 { font-size: 2.4rem; margin-bottom: 12px; line-height: 1.2; }\n");
            html.append("    .hero-content p { color: var(--muted); font-size: 1.05rem; line-height: 1.6; }\n");
            html.append("    .hero-buttons { display: flex; gap: 12px; margin-top: 20px; flex-wrap: wrap; }\n");
            html.append("    .btn {\n");
            html.append("      border: none;\n");
            html.append("      padding: 12px 20px;\n");
            html.append("      border-radius: 999px;\n");
            html.append("      cursor: pointer;\n");
            html.append("      font-weight: 700;\n");
            html.append("      text-decoration: none;\n");
            html.append("      display: inline-block;\n");
            html.append("      transition: transform 0.2s ease;\n");
            html.append("    }\n");
            html.append("    .btn:hover { transform: translateY(-2px); }\n");
            html.append("    .btn-primary { background: var(--accent); color: #052e16; }\n");
            html.append("    .btn-secondary { background: #1e293b; color: white; border: 1px solid #334155; }\n");
            html.append("    .hero-card {\n");
            html.append("      background: linear-gradient(135deg, #1d4ed8, #0f766e);\n");
            html.append("      padding: 24px;\n");
            html.append("      border-radius: 24px;\n");
            html.append("      box-shadow: 0 20px 45px rgba(0,0,0,0.3);\n");
            html.append("      text-align: center;\n");
            html.append("    }\n");
            html.append("    .hero-card img {\n");
            html.append("      width: 100%;\n");
            html.append("      max-width: 320px;\n");
            html.append("      border-radius: 18px;\n");
            html.append("      margin-top: 10px;\n");
            html.append("    }\n");
            html.append("    main { max-width: 1200px; margin: auto; padding: 20px 24px 50px; }\n");
            html.append("    .section-title {\n");
            html.append("      display: flex;\n");
            html.append("      justify-content: space-between;\n");
            html.append("      align-items: center;\n");
            html.append("      margin: 35px 0 16px;\n");
            html.append("      border-bottom: 1px solid #1f2937;\n");
            html.append("      padding-bottom: 10px;\n");
            html.append("    }\n");
            html.append("    .section-title span { color: var(--accent2); font-size: 0.9rem; }\n");
            html.append("    .panel-form {\n");
            html.append("      background: var(--panel);\n");
            html.append("      border: 1px solid #1e293b;\n");
            html.append("      border-radius: 20px;\n");
            html.append("      padding: 24px;\n");
            html.append("      margin: 20px 0 40px;\n");
            html.append("      box-shadow: 0 10px 30px rgba(0,0,0,0.3);\n");
            html.append("    }\n");
            html.append("    .panel-form h2 { color: var(--accent2); margin-bottom: 16px; font-size: 1.4rem; }\n");
            html.append("    .grid-form {\n");
            html.append("      display: grid;\n");
            html.append("      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));\n");
            html.append("      gap: 14px;\n");
            html.append("    }\n");
            html.append("    .grupo { display: flex; flex-direction: column; }\n");
            html.append("    .grupo label { font-size: 0.8rem; font-weight: bold; margin-bottom: 5px; color: var(--muted); }\n");
            html.append("    .grupo input, .grupo select {\n");
            html.append("      background: #0b1329;\n");
            html.append("      border: 1px solid #334155;\n");
            html.append("      color: white;\n");
            html.append("      padding: 10px 12px;\n");
            html.append("      border-radius: 8px;\n");
            html.append("      font-size: 0.9rem;\n");
            html.append("    }\n");
            html.append("    .grupo input:focus, .grupo select:focus { outline: none; border-color: var(--accent2); }\n");
            html.append("    .grupo-full { grid-column: 1 / -1; }\n");
            html.append("    .btn-submit {\n");
            html.append("      width: 100%;\n");
            html.append("      background: var(--accent);\n");
            html.append("      color: #052e16;\n");
            html.append("      border: none;\n");
            html.append("      padding: 12px;\n");
            html.append("      border-radius: 10px;\n");
            html.append("      font-size: 1rem;\n");
            html.append("      font-weight: 700;\n");
            html.append("      cursor: pointer;\n");
            html.append("      margin-top: 15px;\n");
            html.append("      transition: transform 0.2s;\n");
            html.append("    }\n");
            html.append("    .btn-submit:hover { transform: translateY(-2px); }\n");
            html.append("    .products {\n");
            html.append("      display: grid;\n");
            html.append("      grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));\n");
            html.append("      gap: 18px;\n");
            html.append("    }\n");
            html.append("    .product-card {\n");
            html.append("      background: var(--card);\n");
            html.append("      border: 1px solid #1f2937;\n");
            html.append("      border-radius: 18px;\n");
            html.append("      padding: 16px;\n");
            html.append("      display: flex;\n");
            html.append("      flex-direction: column;\n");
            html.append("      justify-content: space-between;\n");
            html.append("      transition: transform 0.2s ease, box-shadow 0.2s ease;\n");
            html.append("    }\n");
            html.append("    .product-card:hover {\n");
            html.append("      transform: translateY(-4px);\n");
            html.append("      box-shadow: 0 12px 24px rgba(0,0,0,0.35);\n");
            html.append("    }\n");
            html.append("    .product-card img {\n");
            html.append("      width: 100%;\n");
            html.append("      height: 170px;\n");
            html.append("      object-fit: cover;\n");
            html.append("      border-radius: 12px;\n");
            html.append("      margin-bottom: 10px;\n");
            html.append("    }\n");
            html.append("    .badge-marca {\n");
            html.append("      background: rgba(56, 189, 248, 0.15);\n");
            html.append("      color: var(--accent2);\n");
            html.append("      font-size: 0.75rem;\n");
            html.append("      font-weight: bold;\n");
            html.append("      padding: 3px 8px;\n");
            html.append("      border-radius: 6px;\n");
            html.append("      display: inline-block;\n");
            html.append("      margin-bottom: 6px;\n");
            html.append("    }\n");
            html.append("    .product-card h3 { margin: 6px 0; font-size: 1.15rem; color: #fff; }\n");
            html.append("    .product-card p { color: var(--muted); font-size: 0.85rem; line-height: 1.4; margin: 4px 0 10px; }\n");
            html.append("    .meta-info { font-size: 0.8rem; color: #cbd5e1; margin-bottom: 10px; }\n");
            html.append("    .price {\n");
            html.append("      color: var(--accent);\n");
            html.append("      font-weight: 800;\n");
            html.append("      font-size: 1.15rem;\n");
            html.append("      margin: 8px 0;\n");
            html.append("    }\n");
            html.append("    .product-card button {\n");
            html.append("      width: 100%;\n");
            html.append("      background: linear-gradient(135deg, var(--accent2), #2563eb);\n");
            html.append("      color: white;\n");
            html.append("      border: none;\n");
            html.append("      border-radius: 10px;\n");
            html.append("      padding: 10px;\n");
            html.append("      font-weight: bold;\n");
            html.append("      cursor: pointer;\n");
            html.append("      margin-top: 8px;\n");
            html.append("      transition: opacity 0.2s;\n");
            html.append("    }\n");
            html.append("    .product-card button:hover { opacity: 0.9; }\n");
            html.append("    .features {\n");
            html.append("      display: grid;\n");
            html.append("      grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));\n");
            html.append("      gap: 16px;\n");
            html.append("      margin-top: 40px;\n");
            html.append("    }\n");
            html.append("    .feature {\n");
            html.append("      background: rgba(255,255,255,0.03);\n");
            html.append("      padding: 18px;\n");
            html.append("      border-radius: 16px;\n");
            html.append("      border: 1px solid #1e293b;\n");
            html.append("    }\n");
            html.append("    .feature h3 { font-size: 1.05rem; margin-bottom: 6px; }\n");
            html.append("    .feature p { color: var(--muted); font-size: 0.85rem; }\n");
            html.append("    footer {\n");
            html.append("      text-align: center;\n");
            html.append("      padding: 24px;\n");
            html.append("      color: var(--muted);\n");
            html.append("      border-top: 1px solid #1f2937;\n");
            html.append("      margin-top: 40px;\n");
            html.append("    }\n");
            html.append("    @media (max-width: 760px) {\n");
            html.append("      .hero { grid-template-columns: 1fr; }\n");
            html.append("    }\n");
            html.append("  </style>\n");
            html.append("</head>\n");
            html.append("<body>\n");

            // HEADER Y NAVEGACIÓN
            html.append("  <header>\n");
            html.append("    <nav>\n");
            html.append("      <div class='logo'>SportX</div>\n");
            html.append("      <div class='nav-links'>\n");
            html.append("        <a href='#productos'>Productos</a>\n");
            html.append("        <a href='#registro'>+ Nuevo Producto</a>\n");
            html.append("        <a href='#ventajas'>Beneficios</a>\n");
            html.append("      </div>\n");
            html.append("    </nav>\n");
            html.append("  </header>\n");

            // SECCIÓN HERO
            html.append("  <section class='hero'>\n");
            html.append("    <div class='hero-content'>\n");
            html.append("      <h1>Equipamiento deportivo para moverte mejor</h1>\n");
            html.append("      <p>Descubre ropa, calzado y accesorios para entrenar con estilo, comodidad y rendimiento profesional.</p>\n");
            html.append("      <div class='hero-buttons'>\n");
            html.append("        <a href='#productos' class='btn btn-primary'>Ver productos</a>\n");
            html.append("        <a href='#registro' class='btn btn-secondary'>Registrar inventario</a>\n");
            html.append("      </div>\n");
            html.append("    </div>\n");
            html.append("    <div class='hero-card'>\n");
            html.append("      <h3>🔥 Nueva colección 2026</h3>\n");
            html.append("      <p>Ropa técnica y calzado para alto rendimiento.</p>\n");
            html.append("      <img src='https://images.unsplash.com/photo-1518611012118-696072aa579a?auto=format&fit=crop&w=900&q=80' alt='Entrenamiento' />\n");
            html.append("    </div>\n");
            html.append("  </section>\n");

            html.append("  <main>\n");

            // FORMULARIO DE REGISTRO EN EL PANEL OSCURO
            html.append("    <div class='panel-form' id='registro'>\n");
            html.append("      <h2>➕ Registrar Producto en Base de Datos (MySQL)</h2>\n");
            html.append("      <form action='/guardar-producto' method='POST'>\n");
            html.append("        <div class='grid-form'>\n");
            html.append("          <div class='grupo'>\n");
            html.append("            <label>Nombre del Producto *</label>\n");
            html.append("            <input type='text' name='nombre' required placeholder='Ej. Guantes de Boxeo' />\n");
            html.append("          </div>\n");
            html.append("          <div class='grupo'>\n");
            html.append("            <label>Categoría *</label>\n");
            html.append("            <select name='id_categoria' required>\n");
            html.append("              <option value='1'>Calzado Deportivo</option>\n");
            html.append("              <option value='2'>Ropa Deportiva</option>\n");
            html.append("              <option value='3'>Accesorios y Balones</option>\n");
            html.append("            </select>\n");
            html.append("          </div>\n");
            html.append("          <div class='grupo'>\n");
            html.append("            <label>Marca *</label>\n");
            html.append("            <input type='text' name='marca' required placeholder='Ej. Everlast, Nike, Adidas' />\n");
            html.append("          </div>\n");
            html.append("          <div class='grupo'>\n");
            html.append("            <label>Talla *</label>\n");
            html.append("            <input type='text' name='talla' required placeholder='Ej. 12 oz, M, 41, Única' />\n");
            html.append("          </div>\n");
            html.append("          <div class='grupo'>\n");
            html.append("            <label>Precio en COP ($) *</label>\n");
            html.append("            <input type='number' step='100' name='precio' required placeholder='Ej. 140000' />\n");
            html.append("          </div>\n");
            html.append("          <div class='grupo'>\n");
            html.append("            <label>Stock Disponible *</label>\n");
            html.append("            <input type='number' name='stock' required placeholder='Ej. 10' />\n");
            html.append("          </div>\n");
            html.append("          <div class='grupo grupo-full'>\n");
            html.append("            <label>URL de Imagen (Opcional - link web)</label>\n");
            html.append("            <input type='text' name='imagen_url' placeholder='https://images.unsplash.com/...' />\n");
            html.append("          </div>\n");
            html.append("          <div class='grupo grupo-full'>\n");
            html.append("            <label>Descripción corta</label>\n");
            html.append("            <input type='text' name='descripcion' placeholder='Detalle técnico del producto...' />\n");
            html.append("          </div>\n");
            html.append("        </div>\n");
            html.append("        <button type='submit' class='btn-submit'>Guardar en Base de Datos</button>\n");
            html.append("      </form>\n");
            html.append("    </div>\n");

            // GRILLA DE PRODUCTOS DESTACADOS
            html.append("    <div class='section-title' id='productos'>\n");
            html.append("      <h2>Productos disponibles</h2>\n");
            html.append("      <span>Conexión directa a MySQL en XAMPP</span>\n");
            html.append("    </div>\n");

            html.append("    <div class='products'>\n");

            // Renderizado dinámico de los productos de MySQL
            for (Producto p : lista) {
                // Imagen por defecto con temática deportiva si no hay URL
                String img = (p.getImagenUrl() != null && p.getImagenUrl().startsWith("http"))
                             ? p.getImagenUrl()
                             : "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=800&q=80";

                html.append("      <div class='product-card'>\n");
                html.append("        <div>\n");
                html.append("          <img src='").append(img).append("' alt='").append(p.getNombre()).append("' />\n");
                html.append("          <span class='badge-marca'>").append(p.getMarca()).append("</span>\n");
                html.append("          <h3>").append(p.getNombre()).append("</h3>\n");
                html.append("          <p>").append(p.getDescripcion() != null ? p.getDescripcion() : "Producto de alta calidad para entrenamiento.").append("</p>\n");
                html.append("          <div class='meta-info'>\n");
                html.append("            <span>Talla: <strong>").append(p.getTalla()).append("</strong></span> | \n");
                html.append("            <span>Stock: <strong>").append(p.getStock()).append(" uds.</strong></span>\n");
                html.append("          </div>\n");
                html.append("        </div>\n");
                html.append("        <div>\n");
                html.append("          <div class='price'>$").append(String.format("%,.0f", p.getPrecio())).append("</div>\n");
                html.append("          <button>Añadir al carrito</button>\n");
                html.append("        </div>\n");
                html.append("      </div>\n");
            }

            html.append("    </div>\n");

            // SECCIÓN DE VENTAJAS / FEATURES
            html.append("    <div class='features' id='ventajas'>\n");
            html.append("      <div class='feature'>\n");
            html.append("        <h3>⚡ Envío rápido</h3>\n");
            html.append("        <p>Entregas ágiles y seguimiento en tiempo real.</p>\n");
            html.append("      </div>\n");
            html.append("      <div class='feature'>\n");
            html.append("        <h3>💳 Base de Datos MySQL</h3>\n");
            html.append("        <p>Gestión persistente e integrada con XAMPP.</p>\n");
            html.append("      </div>\n");
            html.append("      <div class='feature'>\n");
            html.append("        <h3>🏅 Calidad garantizada</h3>\n");
            html.append("        <p>Marcas líderes con garantía oficial de uso.</p>\n");
            html.append("      </div>\n");
            html.append("      <div class='feature'>\n");
            html.append("        <h3>🚀 Arquitectura Java</h3>\n");
            html.append("        <p>Patrón Singleton y DAO para alto rendimiento.</p>\n");
            html.append("      </div>\n");
            html.append("    </div>\n");

            html.append("  </main>\n");

            html.append("  <footer id='contacto'>\n");
            html.append("    <p>SportX © 2026 — Proyecto Formativo SENA ADSO</p>\n");
            html.append("  </footer>\n");

            html.append("</body>\n");
            html.append("</html>\n");

            byte[] bytes = html.toString().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    // MANEJADOR PARA GUARDAR EN LA BD
    static class GuardarProductoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String formData = br.readLine();

                Map<String, String> campos = parsearFormData(formData);

                try {
                    String nombre = campos.getOrDefault("nombre", "Sin nombre");
                    int idCat = Integer.parseInt(campos.getOrDefault("id_categoria", "1"));
                    String marca = campos.getOrDefault("marca", "Genérica");
                    String talla = campos.getOrDefault("talla", "Única");
                    double precio = Double.parseDouble(campos.getOrDefault("precio", "0"));
                    int stock = Integer.parseInt(campos.getOrDefault("stock", "0"));
                    String imagen = campos.getOrDefault("imagen_url", "");
                    String desc = campos.getOrDefault("descripcion", "");

                    Producto nuevo = new Producto(idCat, nombre, desc, precio, stock, talla, marca, imagen);
                    dao.insertarProducto(nuevo);
                    System.out.println("-> [OK] Producto registrado: " + nombre);

                } catch (Exception e) {
                    System.err.println("Error procesando datos: " + e.getMessage());
                }

                // Redireccionar al inicio para ver el nuevo producto
                exchange.getResponseHeaders().set("Location", "/");
                exchange.sendResponseHeaders(302, -1);
            }
        }

        private Map<String, String> parsearFormData(String data) throws UnsupportedEncodingException {
            Map<String, String> mapa = new HashMap<>();
            if (data == null || data.isEmpty()) return mapa;

            String[] pares = data.split("&");
            for (String par : pares) {
                String[] kv = par.split("=");
                if (kv.length == 2) {
                    String clave = URLDecoder.decode(kv[0], StandardCharsets.UTF_8.name());
                    String valor = URLDecoder.decode(kv[1], StandardCharsets.UTF_8.name());
                    mapa.put(clave, valor);
                }
            }
            return mapa;
        }
    }
}