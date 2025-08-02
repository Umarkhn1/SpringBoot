package amaliy.java.amaliy1.model;

public class EmployeeSpending {
        private String fullName;
        private double totalSpent;

        public EmployeeSpending(String fullName, double totalSpent) {
            this.fullName = fullName;
            this.totalSpent = totalSpent;
        }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }
}
