package com.tecsup.petclinic.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Vet Transfer Object (TO)
 *
 * @author jgomezm
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VetTO {

    private Integer id;

    private String name;

    private int typeId;

    private int ownerId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String birthDate;

}