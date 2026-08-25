import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

interface MonthlyMetric {
  month: string;
  inflow: number;
  outflow: number;
  inflowHeight: number;
  outflowHeight: number;
}

@Component({
  selector: 'app-admin-trend-chart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-trend-chart.component.html',
  styleUrls: ['./admin-trend-chart.component.css']
})
export class AdminTrendChartComponent {
  selectedMonth = signal<MonthlyMetric | null>(null);

  months: MonthlyMetric[] = [
    { month: 'Oct', inflow: 38200, outflow: 31000, inflowHeight: 65, outflowHeight: 52 },
    { month: 'Nov', inflow: 42100, outflow: 39400, inflowHeight: 72, outflowHeight: 67 },
    { month: 'Dec', inflow: 51800, outflow: 48900, inflowHeight: 88, outflowHeight: 83 },
    { month: 'Jan', inflow: 34500, outflow: 29800, inflowHeight: 58, outflowHeight: 50 },
    { month: 'Feb', inflow: 39200, outflow: 36100, inflowHeight: 66, outflowHeight: 61 },
    { month: 'Mar', inflow: 44300, outflow: 41200, inflowHeight: 75, outflowHeight: 70 },
    { month: 'Apr', inflow: 47900, outflow: 43500, inflowHeight: 81, outflowHeight: 74 },
    { month: 'May', inflow: 52400, outflow: 49100, inflowHeight: 89, outflowHeight: 83 },
    { month: 'Jun', inflow: 48900, outflow: 45200, inflowHeight: 83, outflowHeight: 77 },
    { month: 'Jul', inflow: 54100, outflow: 51300, inflowHeight: 92, outflowHeight: 87 },
    { month: 'Aug', inflow: 58900, outflow: 55400, inflowHeight: 100, outflowHeight: 94 },
    { month: 'Sep', inflow: 48500, outflow: 46200, inflowHeight: 82, outflowHeight: 78 }
  ];

  constructor() {
    this.selectedMonth.set(this.months[10]); // Default August peak
  }

  onHoverMonth(m: MonthlyMetric): void {
    this.selectedMonth.set(m);
  }
}
