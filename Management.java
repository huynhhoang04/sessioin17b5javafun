import java.sql.*;

public class Management {

    private boolean checkNameExists(String table, String name) {
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean checkIdExists(String table, int id) {
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addEmployee(Employee employee) {
        if (checkNameExists("employees", employee.getName())) {
            System.out.println("Error: Employee name already exists.");
            return;
        }
        String sql = "INSERT INTO employees (name, department, salary) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getName());
            stmt.setString(2, employee.getDepartment());
            stmt.setDouble(3, employee.getSalary());
            stmt.executeUpdate();
            System.out.println("Success: Employee added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProject(Project project) {
        if (checkNameExists("projects", project.getName())) {
            System.out.println("Error: Project name already exists.");
            return;
        }
        String sql = "INSERT INTO projects (name, budget) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, project.getName());
            stmt.setDouble(2, project.getBudget());
            stmt.executeUpdate();
            System.out.println("Success: Project added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignEmployeeToProject(int empId, int projId, String role) {
        if (!checkIdExists("employees", empId)) {
            System.out.println("Error: Employee ID not found.");
            return;
        }
        if (!checkIdExists("projects", projId)) {
            System.out.println("Error: Project ID not found.");
            return;
        }

        String sql = "INSERT INTO assignments (employee_id, project_id, role) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, empId);
            stmt.setInt(2, projId);
            stmt.setString(3, role);
            stmt.executeUpdate();
            System.out.println("Success: Assignment created.");
        } catch (SQLException e) {
            System.out.println("Error: Assignment may already exist or database error.");
        }
    }

    public void updateEmployeeSalary(int empId, double newSalary) {
        if (!checkIdExists("employees", empId)) {
            System.out.println("Error: Employee ID not found.");
            return;
        }
        String sql = "UPDATE employees SET salary = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newSalary);
            stmt.setInt(2, empId);
            stmt.executeUpdate();
            System.out.println("Success: Salary updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listEmployeesAndProjects() {
        String sql = "SELECT e.name AS emp_name, p.name AS proj_name, a.role " +
                     "FROM employees e " +
                     "JOIN assignments a ON e.id = a.employee_id " +
                     "JOIN projects p ON a.project_id = p.id " +
                     "ORDER BY e.name";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.printf("%-20s | %-20s | %-15s\n", "Employee", "Project", "Role");
            System.out.println("------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-20s | %-20s | %-15s\n",
                    rs.getString("emp_name"),
                    rs.getString("proj_name"),
                    rs.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
