import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

export interface LogisticsTransaction {
  id: number;
  date: string;
  partnerName: string;
  partnerCode: string;
  avatarColor: string;
  productName: string;
  sku: string;
  unitPrice: number;
  quantity: number;
  totalPrice: number;
  operatorName: string;
  operatorInitials: string;
  status: 'COMPLETED' | 'IN_TRANSIT' | 'QUARANTINE' | 'PROCESSING';
  selected?: boolean;
}

@Component({
  selector: 'app-admin-dense-table',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dense-table.component.html',
  styleUrls: ['./admin-dense-table.component.css']
})
export class AdminDenseTableComponent {
  selectAll = signal<boolean>(false);
  filterStatus = signal<string>('ALL');

  transactions: LogisticsTransaction[] = [
    {
      id: 101,
      date: '2026-08-25',
      partnerName: 'Apex Industrial Corp',
      partnerCode: 'APX',
      avatarColor: 'av-red',
      productName: 'Heavy Duty Hydraulic Valve',
      sku: 'HYDR-VALVE-02',
      unitPrice: 799.00,
      quantity: 4,
      totalPrice: 3196.00,
      operatorName: 'George Russell',
      operatorInitials: 'GR',
      status: 'COMPLETED'
    },
    {
      id: 102,
      date: '2026-08-25',
      partnerName: 'MicroSensor Labs',
      partnerCode: 'MSL',
      avatarColor: 'av-blue',
      productName: 'Optic Calibration Sensor Pro',
      sku: 'SENS-IND-44',
      unitPrice: 209.90,
      quantity: 12,
      totalPrice: 2518.80,
      operatorName: 'Roger White',
      operatorInitials: 'RW',
      status: 'COMPLETED'
    },
    {
      id: 103,
      date: '2026-08-24',
      partnerName: 'Global Robotics GmbH',
      partnerCode: 'GRG',
      avatarColor: 'av-emerald',
      productName: 'Planetary Gear Reducer 10:1',
      sku: 'MECH-GEAR-08',
      unitPrice: 450.00,
      quantity: 6,
      totalPrice: 2700.00,
      operatorName: 'Jesse Miller',
      operatorInitials: 'JM',
      status: 'IN_TRANSIT'
    },
    {
      id: 104,
      date: '2026-08-24',
      partnerName: 'Thermal Dynamics Ltd',
      partnerCode: 'TDL',
      avatarColor: 'av-purple',
      productName: 'Cold Room Temperature Probe',
      sku: 'COLD-SENS-12',
      unitPrice: 135.50,
      quantity: 20,
      totalPrice: 2710.00,
      operatorName: 'Candy Davis',
      operatorInitials: 'CD',
      status: 'PROCESSING'
    },
    {
      id: 105,
      date: '2026-08-23',
      partnerName: 'Synapse Audio Tech',
      partnerCode: 'SAT',
      avatarColor: 'av-orange',
      productName: 'Hi-Fi Controller Transceiver',
      sku: 'ELEC-CTRL-09',
      unitPrice: 89.00,
      quantity: 35,
      totalPrice: 3115.00,
      operatorName: 'Frank Clark',
      operatorInitials: 'FC',
      status: 'COMPLETED'
    }
  ];

  toggleSelectAll(): void {
    const newState = !this.selectAll();
    this.selectAll.set(newState);
    this.transactions.forEach(t => t.selected = newState);
  }

  toggleRow(tx: LogisticsTransaction): void {
    tx.selected = !tx.selected;
    this.selectAll.set(this.transactions.every(t => t.selected));
  }
}
