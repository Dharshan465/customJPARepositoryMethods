package ford.assignment18.EmployeeDepartmentManagementSystem.repository;

import ford.assignment18.EmployeeDepartmentManagementSystem.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.beans.JavaBean;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
