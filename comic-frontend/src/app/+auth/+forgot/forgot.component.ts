import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {BaseComponent} from "../../base/base.component";
import {UserService} from "../../+service/user.service";

@Component({
  selector: 'app-forgot',
  templateUrl: './forgot.component.html',
  styles: []
})
export class ForgotComponent extends BaseComponent implements OnInit {

  constructor(private router: Router, private us: UserService) {
    super();
  }

  email:string;

  ngOnInit() {
  }

  submit(event){
    event.preventDefault();
    // this.router.navigate(['/dashboard/+analytics'])
      this.us.resetPassword(this.email).subscribe(response => {
              console.log('res: ' + JSON.stringify(response));
              var resetResult = response.success;
              if (resetResult == true) {
                this.successAlert("A password reset email has been sent to your email address");
                this.router.navigate(['/auth/login']);
              } else {
                  this.errorAlert(response.exceptionMessage);
              }
          },
          error => {
              this.errorAlert("Has error!");
          })

  }
}
