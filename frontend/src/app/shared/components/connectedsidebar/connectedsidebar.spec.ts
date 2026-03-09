import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Connectedsidebar } from './connectedsidebar';

describe('Connectedsidebar', () => {
  let component: Connectedsidebar;
  let fixture: ComponentFixture<Connectedsidebar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Connectedsidebar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Connectedsidebar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
