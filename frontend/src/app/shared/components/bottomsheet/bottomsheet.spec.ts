import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Bottomsheet } from './bottomsheet';

describe('Bottomsheet', () => {
  let component: Bottomsheet;
  let fixture: ComponentFixture<Bottomsheet>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Bottomsheet]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Bottomsheet);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
