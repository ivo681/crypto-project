import { TestBed } from '@angular/core/testing';

import { AnalyticalService } from './analytical.service';

describe('AnalyticalService', () => {
  let service: AnalyticalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnalyticalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
