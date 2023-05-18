package com.example.utecparcial4.Models;

public class Vehiculo {
    private int idVehiculo;
    private String marca;
    private String modelo;

    public Vehiculo(int idVehiculo, String marca, String modelo) {
        this.idVehiculo = idVehiculo;
        this.marca = marca;
        this.modelo = modelo;
    }

    public int getIdVehiculo() {
        return idVehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }
}