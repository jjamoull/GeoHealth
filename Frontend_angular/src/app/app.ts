import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {Home} from './components/home/home';
import {Authentication} from './components/authentication/authentication';
import {Map} from './components/map/map';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Home, Authentication, Map],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('GeoHealth_Angular');
}
