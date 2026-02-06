import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Authentication } from './authentication';

describe('Authentication', () => {
  let component: Authentication;
  let fixture: ComponentFixture<Authentication>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Authentication]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Authentication);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
