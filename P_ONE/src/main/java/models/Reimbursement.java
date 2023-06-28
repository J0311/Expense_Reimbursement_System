package models;


import java.util.Objects;

public class Reimbursement {

    private int r_id;
    private double amount;
    private int e_id;
    private int m_id;
    private String status;
    private String type;

    public Reimbursement(){}

    public Reimbursement(int r_id, double amount, int e_id, int m_id, String status, String type){
        this.r_id = r_id;
        this. amount = amount;
        this.e_id = e_id;
        this.m_id = m_id;
        this.status = status;
        this.type = type;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public int getM_id() {
        return m_id;
    }

    public void setM_id(int m_id) {
        this.m_id = m_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbursementId=" + r_id +
                ", amount=" + amount +
                ", employeeId=" + e_id +
                ", managerId=" + m_id +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reimbursement)) return false;
        Reimbursement that = (Reimbursement) o;
        return getR_id() == that.getR_id() && Double.compare(that.getAmount(), getAmount()) == 0 && getE_id()
                == that.getE_id() && getM_id() == that.getM_id() && Objects.equals(getStatus(),
                that.getStatus()) && Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getR_id(), getAmount(), getE_id(), getM_id(), getStatus(), getType());
    }
}
