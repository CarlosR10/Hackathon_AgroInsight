package com.example.database_test;

public class Planta {
    private int idPlanta;
    private String nombreComun;
    private byte[] imagen;
    private String fechaGuardado;

    // Constructor
    public Planta(int idPlanta, String nombreComun, byte[] imagen, String fechaGuardado) {
        this.idPlanta = idPlanta;
        this.nombreComun = nombreComun;
        this.imagen = imagen != null ? imagen : new byte[0]; // Asegúrate de que imagen nunca sea null
        this.fechaGuardado = fechaGuardado;
    }

    // Getters
    public int getIdPlanta() {
        return idPlanta;
    }

    public String getNombreComun() {
        return nombreComun;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public String getFechaGuardado() {
        return fechaGuardado;
    }

    // Puedes agregar un método toString para propósitos de depuración
    @Override
    public String toString() {
        return "Planta{" +
                "idPlanta=" + idPlanta +
                ", nombreComun='" + nombreComun + '\'' +
                ", fechaGuardado='" + fechaGuardado + '\'' +
                '}';
    }
}
