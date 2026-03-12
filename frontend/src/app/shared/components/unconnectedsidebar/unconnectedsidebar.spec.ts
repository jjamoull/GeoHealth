import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Unconnectedsidebar } from './unconnectedsidebar';

describe('Unconnectedsidebar', () => {
  let component: Unconnectedsidebar;
  let fixture: ComponentFixture<Unconnectedsidebar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Unconnectedsidebar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Unconnectedsidebar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
