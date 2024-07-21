import { Component, OnInit } from '@angular/core';
import { NgForOf, NgIf } from "@angular/common";
import { ExpenseEntity } from "./expense-entity.model";
import { ExpenseService } from "./expense.service";
import {MatPaginator, PageEvent} from '@angular/material/paginator';

@Component({
  selector: 'app-expenses',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    MatPaginator
  ],
  templateUrl: './expenses.component.html',
  styleUrl: './expenses.component.css'
})
export class ExpensesComponent implements OnInit {
  expensesList: ExpenseEntity[] = [];
  currentPage = 0;
  pageSize = 25;
  totalItems = 0;

  constructor(private expenseService: ExpenseService) { }

  ngOnInit(): void {
    this.fetchExpenses();
  }

  fetchExpenses() {
    this.expenseService
      .getExpenses(this.currentPage, this.pageSize)
      .subscribe((response) => {
        this.expensesList = response.content;
        this.totalItems = response.totalElements;
      });
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.fetchExpenses();
  }
}
