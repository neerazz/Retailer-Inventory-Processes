package com.hackerrank.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.sample.controller.CustomerController;
import com.hackerrank.sample.model.Customer;
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
public class CustomerUnitTest {

    private MockMvc mockMVC;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerController customerController;

    @Before
    public void setUp() {
        this.mockMVC = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void check_controller_present_in_webApplicationContext() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("customerController"));
        Assert.assertNotNull(webApplicationContext.getBean("customerService"));
    }

    @Test
    public void getCustomerTest() throws Exception {

        mockMVC.perform(
                get("/customer/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
                .andReturn();

    }

    @Test
    public void addCustomer_Test() throws Exception {

        when(customerController.addCustomer(any())).thenReturn(getCorrectCustomer());
        //      Add the Customer.
        mockMVC.perform(
                put("/customer/"+ getCorrectCustomer().getCustomerId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(getCorrectCustomer())))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(jsonPath("$.customerId").isNumber())
//                .andExpect(jsonPath("$.customerName").value(getCorrectCustomer().getCustomerName()))
                .andReturn();
    }

    @Test
    public void updateCustomer_Test() throws Exception {

    //        Creating a customer name.
        String newCustName = "New Test Name";
        Customer updatedCustomer = getCorrectCustomer();
        updatedCustomer.setCustomerName(newCustName);

        when(customerController.updateCustomer(any(), any())).thenReturn(updatedCustomer);

    //      Update the Customer.
        mockMVC.perform(
                put("/customer/"+ updatedCustomer.getCustomerId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(updatedCustomer)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.customerId").isNumber())
                .andExpect(jsonPath("$.customerName").value(newCustName))
                .andReturn();
    }

    @Test
    public void deleteCustomer_Test_negative_case() throws Exception {

        //      Delete the Customer.
        mockMVC.perform(
                delete("/customer/"+ getCorrectCustomer().getCustomerId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deleteAllCustomer_Test() throws Exception {

        //      Delete the Customer.
        mockMVC.perform(
                delete("/customer/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(getCorrectCustomer())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private String toJson(Object r) throws Exception {
        String outputJSON = objectMapper.writeValueAsString(r);
        return outputJSON;
    }

    private Customer getCorrectCustomer(){
        Customer randomCustomer = new Customer();
        randomCustomer.setCustomerName("Random Name");
        randomCustomer.setCustomerId(12345);
        randomCustomer.setGender("M");
        randomCustomer.setContactNumber(Long.parseLong("1234567890"));
        return randomCustomer;
    }
}
