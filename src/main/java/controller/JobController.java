package controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import org.example.dao.JobDAO;
import org.example.dto.JobDTO;
import org.example.dto.JobFilter;
import org.example.exceptions.DataNotFoundException;
import org.example.models.Jobs;

//import org.glassfish.jersey.http.HttpHeaders;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/jobs")
public class JobController
{
    JobDAO dao = new JobDAO();
    @Context UriInfo uriInfo;
    @Context HttpHeaders httpHeaders;


    @GET
    public Response getAllJobs() {

        try {
            System.out.println("first getAllJobs");
            GenericEntity<ArrayList<Jobs>> depts = new GenericEntity<ArrayList<Jobs>>(dao.selectAllJob()) {};
            if(httpHeaders.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(depts)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            }

            return Response
//                    .ok()
//                    .entity(depts)
//                    .type(MediaType.APPLICATION_JSON)
                    .ok(depts, MediaType.APPLICATION_JSON)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getAllJobs(
//                @QueryParam("min_salary") Integer min_salary,
//                @QueryParam("limit") Integer limit,
//                @QueryParam("offset") int offset
            @BeanParam JobFilter filter
    ) {

        try {
            System.out.println("second getAllJobs");
            GenericEntity<ArrayList<Jobs>> jobs = new GenericEntity<ArrayList<Jobs>>(dao.selectAllJob(filter)) {};
            if(httpHeaders.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
                return Response
                        .ok(jobs)
                        .type(MediaType.APPLICATION_XML)
                        .build();
            }

            return Response
//                    .ok()
//                    .entity(depts)
//                    .type(MediaType.APPLICATION_JSON)
                    .ok(jobs, MediaType.APPLICATION_JSON)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GET
    @Path("{jobId}")
    public Response getJob(@PathParam("jobId") int jobId) throws SQLException {

        try {
//            dao.selectJob(jobId);

            Jobs jobs = dao.selectJob(jobId);

//            if(httpHeaders.getAcceptableMediaTypes().contains(MediaType.valueOf(MediaType.APPLICATION_XML))) {
//                return Response
//                        .ok(dao)
//                        .type(MediaType.APPLICATION_XML)
//                        .build();
//            }

            if (jobs == null) {
                throw new DataNotFoundException("Jobs " + jobId + "Not found");
            }

            JobDTO dto = new JobDTO();
            dto.setJob_id(jobs.getJob_id());
            dto.setJob_title(jobs.getJob_title());
            dto.setMin_salary(jobs.getMin_salary());
            dto.setMax_salary(jobs.getMax_salary());

            addLinks(dto);

            return Response.ok(dto).build();

//            return Response
////                    .ok()
////                    .type(MediaType.APPLICATION_JSON)
//                    .ok(dao, MediaType.APPLICATION_JSON)
//                    .build();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void addLinks(JobDTO dto) {
        URI selfUri = uriInfo.getAbsolutePath();
        URI empsUri = uriInfo.getAbsolutePathBuilder()
                .path(EmployeeController.class)
                .build();

        dto.addLink(selfUri.toString(), "self");
        dto.addLink(empsUri.toString(), "employees");
    }

    @DELETE
    @Path("{jobId}")
    public Response deleteJob(@PathParam("jobId") int jobId) {

        try {
            dao.deleteJob(jobId);

            NewCookie cookie = (new NewCookie.Builder("username")).value("102").build();
            URI uri = uriInfo.getAbsolutePathBuilder().path(jobId + "").build();
            return Response
//                    .status(Response.Status.CREATED)
                    .created(uri)
//                    .cookie(new NewCookie("username", "OOOOO"))
                    .cookie(cookie)
                    .header("Created by", "Wael")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    public Response insertJob(Jobs job) {

        try {
            dao.insertJob(job);
            NewCookie cookie = (new NewCookie.Builder("username")).value("100").build();
            URI uri = uriInfo.getAbsolutePathBuilder().path(job.getJob_id() + "").build();
            return Response
//                    .status(Response.Status.CREATED)
                    .created(uri)
//                    .cookie(new NewCookie("username", "OOOOO"))
                    .cookie(cookie)
                    .header("Created by", "Wael")
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("{jobId}")
    public Response updateDepartment(@PathParam("jobId") int jobId, Jobs job) {

        try {
            job.setJob_id(jobId);
            dao.updateJob(job);

            NewCookie cookie = (new NewCookie.Builder("username")).value("101").build();
            URI uri = uriInfo.getAbsolutePathBuilder().path(job.getJob_id() + "").build();
            return Response
//                    .status(Response.Status.CREATED)
                    .created(uri)
//                    .cookie(new NewCookie("username", "OOOOO"))
                    .cookie(cookie)
                    .header("Created by", "Wael")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
