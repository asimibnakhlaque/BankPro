package com.screenprog.application;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.screenprog.application.dtos.AccountDTO;
import com.screenprog.application.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.Document;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BankAccountManagementSystemApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

    @Test
    void addNewStaff(){
        Staff staff = new Staff();
        staff.setFirstName("John");
        staff.setLastName("Maxwell");
        staff.setEmail("johnmaxwell@vola.com");
        staff.setAddress("Street 12, Lava Hotel, Main City, London");
        staff.setPhoneNumber("9089786756");
        staff.setRoles(List.of("STAFF", "USER"));
        staff.setPassword("john@123");

        ResponseEntity<String> entity = restTemplate
                .postForEntity("admin/add-one-staff", staff, String.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

	@Test
	void addNewCustomer() {
		Customer customer = new Customer();
		customer.setFirstName("John");
		customer.setLastName("Maxwell");
		customer.setDob(Date.valueOf("2000-12-10").toLocalDate());
		customer.setAddress("Street 12, Lava Hotel, Main City, London");
		customer.setPassword("secured");
		customer.setPhoneNumber("8909345786");
		int nm = (int)(Math.random() * 1000);
		customer.setEmail(String.format("johnmaxwell%d@example.com", nm));

		ResponseEntity<Void> response = restTemplate
				.postForEntity("/staff/add-one-customer", customer, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI uriOfNewCustomer = response.getHeaders().getLocation();
		System.out.println(uriOfNewCustomer);

		ResponseEntity<Customer> customerResponse = restTemplate
				.getForEntity(uriOfNewCustomer, Customer.class);
		assertThat(customerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		Customer newCustomer = customerResponse.getBody();

//		assertThat(newCustomer).isNotNull();
        assert newCustomer != null;
        assertThat(newCustomer.getCustomerID()).isNotNull();
		assertThat(newCustomer.getFirstName()).isEqualTo("John");
		assertThat(newCustomer.getLastName()).isEqualTo("Maxwell");
		assertThat(newCustomer.getDob()).isEqualTo(Date.valueOf("2000-12-10").toLocalDate());
		assertThat(newCustomer.getAddress()).isEqualTo("Street 12, Lava Hotel, Main City, London");
		assertThat(newCustomer.getPassword()).isEqualTo("secured");
		assertThat(newCustomer.getPhoneNumber()).isEqualTo("8909345786");
		assertThat(newCustomer.getEmail()).isEqualTo(String.format("johnmaxwell%d@example.com", nm));
	}


	@Test
	void shouldCreateAnAccount() {
		AccountDTO accountDTO = new AccountDTO(1L, BigDecimal.valueOf(1000), Status.ACTIVE, AccountType.SAVING, 1234);
		ResponseEntity<String> response = restTemplate
				.postForEntity("/staff/add-one-account", accountDTO, String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfCreated = response.getHeaders().getLocation();
		ResponseEntity<String> accountResponse = restTemplate
				.getForEntity(locationOfCreated, String.class);
		assertThat(accountResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(accountResponse.getBody());
		Number amount = documentContext.read("$.balance");
		assertThat(amount).isEqualTo(1000.00);
	}

	@Test
	void shouldNotCreateAccountOfNonExistingCustomer(){
		AccountDTO accountDTO = new AccountDTO(1000L, BigDecimal.valueOf(1000), Status.ACTIVE, AccountType.SAVING, 1234);
		ResponseEntity<Void> response = restTemplate
				.postForEntity("/staff/add-one-account", accountDTO, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

}
