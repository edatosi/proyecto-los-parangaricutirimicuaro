package co.umariana.trabalenguas.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GET /visor  (tarea de SERVLETS)
 *
 * <p>Contrato (las pruebas lo revisan):
 * <ul>
 *   <li>Parámetro {@code accion}: {@code "siguiente"} usa {@code Contexto.servicio().siguiente()},
 *       {@code "anterior"} usa {@code anterior()}; sin parámetro (o con otro valor) usa {@code actual()}.</li>
 *   <li>Pone el trabalengua resultante (puede ser {@code null}) en el atributo {@code "trabalengua"} y la cantidad
 *       total en {@code "total"}.</li>
 *   <li>Reenvía a {@code /WEB-INF/vistas/visor.jsp}, que debe mostrar el trabalengua, un visor
 *       {@code <img>} (los archivos están en {@code media/}) y los botones Anterior / Siguiente.</li>
 * </ul>
 */
@WebServlet("/visor")
public class Visor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");
        var servicio = Contexto.servicio();
        var trabalengua = switch (accion == null ? "" : accion) {
            case "siguiente" -> servicio.siguiente();
            case "anterior" -> servicio.anterior();
            default -> servicio.actual();
        };
        req.setAttribute("trabalengua", trabalengua);
        req.setAttribute("total", servicio.total());
        req.getRequestDispatcher("/WEB-INF/vistas/visor.jsp").forward(req, resp);
    }
}
