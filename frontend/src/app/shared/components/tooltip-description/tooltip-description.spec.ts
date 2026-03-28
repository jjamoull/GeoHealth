import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TooltipDescription } from './tooltip-description';

describe('TooltipDescription', () => {
  let component: TooltipDescription;
  let fixture: ComponentFixture<TooltipDescription>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TooltipDescription]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TooltipDescription);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
