import {
  Component,
  EventEmitter,
  Input,
  Output,
  OnInit,
  HostListener,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ExpenseEntity, Currency } from '../expense-entity.model';
import { ReactiveFormsModule } from '@angular/forms'; // Make sure to import ReactiveFormsModule

@Component({
  selector: 'expenses-crud-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './crud-form.component.html',
  styleUrl: './crud-form.component.css',
})
export class CrudFormComponent implements OnInit {
  @Input() showModal = false;
  @Input() mode: 'add' | 'edit' | 'view' = 'view';
  @Input() expense: ExpenseEntity | null = null;
  @Output() close = new EventEmitter<void>();
  @Output() save = new EventEmitter<ExpenseEntity>();

  form: FormGroup;
  currencies: string[] = [];

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      amount: [null, [Validators.required, Validators.min(0.01)]],
      currency: ['PLN', Validators.required],
    });
  }

  ngOnInit() {
    this.currencies = Object.keys(Currency).filter((key) => isNaN(Number(key)));
    if (this.expense) {
      // Use setValue to ensure all fields are initialized correctly
      this.form.setValue({
        title: this.expense.title,
        description: this.expense.description || '',
        amount: this.expense.amount / 100, // Display amount divided by 100
        currency: this.expense.currency,
      });
    }
    if (this.mode === 'view') {
      this.form.disable();
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
    if (this.form.valid) {
      const formValue = this.form.value;
      const expenseToSave = {
        ...formValue,
        amount: formValue.amount * 100, // Convert amount to cents before sending to backend
      };
      this.save.emit(expenseToSave);
      this.closeModal();
    } else {
      this.form.markAllAsTouched();
    }
  }

  getTitle(): string {
    if (this.mode === 'add') {
      return 'Add Expense';
    } else if (this.mode === 'edit') {
      return 'Edit Expense';
    } else {
      return 'View Expense';
    }
  }
}
