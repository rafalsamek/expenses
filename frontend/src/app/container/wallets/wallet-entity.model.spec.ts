import { WalletEntity } from './wallet-entity.model';

describe('WalletEntity', () => {
  it('should create an object that adheres to WalletEntity interface', () => {
    const wallet: WalletEntity = {
      id: 1,
      name: 'Test Wallet',
      description: 'This is a test wallet',
      currency: 'USD',
      createdAt: '2024-07-19T12:00:00Z',
      updatedAt: '2024-07-19T12:00:00Z',
    };

    expect(wallet).toBeTruthy();
  });
});
