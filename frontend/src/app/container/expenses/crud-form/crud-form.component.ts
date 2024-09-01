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
import { ExpenseEntity, Currency } from '../expense-entity.model';
import { WalletEntity } from '../wallet-entity.model';
import { ReactiveFormsModule } from '@angular/forms';
import { WalletService } from '../wallet.service';
import { CategoryEntity } from '../category-entity.model';
import { CategoryService } from '../category.service'; // Import CategoryService

@Component({
  selector: 'expenses-crud-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './crud-form.component.html',
  styleUrl: './crud-form.component.css',
})
export class CrudFormComponent implements OnInit, OnChanges {
  @Input() showModal = false;
  @Input() mode: 'add' | 'edit' | 'view' | 'delete' = 'view';
  @Input() expense: ExpenseEntity | null = null;
  @Input() errorMessage: string[] | null = null;
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<ExpenseEntity>();
  @Output() delete = new EventEmitter<ExpenseEntity>();

  form: FormGroup;
  currencies: string[] = [];
  wallets: WalletEntity[] = [];
  categories: CategoryEntity[] = []; // Add a property to hold categories

  constructor(
    private fb: FormBuilder,
    private walletService: WalletService,
    private categoryService: CategoryService // Inject CategoryService
  ) {
    this.form = this.fb.group({
      id: [0, Validators.required],
      title: ['', Validators.required],
      description: [''],
      amount: [null, [Validators.required, Validators.min(0.01)]],
      currency: ['PLN', Validators.required],
      wallet: [null, Validators.required], // This will hold the WalletEntity ID
      categories: [[], Validators.required], // New field for categories
    });
  }

  ngOnInit() {
    this.currencies = Object.keys(Currency).filter((key) => isNaN(Number(key)));
    this.loadWallets();
    this.loadCategories(); // Load categories on init
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['expense'] && !changes['expense'].firstChange) {
      if (this.wallets.length > 0) {
        this.patchFormWithExpense(); // Patch the form with the expense data once wallets are loaded
      }
    }
    if (changes['errorMessage'] && changes['errorMessage'].currentValue) {
      console.log(
        'Received Error Message:',
        changes['errorMessage'].currentValue
      );
    }
  }

  loadWallets() {
    this.walletService.getWallets().subscribe({
      next: (response) => {
        this.wallets = response.content;
        this.patchFormWithExpense(); // After wallets are loaded, patch the form with expense data
      },
      error: (err) => console.error('Failed to load wallets', err),
    });
  }

  loadCategories() {
    this.categoryService.getCategories().subscribe({
      next: (response) => {
        this.categories = response.content;
        this.patchFormWithExpense(); // After categories are loaded, patch the form with expense data
      },
      error: (err) => console.error('Failed to load categories', err),
    });
  }

  patchFormWithExpense() {
    if (this.expense) {
      this.form.patchValue({
        id: this.expense.id,
        title: this.expense.title,
        description: this.expense.description || '',
        amount: this.expense.amount / 100, // Convert amount from cents to units
        currency: this.expense.currency,
        wallet: this.expense.wallet?.id || this.wallets[0]?.id || null, // Use wallet.id
        categories:
          this.expense.categories?.map((category) => category.id) || [], // Patch categories field
      });
    } else {
      this.form.reset({
        id: 0,
        title: '',
        description: '',
        amount: null,
        currency: 'PLN',
        wallet: this.wallets[0]?.id || null, // Default to the first wallet or null
        categories: [], // Default to empty array for categories
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
    if (this.mode === 'delete' && this.expense) {
      this.deleteExpense(this.expense);
      return;
    }

    if (this.form.valid) {
      const formValue = this.form.value;

      // Create an object for submission with walletId
      const expenseToSave: ExpenseEntity = {
        id: formValue.id,
        title: formValue.title,
        description: formValue.description || '',
        amount: Math.round(formValue.amount * 100), // Convert amount to cents
        currency: formValue.currency,
        walletId: formValue.wallet, // Use walletId for submission
        categoryIds: formValue.categories, // Add categories IDs for submission
      };

      this.save.emit(expenseToSave); // Emit the object with walletId
    } else {
      this.form.markAllAsTouched(); // Mark all fields as touched to trigger validation messages
    }
  }

  deleteExpense(expense: ExpenseEntity) {
    this.delete.emit(expense);
    this.closeModal();
  }

  getTitle(): string {
    if (this.mode === 'add') {
      return 'Add Expense';
    } else if (this.mode === 'edit') {
      return 'Edit Expense';
    } else if (this.mode === 'delete') {
      return 'Delete Expense';
    } else {
      return 'View Expense';
    }
  }
}
