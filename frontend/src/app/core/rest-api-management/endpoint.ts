export const API_ENDPOINTS = {
  USER: {
    UPDATE: '/user/update',
    PROFILE: '/user/profile',
    CHANGEPASSWORD: '/user/changePassword',
    DELETE: '/user/delete',
    ISADMIN: '/user/isAdmin',
    ANNOTATIONS: '/user/profile/annotation'
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
      RASTERMAPS: {
        UPLOAD: '/admin/rasterMaps/file'
      }
    },
    EVALUATIONFORM:{
      GETALLFORMFORAMAP:"/admin/evaluationForm/allFormForFinalMap",
      DELETEFORM:"/admin/evaluationForm/deleteForm"
    }
  },
  ANNOTATIONS:{
    GET : "/annotation",
    POST : "/annotation/save"
  },
  FINALMAPS: {
    ALLMAPS: '/finalMaps/allMaps',
    GET: '/finalMaps'
  },
  RASTERMAPS: {
    RASTERS: '/rasterMaps/rasters',
    RISKFACTORS: '/rasterMaps/riskFactors'
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
