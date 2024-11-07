package com.tecsup.petclinic.services;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.exception.VetNotFoundException;
import com.tecsup.petclinic.repositories.VetRepository;


@Service
@Slf4j
public class VetServiceImpl implements VetService {

    VetRepository vetRepository;

    public VetServiceImpl (VetRepository vetRepository) {
        this. vetRepository = vetRepository;
    }

    /**
     *
     * @param pet
     * @return
     */
    @Override
    public Vet create(Pet pet) {
        return vetRepository.save(pet);
    }


    /**
     *
     * @param vet
     * @return
     */
    @Override
    public Vet update(Pet pet) {
        return VetRepository.save(pet);
    }

    /**
     *
     * @param id
     * @throws VetNotFoundException
     */
    @Override
    public void delete(Integer id) throws VetNotFoundException{

        Vet vet = findById(id);
        petRepository.delete(vet);
    }

    /**
     *
     * @param id
     * @return
     */

    @Override
    public Vet findById(Integer id) throws VetNotFoundException {

        Optional<Vet> vet = vetRepository.findById(id);

        if ( !vet.isPresent())
            throw new VetNotFoundException("Record not found...!");

        return vet.get();
    }

    /**
     *
     * @param name
     * @return
     */

    @Override
    public List<Vet> findByName(String name) {

        List<Vet> pets = vetRepository.findByName(name);

        vets.stream().forEach(vet -> log.info("" + vet));

        return vets;
    }

    /**
     *
     * @param typeId
     * @return
     */
    @Override
    public List<Vet> findByTypeId(int typeId) {

        List<Vet> vets = vetRepository.findByTypeId(typeId);

        vets.stream().forEach(vet -> log.info("" + vet));

        return vets;
    }

    /**
     *
     * @param ownerId
     * @return
     */
    @Override
    public List<Vet> findByOwnerId(int ownerId) {

        List<Vet> vets = vetRepository.findByOwnerId(ownerId);

        vets.stream().forEach(vet -> log.info("" + vet));

        return vets;
    }

    /**
     *
     * @return
     */
    @Override
    public List<Vet> findAll() {
        //
        return vetRepository.findAll();
    }
}
