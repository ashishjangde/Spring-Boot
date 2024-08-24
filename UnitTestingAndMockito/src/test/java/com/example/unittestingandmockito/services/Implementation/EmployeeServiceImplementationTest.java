package com.example.unittestingandmockito.services.Implementation;

import com.example.unittestingandmockito.TestContainerConfiguration;
import com.example.unittestingandmockito.dto.EmployeeDto;
import com.example.unittestingandmockito.entities.EmployeeEntity;
import com.example.unittestingandmockito.exceptions.ExistingResourceFoundException;
import com.example.unittestingandmockito.exceptions.ResourceNotFoundException;
import com.example.unittestingandmockito.repositories.EmployeeRepositories;

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

import java.util.Map;
import java.util.Optional;

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

        // assert
        Assertions.assertThat(exception.getMessage()).isEqualTo("Employee with email " + mockEmployeeDto.getEmail() + " already exists");

        // verify
        verify(employeeRepositories).findByEmail(mockEmployeeDto.getEmail());
        verify(employeeRepositories, never()).save(any(EmployeeEntity.class)); // Save should not be called
    }


    @Test
    void updateEmployee_whenIdIsCorrect_thenEmployeeShouldBeUpdated() {
        //assign
        long id = 1L;
        when(employeeRepositories.existsById(id)).thenReturn(true);
        when(employeeRepositories.save(any(EmployeeEntity.class))).thenReturn(mockEmployeeEntity);
        //act
        EmployeeDto updatedEmployee = employeeService.updateEmployee(id, mockEmployeeDto);
        //assert
        assertNotNull(updatedEmployee);
        ArgumentCaptor<EmployeeEntity> captor = ArgumentCaptor.forClass(EmployeeEntity.class);
        verify(employeeRepositories).save(captor.capture());
        verify(employeeRepositories).existsById(id);
        EmployeeEntity captorEmployeeEntity = captor.getValue();
        Assertions.assertThat(captorEmployeeEntity.getEmail()).isEqualTo(mockEmployeeEntity.getEmail());
    }

    @Test
    void updateEmployee_whenIdIsIncorrect_thenResourceNotFoundExceptionShouldBeThrown() {
        // assign
        long id = 1L;
        when(employeeRepositories.existsById(id)).thenReturn(false);

        // act & assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updateEmployee(id, mockEmployeeDto);
        });

        // verify
        Assertions.assertThat(exception.getMessage()).isEqualTo("Employee not found with id " + id);
        verify(employeeRepositories).existsById(id);
        verify(employeeRepositories, never()).save(any(EmployeeEntity.class));  // save should never be called
    }

    @Test
    void updatePartialEmployeeById_whenIdIsCorrectAndFieldsAreValid_thenEmployeeShouldBeUpdated() {
        // Assign
        long id = 1L;
        Map<String, Object> updates = Map.of("name", "Ashish Jangde", "salary", 120000L);

        // Stubbing
        when(employeeRepositories.existsById(id)).thenReturn(true);

        // Act
        EmployeeDto updatedEmployee = employeeService.updatePartialEmployeeById(id, updates);

        // Assert
        Assertions.assertThat(updatedEmployee).isNotNull();
        Assertions.assertThat(updatedEmployee.getName()).isEqualTo("Ashish Jangde");
        Assertions.assertThat(updatedEmployee.getSalary()).isEqualTo(120000L);

        // Verify interactions
        verify(employeeRepositories).existsById(id);
        // Verify the log messages as needed (depends on your logging framework and setup)
    }

    @Test
    void updatePartialEmployeeById_whenIdIsInvalid_thenExceptionShouldBeThrown() {
        // Assign
        long id = 1L;
        Map<String, Object> updates = Map.of("name", "Updated Name");

        // Stubbing
        when(employeeRepositories.existsById(id)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.updatePartialEmployeeById(id, updates);
        });

        Assertions.assertThat(thrown.getMessage()).isEqualTo("Employee not found with id " + id);

        // Verify interactions
        verify(employeeRepositories).existsById(id);
        // Verify the log messages as needed (depends on your logging framework and setup)
    }








}
