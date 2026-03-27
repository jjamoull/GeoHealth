import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluationCommentComponent } from './evaluation-comment';

describe('EvaluationCommentComponent', () => {
  let component: EvaluationCommentComponent;
  let fixture: ComponentFixture<EvaluationCommentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EvaluationCommentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EvaluationCommentComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
