package co.umariana.trabalenguas.persistencia;

import co.umariana.trabalenguas.modelo.Trabalengua;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda los trabalenguas en un archivo de texto (tarea de PERSISTENCIA).
 *
 * <p>Formato: UNA línea por trabalengua, con los campos separados por {@code ;} y en este orden:
 * <pre>codigo;titulo;categoria;enredo;tiempoSeg;descripcion;archivo</pre>
 * Ejemplo:
 * <pre>L001;Pablito clavó un clavito;Lengua;2;1.32;Trabalengua de ejemplo de todos los días;pablito.png</pre>
 * <ul>
 *   <li>Codificación UTF-8 (para las tildes y la ñ).</li>
 *   <li>{@code tiempoSeg} se escribe con punto decimal.</li>
 *   <li>{@code cargar}: si el archivo no existe devuelve una lista vacía; ignora las líneas en
 *       blanco y las líneas dañadas (cantidad de campos distinta de 7 o números inválidos).</li>
 *   <li>{@code guardar}: reemplaza todo el contenido del archivo y crea las carpetas que falten.
 *       Una lista vacía deja el archivo vacío.</li>
 * </ul>
 */
public class RepositorioTxt implements Repositorio {

    private final Path archivo;

    public RepositorioTxt(Path archivo) {
        this.archivo = archivo;
    }

    @Override
    public List<Trabalengua> cargar() throws IOException {
        if (Files.notExists(archivo)) {
            return new ArrayList<>();
        }

        List<Trabalengua> trabalenguas = new ArrayList<>();
        for (String linea : Files.readAllLines(archivo, StandardCharsets.UTF_8)) {
            if (linea.isBlank()) {
                continue;
            }

            String[] campos = linea.split(";", -1);
            if (campos.length != 7) {
                continue;
            }

            try {
                int enredo = Integer.parseInt(campos[3]);
                double tiempoSeg = Double.parseDouble(campos[4]);
                if (!Double.isFinite(tiempoSeg)) {
                    continue;
                }
                trabalenguas.add(new Trabalengua(
                        campos[0], campos[1], campos[2], enredo, tiempoSeg, campos[5], campos[6]));
            } catch (NumberFormatException e) {
                // Una línea inválida no debe impedir cargar los registros restantes.
            }
        }
        return trabalenguas;
    }

    @Override
    public void guardar(List<Trabalengua> trabalenguas) throws IOException {
        Path carpeta = archivo.getParent();
        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }

        List<String> lineas = new ArrayList<>(trabalenguas.size());
        for (Trabalengua trabalengua : trabalenguas) {
            lineas.add(String.join(";",
                    trabalengua.getCodigo(),
                    trabalengua.getTitulo(),
                    trabalengua.getCategoria(),
                    Integer.toString(trabalengua.getEnredo()),
                    Double.toString(trabalengua.getTiempoSeg()),
                    trabalengua.getDescripcion(),
                    trabalengua.getArchivo()));
        }
        Files.write(archivo, lineas, StandardCharsets.UTF_8);
    }
}
