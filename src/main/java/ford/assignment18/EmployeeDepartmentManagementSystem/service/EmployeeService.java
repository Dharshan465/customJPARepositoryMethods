package ford.assignment18.EmployeeDepartmentManagementSystem.service;

import ford.assignment18.EmployeeDepartmentManagementSystem.dto.DepartmentDTO;
import ford.assignment18.EmployeeDepartmentManagementSystem.dto.EmployeeDTO;
import ford.assignment18.EmployeeDepartmentManagementSystem.dto.EmployeeDepartmentDTO;

public interface EmployeeService {
    EmployeeDTO  createEmployeeWithDepartmentId(EmployeeDTO employeeDTO);

    EmployeeDepartmentDTO getEmployeeById(Long id);
    void deleteEmployee(Long id);

}
