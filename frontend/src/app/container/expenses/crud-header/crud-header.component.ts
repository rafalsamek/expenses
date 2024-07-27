import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'expenses-crud-header',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './crud-header.component.html',
  styleUrl: './crud-header.component.css',
})
export class CrudHeaderComponent {
  @Input() searchBy = '';
  @Output() searchChanged = new EventEmitter<string>();
  @Output() addExpense = new EventEmitter<void>();

  onSearchInputChange(event: Event) {
    const target = event.target as HTMLInputElement;
    this.searchBy = target.value;
    this.searchChanged.emit(this.searchBy);
  }

  openModal(mode: string) {
    this.addExpense.emit();
  }
}
