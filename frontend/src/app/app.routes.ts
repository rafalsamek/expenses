import { Routes } from '@angular/router';
import {ExpensesComponent} from "./container/expenses/expenses.component";
import {HomeComponent} from "./container/home/home.component";
import {CategoriesComponent} from "./container/categories/categories.component";
import {WalletsComponent} from "./container/wallets/wallets.component";
import {ReportsComponent} from "./container/reports/reports.component";
import {ImportsComponent} from "./container/imports/imports.component";
import {SettingsComponent} from "./container/settings/settings.component";

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'expenses', component: ExpensesComponent },
  { path: 'categories', component: CategoriesComponent },
  { path: 'wallets', component: WalletsComponent },
  { path: 'reports', component: ReportsComponent },
  { path: 'imports', component: ImportsComponent },
  { path: 'settings', component: SettingsComponent },
];
