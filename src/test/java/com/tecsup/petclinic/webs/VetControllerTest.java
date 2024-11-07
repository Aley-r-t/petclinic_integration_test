package com.tecsup.petclinic.webs;

public class VetControllerTest {
    @Test
    public void testDeleteVet() throws Exception {
        String VET_NAME = "Dr. Brown";
        int TYPE_ID = 2;
        int OWNER_ID = 2;
        String BIRTH_DATE = "1975-04-10";

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
