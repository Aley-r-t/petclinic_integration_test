package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exception.VetNotFoundException;

import java.util.List;

@Service
@Slf4j
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;

    // Constructor con VetRepository
    public VetServiceImpl(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    /**
     * Crear un nuevo veterinario.
     *
     * @param vet
     * @return
     */
    @Override
    public Vet create(Vet vet) {
        return vetRepository.save(vet);
    }

    /**
     * Actualizar un veterinario existente.
     *
     * @param vet
     * @return
     */
    @Override
    public Vet update(Vet vet) {
        return vetRepository.save(vet);
    }

    /**
     * Eliminar un veterinario por su ID.
     *
     * @param id
     * @throws VetNotFoundException
     */
    @Override
    public void delete(Integer id) throws VetNotFoundException {
        Vet vet = findById(id);
        vetRepository.delete(vet);
    }

    /**
     * Buscar un veterinario por su ID.
     *
     * @param id
     * @return
     * @throws VetNotFoundException
     */
    @Override
    public Vet findById(Integer id) throws VetNotFoundException {
        Optional<Vet> vet = vetRepository.findById(id);

        if (!vet.isPresent())
            throw new VetNotFoundException("Veterinarian record not found...!");

        return vet.get();
    }

    /**
     * Buscar veterinarios por nombre.
     *
     * @param name
     * @return
     */
    @Override
    public List<Vet> findByName(String name) {
        List<Vet> vets = vetRepository.findByName(name);

        vets.forEach(vet -> log.info("" + vet));

        return vets;
    }

    /**
     * Buscar veterinarios por especialidad (tipo).
     *
     * @param typeId
     * @return
     */
    @Override
    public List<Vet> findByTypeId(int typeId) {
        List<Vet> vets = vetRepository.findByTypeId(typeId);

        vets.forEach(vet -> log.info("" + vet));

        return vets;
    }

    /**
     * Buscar veterinarios por ID de dueño (owner).
     *
     * @param ownerId
     * @return
     */
    @Override
    public List<Vet> findByOwnerId(int ownerId) {
        List<Vet> vets = vetRepository.findByOwnerId(ownerId);

        vets.forEach(vet -> log.info("" + vet));

        return vets;
    }

    /**
     * Obtener todos los veterinarios.
     *
     * @return
     */
    @Override
    public List<Vet> findAll() {
        return vetRepository.findAll();
    }
}
