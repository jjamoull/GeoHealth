export const API_ENDPOINTS = {
  USER: {
    UPDATE: '/users/update',
    PROFILE: '/users/profile',
    ALL:'/users/all',
    CHANGEPASSWORD: '/user/change-password'
  },
  AUTH: {
    LOGIN: '/auth/login',
    REGISTER: '/auth/register',
    LOGOUT: '/auth/logout',
    STATUS: '/auth/status',
  }
} as const;
