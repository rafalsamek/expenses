import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DecimalPipe, NgClass, NgForOf, NgIf } from '@angular/common';
import { ExpenseEntity } from '../expense-entity.model';
import { provideIcons, NgIconsModule } from '@ng-icons/core';
import {
  heroEye,
  heroPencilSquare,
  heroTrash,
} from '@ng-icons/heroicons/outline';

@Component({
  selector: 'expenses-crud-table',
  standalone: true,
  imports: [NgForOf, NgIf, NgClass, DecimalPipe, NgIconsModule],
  templateUrl: './crud-table.component.html',
  styleUrl: './crud-table.component.css',
  providers: [provideIcons({ heroEye, heroPencilSquare, heroTrash })],
})
export class CrudTableComponent {
  @Input() expensesList: ExpenseEntity[] = [];
  @Output() sortChanged = new EventEmitter<{
    sortColumns: string;
    sortDirections: string;
  }>();

  @Output() editExpense = new EventEmitter<ExpenseEntity>();
  @Output() viewExpense = new EventEmitter<ExpenseEntity>();
  @Output() deleteExpense = new EventEmitter<ExpenseEntity>();

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

  view(expense: ExpenseEntity): void {
    this.viewExpense.emit(expense);
  }

  edit(expense: ExpenseEntity): void {
    this.editExpense.emit(expense);
  }

  delete(expense: ExpenseEntity): void {
    this.deleteExpense.emit(expense);
  }
}
