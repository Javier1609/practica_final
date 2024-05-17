package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class experimento implements Serializable {
    private String nombreArchivo;
    private String descripcion;
    private List<bacteria> poblaciones;

    public experimento(String nombreArchivo, String descripcion) {
        this.nombreArchivo = nombreArchivo;
        this.descripcion = descripcion;
        this.poblaciones = new ArrayList<>();
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void addPoblacion(bacteria poblacion) {
        this.poblaciones.add(poblacion);
    }
    public List<bacteria> getPoblaciones() {
        return this.poblaciones;
    }
    public void removePoblacion(bacteria poblacion) {
        this.poblaciones.remove(poblacion);
    }

}