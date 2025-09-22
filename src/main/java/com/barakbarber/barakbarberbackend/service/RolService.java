package com.barakbarber.barakbarberbackend.service;

import com.barakbarber.barakbarberbackend.aop.Loggable;
import com.barakbarber.barakbarberbackend.model.Rol;
import com.barakbarber.barakbarberbackend.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;


@Service
public class RolService {
    @Autowired
    private RolRepository rolRepository;

    public List<Rol> getAllRoles() {
        return rolRepository.findAll();
    }

    @Loggable("CREAR_ROL")
    public Rol createRol(Rol rol) throws Exception {
        Optional<Rol> existing = rolRepository.findByName(rol.getName());
        if (existing.isPresent()) {
            throw new Exception("El rol ya existe");
        } else {
            return rolRepository.save(rol);
        }
    }

    public Rol getRolById(Long id) throws Exception {
        return rolRepository.findById(id)
                .orElseThrow(() -> new Exception("Rol no encontrado"));
    }

    @Loggable("ELIMINAR_ROL")
    public void deleteRol(Long id) throws Exception {
        Rol rol = getRolById(id);
        rolRepository.delete(rol);
    }

    @Loggable("ACTUALIZAR_ROL")
    public Rol updateRol(Long id, Rol updatedRol) throws Exception {
        Rol rol = getRolById(id);
        rol.setName(updatedRol.getName());
        return rolRepository.save(rol);
    }
}
