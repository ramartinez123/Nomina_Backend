
package com.nomina.backend.service;

import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.model.CuentaContable;
import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.repository.EmpleadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AsientoComtablesService {

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;


    private final CuentaContable cuentaAdicional2 = CuentaContable.RETENCIONES; // Enum constante


    public Map<CuentaContable, Integer> sumarConceptosPorCuentaContable() {
        List<Empleado> empleados = empleadoRepository.findAll(); // Obtener todos los empleados
        Map<CuentaContable, Integer> sumaPorCuentaContable = new HashMap<>();
        
        int totalMontoParaCuentaAdicional1 = 0;
        int totalMontoParaCuentaAdicional2 = 0;

        for (Empleado empleado : empleados) {
            List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByEmpleadoId(empleado.getId());

            for (DetalleLiquidacion detalle : detalles) {
                ConceptoSalarial concepto = detalle.getConceptoSalarial();
                int monto = detalle.getMonto();
                CuentaContable cuentaContable = concepto.getCuentaContable();

                // Asignar monto a la cuenta correspondiente
                if (cuentaContable != null) {
                    sumaPorCuentaContable.put(cuentaContable,
                        sumaPorCuentaContable.getOrDefault(cuentaContable, 0) + monto);
                } else {
                    System.out.println("Cuenta contable nula para concepto: " + concepto);
                }

         
                if (concepto.getId() == 1001 || concepto.getId() == 1002 || concepto.getId() == 1003 || concepto.getId() == 1004|| concepto.getId() == 1005) {
                    totalMontoParaCuentaAdicional2 += monto;
                                }
            }
        }

        // Asignar los montos acumulados a las cuentas adicionales
        sumaPorCuentaContable.put(cuentaAdicional2, 
                sumaPorCuentaContable.getOrDefault(cuentaAdicional2, 0) + totalMontoParaCuentaAdicional2);


        return sumaPorCuentaContable;
    }
}

