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
		import java.util.List;
		import java.util.Map;
		
		@Service
		public class LiquidacionServiceNovedades {
	
		    @Autowired
		    private NovedadLiquidacionRepository novedadLiquidacionRepository;
		    
		    @Autowired
		    private DetalleLiquidacionRepository detalleLiquidacionRepository;
		    
		    @Autowired
		    private LiquidacionTotalesHaberesService liquidacionTotalesHaberesService;
	
		    public List<NovedadLiquidacion> obtenerNovedadesPorConceptosYFecha() {
		        List<Integer> conceptoIds = List.of(11, 31);
		        
		        Date fechaActual = new Date(System.currentTimeMillis());
	
		        Calendar calendar = Calendar.getInstance();
		        calendar.set(Calendar.DAY_OF_MONTH, 1);
		        Date fechaInicio = new Date(calendar.getTimeInMillis());
	
		        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		        Date fechaFin = new Date(calendar.getTimeInMillis());
	
		        List<NovedadLiquidacion> novedades = novedadLiquidacionRepository.findByConceptoSalarialIdAndFecha(conceptoIds, fechaInicio, fechaFin);
		        
		        System.out.println("Número de novedades encontradas: " + novedades.size());
	
		        Map<Integer, Integer> sumaPorEmpleado = new HashMap<>();
	
		        for (NovedadLiquidacion novedad : novedades) {
		            
		            List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByEmpleado_Id(novedad.getEmpleado().getId());
		    
	
		            for (DetalleLiquidacion detalle : detalles) {
		                
		                if (List.of(2, 3, 4, 5).contains(detalle.getConceptoSalarial().getId())) {
		                    sumaPorEmpleado.merge(novedad.getEmpleado().getId(), detalle.getMonto(), Integer::sum);
		                   
		                }
		            }
		        }
		        
		        // Obtener el primer día del mes actual
	
	
	
		        for (NovedadLiquidacion novedad : novedades) {
		            Integer empleadoId = novedad.getEmpleado().getId();
		            Integer cantidadNovedades = novedad.getCantidad();
		            Integer sumaTotal = sumaPorEmpleado.get(empleadoId);
	
		            // Verificar el concepto para aplicar la fórmula adecuada
		            if (novedad.getConcepto().getId() == 11) {
		                int resultado = (int) Math.round((sumaTotal.doubleValue() / 30) * cantidadNovedades*-1);
		
		                DetalleLiquidacion nuevoDetalle = new DetalleLiquidacion();
		                nuevoDetalle.setEmpleado(novedad.getEmpleado());
		                nuevoDetalle.setConceptoSalarial(novedad.getConcepto());
		                nuevoDetalle.setMonto(resultado);
		                nuevoDetalle.setFechaLiquidacion(fechaActual);
		                nuevoDetalle.setPeriodo(fechaInicio); // Establecer como primer día del mes actual
	
		                detalleLiquidacionRepository.save(nuevoDetalle);
		 
		            } else if (novedad.getConcepto().getId() == 31) {
		                int resultado = (int) Math.round(((sumaTotal.doubleValue() / 25) * cantidadNovedades) - ((sumaTotal.doubleValue() / 30) * cantidadNovedades));
		                
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