package com.hackerrank.sample;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerUnitTest {

    private MockMvc mockMVC;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

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
    public void addCustomerTest() throws Exception {

        Customer customer = new Customer();
        customer.setAddress("Test Address");
        customer.setContactNumber(Long.parseLong("1234567890"));
        customer.setCustomerId(Long.parseLong("23456"));
        customer.setGender("M");
        customer.setCustomerName("Test Customer");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer")
                .accept(MediaType.APPLICATION_JSON)
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMVC.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse().getContentAsString());

        System.out.println(
        mockMVC.perform(get("/customer/")
                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString()
        );

        result = mockMVC.perform(post("/customer")
                .content(toJson(customer))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.customerId").isNumber())
                .andReturn();

        System.out.println(
                mockMVC.perform(get("/customer/")
                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
                        .andReturn()
                        .getResponse().getContentAsString()
        );

        JSONObject json = new JSONObject(result.getResponse().getContentAsString());

        mockMVC.perform(MockMvcRequestBuilders.get("/customer/"+json.get("resourceId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.customerId").value(json.get("customerId")));

//        mockMVC.perform(
//                post("/customer/")
//                .content(toJson(customer))
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isCreated());
//
//        mockMVC.perform(
//                get("/customer/" + customer.getCustomerId())
//                        .accept(MediaType.APPLICATION_JSON)
//        )
//                .andExpect(status().isOk())
//                .andExpect(content().string(jsonCustomer))
//                .andExpect(jsonPath("$.customerName").value(customer.getCustomerName()))
//                .andExpect(jsonPath("$.address").value(customer.getAddress()));
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}
