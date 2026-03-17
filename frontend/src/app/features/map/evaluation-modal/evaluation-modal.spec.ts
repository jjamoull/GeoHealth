import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluationModal } from './evaluation-modal';

describe('EvaluationModal', () => {
  let component: EvaluationModal;
  let fixture: ComponentFixture<EvaluationModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EvaluationModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EvaluationModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
