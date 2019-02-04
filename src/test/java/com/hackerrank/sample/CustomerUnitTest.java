package com.hackerrank.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.sample.service.CustomerService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.servlet.ServletContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.sample.controller.CustomerController;
import com.hackerrank.sample.model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

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

    @MockBean
    private CustomerService customerService;

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

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customer/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMVC.perform(requestBuilder).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
                .andReturn();

    }

    @Test
    public void add_update_delete_Customer_Test() throws Exception {

        Customer customer = new Customer();
        customer.setAddress("Test Address");
        customer.setContactNumber(Long.parseLong("1234567890"));
        customer.setCustomerId(Long.parseLong("23456"));
        customer.setGender("M");
        customer.setCustomerName("Test Customer");

    //        Creating a API Request for adding a customer.
        RequestBuilder postBuilder = MockMvcRequestBuilders
                .post("/customer/")
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON);

    //        Add a customer.
        MvcResult result = mockMVC.perform(postBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$").isNotEmpty())
//                .andExpect(jsonPath("$.customerId").isNumber())
//                .andExpect(jsonPath("$.customerName").value(customer.getCustomerName()))
                .andReturn();

    //        Creating a API Request for updating the customer name.
        String newCustName = "New Test Name";
        customer.setAddress(newCustName);
        RequestBuilder putBuilder = MockMvcRequestBuilders
                .put("/customer/" + customer.getCustomerId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(customer));

    //      Update the Customer.
        mockMVC.perform(putBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.customerId").isNumber())
                .andExpect(jsonPath("$.customerName").value(newCustName))
                .andReturn();

    //        Creating a API Request for deleting the customer .
        RequestBuilder deleteBuilder = MockMvcRequestBuilders
                .delete("/customer/"+ customer.getCustomerId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(customer));

    //      Delete the Customer.
        mockMVC.perform(deleteBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty())
                .andReturn();
    }

    private byte[] toJson(Object r) throws Exception {
        return objectMapper.writeValueAsString(r).getBytes();
    }
}
