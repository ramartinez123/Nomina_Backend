	
package com.nomina.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nomina.backend.model.SalarioExcedente;

public interface SalarioExcedenteRepository extends JpaRepository<SalarioExcedente, Integer> {
	Optional<SalarioExcedente> findByEmpleado_Id(int idEmpleado);
}
