package dao;

import dto.JobsFilterDto;
import model.Jobs;
import org.example.dto.JobsFilterDto;
import org.example.model.Jobs;

import java.sql.*;
import java.util.ArrayList;

public class JobDAO {
    private static final String URL = "jdbc:sqlite:C:\\Users\\dev\\IdeaProjects\\HrApiDay06-EV\\src\\main\\java\\org\\example\\hr.db";
    private static final String SELECT_ALL_JOBS = "select * from jobs";
    private static final String SELECT_ONE_JOBS = "select * from jobs where job_id = ?";
    private static final String SELECT_JOBS_WITH_MIN = "select * from jobs where min_Salary = ?";
    private static final String SELECT_JOBS_WITH_MIN_PAGINATION = "select * from jobs where min_Salary = ?order by job_id limit ? offset ?";
    private static final String SELECT_JOBS_WITH_PAGINATION = "select * from jobs order by job_id limit ? offset ?";
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

    public ArrayList<Jobs> selectAllJobs(double minSalary, Integer limit, int offset) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st;

        if(minSalary != 0 && limit != null) {
            System.out.println(SELECT_JOBS_WITH_MIN_PAGINATION);
            st = conn.prepareStatement(SELECT_JOBS_WITH_MIN_PAGINATION);
            st.setDouble(1, minSalary);
            st.setInt(2, limit);
            st.setInt(3, offset);
        }
        else if(minSalary != 0) {
            System.out.println(SELECT_JOBS_WITH_MIN);
            st = conn.prepareStatement(SELECT_JOBS_WITH_MIN);
            st.setDouble(1, minSalary);
        }
        else if(limit != null) {
            System.out.println(SELECT_JOBS_WITH_PAGINATION);
            st = conn.prepareStatement(SELECT_JOBS_WITH_PAGINATION);
            st.setInt(1, limit);
            st.setInt(2, offset);
        }
        else {
            st = conn.prepareStatement(SELECT_ALL_JOBS);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<Jobs> jobs = new ArrayList<>();
        while (rs.next()) {
            jobs.add(new Jobs(rs));
        }

        return jobs;
    }

    public ArrayList<Jobs> selectAllJobs(JobsFilterDto filter) throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection(URL);
        PreparedStatement st;
        if(filter.getMinSalary() != 0 && filter.getLimit() != null) {
            st = conn.prepareStatement(SELECT_JOBS_WITH_MIN_PAGINATION);
            st.setDouble(1, filter.getMinSalary());
            st.setInt(2, filter.getLimit());
            st.setInt(3, filter.getOffset());
        }
        else if(filter.getMinSalary() != 0) {
            st = conn.prepareStatement(SELECT_JOBS_WITH_MIN);
            st.setDouble(1, filter.getMinSalary());
        }
        else if(filter.getLimit() != null) {
            st = conn.prepareStatement(SELECT_JOBS_WITH_PAGINATION);
            st.setInt(1, filter.getLimit());
            st.setInt(2, filter.getOffset());
        }
        else {
            st = conn.prepareStatement(SELECT_ALL_JOBS);
        }
        ResultSet rs = st.executeQuery();
        ArrayList<Jobs> depts = new ArrayList<>();
        while (rs.next()) {
            depts.add(new Jobs(rs));
        }

        return depts;
    }
}