import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DecimalPipe, NgClass, NgForOf, NgIf } from '@angular/common';
import { WalletEntity } from '../wallet-entity.model';
import { provideIcons, NgIconsModule } from '@ng-icons/core';
import {
  heroEye,
  heroPencilSquare,
  heroTrash,
} from '@ng-icons/heroicons/outline';

@Component({
  selector: 'wallets-crud-table',
  standalone: true,
  imports: [NgForOf, NgIf, NgClass, DecimalPipe, NgIconsModule],
  templateUrl: './crud-table.component.html',
  styleUrl: './crud-table.component.css',
  providers: [provideIcons({ heroEye, heroPencilSquare, heroTrash })],
})
export class CrudTableComponent {
  @Input() walletsList: WalletEntity[] = [];
  @Output() sortChanged = new EventEmitter<{
    sortColumns: string;
    sortDirections: string;
  }>();

  @Output() editWallet = new EventEmitter<WalletEntity>();
  @Output() viewWallet = new EventEmitter<WalletEntity>();
  @Output() deleteWallet = new EventEmitter<WalletEntity>();

  sortColumn = 'id';
  sortDirection = 'asc';

  changeSort(column: string): void {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }
    this.sortChanged.emit({
      sortColumns: this.sortColumn,
      sortDirections: this.sortDirection,
    });
  }

  view(wallet: WalletEntity): void {
    this.viewWallet.emit(wallet);
  }

  edit(wallet: WalletEntity): void {
    this.editWallet.emit(wallet);
  }

  delete(wallet: WalletEntity): void {
    this.deleteWallet.emit(wallet);
  }
}
