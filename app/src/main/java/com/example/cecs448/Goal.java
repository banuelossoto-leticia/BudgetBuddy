package com.example.cecs448;

public class Goal
{
    private String categoy;
    private String limit;
    private String startDate;

    public Goal(String categoy, String limit, String startDate)
    {
        this.categoy=categoy;
        this.limit=limit;
        this.startDate=startDate;
    }

    public String getCategory() {
        return categoy;
    }

    public void setCategory(String categoy) {
        this.categoy = categoy;
    }

    public String getLimit() { return limit; }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
