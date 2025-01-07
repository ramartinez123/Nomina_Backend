package com.nomina.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomina.backend.Iservice.IcategoriaService;
import com.nomina.backend.Iservice.IconvenioService;
import com.nomina.backend.dto.CategoriaDTO;
import com.nomina.backend.model.Categoria;
import com.nomina.backend.model.Convenio;
import com.nomina.backend.repository.CategoriaRepository; // Asegúrate de que este repositorio existe

@Service
public class CategoriaService implements IcategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private IconvenioService convenioService;

    @Override
    public List<CategoriaDTO> getAllCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias.stream()
                         .map(this::convertToDTO)
                         .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoriaDTO> getCategoriaById(int id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.map(this::convertToDTO);
    }

    @Override
    public CategoriaDTO createCategoria(CategoriaDTO categoriaDTO) {
        Optional<Convenio> convenioOpt = convenioService.findById(categoriaDTO.getIdConvenio());
        if (convenioOpt.isPresent()) {
            Categoria categoria = new Categoria();
            categoria.setConvenio(convenioOpt.get());
            categoria.setNombre(categoriaDTO.getNombre());
            categoria.setDescripcion(categoriaDTO.getDescripcion());

            Categoria savedCategoria = categoriaRepository.save(categoria);
            return convertToDTO(savedCategoria);
        }
        return null;
    }

    @Override
    public boolean updateCategoria(int id, CategoriaDTO categoriaDTO) {
        Optional<Categoria> existingCategoriaOpt = categoriaRepository.findById(id);
        if (existingCategoriaOpt.isPresent()) {
            Categoria existingCategoria = existingCategoriaOpt.get();
            if (categoriaDTO.getIdConvenio() != null) {
                Optional<Convenio> convenioOpt = convenioService.findById(categoriaDTO.getIdConvenio());
                convenioOpt.ifPresent(existingCategoria::setConvenio);
            }

            existingCategoria.setNombre(categoriaDTO.getNombre());
            existingCategoria.setDescripcion(categoriaDTO.getDescripcion());

            categoriaRepository.save(existingCategoria);
            return true;
        }
        return false;
    }

    /*@Override
    public List<CategoriaDTO> findByNombre(String nombre) {
        List<Categoria> categorias = categoriaRepository.findByNombre(nombre);
        return categorias.stream()
                         .map(this::convertToDTO)
                         .collect(Collectors.toList());
    }*/

    private CategoriaDTO convertToDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getIdCategoria(),
                categoria.getConvenio().getIdConvenio(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }

	@Override
	public List<Categoria> listCategoria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Categoria> findById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public int saveCategoria(Categoria categoria) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteCategoria(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Categoria> findByNombre(String name) {
		// TODO Auto-generated method stub
		return null;
	}
}