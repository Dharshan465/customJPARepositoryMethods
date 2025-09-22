package ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.controller;

import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.EmployeeDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.EmployeeDepartmentDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployeeWithDepartmentId(employeeDTO);
    }
    @GetMapping("/{id}")
    public EmployeeDepartmentDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully with id: " + id;
    }



}
