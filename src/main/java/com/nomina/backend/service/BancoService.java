package com.nomina.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.Iservice.IbancoService;
import com.nomina.backend.model.Banco;
import com.nomina.backend.repository.BancoRepository; // Asegúrate de que este repositorio existe

@Service
public class BancoService implements IbancoService {

    @Autowired
    private BancoRepository bancoRepository; // Inyección de dependencia del repositorio

    @Override
    public List<Banco> listBanco() {
        return (List<Banco>) bancoRepository.findAll();  // Retorna todos los bancos
    }

	@Override
	public Optional<Banco> findById(Integer id) {
		return bancoRepository.findById(id);
	}

    @Override
    public int saveBanco(Banco banco) {
    	try {
    		Banco savedBanco = bancoRepository.save(banco); // Guarda el banco en la base de datos
    		return savedBanco.getIdBanco(); // Retorna el ID del banco guardado
    	}catch (Exception e) {    
	        System.err.println("Error al guardar el course: " + e.getMessage());
	        return -1; 
	    }
	}


    @Override
    public boolean deleteBanco(int id) {
		try {
	        if (bancoRepository.existsById(id)) { // Verifica si el estado existe antes de intentar eliminarlo
	        	bancoRepository.deleteById(id);
	            return true; // Devuelve true si la eliminación fue exitosa
	        } else {
	            return false; // Devuelve false si el estado no existe
	        }
	    } catch (Exception e) {
	        // Manejo de errores
	        System.err.println("Error al eliminar el estado: " + e.getMessage());
	        return false; // Devuelve false en caso de error
	    }
	}

    @Override
    public List<Banco> findByNombre(String nombre) {
        return bancoRepository.findByNombre(nombre); // Busca bancos por su nombre
    }
}