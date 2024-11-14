package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.mapper.VetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tecsup.petclinic.domain.VetTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;

import java.util.List;

/**
 * Controlador para veterinarios.
 */
@RestController
@Slf4j
public class VetController {

    private VetService vetService;
    private VetMapper mapper;

    /**
     * @param vetService
     * @param mapper
     */
    public VetController(VetService vetService, VetMapper mapper) {
        this.vetService = vetService;
        this.mapper = mapper;
    }

    /**
     * Obtener los veterinarios
     *
     * @return
     */
    @GetMapping(value = "/vets")
    public ResponseEntity<List<VetTO>> findAllVets() {
        List<Vet> vets = vetService.findAll();
        log.info("vets: " + vets);
        vets.forEach(vet -> log.info("Vet >> {} ", vet));

        List<VetTO> vetsTO = this.mapper.toVetTOList(vets);
        log.info("vetsTO: " + vetsTO);
        vetsTO.forEach(vetTO -> log.info("VetTO >> {} ", vetTO));

        return ResponseEntity.ok(vetsTO);
    }

    /**
     * Crear
     * @param vetTO
     * @return
     */
    @PostMapping(value = "/vets")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<VetTO> create(@RequestBody VetTO vetTO) {
        Vet newVet = this.mapper.toVet(vetTO);
        VetTO newVetTO = this.mapper.toVetTO(vetService.create(newVet));
        return ResponseEntity.status(HttpStatus.CREATED).body(newVetTO);
    }

    /**
     * Buscar por ID
     * @param id
     * @return
     */
    @GetMapping(value = "/vets/{id}")
    ResponseEntity<VetTO> findById(@PathVariable Integer id) {
        VetTO vetTO;
        try {
            Vet vet = vetService.findById(id);
            vetTO = this.mapper.toVetTO(vet);
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vetTO);
    }

    /**
     * Actualizar
     *
     * @param vetTO
     * @param id
     * @return
     */
    @PutMapping(value = "/vets/{id}")
    ResponseEntity<VetTO> update(@RequestBody VetTO vetTO, @PathVariable Integer id) {
        VetTO updateVetTO;
        try {
            Vet updateVet = vetService.findById(id);
            updateVet.setFirstName(vetTO.getFirstName());
            updateVet.setLastName(vetTO.getLastName());
            vetService.update(updateVet);
            updateVetTO = this.mapper.toVetTO(updateVet);
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updateVetTO);
    }

    /**
     * Eliminar por ID
     *
     * @param id
     */
    @DeleteMapping(value = "/vets/{id}")
    ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            vetService.delete(id);
            return ResponseEntity.ok("Deleted ID: " + id);
        } catch (VetNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
