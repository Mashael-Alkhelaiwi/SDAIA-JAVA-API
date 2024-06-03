package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Jobs {
    private int jobID;
    private String jobnTitle;
    private int minSalary;
    private double mxnSalary;

    public Jobs(ResultSet rs) throws SQLException {
        jobID = rs.getInt("job_Id");
        jobnTitle = rs.getString("job_title");
        minSalary = rs.getInt("min_Salary");
    }

    public Jobs(int jobID, String jobnTitle, int minSalary) {
        this.jobID = jobID;
        this.jobnTitle = jobnTitle;
        this.minSalary = minSalary;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getJobnTitle() {
        return jobnTitle;
    }

    public void setJobnTitle(String jobnTitle) {
        this.jobnTitle = jobnTitle;
    }

    public double getMxnSalary() {
        return mxnSalary;
    }

    public double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    @Override
    public String toString() {
        return "Jobs{" +
                "jobID=" + jobID +
                ", jobnTitle='" + jobnTitle + '\'' +
                ", minSalary=" + minSalary +
                '}';
    }

}
