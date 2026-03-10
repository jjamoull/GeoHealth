import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskFactor } from './risk-factor';

describe('RiskFactor', () => {
  let component: RiskFactor;
  let fixture: ComponentFixture<RiskFactor>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RiskFactor]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RiskFactor);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
