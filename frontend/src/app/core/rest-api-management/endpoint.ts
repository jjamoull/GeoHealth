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
    },
    EVALUATIONFORM:{
      GETALLFORMFORAMAP:"/admin/evaluationForm/allFormForFinalMap",
    }
  },
  FINALMAPS: {
    ALLMAPS: '/finalMaps/allMaps',
    GET: '/finalMaps'
  },
  RISKFACTORMAPS: {
    ALLMAPS: '/riskFactorMaps/allMaps'
  },
  EVALUATIONFORM:{
    SAVE:"/evaluationForm/saveForm",
    UPDATE:"/evaluationForm/updateForm",
    GETMYFORMFORADIVFORAMAP:"/evaluationForm/myForm",
    GETALLFORMFORAMAP:"/evaluationForm/allFormForFinalMap",
    DELETEFORM:"/evaluationForm/deleteForm"
  },
  MEASURE: {
    GLOBALCONSENSUSINDEX: "/measure/globalConsensusIndex",
    WEIGHTEDENTROPY: "/measure/weightedEntropy",
    KRIPPENDORFF:"/measure/krippendorffAlpha"
    }
} as const;
