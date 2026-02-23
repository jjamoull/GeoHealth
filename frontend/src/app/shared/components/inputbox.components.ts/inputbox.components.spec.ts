import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputboxComponents } from './inputbox.components.ts';

describe('InputboxComponents', () => {
  let component: InputboxComponents;
  let fixture: ComponentFixture<InputboxComponents>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InputboxComponents]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InputboxComponents);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
