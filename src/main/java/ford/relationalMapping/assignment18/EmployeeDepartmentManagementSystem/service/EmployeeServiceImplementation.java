package ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.service;

import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.DepartmentDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.EmployeeDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.EmployeeDepartmentDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.model.Department;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.model.Employee;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.exception.DepartmentException;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.exception.EmployeeException;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.repository.DepartmentRepository;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    @Autowired
    public EmployeeServiceImplementation(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployeeWithDepartmentId(EmployeeDTO employeeDTO) {
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new DepartmentException("Department not found with id: " + employeeDTO.getDepartmentId()));

        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setSalary(employeeDTO.getSalary());
        employee.setDepartment(department); // Set the managed Department entity

        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDTO(savedEmployee.getId(), savedEmployee.getFirstName(), savedEmployee.getLastName(),
                savedEmployee.getEmail(), savedEmployee.getSalary(), savedEmployee.getDepartment().getId());
    }

    @Override
    @Transactional(readOnly = true) // Read-only transaction for fetching
    public EmployeeDepartmentDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeException("Employee not found with id: " + id));

        // Accessing employee.getDepartment() here triggers lazy loading within the @Transactional context [2]
        Department department = employee.getDepartment();

        DepartmentDTO departmentDTO = null;
        if (department != null) {
            departmentDTO = new DepartmentDTO(department.getId(), department.getName(), department.getLocation());
        }

        return new EmployeeDepartmentDTO(employee.getId(), employee.getFirstName(), employee.getLastName(),
                employee.getEmail(), employee.getSalary(), departmentDTO);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);

    }


}
