package com.example.unittestingandmockito.controllers;

import com.example.unittestingandmockito.TestContainerConfiguration;
import com.example.unittestingandmockito.dto.EmployeeDto;
import com.example.unittestingandmockito.entities.EmployeeEntity;
import com.example.unittestingandmockito.repositories.EmployeeRepositories;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;




import java.util.Map;
import java.util.Optional;


class EmployeeControllerTestIT extends AbstractIntegrationTest {


    @Autowired
    private EmployeeRepositories employeeRepositories;

    private EmployeeEntity testEmployeeEntity;

    private EmployeeDto testEmployeeDto;

    @BeforeEach
    void setUp() {
        testEmployeeEntity = EmployeeEntity.builder()
                .id(1L)
                .name("Ashish Jangde")
                .email("ashish@gmail.com")
                .salary(100000L)
                .build();
        testEmployeeDto = EmployeeDto.builder()
                .id(1L)
                .name("Ashish Jangde")
                .email("ashish@gmail.com")
                .salary(100000L)
                .build();
        employeeRepositories.delete(testEmployeeEntity);
    }
    @AfterEach
    void tearDown() {
        employeeRepositories.delete(testEmployeeEntity);
    }

    @Test
    void getAllEmployees() {
        employeeRepositories.save(testEmployeeEntity);
        webClient.get()
                .uri("/employee")
                .exchange()
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .value(employeeDto -> {
                    Assertions.assertThat(employeeDto).isNotNull();
                });
    }



    @Test
    void getEmployeeFromId_WhenValidId_ReturnEmployee() {
        EmployeeEntity savedEmployeeEntity = employeeRepositories.save(testEmployeeEntity);

        webClient.get()
                .uri("/employee/{id}", savedEmployeeEntity.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(savedEmployeeEntity.getId())
                .jsonPath("$.data.name").isEqualTo(savedEmployeeEntity.getName())
                .jsonPath("$.data.email").isEqualTo(savedEmployeeEntity.getEmail())
                .jsonPath("$.data.salary").isEqualTo(savedEmployeeEntity.getSalary());

    }

    @Test
    void getEmployeeFromId_WhenInvalidId_ReturnNull() {
        webClient.get()
                .uri("/employee/5")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(EmployeeDto.class)
                .value(employee -> {
                    Assertions.assertThat(employee).isNotNull();
                    Assertions.assertThat(employee.getId()).isEqualTo(0);
                    Assertions.assertThat(employee.getName()).isNull();
                    Assertions.assertThat(employee.getEmail()).isNull();
                    Assertions.assertThat(employee.getSalary()).isNull();
                });
    }

    @Test
    void createEmployee_WhenValidData_ReturnEmployee() {
       EmployeeDto testEmployeeDto1 = EmployeeDto.builder()
                .name("Ashish Jangde")
                .email("ashish54@gmail.com")
                .salary(100000L)
                .build();
        webClient.post()
                .uri("/employee")
                .bodyValue(testEmployeeDto1) // Correctly setting the request body
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.data.email").isEqualTo(testEmployeeDto1.getEmail())
                .jsonPath("$.data.salary").isEqualTo(testEmployeeDto1.getSalary());

    }

    @Test
    void createEmployee_WhenInvalidData_ReturnNull() {
       EmployeeDto testEmployeeDto1 = EmployeeDto.builder()
                .name("")
                .email("")
                .salary(0L)
                .build();
        webClient.post()
                .uri("/employee")
                .bodyValue(testEmployeeDto1)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.apiError.message").isEqualTo("Input Validation Failed");
    }

    @Test
    void updateEmployeeById_WhenValidData_ReturnUpdatedEmployee() {
        EmployeeEntity savedEmployeeEntity = employeeRepositories.save(testEmployeeEntity);

        EmployeeDto updatedEmployeeDto = EmployeeDto.builder()
                .name("Updated Ashish Jangde")
                .email("updated.ashish@gmail.com")
                .salary(120000L)
                .build();

        webClient.put()
                .uri("/employee/{id}", savedEmployeeEntity.getId())
                .bodyValue(updatedEmployeeDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.name").isEqualTo(updatedEmployeeDto.getName())
                .jsonPath("$.data.email").isEqualTo(updatedEmployeeDto.getEmail())
                .jsonPath("$.data.salary").isEqualTo(updatedEmployeeDto.getSalary());
    }

    @Test
    void updateEmployeeById_WhenInvalidId_ReturnNotFound() {
        EmployeeDto updatedEmployeeDto = EmployeeDto.builder()
                .name("Updated Ashish Jangde")
                .email("updated.ashish@gmail.com")
                .salary(120000L)
                .build();

        webClient.put()
                .uri("/employee/{id}", 999L) // Non-existent employee ID
                .bodyValue(updatedEmployeeDto)
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void updatePartialEmployeeById_WhenValidData_ReturnUpdatedEmployee() {
        EmployeeEntity savedEmployeeEntity = employeeRepositories.save(testEmployeeEntity);

        Map<String, Object> updates = Map.of(
                "name", "Partially Updated Ashish",
                "salary", 110000
        );

        webClient.patch()
                .uri("/employee/{id}", savedEmployeeEntity.getId())
                .bodyValue(updates)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data.name").isEqualTo(updates.get("name"))
                .jsonPath("$.data.salary").isEqualTo(updates.get("salary"));
    }


    @Test
    void updatePartialEmployeeById_WhenInvalidId_ReturnNotFound() {
        Map<String, Object> updates = Map.of(
                "name", "Partially Updated Ashish",
                "salary", 110000L
        );

        webClient.patch()
                .uri("/employee/{id}", 999L) // Non-existent employee ID
                .bodyValue(updates)
                .exchange()
                .expectStatus().isNotFound();
    }


    @Test
    void deleteEmployeeById_WhenValidId_ReturnOk() {
        EmployeeEntity savedEmployeeEntity = employeeRepositories.save(testEmployeeEntity);

        webClient.delete()
                .uri("/employee/{id}", savedEmployeeEntity.getId())
                .exchange()
                .expectStatus().isOk();

        Optional<EmployeeEntity> deletedEmployee = employeeRepositories.findById(savedEmployeeEntity.getId());
        Assertions.assertThat(deletedEmployee).isEmpty();
    }


    @Test
    void deleteEmployeeById_WhenInvalidId_ReturnNotFound() {
        webClient.delete()
                .uri("/employee/{id}", 999L) // Non-existent employee ID
                .exchange()
                .expectStatus().isNotFound();
    }







}

