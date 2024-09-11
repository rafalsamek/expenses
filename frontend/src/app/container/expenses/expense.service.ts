import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { ExpenseEntity } from './expense-entity.model';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../../auth.service';

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

  constructor(private httpClient: HttpClient, private authService: AuthService) {
    console.log(`API URL: ${this.apiUrl}`); // Log the API URL to the console
  }

  private getAuthHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders().set('Authorization', `Bearer ${token}`);
  }

  getExpenses(
    page: number,
    size: number,
    sortColumns: string,
    sortDirections: string,
    searchBy: string
  ): Observable<ExpenseResponse> {
    const headers = this.getAuthHeaders();
    return this.httpClient
      .get<ExpenseResponse>(
        `${this.apiUrl}?page=${page}&size=${size}&sortColumns=${sortColumns}&sortDirections=${sortDirections}&searchBy=${searchBy}`,
        { headers }
      )
      .pipe(catchError(this.handleError));
  }

  addExpense(expense: ExpenseEntity): Observable<ExpenseEntity> {
    const headers = this.getAuthHeaders();
    return this.httpClient
      .post<ExpenseEntity>(this.apiUrl, expense, { headers })
      .pipe(catchError(this.handleError));
  }

  updateExpense(expense: ExpenseEntity): Observable<ExpenseEntity> {
    const headers = this.getAuthHeaders();
    return this.httpClient
      .put<ExpenseEntity>(`${this.apiUrl}/${expense.id}`, expense, { headers })
      .pipe(catchError(this.handleError));
  }

  getExpense(id: number): Observable<ExpenseEntity> {
    const headers = this.getAuthHeaders();
    return this.httpClient
      .get<ExpenseEntity>(`${this.apiUrl}/${id}`, { headers })
      .pipe(catchError(this.handleError));
  }

  deleteExpense(expenseId: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.httpClient
      .delete<void>(`${this.apiUrl}/${expenseId}`, { headers })
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage: string[] = ['An unknown error occurred!'];
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred.
      errorMessage = [`Error: ${error.error.message}`];
    } else if (error.error && typeof error.error === 'object') {
      // Handle validation errors
      const validationErrors = error.error;
      errorMessage = Object.entries(validationErrors).map(
        ([field, msg]) => `${msg}`
      );
    } else {
      errorMessage = [
        `Server returned code: ${error.status}, error message is: ${error.message}`,
      ];
    }
    return throwError(errorMessage);
  }
}
