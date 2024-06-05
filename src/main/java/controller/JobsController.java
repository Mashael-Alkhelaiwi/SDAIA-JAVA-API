package controller;

import dao.JobDAO;
import dto.JobsFilterDto;
import exceptions.DataNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.Jobs;
import org.example.dao.JobDAO;
import org.example.dto.JobsFilterDto;
import org.example.model.Jobs;

import java.sql.SQLException;
import java.util.ArrayList;
@Path("/jobs")
public class JobsController {

    JobDAO dao = new JobDAO();

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ArrayList<Jobs> selectAllDepts(
            //       @QueryParam("minSalary") double minSalary,
            //       @QueryParam("limit") Integer limit,
            //       @QueryParam("offset") int offset
            @BeanParam JobsFilterDto filter

    ){
        try {
            GenericEntity<ArrayList<Jobs>> jobss= new GenericEntity<Jobs>(dao.selectAllJobs());
            if (headers.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML)))
            return Response
                    .ok(jobss)
                    .type(MediaType.APPLICATION_XML)
                    .build();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{jobID}")
    public Jobs getJobs(@PathParam("jobID") int jobID) throws SQLException {

        try {
            Jobs jobs = dao.selectJobs(jobID);

            if (jobs == null)
            {
                throw new DataNotFoundException("Jobs"+ jobID + "Not found")
            }
            JobsFilterDto dto = new Jobs();
            dto.setjobId(jobs.getJobID());
            dto.setjobnTitle(jobs.getJobnTitle());
            dto.setMinSalary(jobs.getMinSalary());
            return dao.selectJobs(jobID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DELETE
    @Path("{jobID}")
    public void deleteJobs(@PathParam("jobID") int jobId) {

        try {
            dao.deleteJobs(jobId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void insertJobs(Jobs job) {

        try {
            dao.insertJobs(job);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("{jobID}")
    public void updateJobs(@PathParam("jobID") int jobID,Jobs job) {

        try {
            job.setJobID(jobID);
            dao.updateJobs(job);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Path("{jobID}/jobs")
    public JobsController getJobsController() {
        return new JobsController();
    }


}
