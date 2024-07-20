import { Component, OnInit } from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {ExpenseEntity} from "./expense-entity.model";
import {ExpenseService} from "./expense.service";

@Component({
  selector: 'app-expenses',
  standalone: true,
  imports: [
    NgIf,
    NgForOf
  ],
  templateUrl: './expenses.component.html',
  styleUrl: './expenses.component.css'
})
export class ExpensesComponent implements OnInit {
  expensesList: ExpenseEntity[] = [];

  constructor(private expenseService: ExpenseService) { }

  ngOnInit(): void {
    this.fetchExpenses();
  }

  fetchExpenses() {
    this.expenseService
      .getExpenses()
      .subscribe((expenses) => (this.expensesList = expenses));
  }
}
