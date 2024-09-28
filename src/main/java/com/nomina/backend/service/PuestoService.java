package com.nomina.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.Iservice.IpuestoService;
import com.nomina.backend.model.Puesto;
import com.nomina.backend.repository.PuestoRepository; // Asegúrate de que este repositorio existe

@Service
public class PuestoService implements IpuestoService {

    @Autowired
    private PuestoRepository puestoRepository; // Inyección de dependencia del repositorio

    @Override
    public List<Puesto> listPuesto() {
        return puestoRepository.findAll(); // Retorna todos los puestos
    }

    @Override
	public Optional<Puesto> findById(Integer id) {
		return puestoRepository.findById(id);
	}

    @Override
    public int savePuesto(Puesto puesto) {
        try {
            Puesto savedPuesto = puestoRepository.save(puesto); // Guarda el puesto en la base de datos
            return savedPuesto.getIdPuesto(); // Retorna el ID del puesto guardado
        } catch (Exception e) {
            System.err.println("Error al guardar el puesto: " + e.getMessage());
            return -1;
        }
    }

    @Override
    public boolean deletePuesto(int id) {
        try {
            if (puestoRepository.existsById(id)) { // Verifica si el puesto existe antes de intentar eliminarlo
                puestoRepository.deleteById(id);
                return true; // Devuelve true si la eliminación fue exitosa
            } else {
                return false; // Devuelve false si el puesto no existe
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar el puesto: " + e.getMessage());
            return false; // Devuelve false en caso de error
        }
    }

    @Override
    public List<Puesto> findByNombre(String nombre) {
        return puestoRepository.findByNombre(nombre); // Busca puestos por su nombre
    }
}