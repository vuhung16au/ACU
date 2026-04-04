import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AppComponent],
      imports: [HttpClientTestingModule, FormsModule]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create the app', () => {
    expect(component).toBeTruthy();
  });

  it('should retrieve starter content from the server', () => {
    const mockOverview = {
      title: 'SPA Integration Lesson Board',
      description: 'Starter content',
      localDevelopmentFlow: 'Dev flow',
      publishedDeploymentFlow: 'Publish flow',
      apiRoutes: [],
      learningChecklist: []
    };

    component.ngOnInit();

    const req = httpMock.expectOne('/api/spa-integration/overview');
    expect(req.request.method).toEqual('GET');
    req.flush(mockOverview);

    expect(component.overview).toEqual(mockOverview);
  });
});
