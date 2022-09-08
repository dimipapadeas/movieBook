import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs';


export interface MovieBase {
  title: string;
  description: string;
  userId: string;
}

export interface Vote {
  movieId: string;
  userId: string;
  vote: string;
}

export interface Movie {
  id: string;
  title: string;
  description: string;
  createdBy: string;
  createdOn: string;
  vote: Vote;
}


@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private httpClient: HttpClient) {
  }

 getAllMovies(title: string, sort: string, direction: string, page: string, size: string) {
    return this.httpClient.get(
      environment.apiUrl + '/movie/all', {
        observe: 'response',
        params: new HttpParams()
          .set('page', page)
          .set('size', size)
          .set('direction', direction)
          .set('filter', title)
          .set('sort', sort)
      });
  }

  getAllUsersMovies(sort: string, direction: string, page: string, size: string, userName: string) {
    return this.httpClient.get(
      environment.apiUrl + '/movie/users', {
        observe: 'response',
        params: new HttpParams()
          .set('userName', userName)
          .set('sort', sort)
          .set('direction', direction)
          .set('page', 0)
          .set('size', size)
      });
  }

  addMovie(movie: MovieBase): Observable<MovieBase> {
    return this.httpClient.post<MovieBase>(`${environment.apiUrl + '/movie'}`, movie);
  }

  voteMovie(vote: Vote): Observable<Vote> {
    return this.httpClient.post<Vote>(`${environment.apiUrl + '/vote'}`, vote);
  }

}
