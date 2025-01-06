package com.nomina.backend.service;

import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.model.CuentaContable;
import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.repository.EmpleadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.Month;

@Service
public class AsientoComtablesService {

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Map<String, Object>> generarJsonConDH(int mes, int anio) {
        List<Empleado> empleados = empleadoRepository.findAll();
        Map<String, Integer> sumaPorCuentaContable = new HashMap<>();

        // Crear la fecha de inicio y fin del mes usando LocalDate
        LocalDate fechaInicio = LocalDate.of(anio, mes, 1);  // Primer día del mes
        LocalDate fechaFin = fechaInicio.withDayOfMonth(fechaInicio.lengthOfMonth());  // Último día del mes

        // Sumar los montos por cuenta contable, filtrando por mes y año
        for (Empleado empleado : empleados) {
            // Consultar los detalles de liquidación filtrados por el rango de fechas
            List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByEmpleadoIdAndFechaLiquidacionBetween(
                    empleado.getId(), fechaInicio, fechaFin);

            for (DetalleLiquidacion detalle : detalles) {
                ConceptoSalarial concepto = detalle.getConceptoSalarial();
                String cuentaContable = concepto.getCuentaContable().toString();

                // Excluir las cuentas contables con el valor "NO"
                if ("NO".equals(cuentaContable)) {
                    continue;
                }

                int monto = detalle.getMonto();
                sumaPorCuentaContable.put(cuentaContable,
                    sumaPorCuentaContable.getOrDefault(cuentaContable, 0) + monto);
            }
        }

        // Convertir los datos a la lista con el nuevo campo
        List<Map<String, Object>> resultado = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sumaPorCuentaContable.entrySet()) {
            String cuenta = entry.getKey();
            int monto = entry.getValue();

            // Si es la cuenta "APORTES", duplicarla con tipo D y H
            if ("APORTES".equals(cuenta)) {
                // Agregar con tipo D
                Map<String, Object> cuentaJsonD = new HashMap<>();
                cuentaJsonD.put("tipo", "D");
                cuentaJsonD.put("monto", monto);
                cuentaJsonD.put("cuentaContable", cuenta);
                resultado.add(cuentaJsonD);

                // Agregar con tipo H
                Map<String, Object> cuentaJsonH = new HashMap<>();
                cuentaJsonH.put("tipo", "H");
                cuentaJsonH.put("monto", monto);
                cuentaJsonH.put("cuentaContable", cuenta);
                resultado.add(cuentaJsonH);
            } else {
                // Para las demás cuentas, determinar el valor del nuevo campo (D o H)
                String nuevoCampo = ("SUELDOS".equals(cuenta) || 
                                     "LICENCIAS".equals(cuenta)) ? "D" : "H";

                // Crear el objeto para el JSON
                Map<String, Object> cuentaJson = new HashMap<>();
                cuentaJson.put("tipo", nuevoCampo);
                cuentaJson.put("monto", monto);
                cuentaJson.put("cuentaContable", cuenta);

                resultado.add(cuentaJson);
            }
        }

        return resultado;
    }}