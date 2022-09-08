import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {MatPaginator, PageEvent} from '@angular/material/paginator';
import {MatSort, Sort} from '@angular/material/sort';
import {FormControl, FormGroup} from '@angular/forms';
import {MatTableDataSource} from '@angular/material/table';
import {tap} from 'rxjs/operators';
import {Router} from '@angular/router';
import {Movie, MovieService, Vote} from '../services/movie.service';
import {NotificationService} from '../services/notification.service';
import {AuthenticationService} from '../services/authentication.service';
import {BehaviorSubject, Observable} from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  displayedColumns: string[] = ['title', 'description', 'createdOn', 'createdBy', 'likes', 'dislikes', 'voteBtns'];
  displayedColumnsAccount: string[] = ['description', 'calculatedBalance'];

  @Input() dataLength: number;
  @Input() dataPageIndex = 0; // Which index is the current for the data paginator
  @Input() dataPageSize = 5; // Size of paginator page data
  @Input() pageSizeOptions: number[] = [5, 10, 25, 100]; // Size of paginator page data

  @Output() pageChange: EventEmitter<PageEvent> = new EventEmitter();

  constructor(private authenticationService: AuthenticationService,
              private movieService: MovieService,
              private notifyService: NotificationService, private router: Router) {
  }

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  form: FormGroup;
  moviesList: MatTableDataSource<Movie>;
  headers: string[];
  headersAccount: string[];
  tableSort = '';
  tableSortDir = 'asc';
  filterValues: {
    title: string;
  };

  private userID: string;
  isAdmin = false;

  isUserLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject(false);

  ngOnInit() {
    this.userID = sessionStorage.getItem('username');
    this.isAdmin = (/true/i).test(sessionStorage.getItem('userAdmin'));
    this.isUserLoggedIn = this.authenticationService.isUserLoggedIn();
    this.createFilterForm();
    this.filterValues = this.form.value;
    this.populateMask();
  }

  private populateMask() {
    this.getMovies(this.filterValues.title, this.tableSort, this.tableSortDir, this.dataPageIndex.toString(), this.dataPageSize.toString()).subscribe();
  }

  applyFilter(form: FormGroup) {
    this.filterValues = form.value;
    return this.getMovies(this.filterValues.title, this.tableSort, this.tableSortDir, this.dataPageIndex.toString(), this.dataPageSize.toString()).subscribe();
  }

  clearFilter() {
    this.createFilterForm();
    this.filterValues = this.form.value;
    this.getMovies(this.filterValues.title, this.tableSort, this.tableSortDir, this.dataPageIndex.toString(), this.dataPageSize.toString()).subscribe();
  }

  private createFilterForm() {
    this.form = new FormGroup({
      // id: new FormControl(''),
      amount: new FormControl(''),
      date: new FormControl(''),
      description: new FormControl(''),
      title: new FormControl(''),
      createdBy: new FormControl(''),
      createdOn: new FormControl(''),
      username: new FormControl(''),
    });
  }

  private getMovies(title: string, sort: string, direction: string, page: string, size: string) {
    return this.movieService.getAllMovies(title, sort, direction, page, size).pipe(
      tap((response: any) => {
        const movies: any = response.body.content;
        this.moviesList = new MatTableDataSource(movies);
        this.dataLength = +response.body.totalElements;
        this.dataPageIndex = +response.body.pageable.pageNumber;
      })
    );
  }


  private getMoviesOfUser(sort: string, direction: string, page: string, size: string, user: string) {
    return this.movieService.getAllUsersMovies(sort, direction, page, size, user).pipe(
      tap((response: any) => {
        const movies: any = response.body.content;
        this.moviesList = new MatTableDataSource(movies);
        this.dataLength = +response.body.totalElements;
        this.dataPageIndex = +response.body.pageable.pageNumber;
      })
    );
  }


  sortData(event: Sort) {
    if (event.direction === '') {
      this.tableSort = '';
      this.tableSortDir = 'asc';
    } else {
      this.tableSort = event.active;
      this.tableSortDir = event.direction;
    }
    return this.getMovies(this.filterValues.title, this.tableSort, this.tableSortDir, this.dataPageIndex.toString(),
      this.dataPageSize.toString()).subscribe();
  }

  getServerData(event: PageEvent) {
    this.dataPageIndex = event.pageIndex;
    this.dataPageSize = event.pageSize;
    return this.getMovies(this.filterValues.title, this.tableSort, this.tableSortDir, this.dataPageIndex.toString(),
      this.dataPageSize.toString()).subscribe();
  }

  getServerDataForUser(user: string) {
    return this.getMoviesOfUser(this.tableSort, this.tableSortDir, this.dataPageIndex.toString(),
      this.dataPageSize.toString(), user).subscribe();
  }


  likeMove(id, $event: MouseEvent, like) {
    $event.stopPropagation();
    const vote: Vote = {
      movieId: id,
      userId: sessionStorage.getItem('userId'),
      vote: like
    };
    this.movieService.voteMovie(vote).subscribe(response => {
      this.notifyService.showSuccess('Vote added');
      this.clearFilter();
    });
  }


  login() {
    this.router.navigate(['/login']);
  }

  addNewUser() {
    this.router.navigate(['/newUser']);
  }
}
