package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.tecsup.petclinic.domain.VetTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VetControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllVets() throws Exception {
        int ID_FIRST_RECORD = 1;

        this.mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id", is(ID_FIRST_RECORD)));
    }

    @Test
    public void testFindVetOK() throws Exception {
        // Cambiar los valores a lo que realmente está en la base de datos
        String VET_FIRST_NAME = "James"; // Valor real en tu respuesta
        String VET_LAST_NAME = "Carter"; // Valor real en tu respuesta

        mockMvc.perform(get("/vets/1"))  // Asegúrate de que este ID existe en la base de datos
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is(VET_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(VET_LAST_NAME)));
    }


    @Test
    public void testFindVetKO() throws Exception {
        mockMvc.perform(get("/vets/666"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateVet() throws Exception {
        String VET_FIRST_NAME = "Sarah";
        String VET_LAST_NAME = "Connor";

        VetTO newVetTO = new VetTO();
        newVetTO.setFirstName(VET_FIRST_NAME);
        newVetTO.setLastName(VET_LAST_NAME);

        mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(VET_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(VET_LAST_NAME)));
    }

    @Test
    public void testDeleteVet() throws Exception {
        String VET_FIRST_NAME = "Mark";
        String VET_LAST_NAME = "Smith";

        VetTO newVetTO = new VetTO();
        newVetTO.setFirstName(VET_FIRST_NAME);
        newVetTO.setLastName(VET_LAST_NAME);

        ResultActions mvcActions = mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        mockMvc.perform(delete("/vets/" + id))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteVetKO() throws Exception {
        mockMvc.perform(delete("/vets/1000"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateVet() throws Exception {
        String VET_FIRST_NAME = "Anna";
        String VET_LAST_NAME = "Williams";

        String UP_VET_FIRST_NAME = "Anna Marie";
        String UP_VET_LAST_NAME = "Williams-Smith";

        VetTO newVetTO = new VetTO();
        newVetTO.setFirstName(VET_FIRST_NAME);
        newVetTO.setLastName(VET_LAST_NAME);

        // CREATE
        ResultActions mvcActions = mockMvc.perform(post("/vets")
                        .content(om.writeValueAsString(newVetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        String response = mvcActions.andReturn().getResponse().getContentAsString();
        Integer id = JsonPath.parse(response).read("$.id");

        // UPDATE
        VetTO upVetTO = new VetTO();
        upVetTO.setId(id);
        upVetTO.setFirstName(UP_VET_FIRST_NAME);
        upVetTO.setLastName(UP_VET_LAST_NAME);

        mockMvc.perform(put("/vets/" + id)
                        .content(om.writeValueAsString(upVetTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        // FIND
        mockMvc.perform(get("/vets/" + id))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.firstName", is(UP_VET_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(UP_VET_LAST_NAME)));

        // DELETE
        mockMvc.perform(delete("/vets/" + id))
                .andExpect(status().isOk());
    }
}
