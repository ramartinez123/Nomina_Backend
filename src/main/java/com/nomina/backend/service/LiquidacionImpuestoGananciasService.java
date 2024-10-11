package com.nomina.backend.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.model.Empleado;
import com.nomina.backend.repository.DeduccionImpuestoGananciasRepository;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.repository.EmpleadoRepository;
import com.nomina.backend.repository.FamiliarRepository;

@Service
public class LiquidacionImpuestoGananciasService {

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;
    
    @Autowired
    private FamiliarRepository familiarRepository;
    
    @Autowired
    private DeduccionImpuestoGananciasRepository deduccionImpuestoGananciasRepository;
    
    @Autowired
    private EmpleadoRepository empleadoRepository; 
    
    public Map<Integer, Integer> calcularImpuestoGananciasTodos(Date mesLiquidacion) {
    	
    	
        List<Empleado> empleados = empleadoRepository.findAll(); 
        Map<Integer, Integer> resultados = new HashMap<>();
        
        for (Empleado empleado : empleados) {
            int empleadoId = empleado.getId(); 
            int impuesto = calcularImpuestoGanancias(empleadoId, mesLiquidacion);
            resultados.put(empleadoId, impuesto); 
        }
        
        System.out.println("Resultados del cálculo de impuestos:");
        for (Map.Entry<Integer, Integer> entry : resultados.entrySet()) {
            System.out.println("Empleado ID: " + entry.getKey() + ", Impuesto Ganancias: " + entry.getValue());
        }
        
        return resultados; 
    }

    public int calcularImpuestoGanancias(int empleadoId, Date mesLiquidacion) {
        // Obtener valores de conceptos 92 y 192 de detalleLiquidacion por empleado
        Integer valorConcepto92 = detalleLiquidacionRepository.obtenerValorConcepto(empleadoId, 92);
        Integer valorConcepto192 = detalleLiquidacionRepository.obtenerValorConcepto(empleadoId, 192);
        
        // Obtener cantidad de cónyuges y hijos que cumplen las condiciones
        Integer cantidadConyuges = familiarRepository.contarConyuges(empleadoId, mesLiquidacion);
        Integer cantidadHijos = familiarRepository.contarHijos(empleadoId, mesLiquidacion);
        
        System.out.println("Cantidad de cónyuges: " + cantidadConyuges);
        System.out.println("Cantidad de hijos: " + cantidadHijos);
        
        // Obtener deducciones de impuesto para el mes de liquidación
        Integer deduccionPorFamiliar = deduccionImpuestoGananciasRepository.obtenerDeduccion(mesLiquidacion, 2) ;
        Integer deduccionPorFamiliar2 = deduccionImpuestoGananciasRepository.obtenerDeduccion(mesLiquidacion, 3);
               
        // Manejar el caso donde deduccionPorFamiliar es null
        int deduccionFinal = (deduccionPorFamiliar != null) ? deduccionPorFamiliar : 0;
        int deduccionFinal2 = (deduccionPorFamiliar2 != null) ? deduccionPorFamiliar2 : 0;
        
        // Calcular el valor final del impuesto
        int impuestoGanancias = (valorConcepto92 - valorConcepto192) - (deduccionFinal * cantidadConyuges)-(deduccionFinal2 * cantidadHijos);
        
        return impuestoGanancias;
    }
}
