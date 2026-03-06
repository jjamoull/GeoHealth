import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Unconnectednavbar } from './unconnectednavbar';

describe('Unconnectednavbar', () => {
  let component: Unconnectednavbar;
  let fixture: ComponentFixture<Unconnectednavbar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Unconnectednavbar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Unconnectednavbar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
