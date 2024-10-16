package com.nomina.backend.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.model.ConceptoSalarial;
import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.EscalaGanancias;
import com.nomina.backend.repository.ConceptoSalarialRepository;
import com.nomina.backend.repository.DeduccionImpuestoGananciasRepository;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import com.nomina.backend.repository.EmpleadoRepository;
import com.nomina.backend.repository.EscalaGananciasRepository;
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
    
    @Autowired
    private EscalaGananciasRepository escalaGananciasRepository;
    
    @Autowired
    private ConceptoSalarialRepository conceptoSalarialRepository;
    
    public Map<Integer, Integer> calcularImpuestoGananciasTodos(Date mesLiquidacion) {
    	
    	
    	List<Empleado> empleados = empleadoRepository.findAll(); 
        Map<Integer, Integer> resultados = new HashMap<>();
        
        for (Empleado empleado : empleados) {
            int empleadoId = empleado.getId(); 
            int impuesto = calcularImpuestoGanancias(empleadoId, mesLiquidacion);
            resultados.put(empleadoId, impuesto); 
            calcularSueldoNeto(empleadoId, mesLiquidacion);
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
    	valorConcepto92 = (valorConcepto92 != null) ? valorConcepto92 : 0;

    	Integer valorConcepto192 = detalleLiquidacionRepository.obtenerValorConcepto(empleadoId, 192);
    	valorConcepto192 = (valorConcepto192 != null) ? valorConcepto192 : 0;

    	Integer valorConcepto85 = detalleLiquidacionRepository.obtenerValorConcepto(empleadoId, 85);
    	valorConcepto85 = (valorConcepto85 != null) ? valorConcepto85 : 0;

    	Integer valorConcepto185 = detalleLiquidacionRepository.obtenerValorConcepto(empleadoId, 185);
    	valorConcepto185 = (valorConcepto185 != null) ? valorConcepto185 : 0;

    	Integer valorConcepto285 = detalleLiquidacionRepository.obtenerValorConcepto(empleadoId, 285);
    	valorConcepto285 = (valorConcepto285 != null) ? valorConcepto285 : 0;

    	// Obtener cantidad de cónyuges y hijos que cumplen las condiciones
    	Integer cantidadConyuges = familiarRepository.contarConyuges(empleadoId, mesLiquidacion);
    	cantidadConyuges = (cantidadConyuges != null) ? cantidadConyuges : 0;

    	Integer cantidadHijos = familiarRepository.contarHijos(empleadoId, mesLiquidacion);
    	cantidadHijos = (cantidadHijos != null) ? cantidadHijos : 0;

    	// Obtener deducciones de impuesto para el mes de liquidación
    	Integer gananciaNoImponible = deduccionImpuestoGananciasRepository.obtenerDeduccion(mesLiquidacion, 1);
    	System.out.println("Ganancia No Imponible: " + gananciaNoImponible);

    	Integer deduccionEspecial = deduccionImpuestoGananciasRepository.obtenerDeduccion(mesLiquidacion, 5);
    	System.out.println("Deducción Especial: " + deduccionEspecial);

    	Integer deduccionPorEsposa = deduccionImpuestoGananciasRepository.obtenerDeduccion(mesLiquidacion, 2)*cantidadConyuges;
    	deduccionPorEsposa = (deduccionPorEsposa != null) ? deduccionPorEsposa : 0;

    	Integer deduccionPorHijo = deduccionImpuestoGananciasRepository.obtenerDeduccion(mesLiquidacion, 3)*cantidadHijos;
    	deduccionPorHijo = (deduccionPorHijo != null) ? deduccionPorHijo : 0;



    	// Calcular el valor final del impuesto
    	int imponibleImpuestoGanancias = (valorConcepto92 + valorConcepto85 - valorConcepto192 - valorConcepto185 ) 
    	                        - gananciaNoImponible 
    	                        - deduccionEspecial 
    	                        - deduccionPorEsposa
    	                        - deduccionPorHijo;

    	System.out.println("Base Imponible: " + imponibleImpuestoGanancias); 	
    	System.out.println("Calculando impuesto para empleado ID: " + empleadoId);
    	System.out.println("Valores obtenidos para el cálculo:");
    	System.out.println("Concepto 92: " + valorConcepto92);
    	System.out.println("Concepto 85: " + valorConcepto85);
    	System.out.println("Concepto 192: " + valorConcepto192);
    	System.out.println("Concepto 185: " + valorConcepto185);
    	System.out.println("Concepto 285: " + valorConcepto285);
    	System.out.println("Ganancia No Imponible: " + gananciaNoImponible);
    	System.out.println("Deducción Especial: " + deduccionEspecial);
    	System.out.println("Deducción por Esposa: " + deduccionPorEsposa);
    	System.out.println("Deducción por Hijo: " + deduccionPorHijo);
    	
    	EscalaGanancias escala = escalaGananciasRepository.findByDesdeLessThanEqualAndHastaGreaterThanEqualAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                (int) imponibleImpuestoGanancias, 
                (int) imponibleImpuestoGanancias, 
                mesLiquidacion, 	
                mesLiquidacion);
    	
    	Date fechaActual = new Date(System.currentTimeMillis());
    	
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaInicio = new Date(calendar.getTimeInMillis());
    	
    	if (escala != null) {
            // Restar el valor de 'desde' y multiplicar por el porcentaje
    		System.out.println("Imponible Impuesto Ganancias: " + imponibleImpuestoGanancias);
    		System.out.println("Escala Desde: " + escala.getDesde());

    		int diferencia = imponibleImpuestoGanancias - escala.getDesde();

    		// Mostrar el valor de la diferencia
    		System.out.println("Diferencia: " + diferencia);

    		int impuestoParcial = diferencia * escala.getPorcentaje() / 100;

    		// Mostrar el valor de impuestoParcial	
    		System.out.println("Impuesto Parcial: " + impuestoParcial);

    		int impuestoTotal = impuestoParcial + escala.getFijo();
    		int impMes = impuestoTotal - valorConcepto285;

    		// Mostrar el valor de escala.getFijo() y el resultado de impuestoTotal
    		System.out.println("Escala Fijo: " + escala.getFijo());
    		System.out.println("Impuesto Total: " + impuestoTotal);
            // Sumar el valor fijo al impuesto parcial
            
            
            ConceptoSalarial concepto = conceptoSalarialRepository.findById(230)
            	    .orElseThrow(() -> new IllegalArgumentException("Concepto Salarial no encontrado"));
            
            Empleado empleado = empleadoRepository.findById(empleadoId)
            	    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
            
            DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
            nuevoDetalle.setEmpleado(empleado);
            nuevoDetalle.setConceptoSalarial(concepto);
    		nuevoDetalle.setMonto(impMes);
            nuevoDetalle.setFechaLiquidacion(fechaActual);
            nuevoDetalle.setPeriodo(fechaInicio); // Establecer como primer día del mes actual
            
	            if (nuevoDetalle.getMonto() != 0){
	            	detalleLiquidacionRepository.save(nuevoDetalle);
	            }
            


        } 

    	return imponibleImpuestoGanancias;
    }
    
    public int calcularSueldoNeto(int empleadoId, Date mesLiquidacion) {
        // Obtener valores de conceptos 92 y 192 de detalleLiquidacion por empleado

    	Integer valorConcepto91 = detalleLiquidacionRepository.obtenerValorConcepto(empleadoId, 91);
    	valorConcepto91 = (valorConcepto91 != null) ? valorConcepto91 : 0;

    	Integer valorConcepto191 = detalleLiquidacionRepository.obtenerValorConcepto(empleadoId, 191);
    	valorConcepto191 = (valorConcepto191 != null) ? valorConcepto191 : 0;

    	Integer valorConcepto230 = detalleLiquidacionRepository.obtenerValorConcepto(empleadoId, 230);
    	valorConcepto230 = (valorConcepto230 != null) ? valorConcepto230 : 0;

    	  	


    	// Calcular el valor final del impuesto
    	int sueldoNeto = (valorConcepto91 - valorConcepto191 - valorConcepto230) ;   	

    	Date fechaActual = new Date(System.currentTimeMillis());
    	
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date fechaInicio = new Date(calendar.getTimeInMillis());
    	
    	ConceptoSalarial concepto = conceptoSalarialRepository.findById(491)
        	    .orElseThrow(() -> new IllegalArgumentException("Concepto Salarial no encontrado"));
        
        Empleado empleado = empleadoRepository.findById(empleadoId)
        	    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
    	
        DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
        nuevoDetalle.setEmpleado(empleado);
        nuevoDetalle.setConceptoSalarial(concepto);
		nuevoDetalle.setMonto(sueldoNeto);
        nuevoDetalle.setFechaLiquidacion(fechaActual);
        nuevoDetalle.setPeriodo(fechaInicio); // Establecer como primer día del mes actual
        
        if (nuevoDetalle.getMonto() != 0){
        	detalleLiquidacionRepository.save(nuevoDetalle);
        
        }
       

		return sueldoNeto;
}}
