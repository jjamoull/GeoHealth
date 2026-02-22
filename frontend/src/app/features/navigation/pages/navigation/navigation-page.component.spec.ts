import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationPageComponent } from './navigation-page.component';

describe('Navigation', () => {
  let component: NavigationPageComponent;
  let fixture: ComponentFixture<NavigationPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NavigationPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavigationPageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
