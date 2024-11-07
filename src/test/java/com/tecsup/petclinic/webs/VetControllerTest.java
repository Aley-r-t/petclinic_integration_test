package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.entities.Vet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Date;
import java.text.SimpleDateFormat;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
@Slf4j


public class VetControllerTest {

    private static final ObjectMapper om = new ObjectMapper();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testCreateVet() throws Exception {
        String VET_NAME = "Dr. Smith";
        int TYPE_ID = 1;
        int OWNER_ID = 1;
        Date BIRTH_DATE = dateFormat.parse("1980-06-15");

        Vet newVet = new Vet();
        newVet.setName(VET_NAME);
        newVet.setTypeId(TYPE_ID);
        newVet.setOwnerId(OWNER_ID);
        newVet.setBirthDate(BIRTH_DATE);

        mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVet))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(VET_NAME)))
                .andExpect(jsonPath("$.typeId", is(TYPE_ID)))
                .andExpect(jsonPath("$.ownerId", is(OWNER_ID)))
                .andExpect(jsonPath("$.birthDate", is(BIRTH_DATE)));
    }


    @Test
    public void testFindVetById() throws Exception {
        int VET_ID = 1; // Reemplazar con un ID existente en la base de datos para probar

        mockMvc.perform(get("/vets/" + VET_ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(VET_ID)));
    }
    @Test
    public void testDeleteVet() throws Exception {
        String VET_NAME = "Dr. Brown";
        int TYPE_ID = 2;
        int OWNER_ID = 2;
        Date BIRTH_DATE = dateFormat.parse("1975-04-10");

        Vet newVet = new Vet();
        newVet.setName(VET_NAME);
        newVet.setTypeId(TYPE_ID);
        newVet.setOwnerId(OWNER_ID);
        newVet.setBirthDate(BIRTH_DATE);

        // Crear el veterinario
        ResultActions mvcActions = mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVet))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        // Eliminar el veterinario
        mockMvc.perform(delete("/vets/" + id))
                .andExpect(status().isOk());
    }

}
