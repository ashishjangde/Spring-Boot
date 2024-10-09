package com.example.cacheinspringboot.controllers;

import com.example.cacheinspringboot.dto.EmployeeDto;
import com.example.cacheinspringboot.entities.SalaryEntity;
import com.example.cacheinspringboot.services.EmployeeService;
import com.example.cacheinspringboot.services.SalaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final SalaryService salaryService;

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long employeeId) {
        Optional<EmployeeDto> employeeDto = employeeService.getEmployeeById(employeeId);
       return employeeDto.map(employeeDto1 -> ResponseEntity.ok().body(employeeDto1))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        List<EmployeeDto> employeeDto = employeeService.getAllEmployees();
        return ResponseEntity.ok().body(employeeDto);
    }

   @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto employeeDto1 = employeeService.createEmployee(employeeDto);
        if (employeeDto1 == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(employeeDto1, HttpStatus.CREATED);
   }

   @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> updateEmployeeById(@PathVariable long employeeId , @RequestBody EmployeeDto employeeDto) {
      EmployeeDto employeeDto1 = employeeService.updateEmployee(employeeId,employeeDto);
      if (employeeDto1 == null) return ResponseEntity.notFound().build();
      return new ResponseEntity<>(employeeDto1, HttpStatus.OK);
   }

   @DeleteMapping("/{employeeId}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable long employeeId ) {
        boolean deleted = employeeService.deleteEmployee(employeeId);
        if (!deleted) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(true, HttpStatus.OK);
   }

   @PutMapping("/increment-balance/{employeeId}")
    public ResponseEntity<SalaryEntity> incrementBalance(@PathVariable long employeeId) {
        SalaryEntity updatedSalary = salaryService.updateSalary(employeeId);
        if (updatedSalary == null) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(updatedSalary, HttpStatus.OK);
   }

}
