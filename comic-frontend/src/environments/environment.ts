var HOME_URL = 'http://localhost:4200';
var USER_SERVICE_BASE = 'http://localhost:8290/user';
var USER_GROUP_SERVICE_BASE = 'http://localhost:8290/userGroup';
var BOOK_SERVICE_BASE = 'http://localhost:8290/book';
var LOGIN_ROUTE = '/auth/login';

export const environment = {
    production: false,
    debug: true,
    home_url: HOME_URL,
    authen: {
        login_url: HOME_URL + '/#' + LOGIN_ROUTE,
        logout_url: HOME_URL + '/#' + LOGIN_ROUTE,
        home_url: HOME_URL + '/#/my-account/detail',
        login_route: LOGIN_ROUTE
    },
    user_service: {
        user_validation_endpoint: USER_SERVICE_BASE + '/login',
        user_register_endpoint: USER_SERVICE_BASE,
        user_reset_password_endpoint: USER_SERVICE_BASE + '/reset-password',
        user_get_endpoint: USER_SERVICE_BASE,
        user_update_endpoint: USER_SERVICE_BASE,
        user_email_verify_endpoint: USER_SERVICE_BASE + '/email-verify',
        user_active_endpoint: USER_SERVICE_BASE + '/active',
        user_connect_facebook_endpoint: USER_SERVICE_BASE + '/connect-facebook'

    },
    user_group_service: {
        get_user_group_list_endpoint: USER_GROUP_SERVICE_BASE
    },
    book_service: {
        get_page_endpoint: BOOK_SERVICE_BASE + '/get-page/',
        get_book_endpoint: BOOK_SERVICE_BASE + '/',
        update_book_endpoint: BOOK_SERVICE_BASE,
        copy_book_endpoint: BOOK_SERVICE_BASE + '/',
        delete_book_endpoint: BOOK_SERVICE_BASE + '/soft-delete/'
    }
};
