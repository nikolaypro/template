import { TestBed, inject } from '@angular/core/testing';

import { MascotHttpService } from './mascot-http.service';

describe('MascotHttpService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MascotHttpService]
    });
  });

  it('should be created', inject([MascotHttpService], (service: MascotHttpService) => {
    expect(service).toBeTruthy();
  }));
});
