package Warehousemanagement.project.dto.response;

import java.time.LocalDate;

public class ActivityHeatmapCell {

    private LocalDate date;
    private int volume;
    private int intensityLevel; // 0 to 4 (LogistiQ color spectrum)

    public ActivityHeatmapCell() {
    }

    public ActivityHeatmapCell(LocalDate date, int volume, int intensityLevel) {
        this.date = date;
        this.volume = volume;
        this.intensityLevel = intensityLevel;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getIntensityLevel() {
        return intensityLevel;
    }

    public void setIntensityLevel(int intensityLevel) {
        this.intensityLevel = intensityLevel;
    }
}
