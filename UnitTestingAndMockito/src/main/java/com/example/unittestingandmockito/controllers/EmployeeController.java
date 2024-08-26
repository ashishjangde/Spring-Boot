package com.example.unittestingandmockito.controllers;


import com.example.unittestingandmockito.dto.EmployeeDto;
import com.example.unittestingandmockito.services.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping( "/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employeeDto = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeDto);
    }

    @GetMapping( "/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long employeeId) {
       Optional <EmployeeDto> employeeDto = employeeService.getEmployeeById(employeeId);
        return employeeDto.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<EmployeeDto> createNewEmployee(@Valid  @RequestBody EmployeeDto employeeDto) {
        EmployeeDto employee = employeeService.createEmployee(employeeDto);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployeeById(@PathVariable long employeeId , @Valid @RequestBody EmployeeDto employeeDto) {
        EmployeeDto employee = employeeService.updateEmployee(employeeId, employeeDto);
        if (employee == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PatchMapping("{employeeId}")
    public ResponseEntity<EmployeeDto> updatePartialEmployeeById(@PathVariable long employeeId , @Valid @RequestBody Map<String,Object> employee) {
        EmployeeDto employeeDto = employeeService.updatePartialEmployeeById(employeeId,employee);
        if (employeeDto == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(employeeDto, HttpStatus.OK);
    }

    @DeleteMapping("{employeeId}")
    public ResponseEntity<EmployeeDto> deleteEmployeeById(@PathVariable long employeeId) {
        boolean deleted = employeeService.deleteEmployeeById(employeeId);
        if (!deleted) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
