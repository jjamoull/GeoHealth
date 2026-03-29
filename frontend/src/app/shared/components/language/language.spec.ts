import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Language } from './language';

describe('Language', () => {
  let component: Language;
  let fixture: ComponentFixture<Language>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Language]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Language);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
