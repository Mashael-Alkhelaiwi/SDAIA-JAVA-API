package controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.dao.JobDAO;
import org.example.dto.JobsFilterDto;
import org.example.models.Jobs;

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

            // return dao.selectAllDepts(minSalary,limit,offset);
            return dao.selectAllJobs(filter);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("{jobID}")
    public Jobs getJobs(@PathParam("jobID") int jobID) {

        try {
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
