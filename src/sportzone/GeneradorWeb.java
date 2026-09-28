package sportzone;

import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GeneradorWeb {

    public static void construirYMostrarCatalogo(List<Producto> productos) {
        File archivo = new File("catalogo.html");

        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8))) {

            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("  <meta charset='UTF-8' />");
            out.println("  <meta name='viewport' content='width=device-width, initial-scale=1.0' />");
            out.println("  <title>SportX - Tienda Deportiva y Despacho</title>");
            out.println("  <style>");
            out.println("    :root {");
            out.println("      --bg: #07111f; --panel: #0f172a; --accent: #22c55e; --accent2: #38bdf8;");
            out.println("      --text: #f8fafc; --muted: #94a3b8; --card: #111827; --danger: #ef4444;");
            out.println("    }");
            out.println("    * { box-sizing: border-box; margin: 0; padding: 0; }");
            out.println("    body { font-family: 'Segoe UI', Arial, sans-serif; background: linear-gradient(135deg, var(--bg), #111827); color: var(--text); min-height: 100vh; padding-bottom: 60px; }");
            out.println("    header { background: rgba(7, 17, 31, 0.95); position: sticky; top: 0; z-index: 100; backdrop-filter: blur(10px); border-bottom: 1px solid #1f2937; }");
            out.println("    nav { display: flex; justify-content: space-between; align-items: center; padding: 16px 24px; max-width: 1250px; margin: auto; }");
            out.println("    .logo { font-size: 1.5rem; font-weight: 800; color: var(--accent2); }");
            out.println("    .cart-trigger { background: #1e293b; color: white; border: 1px solid #334155; padding: 10px 18px; border-radius: 999px; cursor: pointer; font-weight: 700; display: flex; align-items: center; gap: 8px; }");
            out.println("    .cart-trigger:hover { border-color: var(--accent); }");
            out.println("    .cart-count { background: var(--accent); color: #052e16; border-radius: 50%; padding: 2px 8px; font-size: 0.8rem; font-weight: 800; }");
            out.println("    main { max-width: 1250px; margin: auto; padding: 30px 24px; }");
            out.println("    .section-title { display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px; border-bottom: 1px solid #1f2937; padding-bottom: 12px; }");
            out.println("    .products { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 24px; }");
            out.println("    .product-card { background: var(--card); border: 1px solid #1f2937; border-radius: 18px; padding: 16px; display: flex; flex-direction: column; justify-content: space-between; transition: transform 0.2s, box-shadow 0.2s; }");
            out.println("    .product-card:hover { transform: translateY(-4px); box-shadow: 0 12px 24px rgba(0,0,0,0.3); }");
            out.println("    .img-box { width: 100%; height: 210px; border-radius: 12px; overflow: hidden; background: #080f1e; position: relative; display: flex; align-items: center; justify-content: center; border: 1px solid #1e293b; }");
            out.println("    .img-box img { width: 100%; height: 100%; object-fit: contain; }");
            out.println("    .badge-stock { position: absolute; top: 8px; right: 8px; padding: 4px 8px; border-radius: 6px; font-size: 0.72rem; font-weight: 800; z-index: 2; }");
            out.println("    .stock-ok { background: rgba(34, 197, 94, 0.9); color: #052e16; }");
            out.println("    .stock-low { background: rgba(245, 158, 11, 0.9); color: #451a03; }");
            out.println("    .stock-out { background: rgba(239, 68, 68, 0.9); color: white; }");
            out.println("    .badge-marca { color: var(--accent2); background: rgba(56, 189, 248, 0.15); font-size: 0.75rem; font-weight: bold; padding: 3px 8px; border-radius: 4px; display: inline-block; margin: 10px 0 6px; }");
            out.println("    .product-card h3 { font-size: 1.15rem; color: #fff; margin-bottom: 6px; }");
            out.println("    .desc { color: var(--muted); font-size: 0.82rem; height: 36px; overflow: hidden; margin-bottom: 10px; }");
            out.println("    .price-row { display: flex; justify-content: space-between; align-items: center; margin-top: 10px; }");
            out.println("    .price { color: var(--accent); font-weight: 800; font-size: 1.25rem; }");
            out.println("    .btn-add { width: 100%; background: linear-gradient(135deg, var(--accent2), #2563eb); color: white; border: none; padding: 10px; border-radius: 8px; font-weight: 700; cursor: pointer; margin-top: 12px; }");
            out.println("    .btn-add:disabled { background: #334155; cursor: not-allowed; }");

            // DRAWER LATERAL DE DESPACHO
            out.println("    .drawer-overlay { display: none; position: fixed; inset: 0; background: rgba(0,0,0,0.7); z-index: 200; backdrop-filter: blur(4px); }");
            out.println("    .drawer { position: fixed; top: 0; right: -480px; width: 100%; max-width: 480px; height: 100vh; background: var(--panel); z-index: 201; box-shadow: -10px 0 30px rgba(0,0,0,0.5); padding: 24px; display: flex; flex-direction: column; transition: right 0.3s ease; overflow-y: auto; }");
            out.println("    .drawer.active { right: 0; }");
            out.println("    .drawer-header { display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #334155; padding-bottom: 12px; margin-bottom: 16px; }");
            out.println("    .close-btn { background: none; border: none; color: var(--muted); font-size: 1.5rem; cursor: pointer; }");
            out.println("    .cart-items { flex-grow: 1; overflow-y: auto; margin-bottom: 15px; max-height: 220px; }");
            out.println("    .cart-item { display: flex; justify-content: space-between; align-items: center; background: #07111f; padding: 12px; border-radius: 8px; margin-bottom: 10px; border: 1px solid #1e293b; }");
            out.println("    .cart-item-info h4 { font-size: 0.92rem; color: #fff; }");
            out.println("    .cart-item-info p { font-size: 0.8rem; color: var(--muted); }");
            out.println("    .remove-btn { color: var(--danger); background: none; border: none; cursor: pointer; font-size: 0.8rem; margin-top: 4px; }");
            out.println("    .checkout-form { background: #07111f; border: 1px solid #1e293b; border-radius: 12px; padding: 16px; margin-bottom: 16px; }");
            out.println("    .checkout-form h4 { color: var(--accent2); margin-bottom: 10px; font-size: 0.95rem; }");
            out.println("    .form-group { margin-bottom: 10px; }");
            out.println("    .form-group label { display: block; font-size: 0.75rem; color: var(--muted); margin-bottom: 4px; font-weight: bold; }");
            out.println("    .form-group input, .form-group select { width: 100%; background: #0b1329; border: 1px solid #334155; color: white; padding: 8px 10px; border-radius: 6px; font-size: 0.85rem; }");
            out.println("    .summary-row { display: flex; justify-content: space-between; margin-bottom: 6px; font-size: 0.9rem; color: var(--muted); }");
            out.println("    .total-row { display: flex; justify-content: space-between; margin-top: 10px; font-size: 1.2rem; font-weight: 800; color: var(--accent); border-top: 1px solid #334155; padding-top: 10px; }");
            out.println("    .btn-checkout { width: 100%; background: var(--accent); color: #052e16; border: none; padding: 14px; border-radius: 10px; font-weight: 800; font-size: 1rem; cursor: pointer; margin-top: 14px; }");
            out.println("    .empty-msg { text-align: center; color: var(--muted); padding: 30px 0; font-size: 0.9rem; }");
            out.println("  </style>");
            out.println("</head>");
            out.println("<body>");

            out.println("  <header>");
            out.println("    <nav>");
            out.println("      <div class='logo'>SportX</div>");
            out.println("      <button class='cart-trigger' onclick='toggleCarrito()'>");
            out.println("        🛒 Despacho y Carrito <span class='cart-count' id='cart-count'>0</span>");
            out.println("      </button>");
            out.println("    </nav>");
            out.println("  </header>");

            out.println("  <main>");
            out.println("    <div class='section-title'>");
            out.println("      <h2>Catálogo de Artículos Deportivos (MySQL)</h2>");
            out.println("      <span style='color:var(--accent2); font-size:0.9rem;'>Gestión en tiempo real con XAMPP</span>");
            out.println("    </div>");

            out.println("    <div class='products'>");

            for (Producto p : productos) {
                String nomMin = p.getNombre().toLowerCase();
                boolean esColombia = nomMin.contains("colombia") || p.getIdProducto() == 3;
                boolean esBalon = nomMin.contains("balon") || nomMin.contains("balón") || p.getIdProducto() == 5;

                String badgeClass = p.getStock() > 5 ? "stock-ok" : (p.getStock() > 0 ? "stock-low" : "stock-out");
                String badgeText = p.getStock() > 5 ? "Stock: " + p.getStock() + " uds." : (p.getStock() > 0 ? "Últimas " + p.getStock() + " uds." : "Agotado");

                out.println("      <div class='product-card'>");
                out.println("        <div>");
                out.println("          <div class='img-box'>");
                out.println("            <span class='badge-stock " + badgeClass + "'>" + badgeText + "</span>");

                if (esColombia) {
                    String base64Img = obtenerImagenCamiseta();
                    out.println("            <img src='" + base64Img + "' alt='Camiseta Selección Colombia' />");
                } else if (esBalon) {
                    out.println("            <img src='https://images.unsplash.com/photo-1579952363873-27f3bade9f55?auto=format&fit=crop&w=800&q=80' alt='Balon de futbol profesional 5' />");
                } else if (nomMin.contains("guayo") || nomMin.contains("predator") || p.getIdProducto() == 2) {
                    out.println("            <img src='https://images.unsplash.com/photo-1511886929837-354d827aae26?auto=format&fit=crop&w=800&q=80' alt='Guayos' />");
                } else if (nomMin.contains("zapatilla") || nomMin.contains("running") || p.getIdProducto() == 1) {
                    out.println("            <img src='https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=800&q=80' alt='Running' />");
                } else {
                    out.println("            <img src='https://images.unsplash.com/photo-1506629082955-511b1aa562c8?auto=format&fit=crop&w=800&q=80' alt='Ropa deportiva' />");
                }

                out.println("          </div>");
                out.println("          <span class='badge-marca'>" + p.getMarca() + " | Talla: " + p.getTalla() + "</span>");
                out.println("          <h3>" + p.getNombre() + "</h3>");
                out.println("          <p class='desc'>" + (p.getDescripcion() != null ? p.getDescripcion() : "") + "</p>");
                out.println("        </div>");
                out.println("        <div>");
                out.println("          <div class='price-row'>");
                out.println("            <span class='price'>$" + String.format("%,.0f", p.getPrecio()) + "</span>");
                out.println("            <span style='font-size:0.75rem; color:#94a3b8;'>Disp: " + p.getStock() + "</span>");
                out.println("          </div>");

                if (p.getStock() > 0) {
                    out.println("          <button class='btn-add' onclick=\"agregarAlCarrito(" + p.getIdProducto() + ", '" + p.getNombre().replace("'", "") + "', " + p.getPrecio() + ", " + p.getStock() + ")\">Añadir al Despacho</button>");
                } else {
                    out.println("          <button class='btn-add' disabled>Sin Disponibilidad</button>");
                }

                out.println("        </div>");
                out.println("      </div>");
            }

            out.println("    </div>");
            out.println("  </main>");

            out.println("  <div class='drawer-overlay' id='overlay' onclick='toggleCarrito()'></div>");
            out.println("  <div class='drawer' id='drawer'>");
            out.println("    <div class='drawer-header'>");
            out.println("      <h3>📦 Carrito y Despacho</h3>");
            out.println("      <button class='close-btn' onclick='toggleCarrito()'>&times;</button>");
            out.println("    </div>");
            out.println("    <div class='cart-items' id='cart-items'>");
            out.println("      <div class='empty-msg'>Tu carrito está vacío. Agrega artículos del catálogo.</div>");
            out.println("    </div>");
            out.println("    <div class='checkout-form'>");
            out.println("      <h4>👤 Datos del Cliente y Envío</h4>");
            out.println("      <div class='form-group'>");
            out.println("        <label>Nombre y Apellidos</label>");
            out.println("        <input type='text' id='cli-nombre' placeholder='Ej. Carlos Mendoza' />");
            out.println("      </div>");
            out.println("      <div class='form-group'>");
            out.println("        <label>Número de Identificación / Cédula</label>");
            out.println("        <input type='text' id='cli-doc' placeholder='Ej. 1020304050' />");
            out.println("      </div>");
            out.println("      <div class='form-group'>");
            out.println("        <label>Ciudad y Dirección de Despacho</label>");
            out.println("        <input type='text' id='cli-dir' placeholder='Ej. Bogotá, Cra 15 # 80-20' />");
            out.println("      </div>");
            out.println("      <div class='form-group'>");
            out.println("        <label>Empresa de Despacho Logístico</label>");
            out.println("        <select id='cli-transporte' onchange='actualizarResumen()'>");
            out.println("          <option value='0'>Envía Express ($0 - Promoción local)</option>");
            out.println("          <option value='12000'>Servientrega Nacional ($12.000 COP)</option>");
            out.println("          <option value='15000'>Interrapidísimo Inmediato ($15.000 COP)</option>");
            out.println("        </select>");
            out.println("      </div>");
            out.println("    </div>");
            out.println("    <div class='checkout-summary'>");
            out.println("      <div class='summary-row'><span>Subtotal Productos:</span><span id='sum-subtotal'>$0</span></div>");
            out.println("      <div class='summary-row'><span>Flete de Despacho:</span><span id='sum-flete'>$0</span></div>");
            out.println("      <div class='total-row'><span>Total a Liquidar:</span><span id='sum-total'>$0</span></div>");
            out.println("      <button class='btn-checkout' onclick='confirmarDespacho()'>Confirmar y Despachar Pedido</button>");
            out.println("    </div>");
            out.println("  </div>");

            out.println("  <script>");
            out.println("    let carrito = [];");
            out.println("    function toggleCarrito() {");
            out.println("      const drawer = document.getElementById('drawer');");
            out.println("      const overlay = document.getElementById('overlay');");
            out.println("      drawer.classList.toggle('active');");
            out.println("      overlay.style.display = drawer.classList.contains('active') ? 'block' : 'none';");
            out.println("    }");
            out.println("    function agregarAlCarrito(id, nombre, precio, stockMax) {");
            out.println("      const item = carrito.find(p => p.id === id);");
            out.println("      if (item) {");
            out.println("        if (item.cantidad < stockMax) { item.cantidad++; }");
            out.println("        else { alert('Límite de stock alcanzado (' + stockMax + ' uds en MySQL).'); return; }");
            out.println("      } else {");
            out.println("        carrito.push({ id, nombre, precio, cantidad: 1, stockMax });");
            out.println("      }");
            out.println("      actualizarVistaCarrito();");
            out.println("      toggleCarrito();");
            out.println("    }");
            out.println("    function eliminarItem(id) {");
            out.println("      carrito = carrito.filter(p => p.id !== id);");
            out.println("      actualizarVistaCarrito();");
            out.println("    }");
            out.println("    function actualizarVistaCarrito() {");
            out.println("      const container = document.getElementById('cart-items');");
            out.println("      const badge = document.getElementById('cart-count');");
            out.println("      badge.innerText = carrito.reduce((acc, p) => acc + p.cantidad, 0);");
            out.println("      if (carrito.length === 0) {");
            out.println("        container.innerHTML = \"<div class='empty-msg'>Tu carrito está vacío. Agrega artículos del catálogo.</div>\";");
            out.println("      } else {");
            out.println("        container.innerHTML = carrito.map(p => `");
            out.println("          <div class='cart-item'>");
            out.println("            <div class='cart-item-info'>");
            out.println("              <h4>${p.nombre}</h4>");
            out.println("              <p>Cant: <strong>${p.cantidad}</strong> / ${p.stockMax} disp. | $${(p.precio * p.cantidad).toLocaleString('es-CO')} COP</p>");
            out.println("              <button class='remove-btn' onclick='eliminarItem(${p.id})'>🗑 Quitar</button>");
            out.println("            </div>");
            out.println("          </div>`).join('');");
            out.println("      }");
            out.println("      actualizarResumen();");
            out.println("    }");
            out.println("    function actualizarResumen() {");
            out.println("      let subtotal = carrito.reduce((acc, p) => acc + (p.precio * p.cantidad), 0);");
            out.println("      let flete = parseInt(document.getElementById('cli-transporte').value);");
            out.println("      let total = subtotal > 0 ? (subtotal + flete) : 0;");
            out.println("      document.getElementById('sum-subtotal').innerText = '$' + subtotal.toLocaleString('es-CO') + ' COP';");
            out.println("      document.getElementById('sum-flete').innerText = '$' + flete.toLocaleString('es-CO') + ' COP';");
            out.println("      document.getElementById('sum-total').innerText = '$' + total.toLocaleString('es-CO') + ' COP';");
            out.println("    }");
            out.println("    function confirmarDespacho() {");
            out.println("      if (carrito.length === 0) { alert('El carrito está vacío.'); return; }");
            out.println("      const nom = document.getElementById('cli-nombre').value.trim();");
            out.println("      const doc = document.getElementById('cli-doc').value.trim();");
            out.println("      const dir = document.getElementById('cli-dir').value.trim();");
            out.println("      if (!nom || !doc || !dir) {");
            out.println("        alert('Por favor diligencia Nombre, Documento y Dirección de despacho.'); return;");
            out.println("      }");
            out.println("      alert('¡ORDEN DE DESPACHO REGISTRADA!\\n\\nCliente: ' + nom + '\\nDocumento: ' + doc + '\\nDirección: ' + dir + '\\nTotal a pagar: ' + document.getElementById('sum-total').innerText);");
            out.println("      carrito = [];");
            out.println("      actualizarVistaCarrito();");
            out.println("      toggleCarrito();");
            out.println("    }");
            out.println("  </script>");
            out.println("</body>");
            out.println("</html>");

            System.out.println("-> [OK] Catalogo SportX renderizado exitosamente.");

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivo);
            }

        } catch (Exception e) {
            System.err.println("-> [ERROR]: " + e.getMessage());
        }
    }

    private static String obtenerImagenCamiseta() {
        // 1. Busca si ya existe copiada localmente en la raíz
        File local = new File("camiseta_guardada.dat");
        if (local.exists()) {
            try {
                byte[] bytes = java.nio.file.Files.readAllBytes(local.toPath());
                return new String(bytes, StandardCharsets.UTF_8);
            } catch (IOException ignored) {}
        }

        // 2. Si no existe, abre una ventana emergente para que selecciones tu foto descargada
        try {
            System.out.println("-> Abre la ventana y selecciona la foto que descargaste...");
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Selecciona la foto de la camiseta de Colombia que descargaste");
            chooser.setFileFilter(new FileNameExtensionFilter("Imágenes (PNG, JPG, JPEG, WEBP)", "png", "jpg", "jpeg", "webp"));
            
            int res = chooser.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                File seleccionado = chooser.getSelectedFile();
                byte[] bytesImg = java.nio.file.Files.readAllBytes(seleccionado.toPath());
                String ext = seleccionado.getName().toLowerCase().endsWith(".png") ? "png" : "jpeg";
                String base64 = "data:image/" + ext + ";base64," + java.util.Base64.getEncoder().encodeToString(bytesImg);

                // Guardamos en disco para que en las siguientes ejecuciones no te vuelva a preguntar
                java.nio.file.Files.write(local.toPath(), base64.getBytes(StandardCharsets.UTF_8));
                System.out.println("-> [ÉXITO] Imagen guardada permanentemente en el proyecto.");
                return base64;
            }
        } catch (HeadlessException | IOException e) {
            System.err.println("No se pudo procesar la imagen: " + e.getMessage());
        }

        // Respaldo visual en caso de cancelar el selector
        return "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?auto=format&fit=crop&w=800&q=80";
    }
}