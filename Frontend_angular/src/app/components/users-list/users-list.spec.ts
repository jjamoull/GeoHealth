import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsersList } from './users-list';

describe('UsersList', () => {
  let component: UsersList;
  let fixture: ComponentFixture<UsersList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UsersList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UsersList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
