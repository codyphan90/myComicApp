var HOME_URL = 'http://localhost:4200';
var USER_SERVICE_BASE = 'http://localhost:8290/user';
var USER_GROUP_SERVICE_BASE = 'http://localhost:8290/userGroup';
var LOGIN_ROUTE = '/auth/login';

export const environment = {
    production: false,
    debug: true,
    authen: {
        login_url: HOME_URL + '/#' + LOGIN_ROUTE,
        logout_url: HOME_URL + '/#' + LOGIN_ROUTE,
        home_url: HOME_URL + '/#/my-account/detail',
        login_route: LOGIN_ROUTE
    },
    user_service : {
        user_validation_endpoint: USER_SERVICE_BASE + '/login',
        user_register_endpoint: USER_SERVICE_BASE,
        user_reset_password_endpoint: USER_SERVICE_BASE + '/reset-password'
    },
    user_group_service : {
        get_user_group_list_endpoint: USER_GROUP_SERVICE_BASE
    }
};
