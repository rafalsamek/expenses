import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { WalletEntity } from './wallet-entity.model';

export interface WalletResponse {
  totalPages: number;
  totalElements: number;
  size: number;
  content: WalletEntity[];
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
export class WalletService {
  private apiUrl = `${environment.apiUrl}/api/wallets`;

  constructor(private httpClient: HttpClient) {
    console.log(`API URL: ${this.apiUrl}`);
  }

  getWallets(
    page: number,
    size: number,
    sortColumns: string,
    sortDirections: string,
    searchBy: string
  ): Observable<WalletResponse> {
    return this.httpClient
      .get<WalletResponse>(
        `${this.apiUrl}?page=${page}&size=${size}&sortColumns=${sortColumns}&sortDirections=${sortDirections}&searchBy=${searchBy}`
      )
      .pipe(catchError(this.handleError));
  }

  addWallet(wallet: WalletEntity): Observable<WalletEntity> {
    return this.httpClient
      .post<WalletEntity>(this.apiUrl, wallet)
      .pipe(catchError(this.handleError));
  }

  updateWallet(wallet: WalletEntity): Observable<WalletEntity> {
    return this.httpClient
      .put<WalletEntity>(`${this.apiUrl}/${wallet.id}`, wallet)
      .pipe(catchError(this.handleError));
  }

  getWallet(id: number): Observable<WalletEntity> {
    return this.httpClient
      .get<WalletEntity>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  deleteWallet(walletId: number): Observable<void> {
    return this.httpClient
      .delete<void>(`${this.apiUrl}/${walletId}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage: string[] = ['An unknown error occurred!'];
    if (error.error instanceof ErrorEvent) {
      errorMessage = [`Error: ${error.error.message}`];
    } else if (error.error && typeof error.error === 'object') {
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
