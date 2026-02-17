import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {UserResponseDto} from '../Model/UserModel/UserResponseDto';
import {UsersServices} from '../Service/UserService/users-services';
import {CommonModule} from '@angular/common';
import {UserUpdateDto} from '../Model/UserModel/UserUpdateDto';
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

  public isEditingField={
    username: false,
    firstName: false,
    lastName: false,
    email: false,
  }

  public errorMessage: string | null = null;

  constructor(private userService:UsersServices, private cdr: ChangeDetectorRef) {}

  /**
   * Collect the user information when the component initialize
   */
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

  /**
   * Toggle the value of the field
   *
   * @param field the isEditingField field we want to toggle
   * @modifies isEditingField
   * @effect toggle the value of the selected field
   **/
  toggleEdit(field: keyof typeof this.isEditingField) {
    this.isEditingField[field] = !this.isEditingField[field];
  }

  /**
   * Collect the user modified information and send them to backend
   *
   * @effect : modifies user information and send to the backend
   * */
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
        this.errorMessage=null;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error while updating', err);
        this.errorMessage = err.error.message;
        this.cdr.detectChanges();
      }
    });
  }


}
