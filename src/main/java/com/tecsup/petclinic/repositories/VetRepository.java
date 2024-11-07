package com.tecsup.petclinic.repositories;

import com.tecsup.petclinic.entities.Vet;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VetRepository extends JpaRepository<Vet, Integer> {
    List<Vet> findByName(String lastName);
    List<Vet> findByTypeId(Integer TypeId);

    @Override
    List<Vet> findAll();
}
