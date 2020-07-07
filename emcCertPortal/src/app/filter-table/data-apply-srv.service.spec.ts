import { TestBed } from '@angular/core/testing';

import { DataApplySrvService } from './data-apply-srv.service';

describe('DataApplySrvService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DataApplySrvService = TestBed.get(DataApplySrvService);
    expect(service).toBeTruthy();
  });
});
