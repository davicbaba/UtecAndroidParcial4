package com.example.utecparcial4.Models;

public class Cliente {
    private int idCliente;
    private String nombre;
    private String apellidos;
    private String direcciones;
    private String ciudad;

    public Cliente(int idCliente, String nombre, String apellidos, String direcciones, String ciudad) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direcciones = direcciones;
        this.ciudad = ciudad;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(String direcciones) {
        this.direcciones = direcciones;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}