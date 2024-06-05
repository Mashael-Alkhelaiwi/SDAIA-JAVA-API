package dto;
import jakarta.ws.rs.QueryParam;

public class JobsFilterDto {
    private @QueryParam("minSalary") double minSalary;
    private @QueryParam("limit") Integer limit;
    private @QueryParam("offset") int offset;

    public double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}