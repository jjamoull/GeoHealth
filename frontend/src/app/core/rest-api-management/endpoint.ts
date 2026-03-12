export const API_ENDPOINTS = {
  USER: {
    UPDATE: '/user/update',
    PROFILE: '/user/profile',
    CHANGEPASSWORD: '/user/changePassword',
    DELETE: '/user/delete',
    ISADMIN: '/user/isAdmin'
  },
  AUTH: {
    LOGIN: '/auth/login',
    REGISTER: '/auth/register',
    LOGOUT: '/auth/logout',
    STATUS: '/auth/status',
  },
  ADMIN: {
    USERS: '/admin/users',
    BAN: '/admin/users/ban',
    UNBAN:'/admin/users/unban/',
    CHANGEROLE:'/admin/users/changeRole',
  },
  FINALMAPS: {
    ALLMAPS: '/finalMaps/allMaps',
    DELETE: '/finalMaps',
    UPLOAD: '/finalMaps/uploadShapeFile',
    GET: '/finalMaps'
    },
  RISKFACTORMAPS: {
    ALLMAPS: '/riskFactorMaps/allMaps',
    UPLOAD: '/riskFactorMaps/file'
  }
} as const;
