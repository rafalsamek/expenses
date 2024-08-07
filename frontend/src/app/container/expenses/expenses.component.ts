import { Component, OnInit } from '@angular/core';
import { NgForOf, NgIf } from '@angular/common';
import { ExpenseEntity } from './expense-entity.model';
import { ExpenseService } from './expense.service';
import { CrudPaginationComponent } from './crud-pagination/crud-pagination.component';
import { CrudHeaderComponent } from './crud-header/crud-header.component';
import { CrudTableComponent } from './crud-table/crud-table.component';
import { CrudFormComponent } from './crud-form/crud-form.component';

@Component({
  selector: 'app-expenses',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    CrudPaginationComponent,
    CrudHeaderComponent,
    CrudTableComponent,
    CrudFormComponent,
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

  showModal = false;
  modalMode: 'add' | 'edit' | 'view' | 'delete' = 'view'; // Include 'delete' mode
  selectedExpense: ExpenseEntity | null = null;
  errorMessage: string[] | null = null;

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

  openModal(mode: 'add' | 'edit' | 'view' | 'delete', expense?: ExpenseEntity) {
    this.modalMode = mode;
    this.errorMessage = null; // Reset the error message when opening the modal
    if (mode === 'add') {
      this.selectedExpense = null; // Clear selected expense for add mode
      this.showModal = true;
    } else if (expense && expense.id) {
      this.expenseService.getExpense(expense.id).subscribe(
        (expenseData) => {
          this.selectedExpense = expenseData;
          this.showModal = true;
        },
        (error) => {
          console.error('Error fetching expense', error);
          // Handle error appropriately
        }
      );
    }
  }

  closeModal() {
    this.showModal = false;
  }

  saveExpense(expense: ExpenseEntity) {
    if (this.modalMode === 'add') {
      this.expenseService.addExpense(expense).subscribe(
        (newExpense) => {
          this.fetchExpenses(
            this.pageNumber,
            this.size,
            this.sortColumns,
            this.sortDirections,
            this.searchBy
          );
          this.closeModal();
        },
        (error) => {
          console.error('Error adding expense', error);
          this.errorMessage = error;
          this.showModal = true; // Ensure the modal stays open
        }
      );
    } else if (this.modalMode === 'edit') {
      this.expenseService.updateExpense(expense).subscribe(
        (updatedExpense) => {
          const index = this.expensesList.findIndex((e) => e.id === expense.id);
          if (index !== -1) {
            this.expensesList[index] = updatedExpense; // Update the existing expense in the list
          }
          this.closeModal();
        },
        (error) => {
          console.error('Error updating expense', error);
          this.errorMessage = error;
          this.showModal = true; // Ensure the modal stays open
        }
      );
    }
  }

  deleteExpense(expense: ExpenseEntity) {
    this.expenseService.deleteExpense(expense.id).subscribe(
      () => {
        this.fetchExpenses(
          this.pageNumber,
          this.size,
          this.sortColumns,
          this.sortDirections,
          this.searchBy
        );
        this.closeModal();
      },
      (error) => {
        console.error('Error deleting expense', error);
        this.errorMessage = error;
        this.showModal = true;
      }
    );
  }
}
