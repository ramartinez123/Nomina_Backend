package com.nomina.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonView;
import com.nomina.backend.Iservice.IcategoriaService;
import com.nomina.backend.Iservice.IconvenioService;
import com.nomina.backend.Iservice.IempleadoService;
import com.nomina.backend.Iservice.IprovinciaService;
import com.nomina.backend.Iservice.IpuestoService;
import com.nomina.backend.Iservice.IdepartamentoService;
import com.nomina.backend.Iservice.IobraSocialService;
import com.nomina.backend.Iservice.IbancoService;
import com.nomina.backend.dto.EmpleadoDTO;
import com.nomina.backend.dto.EmpleadoViews;
import com.nomina.backend.model.Banco;
import com.nomina.backend.model.Convenio;
import com.nomina.backend.model.Empleado;
import com.nomina.backend.model.EstadoCivil;
import com.nomina.backend.model.EstadoEmpleado;
import com.nomina.backend.model.Genero;
import com.nomina.backend.model.ObraSocial;
import com.nomina.backend.model.Provincia;
import com.nomina.backend.model.Puesto;
import com.nomina.backend.model.TipoContrato;
import com.nomina.backend.model.TipoCuentaBancaria;
import com.nomina.backend.model.Departamento;
import com.nomina.backend.model.Categoria;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

	@Autowired
	private IempleadoService empleadoService;

	@Autowired
	private IcategoriaService categoriaService;

	@Autowired
	private IconvenioService convenioService;

	@Autowired
	private IprovinciaService provinciaService;

	@Autowired
	private IpuestoService puestoService;

	@Autowired
	private IdepartamentoService departamentoService;

	@Autowired
	private IbancoService bancoService;

	@Autowired
	private IobraSocialService obraSocialService;




	// Obtener todos los empleados
	@GetMapping
	@JsonView(EmpleadoViews.Basica.class)
	public ResponseEntity<?> getAllEmpleados() {
	    try {
	        List<Empleado> empleados = empleadoService.listEmpleado();

	        if (empleados.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // No hay empleados
	        }

	        List<EmpleadoDTO> empleadoDTOs = empleados.stream()
	                .map(this::convertToEmpleadoDTO) // Uso de un método auxiliar para convertir
	                .collect(Collectors.toList());

	        return new ResponseEntity<>(empleadoDTOs, HttpStatus.OK);
	    } catch (Exception e) {
	        return handleException(e); // Manejo centralizado de excepciones
	    }
	}

	private EmpleadoDTO convertToEmpleadoDTO(Empleado empleado) {
	    return new EmpleadoDTO(
	            empleado.getId(),
	            empleado.getNombre(),
	            empleado.getApellido(),
	            empleado.getDireccion(),
	            empleado.getCiudad(),
	            empleado.getProvincia() != null ? empleado.getProvincia().getIdProvincia() : null,
	            empleado.getDni(),
	            empleado.getEmail(),
	            empleado.getFechaInicio(),
	            empleado.getDepartamento() != null ? empleado.getDepartamento().getNombre() : null,
	            empleado.getPuesto() != null ? empleado.getPuesto().getIdPuesto() : null,
	            empleado.getCuil()
	    );
	}
	// Obtener un empleado por ID
	@GetMapping("/{id}")
	@JsonView(EmpleadoViews.Basica.class)
	public ResponseEntity<?> getEmpleadoById(@PathVariable int id) {
	    try {
	        Optional<Empleado> empleadoOpt = empleadoService.findById(id);

	        if (empleadoOpt.isPresent()) {
	            Empleado empleado = empleadoOpt.get();
	            EmpleadoDTO empleadoDTO = convertToEmpleadoDTO2(empleado);
	            return ResponseEntity.ok(empleadoDTO);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        return handleException(e); // Manejo centralizado de excepciones
	    }
	}

	private EmpleadoDTO convertToEmpleadoDTO2(Empleado empleado) {
	    return new EmpleadoDTO(
	            empleado.getId(),
	            empleado.getNombre(),
	            empleado.getApellido(),
	            empleado.getDepartamento() != null ? empleado.getDepartamento().getIdDepartamento() : null,
	            empleado.getPuesto() != null ? empleado.getPuesto().getIdPuesto() : null,
	            empleado.getCuil()
	    );
	}

	@PostMapping
	public ResponseEntity<?> createEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
		try {

			// Validar que el convenio existe
			Optional<Convenio> convenioOpt = convenioService.findById(empleadoDTO.getIdConvenio());
			if (!convenioOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Convenio no encontrado.");
			}

			Optional<Departamento> departamentoOpt = departamentoService.findById(empleadoDTO.getIdDepartamento());
			if (!departamentoOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Departamento no encontrado.");
			}

			Optional<Categoria> categoriaOpt = categoriaService.findById(empleadoDTO.getIdCategoria());
			if (!categoriaOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Categoria no encontrada.");
			}

			// Validar que la provincia existe
			Optional<Provincia> provinciaOpt = provinciaService.findById(empleadoDTO.getIdProvincia());
			if (!provinciaOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Provincia no encontrada.");
			}

			// Validar que el puesto existe
			Optional<Puesto> puestoOpt = puestoService.findById(empleadoDTO.getIdPuesto());
			if (!puestoOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Puesto no encontrado.");
			}


			Optional<Banco> bancoOpt = bancoService.findById(empleadoDTO.getIdBanco());
			if (!bancoOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Banco no encontrado.");
			}

			Optional<ObraSocial> obraSocialOpt = obraSocialService.findById(empleadoDTO.getIdObraSocial());
			if (!obraSocialOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Obra Social no encontrado.");
			}


			String estadoEmpleadoStr = empleadoDTO.getEstadoEmpleado();

			// Convertir el String a enum estdo
			EstadoEmpleado estadoEmpleado;
			try {
				estadoEmpleado = EstadoEmpleado.valueOf(estadoEmpleadoStr.toUpperCase()); // Asegúrate de que coincida con el nombre del enum
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Estado de empleado no válido.");
			}

			String estadoCivilStr = empleadoDTO. getEstadoCivil();

			// Convertir el String a enum estdo
			EstadoCivil estadoCivil;
			try {
				estadoCivil = EstadoCivil.valueOf(estadoCivilStr.toUpperCase()); // Asegúrate de que coincida con el nombre del enum
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Estado civil no válido.");
			}

			String generoStr = empleadoDTO. getGenero();

			// Convertir el String a enum estdo
			Genero genero;
			try {
				genero = Genero.valueOf(generoStr.toUpperCase()); // Asegúrate de que coincida con el nombre del enum
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Genero no válido.");
			}

			String tipoContratoStr = empleadoDTO. getTipoContrato();

			// Convertir el String a enum estdo
			TipoContrato tipoContrato;
			try {
				tipoContrato = TipoContrato.valueOf(tipoContratoStr.toUpperCase()); // Asegúrate de que coincida con el nombre del enum
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Tipo de Contrato no válido.");
			}

			String tipoCuentaBancariaStr = empleadoDTO. getTipoCuentaBancaria();

			TipoCuentaBancaria tipoCuentaBancaria;
			try {
				tipoCuentaBancaria = TipoCuentaBancaria.valueOf(tipoCuentaBancariaStr.toUpperCase()); // Asegúrate de que coincida con el nombre del enum
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Tipo de Contrato no válido.");
			}

			// Crear la entidad Empleado
			Empleado empleado = new Empleado();
			empleado.setNombre(empleadoDTO.getNombre());
			empleado.setApellido(empleadoDTO.getApellido());
			empleado.setDni(empleadoDTO.getDni());
			empleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
			empleado.setEmail(empleadoDTO.getEmail());
			empleado.setTelefono(empleadoDTO.getTelefono());
			empleado.setDireccion(empleadoDTO.getDireccion());
			empleado.setCiudad(empleadoDTO.getCiudad());
			empleado.setProvincia(provinciaOpt.get());
			empleado.setDepartamento(departamentoOpt.get());
			empleado.setConvenio(convenioOpt.get());
			empleado.setCategoria(categoriaOpt.get());
			empleado.setPuesto(puestoOpt.get());
			empleado.setFechaInicio(empleadoDTO.getFechaInicio());
			empleado.setFechaFin(empleadoDTO.getFechaFin());
			empleado.setDiasVacacionesPactadas(empleadoDTO.getDiasVacacionesPactadas());
			empleado.setEstadoEmpleado(estadoEmpleado);
			empleado.setNacionalidad(empleadoDTO.getNacionalidad());
			empleado.setEstadoCivil(estadoCivil);
			empleado.setGenero(genero);
			empleado.setObraSocial(obraSocialOpt.get());
			empleado.setCbu(empleadoDTO.getCbu());
			empleado.setBanco(bancoOpt.get());
			empleado.setTipoCuentaBancaria(tipoCuentaBancaria);
			empleado.setTipoContrato(tipoContrato);
			empleado.setCuil(empleadoDTO.getCuil());

			// Guarda el empleado y obtiene el ID
			Integer savedId = empleadoService.saveEmpleado(empleado);
			if (savedId == -1) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Error al guardar el empleado.");
			}

			// Establecer el ID guardado en el DTO
			empleadoDTO.setId(savedId);
			return ResponseEntity.status(HttpStatus.CREATED).body(empleadoDTO);

		} catch (Exception e) {
			return handleException(e);
		}
	}
	// Actualizar un empleado existente
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEmpleado(@PathVariable int id, @RequestBody EmpleadoDTO empleadoDTO) {
		try {
			// Validar que el empleado existe
			Optional<Empleado> existingEmpleadoOpt = empleadoService.findById(id);
			if (!existingEmpleadoOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body("Empleado no encontrado.");
			}

			// Validar que el convenio existe
			Optional<Convenio> convenioOpt = convenioService.findById(empleadoDTO.getIdConvenio());
			if (!convenioOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Convenio no encontrado.");
			}

			Optional<Departamento> departamentoOpt = departamentoService.findById(empleadoDTO.getIdDepartamento());
			if (!departamentoOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Departamento no encontrado.");
			}

			Optional<Categoria> categoriaOpt = categoriaService.findById(empleadoDTO.getIdCategoria());
			if (!categoriaOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Categoría no encontrada.");
			}

			// Validar que la provincia existe
			Optional<Provincia> provinciaOpt = provinciaService.findById(empleadoDTO.getIdProvincia());
			if (!provinciaOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Provincia no encontrada.");
			}

			// Validar que el puesto existe
			Optional<Puesto> puestoOpt = puestoService.findById(empleadoDTO.getIdPuesto());
			if (!puestoOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Puesto no encontrado.");
			}

			Optional<Banco> bancoOpt = bancoService.findById(empleadoDTO.getIdBanco());
			if (!bancoOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Banco no encontrado.");
			}

			Optional<ObraSocial> obraSocialOpt = obraSocialService.findById(empleadoDTO.getIdObraSocial());
			if (!obraSocialOpt.isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Obra Social no encontrada.");
			}

			// Obtener el empleado existente
			Empleado existingEmpleado = existingEmpleadoOpt.get();

			// Convertir el String a enum estado
			EstadoEmpleado estadoEmpleado;
			try {
				estadoEmpleado = EstadoEmpleado.valueOf(empleadoDTO.getEstadoEmpleado().toUpperCase());
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Estado de empleado no válido.");
			}

			EstadoCivil estadoCivil;
			try {
				estadoCivil = EstadoCivil.valueOf(empleadoDTO.getEstadoCivil().toUpperCase());
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Estado civil no válido.");
			}

			Genero genero;
			try {
				genero = Genero.valueOf(empleadoDTO.getGenero().toUpperCase());
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Género no válido.");
			}

			TipoContrato tipoContrato;
			try {
				tipoContrato = TipoContrato.valueOf(empleadoDTO.getTipoContrato().toUpperCase());
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Tipo de Contrato no válido.");
			}

			TipoCuentaBancaria tipoCuentaBancaria;
			try {
				tipoCuentaBancaria = TipoCuentaBancaria.valueOf(empleadoDTO.getTipoCuentaBancaria().toUpperCase());
			} catch (IllegalArgumentException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Tipo de Cuenta Bancaria no válido.");
			}

			// Actualizar los campos del empleado existente
			existingEmpleado.setNombre(empleadoDTO.getNombre());
			existingEmpleado.setApellido(empleadoDTO.getApellido());
			existingEmpleado.setDni(empleadoDTO.getDni());
			existingEmpleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
			existingEmpleado.setEmail(empleadoDTO.getEmail());
			existingEmpleado.setTelefono(empleadoDTO.getTelefono());
			existingEmpleado.setDireccion(empleadoDTO.getDireccion());
			existingEmpleado.setCiudad(empleadoDTO.getCiudad());
			existingEmpleado.setProvincia(provinciaOpt.get());
			existingEmpleado.setDepartamento(departamentoOpt.get());
			existingEmpleado.setConvenio(convenioOpt.get());
			existingEmpleado.setCategoria(categoriaOpt.get());
			existingEmpleado.setPuesto(puestoOpt.get());
			existingEmpleado.setFechaInicio(empleadoDTO.getFechaInicio());
			existingEmpleado.setFechaFin(empleadoDTO.getFechaFin());
			existingEmpleado.setDiasVacacionesPactadas(empleadoDTO.getDiasVacacionesPactadas());
			existingEmpleado.setEstadoEmpleado(estadoEmpleado);
			existingEmpleado.setNacionalidad(empleadoDTO.getNacionalidad());
			existingEmpleado.setEstadoCivil(estadoCivil);
			existingEmpleado.setGenero(genero);
			existingEmpleado.setObraSocial(obraSocialOpt.get());
			existingEmpleado.setCbu(empleadoDTO.getCbu());
			existingEmpleado.setBanco(bancoOpt.get());
			existingEmpleado.setTipoCuentaBancaria(tipoCuentaBancaria);
			existingEmpleado.setTipoContrato(tipoContrato);
			existingEmpleado.setCuil(empleadoDTO.getCuil());

			// Guarda el empleado actualizado y verifica si se realizó correctamente
			int updatedId = empleadoService.saveEmpleado(existingEmpleado);
			if (updatedId == -1) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Error al actualizar el empleado.");
			}
			return ResponseEntity.ok("Empleado actualizado con éxito.");
		} catch (Exception e) {
			return handleException(e);
		}
	}

	private ResponseEntity<String> handleException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Error interno del servidor: " + e.getMessage());
	}
}