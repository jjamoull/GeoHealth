import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsensusPageComponent } from './consensus-page.component';

describe('ConsensusPageComponent', () => {
  let component: ConsensusPageComponent;
  let fixture: ComponentFixture<ConsensusPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConsensusPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsensusPageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
