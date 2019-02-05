package com.hackerrank.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.sample.controller.InventoryController;
import com.hackerrank.sample.model.Inventory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InventoryUnitTest {

    private MockMvc mockMVC;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private InventoryController inventoryController;

    @Before
    public void setUp() {
        this.mockMVC = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void check_controller_present_in_webApplicationContext() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("inventoryController"));
    }

    @Test
    public void getInventoryTest() throws Exception {

        mockMVC.perform(
                get("/item/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
                .andReturn();

    }

    @Test
    public void addInventory_Test() throws Exception {

        when(inventoryController.addInventory(any())).thenReturn(getCorrectInventory());
        //      Add the Inventory.
        mockMVC.perform(
                post("/item/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getCorrectInventory())))
                .andDo(print())
                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(jsonPath("$.InventoryId").isNumber())
//                .andExpect(jsonPath("$.InventoryName").value(getCorrectInventory().getInventoryName()))
                .andReturn();
    }

    @Test
    public void updateInventory_Test() throws Exception {

        when(inventoryController.addInventory(any())).thenReturn(getCorrectInventory());
        //      Add the Inventory.
        mockMVC.perform(
                put("/item/"+ getCorrectInventory().getSkuId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(getCorrectInventory())))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(jsonPath("$.InventoryId").isNumber())
//                .andExpect(jsonPath("$.InventoryName").value(getCorrectInventory().getInventoryName()))
                .andReturn();
    }

    @Test
    public void deleteInventory_Test_negative_case() throws Exception {

        //      Delete the Inventory.
        mockMVC.perform(
                delete("/item/"+ getCorrectInventory().getSkuId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deleteAllInventory_Test() throws Exception {

        //      Delete the Inventory.
        mockMVC.perform(
                delete("/item/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getCorrectInventory())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private String toJson(Object r) throws Exception {
        String outputJSON = objectMapper.writeValueAsString(r);
        return outputJSON;
    }

    private Inventory getCorrectInventory(){
        Inventory randomInventory = new Inventory();
        randomInventory.setSkuId(Long.parseLong("1234"));
        randomInventory.setInventoryOnHand(10);
        randomInventory.setPrice(Double.parseDouble("12345678.90"));
        return randomInventory;
    }
}
