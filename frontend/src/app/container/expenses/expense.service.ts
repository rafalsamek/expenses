import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { environment } from '../../../environments/environment';
import {ExpenseEntity} from "./expense-entity.model";
import {Observable} from "rxjs";

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
  providedIn: 'root'
})
export class ExpenseService {
  private apiUrl = `${environment.apiUrl}/api/expenses`;

  constructor(private httpClient: HttpClient) {
    console.log(`API URL: ${this.apiUrl}`); // Log the API URL to the console
  }

  getExpenses(page: number, size: number): Observable<ExpenseResponse> {
    return this.httpClient.get<ExpenseResponse>(`${this.apiUrl}?page=${page}&size=${size}`);
  }
}
