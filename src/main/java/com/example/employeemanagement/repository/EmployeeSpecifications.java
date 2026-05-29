package com.example.employeemanagement.repository;
import com.example.employeemanagement.model.Employee;
import org.springframework.data.jpa.domain.Specification;
public class EmployeeSpecifications {
    public static Specification<Employee> hasDepartment(String department) {
        return (root, query, cb) -> {
            if (department == null || department.isEmpty()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("department"), department);
        };
    }

    public static Specification<Employee> hasName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Employee> hasEmail(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isEmpty()) {
                return cb.conjunction();
            }
            return cb.equal(root.get("email"), email);
        };
    }
}


