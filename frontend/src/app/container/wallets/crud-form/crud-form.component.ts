import {
  Component,
  EventEmitter,
  Input,
  Output,
  OnInit,
  OnChanges,
  SimpleChanges,
  HostListener,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { WalletEntity, Currency } from '../wallet-entity.model';
import { ReactiveFormsModule } from '@angular/forms'; // Make sure to import ReactiveFormsModule

@Component({
  selector: 'wallets-crud-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './crud-form.component.html',
  styleUrl: './crud-form.component.css',
})
export class CrudFormComponent implements OnInit, OnChanges {
  @Input() showModal = false;
  @Input() mode: 'add' | 'edit' | 'view' | 'delete' = 'view';
  @Input() wallet: WalletEntity | null = null;
  @Input() errorMessage: string[] | null = null;
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<WalletEntity>();
  @Output() delete = new EventEmitter<WalletEntity>();

  form: FormGroup;
  currencies: string[] = [];

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      id: [0, Validators.required],
      name: ['', Validators.required],
      description: [''],
      currency: ['PLN', Validators.required],
    });
  }

  ngOnInit() {
    this.currencies = Object.keys(Currency).filter((key) => isNaN(Number(key)));
    this.initializeForm();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['wallet'] && !changes['wallet'].firstChange) {
      this.initializeForm();
    }
    if (changes['errorMessage'] && changes['errorMessage'].currentValue) {
      console.log(
        'Received Error Message:',
        changes['errorMessage'].currentValue
      );
    }
  }

  initializeForm() {
    if (this.wallet) {
      this.form.setValue({
        id: this.wallet.id,
        name: this.wallet.name,
        description: this.wallet.description || '',
        currency: this.wallet.currency,
      });
    } else {
      this.form.reset({
        id: 0,
        name: '',
        description: '',
        currency: 'PLN',
      });
    }

    if (this.mode === 'view' || this.mode === 'delete') {
      this.form.disable();
    } else {
      this.form.enable();
    }
  }

  @HostListener('document:keydown.escape', ['$event'])
  handleEscape(event: KeyboardEvent) {
    this.closeModal();
  }

  closeModal() {
    this.showModal = false;
    this.close.emit();
  }

  onSubmit() {
    if (this.mode === 'delete' && this.wallet) {
      this.deleteWallet(this.wallet);
      return;
    }

    if (this.form.valid) {
      const formValue = this.form.value;
      const walletToSave = {
        ...formValue,
        amount: Math.round(formValue.amount * 100), // Convert amount to cents and ensure it's a whole number
      };
      this.save.emit(walletToSave);
    } else {
      this.form.markAllAsTouched();
    }
  }

  deleteWallet(wallet: WalletEntity) {
    this.delete.emit(wallet);
    this.closeModal();
  }

  getTitle(): string {
    if (this.mode === 'add') {
      return 'Add Wallet';
    } else if (this.mode === 'edit') {
      return 'Edit Wallet';
    } else if (this.mode === 'delete') {
      return 'Delete Wallet';
    } else {
      return 'View Wallet';
    }
  }
}
