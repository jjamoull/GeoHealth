import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapUploadModalComponent } from './map-upload-modal';

describe('MapUploadModal', () => {
  let component: MapUploadModalComponent;
  let fixture: ComponentFixture<MapUploadModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapUploadModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapUploadModalComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
