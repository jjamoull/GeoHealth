import { Component } from '@angular/core';
import {ButtonComponent} from "../../../../../shared/components/button.component/button.component";
import {TranslocoPipe} from "@jsverse/transloco";
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-about',
  imports: [],
  templateUrl: './about.component.html',
  styleUrl: './about.component.css',
})
export class AboutComponent {
  constructor(
    public route: ActivatedRoute,
    private router: Router,
  ) {}


}
