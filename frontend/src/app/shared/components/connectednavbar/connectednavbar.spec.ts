import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Connectednavbar } from './connectednavbar';

describe('Connectednavbar', () => {
  let component: Connectednavbar;
  let fixture: ComponentFixture<Connectednavbar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Connectednavbar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Connectednavbar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
