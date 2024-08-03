import { Component, EventEmitter, Input, Output } from '@angular/core';
import { DecimalPipe, NgClass, NgForOf, NgIf } from '@angular/common';
import { CrudPaginationComponent } from '../crud-pagination/crud-pagination.component';
import { ExpenseEntity } from '../expense-entity.model';

@Component({
  selector: 'expenses-crud-table',
  standalone: true,
  imports: [NgForOf, NgIf, CrudPaginationComponent, NgClass, DecimalPipe],
  templateUrl: './crud-table.component.html',
  styleUrl: './crud-table.component.css',
})
export class CrudTableComponent {
  @Input() expensesList: ExpenseEntity[] = [];
  @Output() sortChanged = new EventEmitter<{
    sortColumns: string;
    sortDirections: string;
  }>();

  sortColumn = 'id';
  sortDirection = 'asc';
  @Output() editExpense = new EventEmitter<ExpenseEntity>();
  @Output() viewExpense = new EventEmitter<ExpenseEntity>();
  @Output() deleteExpense = new EventEmitter<ExpenseEntity>();

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
