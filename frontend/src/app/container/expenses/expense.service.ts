import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ExpenseEntity } from './expense-entity.model';
import { Observable } from 'rxjs';

export interface ExpenseResponse {
  totalPages: number;
  totalElements: number;
  size: number;
  content: ExpenseEntity[];
  number: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  numberOfElements: number;
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: {
      empty: boolean;
      sorted: boolean;
      unsorted: boolean;
    };
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  first: boolean;
  last: boolean;
  empty: boolean;
}

@Injectable({
  providedIn: 'root',
})
export class ExpenseService {
  private apiUrl = `${environment.apiUrl}/api/expenses`;

  constructor(private httpClient: HttpClient) {
    console.log(`API URL: ${this.apiUrl}`); // Log the API URL to the console
  }

  getExpenses(
    page: number,
    size: number,
    sortColumns: string,
    sortDirections: string,
    searchBy: string
  ): Observable<ExpenseResponse> {
    return this.httpClient.get<ExpenseResponse>(
      `${this.apiUrl}?page=${page}&size=${size}&sortColumns=${sortColumns}&sortDirections=${sortDirections}&searchBy=${searchBy}`
    );
  }

  addExpense(expense: ExpenseEntity): Observable<ExpenseEntity> {
    return this.httpClient.post<ExpenseEntity>(this.apiUrl, expense);
  }

  updateExpense(expense: ExpenseEntity): Observable<ExpenseEntity> {
    return this.httpClient.put<ExpenseEntity>(
      `${this.apiUrl}/${expense.id}`,
      expense
    );
  }

  getExpense(id: number): Observable<ExpenseEntity> {
    return this.httpClient.get<ExpenseEntity>(`${this.apiUrl}/${id}`);
  }
}
