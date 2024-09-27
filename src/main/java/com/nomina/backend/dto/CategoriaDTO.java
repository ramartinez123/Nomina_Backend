package com.nomina.backend.dto;

public class CategoriaDTO {
		private Integer idCategoria;
		private Integer idConvenio;
	    private String nombre;
	    private String descripcion;

	    // Constructor
	    public CategoriaDTO(Integer idCategoria, Integer idConvenio, String nombre, String descripcion) {
	        this.idCategoria = idCategoria;
	        this.idConvenio = idConvenio;
	        this.nombre = nombre;
	        this.descripcion = descripcion;
}
	   

		// Getters
	    public Integer getIdCategoria() {
	        return idCategoria;
	    }

	    public String getNombre() {
	        return nombre;
	    }

	    public String getDescripcion() {
	        return descripcion;
	    }

	    // Setters
	    public void setIdCategoria(Integer idCategoria) {
	        this.idCategoria = idCategoria;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public void setDescripcion(String descripcion) {
	        this.descripcion = descripcion;
	    }

		public Integer getIdConvenio() {
			return idConvenio;
		}

		public void setIdConvenio(Integer idConvenio) {
			this.idConvenio = idConvenio;
		}
}