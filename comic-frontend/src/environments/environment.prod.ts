var HOME_URL = 'http://localhost:4200';
var AUTH_SERVICE_BASE = 'http://192.168.0.180:8080';

export const environment = {
    production: true,
    debug: false,
    authen: {
        login_url: HOME_URL + '/#/auth/login',
        logout_url: '',
        home_url: HOME_URL
    },
    auth_service: {
        user_validation_endpoint: AUTH_SERVICE_BASE + '/user/generate'
    }
};