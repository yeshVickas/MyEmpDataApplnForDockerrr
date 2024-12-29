package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Employee;
import com.example.demo.repo.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
	}

	public Employee saveEmployee(Employee employee){
		return employeeRepository.save(employee);
	}

	  public Employee updateEmployee(Long id, Employee updatedEmployee) {
	        return employeeRepository.findById(id)
	                .map(existingEmployee -> {
	                    existingEmployee.setName(updatedEmployee.getName());
	                    existingEmployee.setEmail(updatedEmployee.getEmail());
	                    existingEmployee.setDepartment(updatedEmployee.getDepartment());
	                    return employeeRepository.save(existingEmployee);
	                })
	                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
	    }
	
	public Employee getEmployeeById(Long id){
		return employeeRepository.findById(id).orElse(null );
	}

	public void deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
	}
	 // PATCH: Update specific fields of the employee
    public Employee partialUpdateEmployee(Long id, Map<String, Object> updates) {
        return employeeRepository.findById(id)
                .map(existingEmployee -> {
                    updates.forEach((key, value) -> {
                        switch (key) {
                            case "name":
                                existingEmployee.setName((String) value);
                                break;
                            case "role":
                                existingEmployee.setDepartment((String) value);
                                break;
                            case "salary":
                                existingEmployee.setEmail((String) value);
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid field: " + key);
                        }
                    });
                    return employeeRepository.save(existingEmployee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }
}