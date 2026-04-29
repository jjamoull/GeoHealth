import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
import 'leaflet/dist/leaflet.css';
import '@geoman-io/leaflet-geoman-free/dist/leaflet-geoman.css';

bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));
