export interface AdminUserDto {
  id: number;
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  role: string;
  deleted:boolean;
  banned:boolean;
}
