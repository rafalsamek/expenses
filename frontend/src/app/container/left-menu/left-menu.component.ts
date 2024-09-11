import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { NgIf } from "@angular/common";
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-left-menu',
  standalone: true,
  imports: [RouterLinkActive, RouterLink, NgIf],
  templateUrl: './left-menu.component.html',
  styleUrl: './left-menu.component.css',
})
export class LeftMenuComponent {
  constructor(public authService: AuthService) {}
}
