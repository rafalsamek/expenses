import { ExpenseEntity } from './expense-entity.model';

describe('ExpenseEntity', () => {
  it('should create an object that adheres to ExpenseEntity interface', () => {
    const expense: ExpenseEntity = {
      id: 1,
      title: 'Test Expense',
      description: 'This is a test expense',
      amount: 100,
      currency: 'USD',
      createdAt: '2024-07-19T12:00:00Z',
      updatedAt: '2024-07-19T12:00:00Z',
    };

    expect(expense).toBeTruthy();
  });
});
