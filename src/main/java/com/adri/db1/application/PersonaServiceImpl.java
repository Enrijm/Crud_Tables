package com.adri.db1.application;

import com.adri.db1.application.port.IPersonaService;
import com.adri.db1.domain.IPersonaRepository;
import com.adri.db1.domain.PersonaEntity;
import com.adri.db1.infraestructure.dto.input.PersonaInputDTO;
import com.adri.db1.infraestructure.dto.output.PersonaOutputDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements IPersonaService {
    @Autowired
    IPersonaRepository repository;

    @Override
    public PersonaOutputDTO addPersona(PersonaInputDTO personaIn) throws Exception {
        PersonaEntity persona = new PersonaEntity(personaIn);
        repository.save(persona);
        return new PersonaOutputDTO(persona);
    }

    @Override
    public PersonaOutputDTO getPersonaById(Integer id) throws Exception {
        PersonaEntity persona = repository.findById(id)
                .orElseThrow(() -> new HttpMediaTypeNotAcceptableException("No se ha encontrado ninguna persona con id " + id));
        return new PersonaOutputDTO(persona);
    }

    @Override
    public List<PersonaOutputDTO> getPersonaByName(String nombre) throws Exception {
        List<PersonaOutputDTO> listaSalida = new ArrayList<>();
        repository.findByName(nombre).forEach(pE->{
            listaSalida.add(new PersonaOutputDTO(pE));
        });
        return listaSalida;
    }

    @Override
    public List<PersonaOutputDTO> getPersonas() {
        List<PersonaOutputDTO> listaPersonaOut = new ArrayList<>();
        repository.findAll().forEach(p->{
            PersonaOutputDTO pOut = new PersonaOutputDTO(p);
            listaPersonaOut.add(pOut);
        });
        return listaPersonaOut;
    }

    @Override
    public PersonaOutputDTO updatePersona(Integer id, PersonaInputDTO personaIn) throws Exception {
        PersonaEntity personaToUpdate = repository.findById(id)
                .orElseThrow(()->new HttpMediaTypeNotAcceptableException("No se ha encontrado ninguna persona con id " + id));
        personaToUpdate.update(personaIn);
        repository.save(personaToUpdate);
        return new PersonaOutputDTO(personaToUpdate);
    }

    @Override
    public void deletePersona(Integer id) throws Exception {
        repository.deleteById(id);
    }
}