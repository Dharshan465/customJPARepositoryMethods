package ford.relationalMapping.assignment18.EmployeeDepartmentManagementSystem.dto;


import java.util.List;

public class DepartmentEmployeeDTO {
    private Long id;
    private String name;
    private String location;
    private List<EmployeeResponseDTO> employees;

    public DepartmentEmployeeDTO() {
    }

    public DepartmentEmployeeDTO(Long id, String name, String location, List<EmployeeResponseDTO> employees) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.employees = employees;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<EmployeeResponseDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeResponseDTO> employees) {
        this.employees = employees;
    }
}
