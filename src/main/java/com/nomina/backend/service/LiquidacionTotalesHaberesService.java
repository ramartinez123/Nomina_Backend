	package com.nomina.backend.service;
	
	import java.sql.Date;
	import java.util.Calendar;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	import java.util.stream.Collectors;
	
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;
	
	import com.nomina.backend.model.ConceptoSalarial;
	import com.nomina.backend.model.DetalleLiquidacion;
	import com.nomina.backend.model.Empleado;
	import com.nomina.backend.repository.ConceptoSalarialRepository;
	import com.nomina.backend.repository.DetalleLiquidacionRepository;
	import com.nomina.backend.repository.EmpleadoRepository;
	
	@Service
	public class LiquidacionTotalesHaberesService {

	    @Autowired
	    private DetalleLiquidacionRepository detalleLiquidacionRepository;

	    @Autowired
	    private EmpleadoRepository empleadoRepository;

	    @Autowired
	    private ConceptoSalarialRepository conceptoSalarialRepository;
	    
	    @Autowired
	    private LiquidacionRetencionesService liquidacionRetencionesService;

	    public void sumarConceptosYRegistrar() {
	        // Obtener la fecha actual
	        Date fechaActual = new Date(System.currentTimeMillis());

	        // Obtener la fecha de inicio del mes
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(Calendar.DAY_OF_MONTH, 1);
	        Date fechaInicio = new Date(calendar.getTimeInMillis());

	        // Obtener todos los detalles de liquidación
	        List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findAll();
	        System.out.println("Total de Detalles de Liquidación: " + detalles.size());

	        // Filtrar los detalles para los conceptos del 1 al 89
	        List<DetalleLiquidacion> detallesFiltrados = detalles.stream()
	            .filter(detalle -> detalle.getConceptoSalarial().getId() >= 1 && detalle.getConceptoSalarial().getId() <= 89)
	            .collect(Collectors.toList());
	        System.out.println("Detalles Filtrados (Conceptos 1 a 89): " + detallesFiltrados.size());

	        // Mapas para acumular montos por empleado
	        Map<Integer, Integer> sumaImpAportesPorEmpleado = new HashMap<>();
	        Map<Integer, Integer> sumaImpGananciasPorEmpleado = new HashMap<>();
	        Map<Integer, Integer> sumaImpIndemnizacionPorEmpleado = new HashMap<>();
	        Map<Integer, Integer> sumaSueldoMensualPorEmpleado = new HashMap<>();
	        Map<Integer, Integer> sumaImpAguianldoPorEmpleado = new HashMap<>();

	        // Iterar sobre los detalles
	        for (DetalleLiquidacion detalle : detallesFiltrados) {
	            Integer empleadoId = detalle.getEmpleado().getId();
	            Integer monto = detalle.getMonto();

	            // Verificar si es ImpAportes o ImpGanancias y acumular el monto en el mapa correspondiente
	            if (detalle.getConceptoSalarial().getImpAportes()) {
	                sumaImpAportesPorEmpleado.merge(empleadoId, monto, Integer::sum);
	                
	            }

	            if (detalle.getConceptoSalarial().getImpGanancias()) {
	                sumaImpGananciasPorEmpleado.merge(empleadoId, monto, Integer::sum);
	                
	            }

	            if (detalle.getConceptoSalarial().getImpIndemnizacion()) {
	            	sumaImpIndemnizacionPorEmpleado.merge(empleadoId, monto, Integer::sum);
	               
	            }
	            
	            if (detalle.getConceptoSalarial().getSueldoTotal()) {
	            	sumaSueldoMensualPorEmpleado.merge(empleadoId, monto, Integer::sum);
	               
	            }
	            
	            if (detalle.getConceptoSalarial().getImpSac()) {
	            	sumaImpAguianldoPorEmpleado.merge(empleadoId, monto, Integer::sum);
	                
	            }

	        }

	        // Registrar los totales por empleado
	        registrarTotalesPorConcepto(sumaImpAportesPorEmpleado, fechaActual, fechaInicio, 91); // true para ImpAportes
	        registrarTotalesPorConcepto(sumaImpGananciasPorEmpleado, fechaActual, fechaInicio, 92); // false para ImpGanancias
	        registrarTotalesPorConcepto(sumaImpIndemnizacionPorEmpleado, fechaActual, fechaInicio, 93);
	        registrarTotalesPorConcepto(sumaSueldoMensualPorEmpleado, fechaActual, fechaInicio, 94);
	        registrarTotalesPorConcepto(sumaImpAguianldoPorEmpleado, fechaActual, fechaInicio, 95);
	        
	        liquidacionRetencionesService.procesarYRegistrarNuevosDetalles();

	    }

	    private void registrarTotalesPorConcepto(Map<Integer, Integer> sumaPorEmpleado, Date fechaActual, Date fechaInicio, int conceptoId) {
	        for (Map.Entry<Integer, Integer> entry : sumaPorEmpleado.entrySet()) {
	            Integer empleadoId = entry.getKey();
	            Integer sumaTotal = entry.getValue();

	            // Busca el empleado existente en lugar de crear uno nuevo
	            Empleado empleado = empleadoRepository.findById(empleadoId)
	                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

	            // Crea un concepto salarial dependiendo de si es Aporte o Ganancias
	            ConceptoSalarial concepto = conceptoSalarialRepository.findById(conceptoId)
	                    .orElseThrow(() -> new RuntimeException("ConceptoSalarial no encontrado"));
	            
	            // Crear y guardar el nuevo registro de DetalleLiquidacion
	            DetalleLiquidacion nuevoRegistro = new DetalleLiquidacion();
	            nuevoRegistro.setEmpleado(empleado); // Usa el empleado existente
	            nuevoRegistro.setMonto(sumaTotal);
	            nuevoRegistro.setConceptoSalarial(concepto); // Usa el concepto existente
	            nuevoRegistro.setPeriodo(fechaInicio);
	            nuevoRegistro.setFechaLiquidacion(fechaActual);

	            // Guardar el registro en la base de datos
	            detalleLiquidacionRepository.save(nuevoRegistro);
	        }
	        
	        
	      
	    }
	    
	    
	}