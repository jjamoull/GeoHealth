import { ComponentFixture, TestBed } from '@angular/core/testing';

import {MapLegendComponent } from './map-legend';

describe('MapLegend', () => {
  let component: MapLegendComponent;
  let fixture: ComponentFixture<MapLegendComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapLegendComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapLegendComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
