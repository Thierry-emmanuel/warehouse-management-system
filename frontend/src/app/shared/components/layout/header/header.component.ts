import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  @Output() refresh = new EventEmitter<void>();

  selectedFacility = 'WH-MAIN-01 (Central Logistics)';
  facilities = [
    'WH-MAIN-01 (Central Logistics)',
    'WH-NORTH-02 (Cold Storage)',
    'WH-EAST-03 (Regional Transit)'
  ];

  onRefresh(): void {
    this.refresh.emit();
  }
}
