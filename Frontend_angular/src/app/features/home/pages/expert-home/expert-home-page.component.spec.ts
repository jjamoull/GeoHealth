import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExpertHomePageComponent } from './expert-home-page.component';

describe('Home', () => {
  let component: ExpertHomePageComponent;
  let fixture: ComponentFixture<ExpertHomePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExpertHomePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ExpertHomePageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
