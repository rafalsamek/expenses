import { Component } from '@angular/core';
import {LeftMenuComponent} from "./left-menu/left-menu.component";
import {DocumentsComponent} from "./documents/documents.component";

@Component({
  selector: 'app-container',
  standalone: true,
  imports: [
    LeftMenuComponent,
    DocumentsComponent
  ],
  templateUrl: './container.component.html',
  styleUrl: './container.component.css'
})
export class ContainerComponent {

}
