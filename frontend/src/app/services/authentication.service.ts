import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';
import {BehaviorSubject, of} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private httpClient: HttpClient) {
  }

  isLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject(false);

  authenticate(username, password) {
    return this.httpClient.post<any>(environment.apiUrl + '/authenticate', {username, password});
  }

  isUserLoggedIn() {
    const toekn = sessionStorage.getItem('token');
    this.isLoggedIn.next(!(toekn === null));
    return this.isLoggedIn;
  }

  logOut(username) {
    const toekn = sessionStorage.getItem('token');
    this.isLoggedIn.next(!(toekn === null));
    return this.httpClient.post<any>(environment.apiUrl + '/logout', {username});
  }
}
