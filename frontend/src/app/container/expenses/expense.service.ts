import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { environment } from '../../../environments/environment';
import {ExpenseEntity} from "./expense-entity.model";
import {map, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private apiUrl = `${environment.apiUrl}/api/expenses`;

  constructor(private httpClient: HttpClient) {
    console.log(`API URL: ${this.apiUrl}`); // Log the API URL to the console
  }

  getExpenses(): Observable<ExpenseEntity[]> {
    return this.httpClient.get<any>(this.apiUrl).pipe(
      map(response => response.content as ExpenseEntity[])
    );
  }
}
