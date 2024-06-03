package dao;
import model.Jobs;

import java.sql.*;
import java.util.ArrayList;

public class JobDAO {
    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\Desktop\\JavaBasics-master\\src\\main\\java\\day4\\hr.db";
    private static final String SELECT_ALL_JOBS = "select * from jobs";
    private static final String SELECT_ONE_JOBS = "select * from jobs where job_id = ?";
    private static final String INSERT_JOBS = "insert into jobs values(?, ?, ?, ?)";
    private static final String UPDATE_JOBS = "update jobs set job_Title = ?, min_Salary = ? ,mxn_Salary = ? where job_id = ?";
    private static final String DELETE_JOBS = "delete from jobs where job_id = ?";

    public void insertJobs(Jobs j) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(INSERT_JOBS);
        st.setInt(1, j.getJobID());
        st.setString(2, j.getJobnTitle());
        st.setDouble(3, j.getMinSalary());
        st.setDouble(4, j.getMxnSalary());

        st.executeUpdate();
    }

    public void updateJobs(Jobs j) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(UPDATE_JOBS);
        st.setInt(4, j.getJobID());
        st.setString(1, j.getJobnTitle());
        st.setDouble(2, j.getMinSalary());
        st.setDouble(3, j.getMxnSalary());
        st.executeUpdate();
    }

    public void deleteJobs(int jobId) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(DELETE_JOBS);
        st.setInt(1, jobId);
        st.executeUpdate();
    }

    public Jobs selectJobs(int jobId) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(SELECT_ONE_JOBS);
        st.setInt(1, jobId);
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            return new Jobs(rs);
        }
        else {
            return null;
        }
    }

    public ArrayList<Jobs> selectAllDepts() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st = conn.prepareStatement(SELECT_ALL_JOBS);
        ResultSet rs = st.executeQuery();
        ArrayList<Jobs> jobs = new ArrayList<>();
        while (rs.next()) {
            jobs.add(new Jobs(rs));
        }

        return jobs;
    }

}