package co.umariana.trabalenguas.modelo;

import co.umariana.trabalenguas.estructura.Lista;
import co.umariana.trabalenguas.persistencia.Repositorio;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Reglas de negocio del Trabalenguario (tarea de MODELO).
 *
 * <p>Guarda los trabalenguas en una {@link Lista} (la estructura de datos del equipo) y, después de
 * cada cambio, los guarda con el {@link Repositorio}. No debe usar {@code ArrayList} ni otra
 * colección de Java para guardar los datos: eso lo hace la {@code Lista}.
 */
public class GestorTrabalenguas implements ServicioTrabalenguas {

    private final Lista<Trabalengua> lista;
    private final Repositorio repositorio;

    /**
     * Al crearse, carga con el repositorio los trabalenguas ya guardados y los agrega a la lista.
     * Si el repositorio lanza {@link java.io.IOException}, se relanza como {@link java.io.UncheckedIOException}.
     */
    public GestorTrabalenguas(Lista<Trabalengua> lista, Repositorio repositorio) {
        this.lista = lista;
        this.repositorio = repositorio;
        try {
            for (Trabalengua trabalengua : repositorio.cargar()) {
                lista.agregar(trabalengua);
            }
        } catch (IOException e) {
            throw new UncheckedIOException("No fue posible cargar los trabalenguas.", e);
        }
    }

    @Override
    public void agregar(Trabalengua trabalengua) {
        if (trabalengua == null) {
            throw new IllegalArgumentException("El trabalengua no puede ser nulo.");
        }
        trabalengua.validar();
        if (buscar(trabalengua.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un trabalengua con el código " + trabalengua.getCodigo());
        }
        List<Trabalengua> cambios = listar();
        cambios.add(trabalengua);
        persistir(cambios);
        lista.agregar(trabalengua);
    }

    @Override
    public Trabalengua buscar(String codigo) {
        if (codigo == null) {
            return null;
        }
        for (Trabalengua trabalengua : lista) {
            if (codigo.equals(trabalengua.getCodigo())) {
                return trabalengua;
            }
        }
        return null;
    }

    @Override
    public boolean eliminar(String codigo) {
        for (int i = 0; i < lista.tamano(); i++) {
            if (codigo != null && codigo.equals(lista.obtener(i).getCodigo())) {
                List<Trabalengua> cambios = listar();
                cambios.remove(i);
                persistir(cambios);
                lista.eliminar(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Trabalengua> listar() {
        List<Trabalengua> resultado = new ArrayList<>(lista.tamano());
        for (Trabalengua trabalengua : lista) {
            resultado.add(trabalengua);
        }
        return resultado;
    }

    @Override
    public List<Trabalengua> ranking() {
        List<Trabalengua> resultado = listar();
        resultado.sort(Comparator.comparingInt(Trabalengua::getEnredo).reversed()
                .thenComparing(Trabalengua::getCodigo));
        return resultado;
    }

    @Override
    public Map<String, Double> promedioPorCategoria() {
        Map<String, Integer> cantidades = new HashMap<>();
        Map<String, Integer> sumas = new HashMap<>();
        for (Trabalengua trabalengua : lista) {
            String categoria = trabalengua.getCategoria();
            cantidades.merge(categoria, 1, Integer::sum);
            sumas.merge(categoria, trabalengua.getEnredo(), Integer::sum);
        }

        Map<String, Double> promedios = new TreeMap<>();
        for (Map.Entry<String, Integer> entrada : cantidades.entrySet()) {
            promedios.put(entrada.getKey(), (double) sumas.get(entrada.getKey()) / entrada.getValue());
        }
        return promedios;
    }

    @Override
    public int total() {
        return lista.tamano();
    }

    @Override
    public Trabalengua actual() {
        return lista.actual();
    }

    @Override
    public Trabalengua siguiente() {
        lista.avanzar();
        return lista.actual();
    }

    @Override
    public Trabalengua anterior() {
        lista.retroceder();
        return lista.actual();
    }

    private void persistir(List<Trabalengua> trabalenguas) {
        try {
            repositorio.guardar(trabalenguas);
        } catch (IOException e) {
            throw new UncheckedIOException("No fue posible guardar los trabalenguas.", e);
        }
    }
}
