/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coche.auto.Coontrol;

import com.coche.auto.ML.Agencia;
import com.coche.auto.ML.Coche;
import com.coche.auto.ML.Result;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author HP
 */
@Controller
@RequestMapping("/coche")
public class control {
    
     @GetMapping
    public String Getall(Model model) {

        Result result = new Result();

        RestTemplate restTemplate = new RestTemplate();
        
        // Obtener Agencias 
        ResponseEntity<Result<List<Agencia>>> agenciaResponse = restTemplate.exchange(
                "http://localhost:8081/ApiRestController/agencia",
                HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Agencia>>>() {});

        // Obtener coches
        ResponseEntity<Result<List<Coche>>> response = restTemplate.exchange(
                "http://localhost:8081/ApiRestController",
                HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Coche>>>() {});

        model.addAttribute("agencias", agenciaResponse.getBody().object);
        
        model.addAttribute("Coche", response.getBody().object);
        
        model.addAttribute("cocheBusqueda", new Coche());
        
        return "CocheIndex";
    }

    @PostMapping
    public String GetAll(@ModelAttribute Coche cocheBusqueda, Model model) {

        RestTemplate restTemplate = new RestTemplate();
        
       HttpEntity<Coche> cocheEntity = new HttpEntity<>(cocheBusqueda);

        ResponseEntity<Result<List<Coche>>> response = restTemplate.exchange(
                "http://localhost:8081/ApiRestController",
                HttpMethod.POST, cocheEntity,
                new ParameterizedTypeReference<Result<List<Coche>>>() {
        });
        
        // Obtener Agencias 
        ResponseEntity<Result<List<Agencia>>> agenciaResponse = restTemplate.exchange(
                "http://localhost:8081/ApiRestController/agencia",
                HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Agencia>>>() {});


        Result<List<Coche>> result = response.getBody();
        
        model.addAttribute("cocheBusqueda", cocheBusqueda);
        
        model.addAttribute("Coche", result.object);
        
        model.addAttribute("agencias", agenciaResponse.getBody().object);
         
        return "CocheIndex";
    }

    @GetMapping("/modify/{IdCoche}")
    public String AddUpdate(@PathVariable int IdCoche, Model model) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Result<List<Agencia>>> agenciaResponse = restTemplate.exchange(
                "http://localhost:8081/ApiRestController/agencia",
                HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Agencia>>>() {
        });

        model.addAttribute("agencias", agenciaResponse.getBody().object);

        if (IdCoche == 0) {
            Coche coche = new Coche();
            coche.Agencia = new Agencia();
            model.addAttribute("Coche", coche);
        } else {
            ResponseEntity<Result<Coche>> getByresponse = restTemplate.exchange(
                    "http://localhost:8081/ApiRestController/id/" + IdCoche,
                    HttpMethod.GET, null,
                    new ParameterizedTypeReference<Result<Coche>>() {
            });

            model.addAttribute("Coche", getByresponse.getBody().object);

        }
        return "Cocheform";
    }

    @PostMapping("/form")
    public String AddUpdate(@Valid @ModelAttribute("Coche") Coche coche, BindingResult bindingResult, Model model) {

        RestTemplate restTemplate = new RestTemplate();

        Result result = new Result();
        
        ResponseEntity<Result<List<Agencia>>> agenciaResponse = restTemplate.exchange(
                "http://localhost:8081/ApiRestController/agencia",
                HttpMethod.GET, null, new ParameterizedTypeReference<Result<List<Agencia>>>() {
        });

        model.addAttribute("agencias", agenciaResponse.getBody().object);
        
        if (bindingResult.hasErrors()) {
            model.addAttribute("Coche", coche);
            return "Cocheform";
        }
        
        if (coche.getIdCoche() == 0) {
            ResponseEntity<Result<List<Coche>>> addresponse = restTemplate.exchange(
                    "http://localhost:8081/ApiRestController/form",
                    HttpMethod.POST,
                    new HttpEntity<>(coche),
                    new ParameterizedTypeReference<Result<List<Coche>>>() {
            });

            result = addresponse.getBody();
        } else {
            ResponseEntity<Result<List<Coche>>> updateresponse = restTemplate.exchange(
                    "http://localhost:8081/ApiRestController/form",
                    HttpMethod.POST,
                    new HttpEntity<>(coche),
                    new ParameterizedTypeReference<Result<List<Coche>>>() {
            });

            result = updateresponse.getBody();
        }

        return "redirect:/coche";
    }

    @PostMapping("/delete/{IdCoche}")
    public String Delate(@PathVariable("IdCoche") int idCoche, Model model) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Result> deleteresponse = restTemplate.exchange(
                "http://localhost:8081/ApiRestController/delete/" + idCoche,
                HttpMethod.POST,
                null,
                Result.class);

        Result result = deleteresponse.getBody();

        return "redirect:/coche";
    }

    @GetMapping("/UpdateStatus")
    @ResponseBody
    public Result UpdateStatus(@RequestParam("idCoche") int idUser, @RequestParam boolean isChecked) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Result> statusResponse = restTemplate.exchange(
                "http://localhost:8081/ApiRestController/UpdateStatus?idCoche=" + idUser + "&isChecked=" + isChecked,
                HttpMethod.GET, null, new ParameterizedTypeReference<Result>() {
        });

        return statusResponse.getBody();
    }
}
