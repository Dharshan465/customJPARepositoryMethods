package ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.service;

import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.DepartmentDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.DepartmentEmployeeDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);

    DepartmentEmployeeDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();

    void deleteDepartment(Long id);
}
