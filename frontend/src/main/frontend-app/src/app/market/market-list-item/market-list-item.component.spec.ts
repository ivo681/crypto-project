import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketListItemComponent } from './market-list-item.component';

describe('MarketListItemComponent', () => {
  let component: MarketListItemComponent;
  let fixture: ComponentFixture<MarketListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MarketListItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MarketListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
