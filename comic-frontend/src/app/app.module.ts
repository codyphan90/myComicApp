import {NgModule, ApplicationRef} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {HttpModule, Http, RequestOptions, Response} from '@angular/http';
import {FormsModule} from '@angular/forms';
import {AuthConfig} from "angular2-jwt";
import {JwtConfigService, JwtHttp} from "angular2-jwt-refresh";
/*
 * Platform and Environment providers/directives/pipes
 */
import {routing} from './app.routing'
// App is our top level component
import {AppComponent} from './app.component';
import {APP_RESOLVER_PROVIDERS} from './app.resolver';
import {AppState, InternalStateType} from './app.service';

// Core providers
import {CoreModule} from "./core/core.module";
import {SmartadminLayoutModule} from "./shared/layout/layout.module";


import {ModalModule} from 'ngx-bootstrap/modal';
import {FacebookModule} from "ngx-facebook";

// Application wide providers
const APP_PROVIDERS = [
    ...APP_RESOLVER_PROVIDERS,
    AppState
];

type StoreType = {
    state: InternalStateType,
    restoreInputValues: () => void,
    disposeOldHosts: () => void
};

/**
 * `AppModule` is the main entry point into Angular2's bootstraping process
 */
@NgModule({
    bootstrap: [AppComponent],
    declarations: [
        AppComponent,

    ],
    imports: [ // import Angular's modules
        BrowserModule,
        BrowserAnimationsModule,
        FormsModule,
        HttpModule,
        ModalModule.forRoot(),
        CoreModule,
        SmartadminLayoutModule,
        FacebookModule.forRoot(),
        routing
    ],
    exports: [],
    providers: [ // expose our Services and Providers into Angular's dependency injection
        {
            provide: JwtHttp,//angular2-jwt
            useFactory: getJwtHttp,
            deps: [Http, RequestOptions]
        },
        // ENV_PROVIDERS,
        APP_PROVIDERS
    ]
})
export class AppModule {
    constructor(public appRef: ApplicationRef, public appState: AppState) {
    }


}

export function getJwtHttp(http: Http, options: RequestOptions) {
    let jwtOptions = {
        endPoint: 'http://localhost:9966/api/auth/token',
        // optional
        payload: {type: 'refresh'},
        beforeSeconds: 600, // refresh tokeSn before 600 second
        tokenName: 'refresh_token',
        refreshTokenGetter: (() => localStorage.getItem('refresh_token')),
        tokenSetter: ((res: Response): boolean | Promise<void> => {
            console.log("refresh token reponse:", res);
            res = res.json();

            if (!res['token'] || !res['refreshToken']) {
                localStorage.removeItem('id_token');
                localStorage.removeItem('refresh_token');

                return false;
            }

            localStorage.setItem('id_token', res['token']);
            localStorage.setItem('refresh_token', res['refreshToken']);

            return true;
        })
    };
    let authConfig = new AuthConfig({
        noJwtError: true,
        globalHeaders: [{'Accept': 'application/json'}],
        tokenGetter: (() => localStorage.getItem('id_token')),
    });

    return new JwtHttp(
        new JwtConfigService(jwtOptions, authConfig),
        http,
        options
    );
}

