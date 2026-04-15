import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalMapForm } from './final-map-form';

describe('FinalMapForm', () => {
  let component: FinalMapForm;
  let fixture: ComponentFixture<FinalMapForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FinalMapForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FinalMapForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
