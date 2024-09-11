import { Routes } from '@angular/router';
import { ExpensesComponent } from './container/expenses/expenses.component';
import { HomeComponent } from './container/home/home.component';
import { CategoriesComponent } from './container/categories/categories.component';
import { WalletsComponent } from './container/wallets/wallets.component';
import { ReportsComponent } from './container/reports/reports.component';
import { ImportsComponent } from './container/imports/imports.component';
import { SettingsComponent } from './container/settings/settings.component';
import { authGuard } from './auth.guard';
import {ActivateComponent} from "./container/activate/activate.component";

export const routes: Routes = [
  { path: '', component: HomeComponent },  // Home is accessible without login
  { path: 'activate', component: ActivateComponent },  // Activate is accessible without login
  { path: 'expenses', component: ExpensesComponent, canActivate: [authGuard] },  // Protected by authGuard
  { path: 'categories', component: CategoriesComponent, canActivate: [authGuard] },  // Protected by authGuard
  { path: 'wallets', component: WalletsComponent, canActivate: [authGuard] },  // Protected by authGuard
  { path: 'reports', component: ReportsComponent, canActivate: [authGuard] },  // Protected by authGuard
  { path: 'imports', component: ImportsComponent, canActivate: [authGuard] },  // Protected by authGuard
  { path: 'settings', component: SettingsComponent, canActivate: [authGuard] },  // Protected by authGuard
];
