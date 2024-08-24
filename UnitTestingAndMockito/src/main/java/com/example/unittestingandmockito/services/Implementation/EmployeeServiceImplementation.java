package com.example.unittestingandmockito.services.Implementation;

import com.example.unittestingandmockito.dto.EmployeeDto;
import com.example.unittestingandmockito.entities.EmployeeEntity;
import com.example.unittestingandmockito.exceptions.EmployeeValidationException;
import com.example.unittestingandmockito.exceptions.ExistingResourceFoundException;
import com.example.unittestingandmockito.exceptions.ResourceNotFoundException;
import com.example.unittestingandmockito.repositories.EmployeeRepositories;
import com.example.unittestingandmockito.services.EmployeeService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImplementation implements EmployeeService {
    private final EmployeeRepositories employeeRepositories;
    private final ModelMapper modelMapper;
    private final Validator validator;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeEntity> employee = employeeRepositories.findAll();
        return  employee
                .stream()
                .map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @Override
      public Optional <EmployeeDto> getEmployeeById(long employeeId) {
        Optional<EmployeeEntity> employee = employeeRepositories.findById(employeeId);
       if (employee.isEmpty()){
           log.error("Employee with id {} not found", employeeId);
            throw  new ResourceNotFoundException("Cannot find employee with id " + employeeId);
       }
       log.info("Employee with id {} found", employeeId);
       log.info("Employee {}", employee);
      return employee.map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class));
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employee = employeeRepositories.findByEmail(employeeDto.getEmail());
        if (employee != null){
            log.error("Employee with email {} found you can try with another email and try login to this email", employeeDto.getEmail());
            throw new ExistingResourceFoundException("Employee with email " + employeeDto.getEmail() + " already exists");
        }
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto,EmployeeEntity.class);
        log.info("Employee  with data :- {} created ", employeeEntity);
        employeeRepositories.save(employeeEntity);
        log.info("Employee saved");
        return modelMapper.map(employeeEntity,EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(long employeeId, EmployeeDto employeeDto) {
        log.info("Updating employee with ID: {}", employeeId);

        boolean isFound = employeeRepositories.existsById(employeeId);
        if (!isFound) {
            log.error("Employee not found with ID: {}", employeeId);
            throw new ResourceNotFoundException("Employee not found with id " + employeeId);
        }
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        employeeRepositories.save(employeeEntity);

        log.info("Employee with ID: {} has been updated successfully", employeeId);
        return modelMapper.map(employeeEntity, EmployeeDto.class);
    }


    public EmployeeDto updatePartialEmployeeById(long employeeId, Map<String, Object> updates) {
        // Fetch the employee entity
        EmployeeEntity employee = employeeRepositories.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));

        // Apply updates directly to the employee entity
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findRequiredField(EmployeeEntity.class, key);
            field.setAccessible(true);
            if (field.getType().equals(Long.class) && value instanceof Integer) {
                value = Long.valueOf((Integer) value);
            }
            ReflectionUtils.setField(field, employee, value);
        });

        // Manually validate the updated entity
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        Set<ConstraintViolation<EmployeeDto>> violations = validator.validate(employeeDto);
        if (!violations.isEmpty()) {
            List<String> errors = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            throw new EmployeeValidationException(errors);
        }

        // Save the updated employee entity
        EmployeeEntity updatedEmployee = employeeRepositories.save(employee);

        // Return the updated DTO
        return modelMapper.map(updatedEmployee, EmployeeDto.class);
    }


    @Override
    public boolean deleteEmployeeById(long employeeId) {
        boolean isFound = employeeRepositories.existsById(employeeId);
        if (!isFound) throw new ResourceNotFoundException("Employee not found with id " + employeeId);
        employeeRepositories.deleteById(employeeId);
        return true;
    }
}
