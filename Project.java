public class Project {
    private int id;
    private String name;
    private double budget;

    public Project(String name, double budget) {
        this.name = name;
        this.budget = budget;
    }

    public Project(int id, String name, double budget) {
        this.id = id;
        this.name = name;
        this.budget = budget;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getBudget() { return budget; }
}
