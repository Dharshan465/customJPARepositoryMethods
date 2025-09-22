package ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem;

import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.DepartmentDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.DepartmentEmployeeDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.EmployeeDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto.EmployeeDepartmentDTO;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.service.DepartmentService;
import ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeDepartmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeDepartmentApplication.class, args);

    }
    @Bean
    public CommandLineRunner run(DepartmentService departmentService, EmployeeService employeeService) {
        return args -> {
            System.out.println("Initializing sample data...");

            // Create Departments
            DepartmentDTO hrDept = new DepartmentDTO(null, "Human Resources", "Building A, Floor 1");
            DepartmentDTO itDept = new DepartmentDTO(null, "Information Technology", "Building B, Floor 3");
            DepartmentDTO salesDept = new DepartmentDTO(null, "Sales", "Building C, Floor 2");

            hrDept = departmentService.createDepartment(hrDept);
            itDept = departmentService.createDepartment(itDept);
            salesDept = departmentService.createDepartment(salesDept);

            System.out.println("Created Departments:");
            System.out.println("HR: " + hrDept.getName() + " (ID: " + hrDept.getId() + ")");
            System.out.println("IT: " + itDept.getName() + " (ID: " + itDept.getId() + ")");
            System.out.println("Sales: " + salesDept.getName() + " (ID: " + salesDept.getId() + ")");

            // Create Employees
            EmployeeDTO emp1 = new EmployeeDTO(null, "Alice", "Smith", "alice.smith@example.com", 70000.0, hrDept.getId());
            EmployeeDTO emp2 = new EmployeeDTO(null, "Bob", "Johnson", "bob.j@example.com", 85000.0, itDept.getId());
            EmployeeDTO emp3 = new EmployeeDTO(null, "Charlie", "Brown", "charlie.b@example.com", 60000.0, salesDept.getId());
            EmployeeDTO emp4 = new EmployeeDTO(null, "Diana", "Prince", "diana.p@example.com", 95000.0, itDept.getId());
            EmployeeDTO emp5 = new EmployeeDTO(null, "Eve", "Adams", "eve.a@example.com", 72000.0, hrDept.getId());

            employeeService.createEmployeeWithDepartmentId(emp1);
            EmployeeDTO createdBobJohnson = employeeService.createEmployeeWithDepartmentId(emp2);
            employeeService.createEmployeeWithDepartmentId(emp3);
            employeeService.createEmployeeWithDepartmentId(emp4);
            employeeService.createEmployeeWithDepartmentId(emp5);

            System.out.println("Created Employees.");
            System.out.println("Sample data initialization complete.");

            // Demonstrate lazy loading for departments (without employees)
            System.out.println("\n--- Listing all departments (without employees) ---");
            departmentService.getAllDepartments().forEach(dept ->
                    System.out.println("Department ID: " + dept.getId() + ", Name: " + dept.getName() + ", Location: " + dept.getLocation())
            );

            // Demonstrate lazy loading for a specific department (with employees)
            System.out.println("\n--- Getting IT Department with its employees ---");
            DepartmentEmployeeDTO itDepartmentWithEmployees = departmentService.getDepartmentById(itDept.getId());
            System.out.println("Department: " + itDepartmentWithEmployees.getName() + " (ID: " + itDepartmentWithEmployees.getId() + ")");
            itDepartmentWithEmployees.getEmployees().forEach(emp ->
                    System.out.println("  Employee: " + emp.getFirstName() + " " + emp.getLastName() + " (Email: " + emp.getEmail() + ")")
            );

            // Demonstrate lazy loading for an employee (with department info)
            System.out.println("\n--- Getting Employee Bob Johnson with department info ---");
            // Use the ID from the previously created Bob Johnson
            EmployeeDepartmentDTO bob = employeeService.getEmployeeById(createdBobJohnson.getId());
            System.out.println("Employee: " + bob.getFirstName() + " " + bob.getLastName() + " (Email: " + bob.getEmail() + ")");
            System.out.println("  Department: " + bob.getDepartment().getName() + " (ID: " + bob.getDepartment().getId() + ")");
            System.out.println(" Department Location: " + bob.getDepartment().getLocation());
        };

    }
}
