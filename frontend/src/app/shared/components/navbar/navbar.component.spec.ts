import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarComponents } from './navbar.component';

describe('NavbarComponents', () => {
  let component: NavbarComponents;
  let fixture: ComponentFixture<NavbarComponents>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavbarComponents]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavbarComponents);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
