/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coche.auto.ML;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author Alien 9
 */
public class Agencia {
    
    @NotNull(message = "Por favor selecciona una agencia.")  
    @Min(value = 1, message = "Selecciona una agencia válida.") 
    private int IdAgencia;
    
    private String Nombre;

    public Agencia() {
    }

    public int getIdAgencia() {
        return IdAgencia;
    }

    public void setIdAgencia(int IdAgencia) {
        this.IdAgencia = IdAgencia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
}
