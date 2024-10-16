package com.nomina.backend.controller;

import com.nomina.backend.model.CuentaContable;
import com.nomina.backend.service.AnulaBajaEmpleadoService;
import com.nomina.backend.service.AsientoComtablesService;
import com.nomina.backend.service.BajaEmpleadoService;
import com.nomina.backend.service.LiquidacionCargasSocialesService;
import com.nomina.backend.service.LiquidacionServiceSueldo;
import com.nomina.backend.service.LiquidacionSueldoIdService;

import jakarta.persistence.EntityNotFoundException;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/liquidacion") // Define el path base para el controlador
public class LiquidacionController {

    @Autowired
    private LiquidacionServiceSueldo liquidacionService;
    
    @Autowired
    private LiquidacionSueldoIdService liquidacionServiceById;
    
    @Autowired
    private BajaEmpleadoService darDeBajaEmpleado;
    
    @Autowired
    private AnulaBajaEmpleadoService anulaBajaEmpleado;
    
    @Autowired
    private LiquidacionCargasSocialesService liquidacionCargasSocialesService;
    
    @Autowired
    private AsientoComtablesService asientoComtablesService;

    @PostMapping("/realizar") // Define el endpoint para realizar la liquidación
    public ResponseEntity<String> realizarLiquidacion() {
        try {
            liquidacionService.realizarLiquidacion(); // Llama al servicio para realizar la liquidación
            return ResponseEntity.ok("Liquidación realizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al realizar la liquidación: " + e.getMessage());
        }
    }
    
    @PostMapping("/realizarById/{id}") // Define el endpoint para realizar la liquidación por ID
    public ResponseEntity<String> realizarLiquidacionById(@PathVariable Integer id) {
        try {
            liquidacionServiceById.realizarLiquidacionPorEmpleado(id); // Llama al servicio con el ID del empleado
            return ResponseEntity.ok("Liquidación realizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al realizar la liquidación: " + e.getMessage());
        }
    }
    
    @PostMapping("/cargasSociales") // Define el endpoint para realizar la liquidación
    public ResponseEntity<String> procesarCargasSociales() {
        try {
            liquidacionCargasSocialesService.procesarCargasSociales(); // Llama al servicio para realizar la liquidación
            return ResponseEntity.ok("Liquidación de Cargas Sociales 	realizada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al realizar la liquidación: " + e.getMessage());
        }
    }
    
    @PutMapping("/darDeBaja/{id}")
    public ResponseEntity<String> darDeBajaEmpleado(@PathVariable Integer id, @RequestParam java.sql.Date fechaFin, @RequestParam String motivo) {
        try {
            darDeBajaEmpleado.darDeBajaEmpleado(id, fechaFin, motivo); // Llamada al servicio que realiza la baja del empleado
            return ResponseEntity.ok("Empleado dado de baja correctamente");
        } catch (EntityNotFoundException e) {
            // Si no se encuentra el empleado, se devuelve un 404 con un mensaje apropiado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado: " + e.getMessage());
        } catch (Exception e) {
            // Si ocurre cualquier otro error, se devuelve un 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al dar de baja al empleado: " + e.getMessage());
        }
    }
    
    @PutMapping("/anularBaja/{id}")
    public ResponseEntity<String> anulaBajaEmpleado(@PathVariable Integer id) {
        try {
            anulaBajaEmpleado.anulaBajaEmpleado(id); // Llamada al servicio que realiza la baja del empleado
            return ResponseEntity.ok("Empleado reactivado correctamente");
        } catch (EntityNotFoundException e) {
            // Si no se encuentra el empleado, se devuelve un 404 con un mensaje apropiado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empleado no encontrado: " + e.getMessage());
        } catch (Exception e) {
            // Si ocurre cualquier otro error, se devuelve un 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al dar de baja al empleado: " + e.getMessage());
        }
    }
    
    @GetMapping("/sumaPorCuenta")
    public ResponseEntity<Map<CuentaContable, Integer>> obtenerSumaPorCuentaContable() {
        Map<CuentaContable, Integer> sumaPorCuentaContable = asientoComtablesService.sumarConceptosPorCuentaContable();
        return ResponseEntity.ok(sumaPorCuentaContable);
    }
}

