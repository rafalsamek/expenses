import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgForOf, NgIf } from '@angular/common';
import { CrudPaginationComponent } from '../crud-pagination/crud-pagination.component';
import { ExpenseEntity } from '../expense-entity.model';

@Component({
  selector: 'expenses-crud-table',
  standalone: true,
  imports: [NgForOf, NgIf, CrudPaginationComponent],
  templateUrl: './crud-table.component.html',
  styleUrl: './crud-table.component.css',
})
export class CrudTableComponent {
  @Input() expensesList: ExpenseEntity[] = [];
}
