package models;

import java.util.Objects;

public class Manager {

    private int m_id;
    private String fName;
    private String lName;
    private String username;
    private String email;
    private String password;

    public Manager(){}


    public Manager(int m_id, String fName, String lName, String username, String password, String email) {
        this.m_id = m_id;
        this.fName = fName;
        this.lName = lName;
        this.username = username;
        this.password = password;
        this.email = email;
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

    public int getM_id() {return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "fName ='" + fName + '\'' +
                ", lName ='" + lName + '\'' +
                ", email ='" + email + '\'' +
                ", m_idD =" + m_id +
                ", password ='" + password + '\'' +
                ", username ='" + username + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manager)) return false;
        Manager m = (Manager) o;
        return getM_id() == m.getM_id() && Objects.equals(getfName(), m.getfName()) &&
                Objects.equals(getlName(), m.getlName()) && Objects.equals(getEmail(),
                m.getEmail()) && Objects.equals(getPassword(), m.getPassword()) &&
                Objects.equals(getUsername(), m.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getfName(), getlName(), getEmail(), getM_id(), getPassword(), getUsername());
    }
}