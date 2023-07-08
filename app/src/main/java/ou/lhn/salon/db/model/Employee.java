package ou.lhn.salon.db.model;

public class Employee {
    private int id;
    private String salary;
    private String identityCardNumber;

    public Employee() {
    }

    public Employee(int id, String salary, String identityCardNumber) {
        this.id = id;
        this.salary = salary;
        this.identityCardNumber = identityCardNumber;
    }

    public int getId() {
        return id;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }
}
