import { Component } from '@angular/core';
import {LeftMenuComponent} from "./left-menu/left-menu.component";
import {ExpensesComponent} from "./expenses/expenses.component";
import {RouterOutlet} from "@angular/router";
import {CategoriesComponent} from "./categories/categories.component";
import {WalletsComponent} from "./wallets/wallets.component";
import {ReportsComponent} from "./reports/reports.component";
import {ImportsComponent} from "./imports/imports.component";
import {SettingsComponent} from "./settings/settings.component";

@Component({
  selector: 'app-container',
  standalone: true,
  imports: [
    LeftMenuComponent,
    ExpensesComponent,
    CategoriesComponent,
    WalletsComponent,
    ReportsComponent,
    ImportsComponent,
    SettingsComponent,
    RouterOutlet
  ],
  templateUrl: './container.component.html',
  styleUrl: './container.component.css'
})
export class ContainerComponent {

}
