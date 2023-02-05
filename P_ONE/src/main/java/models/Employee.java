package models;

import java.util.Objects;

public class Employee {

    private int e_id;
    private String fName;
    private String lName;
    private String username;
    private String email;
    private String password;

    //public static List<Employee> employees = new ArrayList<>(); //

    public Employee(){}

    public Employee(int e_id, String fName, String lName, String username, String password, String email) {
        this.e_id = e_id;
        this.fName = fName;
        this.lName = lName;
        this.username = username;
        this.password = password;
        this.email = email;
        //employees.add(this);
    }

    public String getfName() {

        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String employeeId) {
        this.username = username;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "fName='" + fName + '\'' +
                ", lName ='" + lName + '\'' +
                ", email ='" + email + '\'' +
                ", e_id =" + e_id +
                ", password ='" + password + '\'' +
                ", username ='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return getE_id() == employee.getE_id() && Objects.equals(getfName(), employee.getfName()) &&
                Objects.equals(getlName(), employee.getlName()) && Objects.equals(getEmail(),
                employee.getEmail()) && Objects.equals(getPassword(), employee.getPassword()) &&
                Objects.equals(getUsername(), employee.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getfName(), getlName(), getEmail(), getE_id(), getPassword(), getUsername());
    }
}
// valid
