import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalMap } from './final-map';

describe('FinalMap', () => {
  let component: FinalMap;
  let fixture: ComponentFixture<FinalMap>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FinalMap]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FinalMap);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
