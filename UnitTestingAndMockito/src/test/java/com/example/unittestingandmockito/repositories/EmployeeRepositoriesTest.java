package com.example.unittestingandmockito.repositories;

import com.example.unittestingandmockito.TestContainerConfiguration;
import com.example.unittestingandmockito.entities.EmployeeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest  // we don't use when we're testing repositories cuz it reloads whole application and take time
@DataJpaTest
@Import(TestContainerConfiguration.class)
//@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  // DataJpaTest automatically do that on our behalf
class EmployeeRepositoriesTest {

    @Autowired
    private  EmployeeRepositories employeeRepositories;

    private EmployeeEntity employee;




    @BeforeEach
    void setUp() {
        employee = EmployeeEntity.builder()
                .id(1L)
                .name("Ashish")
                .email("ashish@gmail.com")
                .salary(100000L)
                .build();
        employeeRepositories.save(employee);
    }

    @Test
    void testFindByEmail_WhenEmailExists_ReturnsEmployee() {
       EmployeeEntity employeeFound = employeeRepositories.findByEmail(employee.getEmail());
       assertNotNull(employeeFound);
       assertEquals(employee.getEmail(), employeeFound.getEmail());
    }

    @Test
    void testFindByEmail_WhenEmailDoesNotExist_ReturnsEmpty() {
        EmployeeEntity employeeFound = employeeRepositories.findByEmail("nonexistent@gmail.com");
        assertNull(employeeFound);
    }

}