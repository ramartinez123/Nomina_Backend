		package com.nomina.backend.service;
		import com.nomina.backend.model.DetalleLiquidacion;
		import com.nomina.backend.model.NovedadLiquidacion;
		import com.nomina.backend.repository.DetalleLiquidacionRepository;
		import com.nomina.backend.repository.NovedadLiquidacionRepository;
		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.stereotype.Service;
		import java.sql.Date;
		import java.util.Calendar;
		import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
		import java.util.Map;
import java.util.Set;
		
		@Service
		public class LiquidacionServiceNovedades {
	
		    @Autowired
		    private NovedadLiquidacionRepository novedadLiquidacionRepository;
		    
		    @Autowired
		    private DetalleLiquidacionRepository detalleLiquidacionRepository;
		    
		    @Autowired
		    private LiquidacionTotalesHaberesService liquidacionTotalesHaberesService;
	
		    public List<NovedadLiquidacion> obtenerNovedadesPorConceptosYFecha() {
		        List<Integer> conceptoIds = List.of(11,31,85);
		        
		        Date fechaActual = new Date(System.currentTimeMillis());
	
		        Calendar calendar = Calendar.getInstance();
		        calendar.set(Calendar.DAY_OF_MONTH, 1);
		        Date fechaInicio = new Date(calendar.getTimeInMillis());
	
		        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		        Date fechaFin = new Date(calendar.getTimeInMillis());
	
		        List<NovedadLiquidacion> novedades = novedadLiquidacionRepository.findByConceptoSalarialIdAndFecha(conceptoIds, fechaInicio, fechaFin);	        
		        System.out.println("Número de novedades encontradas: " + novedades.size());
		        

		        Map<Integer, Integer> sumaPorEmpleado = new HashMap<>();

		     // Set para almacenar los IDs de empleados procesados
		     Set<Integer> empleadosProcesados = new HashSet<>();

		     for (NovedadLiquidacion novedad : novedades) {	        
		         // Obtener los detalles de liquidación del empleado asociado a la novedad
		         List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByEmpleado_Id(novedad.getEmpleado().getId());	    
		        
		         // Verificar si el empleado ya ha sido procesado
		         int empleadoId = novedad.getEmpleado().getId();
		         if (!empleadosProcesados.contains(empleadoId)) {
		             // Iterar sobre cada detalle
		             for (DetalleLiquidacion detalle : detalles) {	                
		                 // Verificar si el concepto salarial está en la lista de conceptos de interés
		                 if (List.of(1, 2, 3, 4).contains(detalle.getConceptoSalarial().getId())) {
		                     // Acumular el monto en el mapa por empleado
		                     sumaPorEmpleado.merge(empleadoId, detalle.getMonto(), Integer::sum);                   
		                 }
		             }
		             // Marcar el empleado como procesado
		             empleadosProcesados.add(empleadoId);
		         }
		     }
		    	// Imprimir la suma por empleado
		    	System.out.println("Suma por empleado:");
		    	sumaPorEmpleado.forEach((empleadoId, suma) -> {
		    	    System.out.println("Empleado ID: " + empleadoId + ", Suma: " + suma);
		    	});

		        
		        // Obtener el primer día del mes actual
	
		        for (NovedadLiquidacion novedad : novedades) {
		            Integer empleadoId = novedad.getEmpleado().getId();
		            Integer cantidadNovedades = novedad.getCantidad();
		            Integer sumaTotal = sumaPorEmpleado.get(empleadoId);
		        	if (sumaTotal == null) {
		                System.out.println("No hay suma total para el empleado con ID: " + empleadoId);
		                continue; }
		           /* if (sumaTotal == null) {
		                System.out.println("No hay suma total para el empleado con ID: " + empleadoId);
		                continue;  // Saltar este empleado si no tiene suma total
		            }*/
	
		            // Verificar el concepto para aplicar la fórmula adecuada
			         if (novedad.getConcepto().getId() == 11) {
			         // Saltar este empleado si no tiene suma total
			                //int resultado = (int) Math.round((sumaTotal.doubleValue() / 30) * cantidadNovedades*-1);
			            int resultado = (sumaTotal / 30) * cantidadNovedades*-1;
			            
			            System.out.println("Procesando novedad para concepto ID 11:");
			            System.out.println("Empleado ID: " + novedad.getEmpleado().getId());
			            System.out.println("Suma Total: " + sumaTotal);
			            System.out.println("Cantidad Novedades: " + cantidadNovedades);
			            System.out.println("Resultado calculado: " + resultado);
			            
		                DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
		                nuevoDetalle.setEmpleado(novedad.getEmpleado());
		                nuevoDetalle.setConceptoSalarial(novedad.getConcepto());
		                nuevoDetalle.setMonto(resultado);
		                nuevoDetalle.setFechaLiquidacion(fechaActual);
		                nuevoDetalle.setPeriodo(fechaInicio); // Establecer como primer día del mes actual
		                detalleLiquidacionRepository.save(nuevoDetalle);
		 
		            }         
		               else if  (novedad.getConcepto().getId() == 31) {
		                //int resultado = (int) Math.round(((sumaTotal.doubleValue() / 25) * cantidadNovedades) - ((sumaTotal.doubleValue() / 30) * cantidadNovedades));
			        	int resultado = ((sumaTotal / 25 * cantidadNovedades) - (sumaTotal / 30 * cantidadNovedades));
		                System.out.println("Procesando novedad para concepto ID 31:");
		                System.out.println("Empleado ID: " + novedad.getEmpleado().getId());
		                System.out.println("Suma Total: " + sumaTotal);
		                System.out.println("Cantidad Novedades: " + cantidadNovedades);
		                System.out.println("Resultado calculado: " + resultado);             	                
		                
		                DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
		                nuevoDetalle.setEmpleado(novedad.getEmpleado());
		                nuevoDetalle.setConceptoSalarial(novedad.getConcepto());
		                nuevoDetalle.setMonto(resultado);
		                nuevoDetalle.setFechaLiquidacion(fechaActual);
		                nuevoDetalle.setPeriodo(fechaInicio); 
		                detalleLiquidacionRepository.save(nuevoDetalle);
		                
		            } 
			         
		               else if (novedad.getConcepto().getId() == 85) {
		                int resultado =  cantidadNovedades + sumaTotal -sumaTotal ;
		                System.out.println("Procesando novedad para concepto ID 85:");
		                System.out.println("Empleado ID: " + novedad.getEmpleado().getId());
		                System.out.println("Suma Total: " + sumaTotal);
		                System.out.println("Cantidad Novedades: " + cantidadNovedades);
		                System.out.println("Resultado calculado: " + resultado);       
		                
		                DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
		                nuevoDetalle.setEmpleado(novedad.getEmpleado());
		                nuevoDetalle.setConceptoSalarial(novedad.getConcepto());
		                nuevoDetalle.setMonto(resultado);
		                nuevoDetalle.setFechaLiquidacion(fechaActual);
		                nuevoDetalle.setPeriodo(fechaInicio); 
		                detalleLiquidacionRepository.save(nuevoDetalle);              
		               } else {
			                continue; // Si no es un concepto relevante, continuar  
		                
		            } 
		            
		            
		        }
	
	
		        sumaPorEmpleado.forEach((empleadoId, suma) -> {
	
		        });
		        
		        
		        liquidacionTotalesHaberesService.sumarConceptosYRegistrar();
		        return novedades;	
		    }
		    
		    
		}