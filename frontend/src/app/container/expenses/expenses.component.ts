import { Component } from '@angular/core';
import {ExpenseEntity} from "../../models";
import {NgForOf, NgIf} from "@angular/common";

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
export class ExpensesComponent {
  ngOnInit(): void {
    this.fetchExpenses();
  }

  expensesList: ExpenseEntity[] = [];


  fetchExpenses() {
    this.expensesList = [
      {
        id: 1,
        title: 'Grocery Shopping',
        description: 'Bought groceries for the week',
        amount: 75.50,
        currency: 'USD',
        createdAt: '2024-07-01T10:00:00Z',
        updatedAt: '2024-07-01T10:00:00Z'
      },
      {
        id: 2,
        title: 'Electricity Bill',
        description: 'Monthly electricity bill payment',
        amount: 120.00,
        currency: 'USD',
        createdAt: '2024-07-05T15:00:00Z',
        updatedAt: '2024-07-05T15:00:00Z'
      },
      {
        id: 3,
        title: 'Internet Subscription',
        description: 'Monthly internet subscription fee',
        amount: 45.00,
        currency: 'USD',
        createdAt: '2024-07-10T08:30:00Z',
        updatedAt: '2024-07-10T08:30:00Z'
      },
      {
        id: 4,
        title: 'Restaurant Dinner',
        description: 'Dinner at a local restaurant',
        amount: 60.25,
        currency: 'USD',
        createdAt: '2024-07-15T19:00:00Z',
        updatedAt: '2024-07-15T19:00:00Z'
      },
      {
        id: 5,
        title: 'Gym Membership',
        description: 'Monthly gym membership fee',
        amount: 35.00,
        currency: 'USD',
        createdAt: '2024-07-20T06:00:00Z',
        updatedAt: '2024-07-20T06:00:00Z'
      }
    ];

    return this.expensesList;
  }
}
