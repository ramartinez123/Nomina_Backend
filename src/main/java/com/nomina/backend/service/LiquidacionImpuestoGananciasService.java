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
        // Obtener valores de conceptos 92, 192, 85, 185 y 285 de detalleLiquidacion por empleado
        Integer valorConcepto92 = obtenerValorConcepto(empleadoId, 92, mesLiquidacion);
        Integer valorConcepto192 = obtenerValorConcepto(empleadoId, 192, mesLiquidacion);
        Integer valorConcepto85 = obtenerValorConcepto(empleadoId, 85, mesLiquidacion);
        Integer valorConcepto185 = obtenerValorConcepto(empleadoId, 185, mesLiquidacion);
        Integer valorConcepto285 = obtenerValorConcepto(empleadoId, 285, mesLiquidacion);

        // Log de los valores obtenidos de los conceptos
        logger.info("Valor Concepto 92: {}", valorConcepto92);
        logger.info("Valor Concepto 192: {}", valorConcepto192);
        logger.info("Valor Concepto 85: {}", valorConcepto85);
        logger.info("Valor Concepto 185: {}", valorConcepto185);
        logger.info("Valor Concepto 285: {}", valorConcepto285);

        // Obtener cantidad de cónyuges y hijos que cumplen las condiciones
        Integer cantidadConyuges = familiarRepository.contarConyuges(empleadoId, mesLiquidacion);
        cantidadConyuges = (cantidadConyuges != null) ? cantidadConyuges : 0;

        Integer cantidadHijos = familiarRepository.contarHijos(empleadoId, mesLiquidacion);
        cantidadHijos = (cantidadHijos != null) ? cantidadHijos : 0;

        // Log de la cantidad de cónyuges e hijos
        logger.info("Cantidad de cónyuges: {}", cantidadConyuges);
        logger.info("Cantidad de hijos: {}", cantidadHijos);

        // Obtener deducciones
        Integer gananciaNoImponible = obtenerValorDeduccion(mesLiquidacion, 1);
        Integer deduccionEspecial = obtenerValorDeduccion(mesLiquidacion, 5);
        Integer deduccionPorEsposa = obtenerValorDeduccion(mesLiquidacion, 2) * cantidadConyuges;
        Integer deduccionPorHijo = obtenerValorDeduccion(mesLiquidacion, 3) * cantidadHijos;

        // Log de las deducciones
        logger.info("Ganancia No Imponible: {}", gananciaNoImponible);
        logger.info("Deducción Especial: {}", deduccionEspecial);
        logger.info("Deducción por Esposa: {}", deduccionPorEsposa);
        logger.info("Deducción por Hijo: {}", deduccionPorHijo);

        // Calcular el valor final del impuesto
        int imponibleImpuestoGanancias = (valorConcepto92 - valorConcepto192)
                - gananciaNoImponible
                - deduccionEspecial
                - deduccionPorEsposa
                - deduccionPorHijo;

        // Log de la base imponible
        logger.info("Base Imponible para el Impuesto de Ganancias: {}", imponibleImpuestoGanancias);

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

            crearYGuardarDetalleLiquidacion(empleado, concepto, impMes, fechaActual, obtenerFechaInicioDelMes());
            return impMes;

        } else {
            logger.warn("No se encontró escala para la base imponible de empleado ID: {}", empleadoId);
            return 0;
        }
    }

    private Integer obtenerValorConcepto(Integer empleadoId, Integer conceptoId, Date periodo) {
        // Registrar los parámetros de entrada para depuración
        logger.info("Consultando Concepto: {} para Empleado ID: {} y Periodo: {}", conceptoId, empleadoId, periodo);

        // Realizar la consulta en el repositorio para obtener el detalle de liquidación correspondiente
        List<DetalleLiquidacion> detalles = detalleLiquidacionRepository
                .findByEmpleadoIdAndConceptoSalarialIdAndPeriodo(empleadoId, conceptoId, periodo);

        // Verificar si se encuentran detalles y registrar el resultado
        if (!detalles.isEmpty()) {
            Integer monto = detalles.get(0).getMonto();
            logger.info("Se encontró monto para el Concepto {}: {}", conceptoId, monto);
            return monto;
        } else {
            logger.warn("No se encontró el concepto {} para el Empleado ID: {} en el Periodo: {}", conceptoId, empleadoId, periodo);
            return 0;  // Si no se encuentra ningún detalle, retornar 0
        }
    }

    private Integer obtenerValorDeduccion(Date fecha, int conceptoId) {
    	logger.info("Llamando a obtenerDeduccion con fecha: {} y conceptoId: {}", fecha, conceptoId);
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

    public int calcularSueldoNeto(int empleadoId, Date mesLiquidacion) {
        // Obtener valores de conceptos 91, 191 y 230 de detalleLiquidacion por empleado
        Integer valorConcepto91 = obtenerValorConcepto(empleadoId, 91, mesLiquidacion);
        Integer valorConcepto191 = obtenerValorConcepto(empleadoId, 191, mesLiquidacion);
        Integer valorConcepto230 = obtenerValorConcepto(empleadoId, 230, mesLiquidacion);

        // Calcular el valor final del sueldo neto
        int sueldoNeto = (valorConcepto91 - valorConcepto191 - valorConcepto230);

        // Guardar el detalle de liquidación para el sueldo neto calculado
        Date fechaInicio = obtenerFechaInicioDelMes();
        ConceptoSalarial concepto = conceptoSalarialRepository.findById(491)
                .orElseThrow(() -> new IllegalArgumentException("Concepto Salarial no encontrado"));

        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

        crearYGuardarDetalleLiquidacion(empleado, concepto, sueldoNeto, fechaActual, fechaInicio);

        return sueldoNeto;
    }
}