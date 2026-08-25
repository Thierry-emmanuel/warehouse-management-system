package Warehousemanagement.project.dashboard.mapper;

import Warehousemanagement.project.dashboard.dto.response.ActivityHeatmapCell;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class DashboardMapper {

    public List<ActivityHeatmapCell> generateLogistiqHeatmapGrid(int daysBack) {
        List<ActivityHeatmapCell> cells = new ArrayList<>();
        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());

        for (int i = daysBack; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            int dayOfWeek = date.getDayOfWeek().getValue();
            int weekOfYear = date.get(weekFields.weekOfWeekBasedYear());

            long activityCount = (long) ((Math.sin(i * 0.3) + 1.2) * 45 + (i % 7 == 0 ? 80 : 20));
            int intensityLevel = calculateIntensity(activityCount);

            cells.add(new ActivityHeatmapCell(date, dayOfWeek, weekOfYear, activityCount, intensityLevel));
        }

        return cells;
    }

    private int calculateIntensity(long count) {
        if (count == 0) return 0;
        if (count < 30) return 1;
        if (count < 60) return 2;
        if (count < 100) return 3;
        return 4;
    }
}
