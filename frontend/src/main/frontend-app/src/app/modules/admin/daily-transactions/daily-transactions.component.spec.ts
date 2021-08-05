import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DailyTransactionsComponent } from './daily-transactions.component';

describe('DailyTransactionsComponent', () => {
  let component: DailyTransactionsComponent;
  let fixture: ComponentFixture<DailyTransactionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DailyTransactionsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DailyTransactionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
