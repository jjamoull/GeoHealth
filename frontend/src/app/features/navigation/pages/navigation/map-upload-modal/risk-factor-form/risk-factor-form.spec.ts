import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RiskFactorForm } from './risk-factor-form';

describe('RiskFactorForm', () => {
  let component: RiskFactorForm;
  let fixture: ComponentFixture<RiskFactorForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RiskFactorForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RiskFactorForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
