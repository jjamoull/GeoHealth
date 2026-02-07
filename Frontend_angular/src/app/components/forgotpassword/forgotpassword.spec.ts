import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Forgotpassword } from './forgotpassword';

describe('Forgotpassword', () => {
  let component: Forgotpassword;
  let fixture: ComponentFixture<Forgotpassword>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Forgotpassword]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Forgotpassword);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
