import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PickTaskService, PickTaskDto } from '../../core/services/pick-task.service';

@Component({
  selector: 'app-order-management',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-management.component.html',
  styleUrls: ['./order-management.component.css']
})
export class OrderManagementComponent implements OnInit {
  private pickTaskService = inject(PickTaskService);

  activeTab = signal<'INBOUND' | 'OUTBOUND'>('OUTBOUND');
  pickTasks = signal<PickTaskDto[]>([]);
  isLoading = signal<boolean>(true);

  purchaseOrders = [
    { poNumber: 'PO-2026-081', supplier: 'Apex Industrial Corp', itemsCount: 4, status: 'CONFIRMED', expectedDate: '2026-08-26', dockBay: 'DOCK-01' },
    { poNumber: 'PO-2026-082', supplier: 'MicroSensor Labs', itemsCount: 12, status: 'IN_RECEIVING', expectedDate: '2026-08-25', dockBay: 'DOCK-02' },
    { poNumber: 'PO-2026-083', supplier: 'Global Robotics GmbH', itemsCount: 6, status: 'PENDING_QC', expectedDate: '2026-08-25', dockBay: 'QC-BAY' }
  ];

  ngOnInit(): void {
    this.loadPickTasks();
  }

  loadPickTasks(): void {
    this.isLoading.set(true);
    this.pickTaskService.getPickTasks().subscribe({
      next: (res) => {
        this.isLoading.set(false);
        if (res.success && res.data) {
          this.pickTasks.set(res.data.content);
        }
      },
      error: () => {
        this.isLoading.set(false);
      }
    });
  }

  switchTab(tab: 'INBOUND' | 'OUTBOUND'): void {
    this.activeTab.set(tab);
  }

  confirmPick(taskId: number): void {
    this.pickTaskService.confirmPick(taskId, 1).subscribe({
      next: () => {
        this.loadPickTasks();
      }
    });
  }
}
