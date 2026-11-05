package co.umariana.trabalenguas.modelo;

/**
 * Un trabalengua del Trabalenguario: una imagen con su título, categoría y nivel de enredo.
 *
 * <p>La clase ya tiene sus atributos, constructor y getters. Lo que falta es {@link #validar()}
 * (tarea de MODELO).
 */
public class Trabalengua {

    private final String codigo;
    private final String titulo;
    private final String categoria;
    private final int enredo;
    private final double tiempoSeg;
    private final String descripcion;
    private final String archivo;

    public Trabalengua(String codigo, String titulo, String categoria, int enredo,
                   double tiempoSeg, String descripcion, String archivo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.categoria = categoria;
        this.enredo = enredo;
        this.tiempoSeg = tiempoSeg;
        this.descripcion = descripcion;
        this.archivo = archivo;
    }

    /**
     * Revisa que el trabalengua sea válido. Si no lo es, lanza {@link IllegalArgumentException}
     * con un mensaje que explique el problema. Reglas:
     * <ul>
     *   <li>{@code codigo}, {@code titulo}, {@code categoria} y {@code archivo} no pueden ser nulos ni estar en blanco.</li>
     *   <li>{@code enredo} debe estar entre 1 y 10 (incluidos).</li>
     *   <li>{@code tiempoSeg} debe ser mayor que 0.</li>
     *   <li>{@code descripcion} puede estar vacía, pero no ser nula.</li>
     *   <li>Ningún texto puede contener el carácter {@code ;} (separa los campos del archivo .txt)
     *       ni saltos de línea.</li>
     * </ul>
     */
    public void validar() {
        validarTextoObligatorio(codigo, "El código");
        validarTextoObligatorio(titulo, "El título");
        validarTextoObligatorio(categoria, "La categoría");
        validarTextoObligatorio(archivo, "El archivo");
        validarTexto(descripcion, "La descripción");

        if (enredo < 1 || enredo > 10) {
            throw new IllegalArgumentException("El enredo debe estar entre 1 y 10.");
        }
        if (!Double.isFinite(tiempoSeg) || tiempoSeg <= 0) {
            throw new IllegalArgumentException("El tiempo debe ser mayor que 0.");
        }
    }

    private static void validarTextoObligatorio(String texto, String campo) {
        validarTexto(texto, campo);
        if (texto.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar en blanco.");
        }
    }

    private static void validarTexto(String texto, String campo) {
        if (texto == null) {
            throw new IllegalArgumentException(campo + " no puede ser nulo.");
        }
        if (texto.indexOf(';') >= 0 || texto.indexOf('\n') >= 0 || texto.indexOf('\r') >= 0) {
            throw new IllegalArgumentException(campo + " no puede contener ';' ni saltos de línea.");
        }
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public String getCategoria() { return categoria; }
    public int getEnredo() { return enredo; }
    public double getTiempoSeg() { return tiempoSeg; }
    public String getDescripcion() { return descripcion; }
    public String getArchivo() { return archivo; }

    @Override
    public String toString() {
        return codigo + " - " + titulo + " (" + categoria + ", enredo " + enredo + ")";
    }
}
