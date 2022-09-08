import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';
import {Router} from '@angular/router';
import {tap} from 'rxjs/operators';
import {NotificationService} from '../services/notification.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  users = [];

  constructor(private userService: UserService, private router: Router, private notifyService: NotificationService) {
  }

  private userID: string;

  ngOnInit(): void {
    this.userID = sessionStorage.getItem('username');
    this.populateMask();
  }

  private populateMask() {
    this.userService.getAll().pipe(
      tap((response: any) => {
        this.users = response.body;
      })
    ).subscribe();
  }

  editUser(useId) {
    this.router.navigate(['/editUser', useId]);
  }

  deleteUser(useId) {
    this.userService.deleteUser(useId).subscribe((data: any[]) => {
      this.notifyService.showSuccess('User deleted');
      this.router.navigate((['/user']));
    });
  }

  createUser() {
    this.router.navigate(['/newUser']);
  }
}
