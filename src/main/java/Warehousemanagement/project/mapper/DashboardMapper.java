package Warehousemanagement.project.mapper;

import Warehousemanagement.project.dto.response.ActivityHeatmapCell;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DashboardMapper {

    public List<ActivityHeatmapCell> generateLogistiqActivityHeatmap(int daysBack) {
        List<ActivityHeatmapCell> heatmap = new ArrayList<>();
        LocalDate startDate = LocalDate.now().minusDays(daysBack);

        // Deterministic baseline volume for demonstration of LogistiQ activity grid
        for (int i = 0; i <= daysBack; i++) {
            LocalDate date = startDate.plusDays(i);
            int dayOfWeek = date.getDayOfWeek().getValue(); // 1=Mon, 7=Sun
            int base = (dayOfWeek == 6 || dayOfWeek == 7) ? 20 : 120;
            int volume = base + ((date.getDayOfMonth() * 7) % 180);

            int intensity = 0;
            if (volume > 220) {
                intensity = 4;
            } else if (volume > 150) {
                intensity = 3;
            } else if (volume > 80) {
                intensity = 2;
            } else if (volume > 0) {
                intensity = 1;
            }

            heatmap.add(new ActivityHeatmapCell(date, volume, intensity));
        }
        return heatmap;
    }
}
