import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-employee-task-queue',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './employee-task-queue.component.html',
  styleUrls: ['./employee-task-queue.component.css']
})
export class EmployeeTaskQueueComponent {
  @Input() tasks: Array<Record<string, unknown>> = [];
}
