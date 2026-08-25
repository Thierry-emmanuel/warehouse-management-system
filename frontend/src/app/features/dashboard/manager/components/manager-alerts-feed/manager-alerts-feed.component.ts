import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-manager-alerts-feed',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './manager-alerts-feed.component.html',
  styleUrls: ['./manager-alerts-feed.component.css']
})
export class ManagerAlertsFeedComponent {
  @Input() alerts: string[] = [];
}
