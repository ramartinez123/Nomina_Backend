package com.nomina.backend.dto;

import java.util.List;

public class EmpleadoDetalleDTO {
    private EmpleadoDTO2 empleado; // Mantener el tipo correcto de EmpleadoDTO2
    private List<ConceptoDTO> conceptos;
    private int total;

    public EmpleadoDetalleDTO(EmpleadoDTO2 empleado, List<ConceptoDTO> conceptos, int total) {
        this.setEmpleado(empleado);
        this.setConceptos(conceptos);
        this.setTotal(total);
    }

    public EmpleadoDTO2 getEmpleado() { // Método getter debe devolver EmpleadoDTO2
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO2 empleado) { // Método setter debe recibir EmpleadoDTO2
        this.empleado = empleado;
    }

    public List<ConceptoDTO> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<ConceptoDTO> conceptos) {
        this.conceptos = conceptos;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}