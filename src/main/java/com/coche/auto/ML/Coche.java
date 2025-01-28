/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coche.auto.ML;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 *
 * @author Alien 9
 */
public class Coche {

    private int IdCoche;

    @NotEmpty(message = "No puede estar vacio el campo")
    @Size(min = 10, max = 17)
    private String Vin;

    @NotEmpty(message = "No puede estar vacio el campo")
    @Size(max = 7)
    private String Placa;

    @NotEmpty(message = "No puede estar vacio el campo")
    private String Modelo;
    
    private int Status; 
    
    public  Agencia Agencia;

    public Coche() {
    }

    public int getIdCoche() {
        return IdCoche;
    }

    public void setIdCoche(int IdCoche) {
        this.IdCoche = IdCoche;
    }

    public String getVin() {
        return Vin;
    }

    public void setVin(String Vin) {
        this.Vin = Vin;
    }

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String Placa) {
        this.Placa = Placa;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String Modelo) {
        this.Modelo = Modelo;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public Agencia getAgencia() {
        return Agencia;
    }

    public void setAgencia(Agencia Agencia) {
        this.Agencia = Agencia;
    }
    
    
}
