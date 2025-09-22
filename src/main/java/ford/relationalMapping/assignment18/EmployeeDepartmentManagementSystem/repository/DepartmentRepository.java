package ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.repository;

import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
