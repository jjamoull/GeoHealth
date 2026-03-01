import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorSuccessMessageComponent } from './error-success-message.component';

describe('ErrorSuccessMessageComponent', () => {
  let component: ErrorSuccessMessageComponent;
  let fixture: ComponentFixture<ErrorSuccessMessageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ErrorSuccessMessageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ErrorSuccessMessageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
