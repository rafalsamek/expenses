import { CategoryEntity } from './category-entity.model';

describe('CategoryEntity', () => {
  it('should create an object that adheres to CategoryEntity interface', () => {
    const category: CategoryEntity = {
      id: 1,
      name: 'Test Category',
      description: 'This is a test category',
      createdAt: '2024-07-19T12:00:00Z',
      updatedAt: '2024-07-19T12:00:00Z',
    };

    expect(category).toBeTruthy();
  });
});
