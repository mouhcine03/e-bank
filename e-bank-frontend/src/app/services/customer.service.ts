import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {BehaviorSubject, catchError, Observable, tap, throwError} from 'rxjs';
import {CustomersModel} from '../CustomersModel';
import {environment} from '../../environments/environement';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private customerSubject=new BehaviorSubject<CustomersModel[]>([])
  customers$=this.customerSubject.asObservable();



  constructor(private http:HttpClient) {
    this.loadCustomer()
  }

   loadCustomer() {
    this.http.get<CustomersModel[]>(environment.apiUrl+"All").pipe(
      catchError(err => {
        console.log(err)
        return throwError(()=>err);
      })
    ).subscribe(customers=>this.customerSubject.next(customers));


  }

  public getCustomers() : Observable<Array<CustomersModel>> {
    return this.http.get<Array<CustomersModel>>(environment.apiUrl+"All")
  }

  public searchCustomer(keyword:string):Observable<Array<CustomersModel>>{
    return this.http.get<Array<CustomersModel>>(environment.apiUrl+"search?keyword="+keyword).pipe(
      tap(
        result=>this.customerSubject.next(result)
      )
    )

  }

  public saveCustomer(customer:CustomersModel):Observable<CustomersModel> {
    return this.http.post<CustomersModel>(environment.apiUrl+"save",customer).pipe(
      tap(newCustomer=>{
      const current=this.customerSubject.value;
      this.customerSubject.next([...current,newCustomer]);
      })
    )

  }

  public deleteCustomer(idCustomer:number):Observable<void>{
     return this.http.delete<void>(environment.apiUrl+"delete/"+idCustomer).pipe(
       tap(()=>{
       const updated=this.customerSubject.value.filter(c=>c.id!==idCustomer);
       this.customerSubject.next(updated)
       })
     )
  }



}
