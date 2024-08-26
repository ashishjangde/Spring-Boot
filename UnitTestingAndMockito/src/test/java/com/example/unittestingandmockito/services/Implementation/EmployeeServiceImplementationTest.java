package com.example.unittestingandmockito.services.Implementation;

import com.example.unittestingandmockito.TestContainerConfiguration;
import com.example.unittestingandmockito.dto.EmployeeDto;
import com.example.unittestingandmockito.entities.EmployeeEntity;
import com.example.unittestingandmockito.exceptions.EmployeeValidationException;
import com.example.unittestingandmockito.exceptions.ExistingResourceFoundException;
import com.example.unittestingandmockito.exceptions.ResourceNotFoundException;
import com.example.unittestingandmockito.repositories.EmployeeRepositories;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfiguration.class)
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplementationTest {

    @Mock
    private EmployeeRepositories employeeRepositories;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private EmployeeServiceImplementation employeeService;

    @Mock
    private Validator validator;

    private EmployeeDto mockEmployeeDto;

    private  EmployeeEntity mockEmployeeEntity;

    @BeforeEach
    void setUp() {
         mockEmployeeEntity = EmployeeEntity.builder()
                .id(1L)
                .name("Ashish Jangde")
                .email("ashish@gmail.com")
                .salary(100000L)
                .build();
          mockEmployeeDto = modelMapper.map(mockEmployeeEntity, EmployeeDto.class);
    }


    @Test
    void  test_getEmployeeById_whenIdIsCorrect_thenEmployeeShouldBeReturned() {
        //assign  --> act --> assert ;//assign
       long id = 1L;
        when(employeeRepositories.findById(id)).thenReturn(Optional.of(mockEmployeeEntity)); //stubbing
        //act
        Optional<EmployeeDto> employeeDto = employeeService.getEmployeeById(id);
        //assert
        Assertions.assertThat(employeeDto.isPresent()).isTrue();
        Assertions.assertThat(employeeDto.get().getId()).isEqualTo(id);
        Assertions.assertThat(employeeDto.get().getName()).isEqualTo("Ashish Jangde");
        verify(employeeRepositories).findById(id);
    }

    @Test
    void test_getEmployeeById_whenIdIsIncorrect_thenExceptionShouldBeThrown() {
        // assign
        long id = 1L;
        when(employeeRepositories.findById(id)).thenReturn(Optional.empty()); // Stub to simulate employee not found

        // act & assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.getEmployeeById(id);
        });

        // assert
        Assertions.assertThat(exception.getMessage()).isEqualTo("Cannot find employee with id " + id);

        // verify
        verify(employeeRepositories).findById(id);
    }


    @Test
    void test_createEmployee_whenEmailNotExist_thenEmployeeShouldBeCreated() {

        //assign
        long id = 1L;
        when(employeeRepositories.findByEmail(anyString())).thenReturn(null);
        when(employeeRepositories.save(any(EmployeeEntity.class))).thenReturn(mockEmployeeEntity);

        //act
        EmployeeDto createdEmployee = employeeService.createEmployee(mockEmployeeDto);

        //assert
        assertNotNull(createdEmployee);
       Assertions.assertThat(createdEmployee.getEmail()).isEqualTo(mockEmployeeEntity.getEmail());
       Assertions.assertThat(createdEmployee.getName()).isEqualTo(mockEmployeeEntity.getName());
       ArgumentCaptor<EmployeeEntity> captor = ArgumentCaptor.forClass(EmployeeEntity.class);
       verify(employeeRepositories).save(captor.capture());
       EmployeeEntity captorEmployeeEntity = captor.getValue();
       Assertions.assertThat(captorEmployeeEntity.getEmail()).isEqualTo(mockEmployeeEntity.getEmail());

    }

    @Test
    void test_createEmployee_whenEmailAlreadyExists_thenExceptionShouldBeThrown() {
        // assign
        when(employeeRepositories.findByEmail(anyString())).thenReturn(mockEmployeeEntity); // Return mock employee indicating email already exists

        // act & assert
        ExistingResourceFoundException exception = assertThrows(ExistingResourceFoundException.class, () -> {
            employeeService.createEmployee(mockEmployeeDto);
        });

        Assertions.assertThat(exception.getMessage()).isEqualTo("Employee with email " + mockEmployeeDto.getEmail() + " already exists");

        // verify
        verify(employeeRepositories).findByEmail(mockEmployeeDto.getEmail());
        verify(employeeRepositories, never()).save(any(EmployeeEntity.class)); // Save should not be called
    }


    @Test
    void test_updateEmployee_whenIdIsCorrect_thenEmployeeShouldBeUpdated() {
        // assign
        long id = 1L;
        when(employeeRepositories.existsById(id)).thenReturn(true);
        when(employeeRepositories.save(any(EmployeeEntity.class))).thenReturn(mockEmployeeEntity);

        // act
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, mockEmployeeDto);

        // assert
        assertNotNull(updatedEmployee);
        assertEquals("Ashish Jangde", updatedEmployee.getName());
        assertEquals("ashish@gmail.com", updatedEmployee.getEmail());

        // Verify that the repository methods were called
        verify(employeeRepositories).save(any(EmployeeEntity.class));
        verify(employeeRepositories).existsById(id);

    }

    @Test
    void test_updateEmployee_whenIdIsIncorrect_thenThrowResourceNotFoundException() {
        // assign
        long id = 1L;
        when(employeeRepositories.existsById(id)).thenReturn(false);

        // act & assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(id, mockEmployeeDto));

        assertEquals("Employee not found with id " + id, exception.getMessage());
        verify(employeeRepositories, never()).save(any(EmployeeEntity.class));
        verify(employeeRepositories).existsById(id);
    }

    @Test
    void test_updatePartialEmployeeById_whenValidUpdates_thenEmployeeShouldBeUpdated() {
        // assign
        long id = 1L;

        // Original employee data
        EmployeeEntity originalEmployee = EmployeeEntity.builder()
                .id(id)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .salary(100000L)
                .build();

        // Updates to apply
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "John Doe");
        updates.put("email", "john.doe@example.com");

        // Stubbing the repository methods
        when(employeeRepositories.findById(id)).thenReturn(Optional.of(originalEmployee));
        when(employeeRepositories.save(any(EmployeeEntity.class))).thenReturn(originalEmployee);

        // act
        EmployeeDto updatedEmployee = employeeService.updatePartialEmployeeById(id, updates);

        // assert
        assertNotNull(updatedEmployee);
        assertEquals("John Doe", updatedEmployee.getName());
        assertEquals("john.doe@example.com", updatedEmployee.getEmail());

        // Verify that the repository methods were called
        verify(employeeRepositories).findById(id);
        verify(employeeRepositories).save(any(EmployeeEntity.class));
    }

    @Test
    void test_updatePartialEmployeeById_whenEmployeeNotFound_thenThrowResourceNotFoundException() {
        // assign
        long id = 1L;

        // Updates to apply
        Map<String, Object> updates = new HashMap<>();
        updates.put("name", "John Doe");

        // Stubbing the repository method to return empty
        when(employeeRepositories.findById(id)).thenReturn(Optional.empty());

        // act & assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updatePartialEmployeeById(id, updates);
        });

        assertEquals("Employee not found with id " + id, exception.getMessage());

        // Verify that the findById method was called, but save was not called
        verify(employeeRepositories).findById(id);
        verify(employeeRepositories, never()).save(any(EmployeeEntity.class));
    }


    @Test
    void test_updatePartialEmployeeById_whenValidationFails_thenThrowEmployeeValidationException() {
        // assign
        long id = 1L;
        EmployeeEntity existingEmployee = EmployeeEntity.builder()
                .id(id)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .salary(100000L)
                .build();

        Map<String, Object> updates = new HashMap<>();
        updates.put("email", "invalid-email"); // Invalid email to trigger validation error

        when(employeeRepositories.findById(id)).thenReturn(Optional.of(existingEmployee));

        Set<ConstraintViolation<EmployeeDto>> violations = new HashSet<>();
        ConstraintViolation<EmployeeDto> violation = mock(ConstraintViolation.class);
        when(violation.getMessage()).thenReturn("Invalid email format");
        when(violation.getPropertyPath()).thenReturn(mock(Path.class));
        when(violation.getPropertyPath().toString()).thenReturn("email");
        violations.add(violation);

        when(validator.validate(any(EmployeeDto.class))).thenReturn(violations);

        // act & assert
        EmployeeValidationException exception = assertThrows(EmployeeValidationException.class, () -> {
            employeeService.updatePartialEmployeeById(id, updates);
        });

        // assert
        assertNotNull(exception.getErrors());
        assertEquals(1, exception.getErrors().size());
        assertEquals("Invalid email format", exception.getErrors().getFirst().get("email"));

        // verify
        verify(employeeRepositories).findById(id);
        verify(validator).validate(any(EmployeeDto.class));
        verify(employeeRepositories, never()).save(any(EmployeeEntity.class));
    }


    @Test
    void test_updatePartialEmployeeById_whenLongFieldUpdatedWithInteger_thenConvertToLong() {
        // assign
        long id = 1L;
        EmployeeEntity existingEmployee = EmployeeEntity.builder()
                .id(id)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .salary(100000L)
                .build();

        Map<String, Object> updates = new HashMap<>();
        updates.put("salary", 150000); // Updating salary (Long) with an Integer value

        when(employeeRepositories.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepositories.save(any(EmployeeEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));


        // act
        EmployeeDto updatedEmployee = employeeService.updatePartialEmployeeById(id, updates);

        // assert
        assertNotNull(updatedEmployee);
        assertEquals(150000L, updatedEmployee.getSalary());
        assertInstanceOf(Long.class, updatedEmployee.getSalary());

        // verify
        ArgumentCaptor<EmployeeEntity> employeeCaptor = ArgumentCaptor.forClass(EmployeeEntity.class);
        verify(employeeRepositories).save(employeeCaptor.capture());
        EmployeeEntity savedEmployee = employeeCaptor.getValue();
        assertEquals(150000L, savedEmployee.getSalary());
        assertInstanceOf(Long.class, savedEmployee.getSalary());

        verify(employeeRepositories).findById(id);
        verify(employeeRepositories).save(any(EmployeeEntity.class));
    }

    @Test
    void test_deleteEmployeeById_whenEmployeeExists_thenReturnTrue() {
        // assign
        long id = 1L;
        when(employeeRepositories.existsById(id)).thenReturn(true);

        // act
        boolean result = employeeService.deleteEmployeeById(id);

        // assert
        assertTrue(result);

        // verify
        verify(employeeRepositories).existsById(id);
        verify(employeeRepositories).deleteById(id);

    }
        @Test
        void test_deleteEmployeeById_whenEmployeeDoesNotExist_thenThrowResourceNotFoundException() {
            // assign
            long id = 1L;
            when(employeeRepositories.existsById(id)).thenReturn(false);

            // act & assert
            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                employeeService.deleteEmployeeById(id);
            });

            // assert
            assertEquals("Employee not found with id " + id, exception.getMessage());

            // verify
            verify(employeeRepositories).existsById(id);
            verify(employeeRepositories, never()).deleteById(id); // Ensure deleteById is not called
        }

    @Test
    void test_getAllEmployees_whenEmployeesExist_thenReturnEmployeeDtoList() {
        // assign
        EmployeeEntity employee1 = EmployeeEntity.builder()
                .id(1L)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .salary(100000L)
                .build();

        EmployeeEntity employee2 = EmployeeEntity.builder()
                .id(2L)
                .name("John Smith")
                .email("john.smith@example.com")
                .salary(120000L)
                .build();

        List<EmployeeEntity> employees = Arrays.asList(employee1, employee2);

        // Mocking the repository call
        when(employeeRepositories.findAll()).thenReturn(employees);

        // Mocking the mapping
        EmployeeDto dto1 = EmployeeDto.builder()
                .id(1L)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .salary(100000L)
                .build();

        EmployeeDto dto2 = EmployeeDto.builder()
                .id(2L)
                .name("John Smith")
                .email("john.smith@example.com")
                .salary(120000L)
                .build();

        when(modelMapper.map(employee1, EmployeeDto.class)).thenReturn(dto1);
        when(modelMapper.map(employee2, EmployeeDto.class)).thenReturn(dto2);

        // act
        List<EmployeeDto> result = employeeService.getAllEmployees();

        // assert
        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));

        // verify
        verify(employeeRepositories).findAll();
        verify(modelMapper).map(employee1, EmployeeDto.class);
        verify(modelMapper).map(employee2, EmployeeDto.class);
    }

    @Test
    void test_getAllEmployees_whenNoEmployeesExist_thenReturnEmptyList() {
        // assign
        when(employeeRepositories.findAll()).thenReturn(Collections.emptyList());

        // act
        List<EmployeeDto> result = employeeService.getAllEmployees();

        // assert
        assertTrue(result.isEmpty());

        // verify
        verify(employeeRepositories).findAll();
    }

















}
