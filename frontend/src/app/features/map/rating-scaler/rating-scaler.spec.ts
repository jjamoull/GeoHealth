import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RatingScaler } from './rating-scaler';

describe('RatingScaler', () => {
  let component: RatingScaler;
  let fixture: ComponentFixture<RatingScaler>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RatingScaler]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RatingScaler);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
