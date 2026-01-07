import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Management management = new Management();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== COMPANY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Employee");
            System.out.println("2. Add Project");
            System.out.println("3. Assign Employee to Project");
            System.out.println("4. List Employees & Projects");
            System.out.println("5. Update Employee Salary");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                choice = -1;
            }

            switch (choice) {
                case 1:
                    handleAddEmployee();
                    break;
                case 2:
                    handleAddProject();
                    break;
                case 3:
                    handleAssign();
                    break;
                case 4:
                    management.listEmployeesAndProjects();
                    break;
                case 5:
                    handleUpdateSalary();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void handleAddEmployee() {
        try {
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            if(name.isEmpty()) throw new Exception("Name empty");
            
            System.out.print("Enter Department: ");
            String dept = scanner.nextLine();
            
            System.out.print("Enter Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            management.addEmployee(new Employee(name, dept, salary));
        } catch (Exception e) {
            System.out.println("Input Error: " + e.getMessage());
        }
    }

    private static void handleAddProject() {
        try {
            System.out.print("Enter Project Name: ");
            String name = scanner.nextLine();
            if(name.isEmpty()) throw new Exception("Name empty");

            System.out.print("Enter Budget: ");
            double budget = Double.parseDouble(scanner.nextLine());

            management.addProject(new Project(name, budget));
        } catch (Exception e) {
            System.out.println("Input Error: " + e.getMessage());
        }
    }

    private static void handleAssign() {
        try {
            System.out.print("Enter Employee ID: ");
            int empId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Project ID: ");
            int projId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Role: ");
            String role = scanner.nextLine();

            management.assignEmployeeToProject(empId, projId, role);
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a number.");
        }
    }

    private static void handleUpdateSalary() {
        try {
            System.out.print("Enter Employee ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter New Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            management.updateEmployeeSalary(id, salary);
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format.");
        }
    }
}
