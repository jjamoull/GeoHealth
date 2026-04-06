import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalRasterMap } from './final-raster-map';

describe('FinalRasterMap', () => {
  let component: FinalRasterMap;
  let fixture: ComponentFixture<FinalRasterMap>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FinalRasterMap]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FinalRasterMap);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
