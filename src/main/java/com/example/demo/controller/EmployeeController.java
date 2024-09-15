package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;

	@PostMapping("/employees")
	public String createNewEmployee(@RequestBody List<Employee> employee) {
		employeeRepository.saveAll(employee);
		return "Employee Database Created";

	}

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> empList = new ArrayList<>();
		employeeRepository.findAll().forEach(empList::add);
		return new ResponseEntity<List<Employee>>(empList, HttpStatus.OK);
	}

	@PutMapping("/employees/{empid}")
	public String updateEmployeeById(@PathVariable long empid, @RequestBody Employee employee) {
		Optional<Employee> emp = employeeRepository.findById(empid);
		if (emp.isPresent()) {
			Employee existEmp = emp.get();
			existEmp.setEmpAge(employee.getEmpAge());
			existEmp.setEmpCity(employee.getEmpCity());
			existEmp.setEmpName(employee.getEmpName());
			existEmp.setEmpSalary(employee.getEmpSalary());
			employeeRepository.save(existEmp);
			return "Employee Details against Id " + empid + " updated";
		} else {
			return "Employee Details does not exist for empid " + empid;
		}
	}

	@DeleteMapping("/employees/{empId}")
	public String deleteEmployeeByEmpId(@PathVariable Long empId) {
		employeeRepository.deleteById(empId);
		return "Employee Deleted Successfully";
	}

}
