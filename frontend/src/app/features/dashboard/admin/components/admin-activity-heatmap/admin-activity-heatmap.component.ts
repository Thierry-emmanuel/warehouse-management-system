import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

interface HeatmapDay {
  date: string;
  dayIndex: number;
  weekIndex: number;
  count: number;
  level: number;
}

@Component({
  selector: 'app-admin-activity-heatmap',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-activity-heatmap.component.html',
  styleUrls: ['./admin-activity-heatmap.component.css']
})
export class AdminActivityHeatmapComponent {
  hoveredCell = signal<HeatmapDay | null>(null);

  daysOfWeek = ['Mon', 'Wed', 'Fri', 'Sun'];
  weeks: HeatmapDay[][] = [];

  constructor() {
    this.generateHeatmapData();
  }

  generateHeatmapData(): void {
    const totalWeeks = 28;
    const daysPerWeek = 7;
    const generatedWeeks: HeatmapDay[][] = [];

    for (let w = 0; w < totalWeeks; w++) {
      const week: HeatmapDay[] = [];
      for (let d = 0; d < daysPerWeek; d++) {
        const rand = Math.random();
        let level = 0;
        let count = 0;

        if (rand > 0.8) {
          level = 4;
          count = Math.floor(Math.random() * 80) + 180;
        } else if (rand > 0.6) {
          level = 3;
          count = Math.floor(Math.random() * 50) + 110;
        } else if (rand > 0.35) {
          level = 2;
          count = Math.floor(Math.random() * 40) + 50;
        } else if (rand > 0.15) {
          level = 1;
          count = Math.floor(Math.random() * 30) + 10;
        }

        const date = new Date();
        date.setDate(date.getDate() - ((totalWeeks - 1 - w) * 7 + (6 - d)));

        week.push({
          date: date.toISOString().split('T')[0],
          dayIndex: d,
          weekIndex: w,
          count,
          level
        });
      }
      generatedWeeks.push(week);
    }
    this.weeks = generatedWeeks;
  }

  onCellHover(cell: HeatmapDay): void {
    this.hoveredCell.set(cell);
  }

  onCellLeave(): void {
    this.hoveredCell.set(null);
  }
}
