import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluationModalComponent } from './evaluation-modal';

describe('EvaluationModalComponent', () => {
  let component: EvaluationModalComponent;
  let fixture: ComponentFixture<EvaluationModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EvaluationModalComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(EvaluationModalComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
