package com.example.unittestingandmockito.services.Implementation;

import com.example.unittestingandmockito.dto.EmployeeDto;
import com.example.unittestingandmockito.entities.EmployeeEntity;
import com.example.unittestingandmockito.exceptions.ResourceNotFoundException;
import com.example.unittestingandmockito.repositories.EmployeeRepositories;
import com.example.unittestingandmockito.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImplementation implements EmployeeService {
    private final EmployeeRepositories employeeRepositories;
    private final ModelMapper modelMapper;

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
      return employee.map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDto.class));
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto,EmployeeEntity.class);
        employeeRepositories.save(employeeEntity);
        return modelMapper.map(employeeEntity,EmployeeDto.class);
    }

    @Override
    public EmployeeDto updateEmployee(long employeeId, EmployeeDto employeeDto) {
        boolean isFound = employeeRepositories.existsById(employeeId);
        if (!isFound) throw new ResourceNotFoundException("Employee not found with id " + employeeId);
        EmployeeEntity employeeEntity = modelMapper.map(employeeDto,EmployeeEntity.class);
        employeeRepositories.save(employeeEntity);
        return modelMapper.map(employeeEntity,EmployeeDto.class);
    }

    @Override
    public EmployeeDto updatePartialEmployeeById(long employeeId, Map<String, Object> employee) {
       boolean isFound = employeeRepositories.existsById(employeeId);
       if (!isFound) throw new ResourceNotFoundException("Employee not found with id " + employeeId);
       employee.forEach((key, value) -> {
           Field field = ReflectionUtils.findRequiredField(EmployeeEntity.class, key);
           field.setAccessible(true);
           ReflectionUtils.setField(field,EmployeeEntity.class, value);
       });
       return modelMapper.map(employee,EmployeeDto.class);
    }

    @Override
    public boolean deleteEmployeeById(long employeeId) {
        boolean isFound = employeeRepositories.existsById(employeeId);
        if (!isFound) throw new ResourceNotFoundException("Employee not found with id " + employeeId);
        employeeRepositories.deleteById(employeeId);
        return true;
    }
}
