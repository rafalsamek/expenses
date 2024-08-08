import { Component, OnInit } from '@angular/core';
import { NgForOf, NgIf } from '@angular/common';
import { WalletEntity } from './wallet-entity.model';
import { WalletService } from './wallet.service';
import { CrudPaginationComponent } from './crud-pagination/crud-pagination.component';
import { CrudHeaderComponent } from './crud-header/crud-header.component';
import { CrudTableComponent } from './crud-table/crud-table.component';
import { CrudFormComponent } from './crud-form/crud-form.component';

@Component({
  selector: 'app-wallets',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    CrudPaginationComponent,
    CrudHeaderComponent,
    CrudTableComponent,
    CrudFormComponent,
  ],
  templateUrl: './wallets.component.html',
  styleUrl: './wallets.component.css',
})
export class WalletsComponent implements OnInit {
  walletsList: WalletEntity[] = [];
  pageNumber = 1;
  size = 25;
  totalPages = 0;
  totalElements = 0;
  sortColumns = 'id';
  sortDirections = 'asc';
  searchBy = '';

  showModal = false;
  modalMode: 'add' | 'edit' | 'view' | 'delete' = 'view'; // Include 'delete' mode
  selectedWallet: WalletEntity | null = null;
  errorMessage: string[] | null = null;

  constructor(private walletService: WalletService) {}

  ngOnInit(): void {
    this.fetchWallets(
      this.pageNumber,
      this.size,
      this.sortColumns,
      this.sortDirections,
      this.searchBy
    );
  }

  fetchWallets(
    pageNumber: number,
    size: number,
    sortColumns: string,
    sortDirections: string,
    searchBy: string
  ): void {
    this.walletService
      .getWallets(pageNumber - 1, size, sortColumns, sortDirections, searchBy)
      .subscribe((response) => {
        this.walletsList = response.content;
        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
      });
  }

  onPageChanged(pageNumber: number): void {
    this.pageNumber = pageNumber;
    this.fetchWallets(
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
    this.fetchWallets(
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
    this.fetchWallets(
      this.pageNumber,
      this.size,
      this.sortColumns,
      this.sortDirections,
      this.searchBy
    );
  }

  openModal(mode: 'add' | 'edit' | 'view' | 'delete', wallet?: WalletEntity) {
    this.modalMode = mode;
    this.errorMessage = null; // Reset the error message when opening the modal
    if (mode === 'add') {
      this.selectedWallet = null; // Clear selected wallet for add mode
      this.showModal = true;
    } else if (wallet && wallet.id) {
      this.walletService.getWallet(wallet.id).subscribe(
        (walletData) => {
          this.selectedWallet = walletData;
          this.showModal = true;
        },
        (error) => {
          console.error('Error fetching wallet', error);
          // Handle error appropriately
        }
      );
    }
  }

  closeModal() {
    this.showModal = false;
  }

  saveWallet(wallet: WalletEntity) {
    if (this.modalMode === 'add') {
      this.walletService.addWallet(wallet).subscribe(
        (newWallet) => {
          this.fetchWallets(
            this.pageNumber,
            this.size,
            this.sortColumns,
            this.sortDirections,
            this.searchBy
          );
          this.closeModal();
        },
        (error) => {
          console.error('Error adding wallet', error);
          this.errorMessage = error;
          this.showModal = true; // Ensure the modal stays open
        }
      );
    } else if (this.modalMode === 'edit') {
      this.walletService.updateWallet(wallet).subscribe(
        (updatedWallet) => {
          const index = this.walletsList.findIndex((e) => e.id === wallet.id);
          if (index !== -1) {
            this.walletsList[index] = updatedWallet; // Update the existing wallet in the list
          }
          this.closeModal();
        },
        (error) => {
          console.error('Error updating wallet', error);
          this.errorMessage = error;
          this.showModal = true; // Ensure the modal stays open
        }
      );
    }
  }

  deleteWallet(wallet: WalletEntity) {
    this.walletService.deleteWallet(wallet.id).subscribe(
      () => {
        this.fetchWallets(
          this.pageNumber,
          this.size,
          this.sortColumns,
          this.sortDirections,
          this.searchBy
        );
        this.closeModal();
      },
      (error) => {
        console.error('Error deleting wallet', error);
        this.errorMessage = error;
        this.showModal = true;
      }
    );
  }
}
