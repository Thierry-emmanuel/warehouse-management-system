import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StockMovementDto } from '../../../../core/services/inventory.service';

@Component({
  selector: 'app-movement-ledger',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './movement-ledger.component.html',
  styleUrls: ['./movement-ledger.component.css']
})
export class MovementLedgerComponent {
  @Input() movements: StockMovementDto[] = [];
  @Input() isLoading = false;
}
