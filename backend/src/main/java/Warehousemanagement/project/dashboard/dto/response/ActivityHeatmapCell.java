package Warehousemanagement.project.dashboard.dto.response;

import java.time.LocalDate;

public class ActivityHeatmapCell {

    private LocalDate date;
    private int dayOfWeek;
    private int weekOfYear;
    private long activityCount;
    private int intensityLevel;

    public ActivityHeatmapCell() {
    }

    public ActivityHeatmapCell(LocalDate date, int dayOfWeek, int weekOfYear, long activityCount, int intensityLevel) {
        this.date = date;
        this.dayOfWeek = dayOfWeek;
        this.weekOfYear = weekOfYear;
        this.activityCount = activityCount;
        this.intensityLevel = intensityLevel;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(int weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public long getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(long activityCount) {
        this.activityCount = activityCount;
    }

    public int getIntensityLevel() {
        return intensityLevel;
    }

    public void setIntensityLevel(int intensityLevel) {
        this.intensityLevel = intensityLevel;
    }
}
