import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidationComment } from './validation-comment';

describe('ValidationComment', () => {
  let component: ValidationComment;
  let fixture: ComponentFixture<ValidationComment>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ValidationComment]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ValidationComment);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
