package com.nomina.backend.Iservice;

import java.util.List;
import java.util.Optional;
import com.nomina.backend.model.Empleado;

public interface IempleadoService {
	public List<Empleado>listEmpleado();
	public Optional<Empleado> findById(Integer id);
	public int saveEmpleado(Empleado empleado);
	public boolean deleteEmpleado(int id);
	public List<Empleado> findByNombre(String name);

}
