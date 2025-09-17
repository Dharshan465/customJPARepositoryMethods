package ford.assignment18.EmployeeDepartmentManagementSystem.service;

import ford.assignment18.EmployeeDepartmentManagementSystem.dto.*;
import ford.assignment18.EmployeeDepartmentManagementSystem.exception.DepartmentException;
import ford.assignment18.EmployeeDepartmentManagementSystem.model.*;
import ford.assignment18.EmployeeDepartmentManagementSystem.repository.DepartmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImplementation implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Autowired
    public DepartmentServiceImplementation(DepartmentRepository departmentRepository, RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        this.departmentRepository = departmentRepository;
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
    }

    @Override
    @Transactional
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setLocation(departmentDTO.getLocation());
        Department savedDepartment = departmentRepository.save(department);
        return new DepartmentDTO(savedDepartment.getId(), savedDepartment.getName(), savedDepartment.getLocation());
    }

    @Override
    @Transactional
    public DepartmentEmployeeDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentException("Department not found with id: " + id));

        // Accessing employees list here triggers lazy loading within the @Transactional context [2]
        List<EmployeeResponseDTO> employees = department.getEmployees().stream()
                .map(employee -> new EmployeeResponseDTO(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getEmail(),
                        employee.getSalary()))
                .collect(Collectors.toList());

        return new DepartmentEmployeeDTO(department.getId(), department.getName(), department.getLocation(), employees);
    }

    @Override
    @Transactional
    public List<DepartmentDTO> getAllDepartments() {
        // This method demonstrates lazy fetching as it doesn't access the employees collection,
        // so employees are not loaded.
        return departmentRepository.findAll().stream()
                .map(department -> new DepartmentDTO(department.getId(), department.getName(), department.getLocation()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentException("Department not found with id: " + id);
        }
        // Due to CascadeType.ALL and orphanRemoval=true on Department.employees,
        // associated employees will also be deleted. [1]
        departmentRepository.deleteById(id);
    }
}