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
    MAPS: {
      FINALMAPS:{
        DELETE: '/admin/finalMaps',
        UPLOAD: '/admin/finalMaps/uploadShapeFile'
      },
      RISKFACTORMAPS: {
        UPLOAD: '/admin/riskFactorMaps/file'
      }
    }
  },
  FINALMAPS: {
    ALLMAPS: '/finalMaps/allMaps',
    GET: '/finalMaps'
  },
  RISKFACTORMAPS: {
    ALLMAPS: '/riskFactorMaps/allMaps'
  },
  VAlIDATIONFORM:{
    SAVE:"/validationForm/saveForm",
    UPDATE:"/validationForm/updateForm",
    GETFORM:"/validationForm/form",
    GETMYFORMFORADEP:"/validationForm/myForm",
    GETALLFORM:"/validationForm/allForm",
    GETALLFORMFORADEP:"/validationForm/allFormForDepartment"
  }
} as const;
