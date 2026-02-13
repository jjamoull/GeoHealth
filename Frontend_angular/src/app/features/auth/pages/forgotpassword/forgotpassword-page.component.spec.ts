import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ForgotpasswordPageComponent } from './forgotpassword-page.component';

describe('Forgotpassword', () => {
  let component: ForgotpasswordPageComponent;
  let fixture: ComponentFixture<ForgotpasswordPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ForgotpasswordPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ForgotpasswordPageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
