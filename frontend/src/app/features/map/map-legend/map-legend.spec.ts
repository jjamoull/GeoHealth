import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapLegend } from './map-legend';

describe('MapLegend', () => {
  let component: MapLegend;
  let fixture: ComponentFixture<MapLegend>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapLegend]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapLegend);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
