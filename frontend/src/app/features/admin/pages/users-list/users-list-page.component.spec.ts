import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersListPageComponent } from './users-list-page.component';

describe('UsersList', () => {
  let component: UsersListPageComponent;
  let fixture: ComponentFixture<UsersListPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsersListPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersListPageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
