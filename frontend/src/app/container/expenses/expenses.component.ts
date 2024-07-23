import { Component, OnInit } from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {ExpenseEntity} from "./expense-entity.model";
import {ExpenseService} from "./expense.service";
import {PaginationComponent} from "./pagination/pagination.component";

@Component({
  selector: 'app-expenses',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    PaginationComponent
  ],
  templateUrl: './expenses.component.html',
  styleUrl: './expenses.component.css'
})
export class ExpensesComponent implements OnInit {
  expensesList: ExpenseEntity[] = [];
  currentPage = 1;
  totalElements = 0;
  size = 25;
  totalPages = 0;

  constructor(private expenseService: ExpenseService) { }

  ngOnInit(): void {
    this.fetchExpenses(this.currentPage, this.size);
  }

  fetchExpenses(page: number, size: number): void {
    this.expenseService.getExpenses(page - 1, size)
      .subscribe(response => {
        this.expensesList = response.content;
        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
      });
  }

  onPageChanged(page: number): void {
    console.log('Current page:', page);
    this.currentPage = page;
    this.fetchExpenses(this.currentPage, this.size);
  }
}
