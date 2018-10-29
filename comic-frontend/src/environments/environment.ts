var HOME_URL = 'http://localhost:4200';
var AUTH_SERVICE_BASE = 'http://localhost:8080';
var LOGIN_ROUTE = '/auth/login';

export const environment = {
    production: false,
    debug: false,
    authen: {
        login_url: HOME_URL + '/#' + LOGIN_ROUTE,
        logout_url: HOME_URL + '/#' + LOGIN_ROUTE,
        home_url: HOME_URL,
        login_route: LOGIN_ROUTE
    },
    auth_service: {
        user_validation_endpoint: AUTH_SERVICE_BASE + '/user/login'
    }
};
