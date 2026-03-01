export const API_ENDPOINTS = {
  USER: {
    UPDATE: '/users/update',
    PROFILE: '/users/profile',
    ALL:'/users/all',
    CHANGEPASSWORD: '/users/change-password',
    DELETE: '/users/delete',
    ISADMIN: '/users/isAdmin'
  },
  AUTH: {
    LOGIN: '/auth/login',
    REGISTER: '/auth/register',
    LOGOUT: '/auth/logout',
    STATUS: '/auth/status',
  },
  ADMIN: {
    ALLUSER: '/admin/users',
    BAN: '/admin/users/ban',
    UNBAN:'/admin/users/unban/',
    CHANGEROLE:'/admin/users/change-role',
  }
} as const;
