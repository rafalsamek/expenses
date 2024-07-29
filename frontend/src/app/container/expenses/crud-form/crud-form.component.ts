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
import { ReactiveFormsModule } from '@angular/forms'; // Make sure to import ReactiveFormsModule

@Component({
  selector: 'expenses-crud-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './crud-form.component.html',
  styleUrl: './crud-form.component.css',
})
export class CrudFormComponent implements OnInit, OnChanges {
  @Input() showModal = false;
  @Input() mode: 'add' | 'edit' | 'view' = 'view';
  @Input() expense: ExpenseEntity | null = null;
  @Input() errorMessage: string[] | null = null;
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
    this.initializeForm();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['expense'] && !changes['expense'].firstChange) {
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
    if (this.expense) {
      this.form.setValue({
        title: this.expense.title,
        description: this.expense.description || '',
        amount: this.expense.amount / 100, // Display amount divided by 100
        currency: this.expense.currency,
      });
    } else {
      this.form.reset({
        title: '',
        description: '',
        amount: null,
        currency: 'PLN',
      });
    }

    if (this.mode === 'view') {
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
    if (this.form.valid) {
      const formValue = this.form.value;
      const expenseToSave = {
        ...formValue,
        amount: formValue.amount * 100, // Convert amount to cents before sending to backend
      };
      this.save.emit(expenseToSave);
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
