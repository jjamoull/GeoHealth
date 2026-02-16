import {Component, OnInit} from '@angular/core';
import {UserResponseDto} from '../Model/UserModel/UserResponseDto';
import {UsersServices} from '../Service/UserService/users-services';
import {CommonModule} from '@angular/common';


@Component({
  selector: 'app-profile',
  imports: [CommonModule],
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


constructor(private userService:UsersServices) {
  }

  ngOnInit() {
    this.userService.getConnectedUser().subscribe({
      next: user =>{
        console.log(user);
        this.user=user;
      },
      error:(err)=>{
        console.log(err);
        console.log("Error occured");
      }
    })
  }


}
