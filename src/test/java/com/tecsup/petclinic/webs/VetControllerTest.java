package com.tecsup.petclinic.webs;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class VetControllerTest {
    @Test
    public void testFindVetById() throws Exception {
        int VET_ID = 1; // Reemplazar con un ID existente en la base de datos para probar

        mockMvc.perform(get("/vets/" + VET_ID))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(VET_ID)));
    }

}
