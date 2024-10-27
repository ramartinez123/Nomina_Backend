package com.nomina.backend.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(LiquidacionImpuestoGananciasService.class); // Crear el logger

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

	Calendar calendar = Calendar.getInstance();
	Date fechaActual = new Date(System.currentTimeMillis());
		
	
	public Map<Integer, Integer> calcularImpuestoGananciasTodos(Date mesLiquidacion) {
		List<Empleado> empleados = empleadoRepository.findAll(); 
		Map<Integer, Integer> resultados = new HashMap<>();

		for (Empleado empleado : empleados) {
			int empleadoId = empleado.getId(); 
			int impuesto = calcularImpuestoGanancias(empleadoId, mesLiquidacion);
			resultados.put(empleadoId, impuesto); 
			calcularSueldoNeto(empleadoId, mesLiquidacion);
		}

		for (Map.Entry<Integer, Integer> entry : resultados.entrySet()) {
			logger.info("Empleado ID: {}, Impuesto Ganancias: {}", entry.getKey(), entry.getValue());
		}

		return resultados; 
	}	

	
	public int calcularImpuestoGanancias(int empleadoId, Date mesLiquidacion) {
		// Obtener valores de conceptos 92 y 192 de detalleLiquidacion por empleado

		Integer valorConcepto92 = obtenerValorConcepto(empleadoId, 92);
		Integer valorConcepto192 = obtenerValorConcepto(empleadoId, 192);
		Integer valorConcepto85 = obtenerValorConcepto(empleadoId, 85);
		Integer valorConcepto185 = obtenerValorConcepto(empleadoId, 185);
		Integer valorConcepto285 = obtenerValorConcepto(empleadoId, 285);
		
		// Obtener cantidad de cónyuges y hijos que cumplen las condiciones
		Integer cantidadConyuges = familiarRepository.contarConyuges(empleadoId, mesLiquidacion);
		cantidadConyuges = (cantidadConyuges != null) ? cantidadConyuges : 0;

		Integer cantidadHijos = familiarRepository.contarHijos(empleadoId, mesLiquidacion);
		cantidadHijos = (cantidadHijos != null) ? cantidadHijos : 0;
		
		Integer gananciaNoImponible = obtenerValorDeduccion(mesLiquidacion, 1);
		Integer deduccionEspecial = obtenerValorDeduccion(mesLiquidacion, 5);
		Integer deduccionPorEsposa = obtenerValorDeduccion(mesLiquidacion, 2) * cantidadConyuges;	
		Integer deduccionPorHijo = obtenerValorDeduccion(mesLiquidacion, 3) * cantidadHijos;

		// Calcular el valor final del impuesto
		int imponibleImpuestoGanancias = (valorConcepto92  - valorConcepto192 ) 
				- gananciaNoImponible 
				- deduccionEspecial 
				- deduccionPorEsposa
				- deduccionPorHijo;
		
		if (imponibleImpuestoGanancias <= 0) {
	        logger.info("La base imponible es negativa o cero para empleado ID: {}. Impuesto de ganancias no aplicable.", empleadoId);
	        return 0; // Si la base imponible es negativa o cero, no se calcula impuesto
	    }


		logger.info("Calculando impuesto para empleado ID: {}", empleadoId);
		logger.info("Base Imponible: {}", imponibleImpuestoGanancias);     
		logger.info("Valores obtenidos para el cálculo:");
		logger.info("Concepto 92: {}", valorConcepto92);
		logger.info("Concepto 85: {}", valorConcepto85);
		logger.info("Concepto 192: {}", valorConcepto192);
		logger.info("Concepto 185: {}", valorConcepto185);
		logger.info("Concepto 285: {}", valorConcepto285);
		logger.info("Ganancia No Imponible: {}", gananciaNoImponible);
		logger.info("Deducción Especial: {}", deduccionEspecial);
		logger.info("Deducción por Esposa: {}", deduccionPorEsposa);
		logger.info("Deducción por Hijo: {}", deduccionPorHijo);

		EscalaGanancias escala = escalaGananciasRepository.findByDesdeLessThanEqualAndHastaGreaterThanEqualAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
				(int) imponibleImpuestoGanancias, 
				(int) imponibleImpuestoGanancias, 
				mesLiquidacion, 	
				mesLiquidacion);

		//calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date fechaInicio = obtenerFechaInicioDelMes();

		if (escala != null) {
	        // Calcular el impuesto según la escala
	        int diferencia = imponibleImpuestoGanancias - escala.getDesde();
	        int impuestoParcial = (diferencia * escala.getPorcentaje()) / 100;
	        int impuestoTotal = impuestoParcial + escala.getFijo();
	        int impMes = impuestoTotal - valorConcepto285;

	        logger.info("Impuesto Total para empleado ID {}: {}", empleadoId, impMes);

	        // Guardar el detalle de liquidación para el impuesto calculado
	        ConceptoSalarial concepto = conceptoSalarialRepository.findById(230)
	                .orElseThrow(() -> new IllegalArgumentException("Concepto Salarial no encontrado"));
	        Empleado empleado = empleadoRepository.findById(empleadoId)
	                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

	        crearYGuardarDetalleLiquidacion(empleado, concepto, impMes, fechaActual, fechaInicio);
	        return impMes;
     		
		} else {
	        logger.warn("No se encontró escala para la base imponible de empleado ID: {}", empleadoId);
	        return 0;
	    }

	}
	

	private Integer obtenerValorConcepto(int empleadoId, int conceptoId) {
	    Integer valor = detalleLiquidacionRepository.obtenerValorConcepto(empleadoId, conceptoId);
	    return (valor != null) ? valor : 0;
	}
	
	
	private Integer obtenerValorDeduccion(Date fecha, int conceptoId) {
	    Integer valor = deduccionImpuestoGananciasRepository.obtenerDeduccion(fecha, conceptoId);
	    return (valor != null) ? valor : 0;
	}	
	
	
	private Date obtenerFechaInicioDelMes() {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new Date(calendar.getTimeInMillis());
    }
	
	
	private void crearYGuardarDetalleLiquidacion(Empleado empleado, ConceptoSalarial concepto, int monto, Date fechaLiquidacion, Date periodo) {
	    DetalleLiquidacion detalle = new DetalleLiquidacion();
	    detalle.setEmpleado(empleado);
	    detalle.setConceptoSalarial(concepto);
	    detalle.setMonto(monto);
	    detalle.setFechaLiquidacion(fechaLiquidacion);
	    detalle.setPeriodo(periodo);

	    if (detalle.getMonto() != 0) {
	        detalleLiquidacionRepository.save(detalle);
	    }
	}
	
	
	int calcularSueldoNeto(int empleadoId, Date mesLiquidacion) {
		// Obtener valores de conceptos 92 y 192 de detalleLiquidacion por empleado

		Integer valorConcepto91 = obtenerValorConcepto(empleadoId, 91);
		Integer valorConcepto191 = obtenerValorConcepto(empleadoId, 191);
		Integer valorConcepto230 = obtenerValorConcepto(empleadoId, 230);
		
	    // Calcular el valor final del impuesto
		int sueldoNeto = (valorConcepto91 - valorConcepto191 - valorConcepto230) ;   	
		
		Date fechaInicio = obtenerFechaInicioDelMes();

		ConceptoSalarial concepto = conceptoSalarialRepository.findById(491)
				.orElseThrow(() -> new IllegalArgumentException("Concepto Salarial no encontrado"));

		Empleado empleado = empleadoRepository.findById(empleadoId)
				.orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

		crearYGuardarDetalleLiquidacion(empleado, concepto, sueldoNeto, fechaActual, fechaInicio);

		return sueldoNeto;
	}
}
