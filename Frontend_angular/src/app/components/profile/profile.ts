import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {UserResponseDto} from '../Model/UserModel/UserResponseDto';
import {UserUpdateDto} from '../Model/UserModel/UserUpdateDto';
import {UsersServices} from '../Service/UserService/users-services';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';



@Component({
  selector: 'app-profile',
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css',
})
export class Profile implements OnInit{

  public user: UserResponseDto={
    username: '',
    firstName: '',
    lastName: '',
    email: '',
    role: ''
  };

  public editing = {
    username: false,
    firstName: false,
    lastName: false,
    email: false,
    role: false
  };

  constructor(private userService:UsersServices, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.userService.getConnectedUser().subscribe({
      next: user =>{
        console.log(user);
        this.user=user;
        this.cdr.detectChanges();
      },
      error:(err)=>{
        console.log(err);
        console.log("Error occured");
      }
    })
  }


  toggleEdit(field: keyof typeof this.editing) {
    this.editing[field] = !this.editing[field];
  }

  updateProfile(){

    let userToUpdate:UserUpdateDto ={
      username: this.user.username,
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email
    }

    this.userService.updateUser(userToUpdate).subscribe({
      next: (response) => {
        console.log('Update successful!', response);
      },
      error: (err) => {
        console.error('Error while updating', err);
      }
    });
  }
}
