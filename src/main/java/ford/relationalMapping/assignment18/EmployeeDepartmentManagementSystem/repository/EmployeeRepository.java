package ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.repository;

import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
