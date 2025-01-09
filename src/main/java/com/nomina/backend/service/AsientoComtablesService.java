package com.nomina.backend.service;

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

@Service
public class AsientoComtablesService {

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public List<Map<String, Object>> generarJsonConDH(int mes, int anio) {
        LocalDate fechaInicio = LocalDate.of(anio, mes, 1);
        LocalDate fechaFin = fechaInicio.withDayOfMonth(fechaInicio.lengthOfMonth());

        List<Empleado> empleados = empleadoRepository.findAll();
        Map<String, Integer> sumaPorCuentaContable = calcularSumaPorCuenta(empleados, fechaInicio, fechaFin);

        return generarJsonConCampo(sumaPorCuentaContable);
    }

    private Map<String, Integer> calcularSumaPorCuenta(List<Empleado> empleados, LocalDate fechaInicio, LocalDate fechaFin) {
        Map<String, Integer> sumaPorCuentaContable = new HashMap<>();

        for (Empleado empleado : empleados) {
            List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByEmpleadoIdAndFechaLiquidacionBetween(
                    empleado.getId(), fechaInicio, fechaFin);

            for (DetalleLiquidacion detalle : detalles) {
                String cuentaContable = detalle.getConceptoSalarial().getCuentaContable().toString();
                if (esCuentaExcluida(cuentaContable)) continue;

                int monto = detalle.getMonto();
                sumaPorCuentaContable.put(cuentaContable,
                        sumaPorCuentaContable.getOrDefault(cuentaContable, 0) + monto);
            }
        }

        return sumaPorCuentaContable;
    }

    private boolean esCuentaExcluida(String cuentaContable) {
        return "NO".equals(cuentaContable);
    }

    private List<Map<String, Object>> generarJsonConCampo(Map<String, Integer> sumaPorCuentaContable) {
        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : sumaPorCuentaContable.entrySet()) {
            String cuenta = entry.getKey();
            int monto = entry.getValue();

            if ("APORTES".equals(cuenta)) {
                resultado.add(crearJson(cuenta, monto, "D"));
                resultado.add(crearJson(cuenta, monto, "H"));
            } else {
                String tipo = determinarTipoCuenta(cuenta);
                resultado.add(crearJson(cuenta, monto, tipo));
            }
        }

        return resultado;
    }

    private Map<String, Object> crearJson(String cuenta, int monto, String tipo) {
        Map<String, Object> cuentaJson = new HashMap<>();
        cuentaJson.put("tipo", tipo);
        cuentaJson.put("monto", monto);
        cuentaJson.put("cuentaContable", cuenta);
        return cuentaJson;
    }

    private String determinarTipoCuenta(String cuenta) {
        return ("SUELDOS".equals(cuenta) || "LICENCIAS".equals(cuenta)) ? "D" : "H";
    }
}