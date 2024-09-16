package com.example.cacheinspringboot.services.serviceImplementation;

import com.example.cacheinspringboot.dto.EmployeeDto;
import com.example.cacheinspringboot.entities.EmployeeEntity;
import com.example.cacheinspringboot.repositories.EmployeeRepositories;
import com.example.cacheinspringboot.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeeRepositories employeeRepositories;
    private final ModelMapper modelMapper;


    @Cacheable(cacheNames = "employee", key = "#employeeId")
    @Override
    public Optional<EmployeeDto> getEmployeeById(long employeeId) {
        EmployeeEntity employee = employeeRepositories.findById(employeeId).orElse(null);
        if (employee == null)  throw new RuntimeException("Employee not found with id " + employeeId);
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);
        return Optional.of(employeeDto);
    }

    @Override
    @Cacheable("employee")
    public List<EmployeeDto> getAllEmployees() {
       List <EmployeeEntity> employees = employeeRepositories.findAll();
       return employees.stream()
               .map(employee -> modelMapper.map(employee, EmployeeDto.class))
               .collect(Collectors.toList());
    }

    @Override
    @CachePut(cacheNames = "employee", key = "#result.employeeId")
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
      EmployeeEntity employeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class);
      employeeRepositories.save(employeeEntity);
      return modelMapper.map(employeeEntity, EmployeeDto.class);
    }

    @Override
    @CachePut(cacheNames = "employee", key = "#result.employeeId")
    public EmployeeDto updateEmployee(long id, EmployeeDto employeeDto) {
       EmployeeEntity employeeEntity = employeeRepositories.findById(id).orElse(null);
       if (employeeEntity == null)  throw new RuntimeException("Employee not found with id " + id);
       EmployeeEntity updatedEmployeeEntity = modelMapper.map(employeeDto, EmployeeEntity.class);
       updatedEmployeeEntity.setEmployeeId(id);
       employeeRepositories.save(updatedEmployeeEntity);
       return modelMapper.map(employeeEntity, EmployeeDto.class);
    }

    @Override
    @CacheEvict(cacheNames = "employee", key = "#employeeId")
    public boolean deleteEmployee(long employeeId) {
       boolean exists = employeeRepositories.existsById(employeeId);
       if(!exists) throw new RuntimeException("Employee not found with id " + employeeId);
       employeeRepositories.deleteById(employeeId);
       return true;
    }
}
