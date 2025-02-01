package com.nomina.backend.service;

import com.nomina.backend.dto.ConceptoDTO;
import com.nomina.backend.dto.EmpleadoDetalleDTO;
import com.nomina.backend.dto.EmpleadoDTO2;
import com.nomina.backend.dto.LiquidacionRespuestaDTO;
import com.nomina.backend.model.DetalleLiquidacion;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.repository.DetalleLiquidacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReciboLiquidacionService {

    @Autowired
    private DetalleLiquidacionRepository detalleLiquidacionRepository;

    public LiquidacionRespuestaDTO obtenerLiquidacionesPorPeriodo(int mes, int anio) {
        // Configurar fechas de inicio y fin del período
        Calendar inicio = Calendar.getInstance();
        inicio.set(anio, mes - 1, 1);
        Date fechaInicio = new Date(inicio.getTimeInMillis());

        Calendar fin = Calendar.getInstance();
        fin.set(anio, mes - 1, inicio.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date fechaFin = new Date(fin.getTimeInMillis());

        // Obtener detalles por periodo
        List<DetalleLiquidacion> detalles = detalleLiquidacionRepository.findByPeriodoBetween(fechaInicio, fechaFin);

        // Filtrar los conceptos con IDs específicos
        Set<Integer> conceptosExcluir = new HashSet<>(Arrays.asList(85, 92, 93, 94, 95, 185, 192, 285));
        List<DetalleLiquidacion> detallesFiltrados = detalles.stream()
                .filter(detalle -> !conceptosExcluir.contains(detalle.getConceptoSalarial().getId()))
                .collect(Collectors.toList());

        // Agrupar por empleado y mapear a DTOs
        Map<Empleado, List<DetalleLiquidacion>> detallesPorEmpleado = detallesFiltrados.stream()
                .collect(Collectors.groupingBy(DetalleLiquidacion::getEmpleado));

        List<EmpleadoDetalleDTO> detallesDTO = detallesPorEmpleado.entrySet().stream()
                .map(entry -> {
                    Empleado empleado = entry.getKey();
                    List<ConceptoDTO> conceptos = entry.getValue().stream()
                            .map(detalle -> new ConceptoDTO(
                                    detalle.getConceptoSalarial().getId(),
                                    detalle.getConceptoSalarial().getNombre(),
                                    detalle.getMonto()
                            ))
                            .collect(Collectors.toList());

                    int total = conceptos.stream().mapToInt(ConceptoDTO::getMonto).sum();

                    return new EmpleadoDetalleDTO(
                            new EmpleadoDTO2(empleado.getId(), empleado.getApellido() + " " + empleado.getNombre()),
                            conceptos,
                            total
                    );
                })
                .collect(Collectors.toList());

        String periodo = anio + "-" + (mes < 10 ? "0" + mes : mes);
        return new LiquidacionRespuestaDTO(periodo, detallesDTO);
    }
}