package com.hackerrank.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.sample.controller.VendorConroller;
import com.hackerrank.sample.model.Vendor;
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
public class VendorUnitTest {

    private MockMvc mockMVC;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VendorConroller vendorController;

    @Before
    public void setUp() {
        this.mockMVC = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getvendorTest() throws Exception {

        mockMVC.perform(
                get("/vendor/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
                .andReturn();

    }

    @Test
    public void addvendor_Test() throws Exception {

        when(vendorController.addVendor(any())).thenReturn(getCorrectvendor());
        //      Add the vendor.
        mockMVC.perform(
                put("/vendor/"+ getCorrectvendor().getVendorId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(getCorrectvendor())))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(jsonPath("$.vendorId").isNumber())
//                .andExpect(jsonPath("$.vendorName").value(getCorrectvendor().getvendorName()))
                .andReturn();
    }

    @Test
    public void updatevendor_Test() throws Exception {

    //        Creating a vendor name.
        String newCustName = "New Test Name";
        Vendor updatedvendor = getCorrectvendor();
        updatedvendor.setVendorName(newCustName);

        when(vendorController.addVendor(any(), any())).thenReturn(updatedvendor);

    //      Update the vendor.
        mockMVC.perform(
                put("/vendor/"+ updatedvendor.getVendorId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(updatedvendor)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.vendorId").isNumber())
                .andExpect(jsonPath("$.vendorName").value(newCustName))
                .andReturn();
    }

    @Test
    public void deletevendor_Test_negative_case() throws Exception {

        //      Delete the vendor.
        mockMVC.perform(
                delete("/vendor/"+ getCorrectvendor().getVendorId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deleteAllvendor_Test() throws Exception {

        //      Delete the vendor.
        mockMVC.perform(
                delete("/vendor/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getCorrectvendor())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private String toJson(Object r) throws Exception {
        String outputJSON = objectMapper.writeValueAsString(r);
        return outputJSON;
    }

    private Vendor getCorrectvendor(){
        Vendor randomvendor = new Vendor();
        randomvendor.setVendorName("Random Name");
        randomvendor.setVendorId(Long.parseLong("12345"));
        randomvendor.setVendorEmail("EMAIL");
        randomvendor.setVendorContactNo("1234567890");
        return randomvendor;
    }
}
