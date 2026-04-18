import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapUploadModal } from './map-upload-modal';

describe('MapUploadModal', () => {
  let component: MapUploadModal;
  let fixture: ComponentFixture<MapUploadModal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapUploadModal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapUploadModal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
