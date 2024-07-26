import { Component, OnInit } from '@angular/core';
import { NgForOf, NgIf } from '@angular/common';
import { ExpenseEntity } from './expense-entity.model';
import { ExpenseService } from './expense.service';
import { CrudPaginationComponent } from './crud-pagination/crud-pagination.component';
import { CrudHeaderComponent } from './crud-header/crud-header.component';
import { CrudTableComponent } from './crud-table/crud-table.component';

@Component({
  selector: 'app-expenses',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    CrudPaginationComponent,
    CrudHeaderComponent,
    CrudTableComponent,
  ],
  templateUrl: './expenses.component.html',
  styleUrl: './expenses.component.css',
})
export class ExpensesComponent implements OnInit {
  expensesList: ExpenseEntity[] = [];
  pageNumber = 1;
  size = 25;
  totalPages = 0;
  totalElements = 0;
  sortColumns = 'id';
  sortDirections = 'asc';
  searchBy = '';

  constructor(private expenseService: ExpenseService) {}

  ngOnInit(): void {
    this.fetchExpenses(
      this.pageNumber,
      this.size,
      this.sortColumns,
      this.sortDirections,
      this.searchBy
    );
  }

  fetchExpenses(
    pageNumber: number,
    size: number,
    sortColumns: string,
    sortDirections: string,
    searchBy: string
  ): void {
    this.expenseService
      .getExpenses(pageNumber - 1, size, sortColumns, sortDirections, searchBy)
      .subscribe((response) => {
        this.expensesList = response.content;
        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
      });
  }

  onPageChanged(pageNumber: number): void {
    console.log('Current pageNumber:', pageNumber);
    this.pageNumber = pageNumber;
    this.fetchExpenses(
      this.pageNumber,
      this.size,
      this.sortColumns,
      this.sortDirections,
      this.searchBy
    );
  }

  onSortChanged(sortColumns: string, sortDirections: string): void {
    console.log('Current sortColumns:', sortColumns);
    console.log('Current sortDirections:', sortDirections);
    this.sortColumns = sortColumns;
    this.sortDirections = sortDirections;
    this.fetchExpenses(
      this.pageNumber,
      this.size,
      this.sortColumns,
      this.sortDirections,
      this.searchBy
    );
  }

  onSearchChanged(searchBy: string) {
    this.searchBy = searchBy;
    this.pageNumber = 1;
    this.fetchExpenses(
      this.pageNumber,
      this.size,
      this.sortColumns,
      this.sortDirections,
      this.searchBy
    );
  }
}
