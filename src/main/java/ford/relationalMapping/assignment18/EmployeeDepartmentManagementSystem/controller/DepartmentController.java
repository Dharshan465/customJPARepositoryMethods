package ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.controller;

import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.DepartmentDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.DepartmentEmployeeDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.exception.DepartmentException;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.exception.EmployeeException;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public DepartmentDTO createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) throws EmployeeException , DepartmentException {
        return departmentService.createDepartment(departmentDTO);
    }

    @GetMapping("/{id}")
    public DepartmentEmployeeDTO getDepartmentById(@PathVariable Long id)throws EmployeeException , DepartmentException {
        return departmentService.getDepartmentById(id);
    }

    @GetMapping
    public List<DepartmentDTO> getAllDepartments() throws EmployeeException , DepartmentException {
        return departmentService.getAllDepartments();
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "Department deleted successfully with id: " + id;

    }
}