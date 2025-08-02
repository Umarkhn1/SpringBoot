package amaliy.java.amaliy1.model;

public class DepartmentStats {
    private String departmentName;
    private long count;
    private double percentage;

    public DepartmentStats(String departmentName, long count, double percentage) {
        this.departmentName = departmentName;
        this.count = count;
        this.percentage = percentage;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
