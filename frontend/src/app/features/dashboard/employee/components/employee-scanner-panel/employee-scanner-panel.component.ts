import { Component, EventEmitter, Output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-employee-scanner-panel',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-scanner-panel.component.html',
  styleUrls: ['./employee-scanner-panel.component.css']
})
export class EmployeeScannerPanelComponent {
  @Output() scanComplete = new EventEmitter<{ barcode: string; step: number }>();

  currentStep = signal<number>(1);
  barcodeInput = '';
  lastScanStatus = signal<'IDLE' | 'SUCCESS' | 'ERROR'>('IDLE');
  lastScanMessage = signal<string>('Ready for Bin Barcode scan...');

  targetBin = 'WH1-Z01-A02-S1-B03';
  targetSku = 'ELEC-AUDIO-01';
  targetQty = 12;
  confirmedQty = 12;

  shortcuts = [
    { key: 'F1', label: 'Trigger Laser Scan' },
    { key: 'F2', label: 'Flag Bin Exception' },
    { key: 'F3', label: 'Print Tote Barcode' },
    { key: 'F4', label: 'Next Pick Sequence' }
  ];

  onSubmitBarcode(): void {
    const code = this.barcodeInput.trim().toUpperCase();
    if (!code) return;

    if (this.currentStep() === 1) {
      if (code === this.targetBin || code.includes('A02')) {
        this.lastScanStatus.set('SUCCESS');
        this.lastScanMessage.set(`Bin ${code} verified. Proceed to scan item SKU.`);
        this.currentStep.set(2);
      } else {
        this.lastScanStatus.set('ERROR');
        this.lastScanMessage.set(`Incorrect bin scanned: ${code}. Expected: ${this.targetBin}`);
      }
    } else if (this.currentStep() === 2) {
      if (code === this.targetSku || code.includes('ELEC')) {
        this.lastScanStatus.set('SUCCESS');
        this.lastScanMessage.set(`SKU ${code} verified. Enter pick quantity.`);
        this.currentStep.set(3);
      } else {
        this.lastScanStatus.set('ERROR');
        this.lastScanMessage.set(`SKU mismatch: ${code}. Expected: ${this.targetSku}`);
      }
    }

    this.barcodeInput = '';
  }

  confirmPick(): void {
    this.lastScanStatus.set('SUCCESS');
    this.lastScanMessage.set(`Pick confirmed: ${this.confirmedQty} units of ${this.targetSku} to Tote #T-8819.`);
    this.currentStep.set(1);
    this.scanComplete.emit({ barcode: this.targetSku, step: 3 });
  }

  resetWorkflow(): void {
    this.currentStep.set(1);
    this.lastScanStatus.set('IDLE');
    this.lastScanMessage.set('Ready for Bin Barcode scan...');
    this.barcodeInput = '';
  }
}
