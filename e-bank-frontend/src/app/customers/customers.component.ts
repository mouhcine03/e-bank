import {Component, OnInit} from '@angular/core';
import {CustomerService} from '../services/customer.service';
import {catchError, Observable, throwError} from 'rxjs';
import {CustomersModel} from '../CustomersModel';
import {FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-customers',
  standalone: false,
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.scss'
})
export class CustomersComponent implements OnInit{
  customers! : Observable<Array<CustomersModel>>
  errorMessage! :string
  searchCustomerForm: FormGroup | undefined

  constructor(private customerService:CustomerService,private fb:FormBuilder) {


  }

  ngOnInit(): void {

    this.searchCustomerForm = this.fb.group({
      keyword: [""]
    });
    this.customers = this.customerService.customers$


  }

  onSearchCustomer() {
    let keyword=this.searchCustomerForm!.value.keyword;
    this.customerService.searchCustomer(keyword).subscribe({
      error: err => this.errorMessage=err.message()
    })

  }

  onDeleteCustomer(id: number) {
    let c =confirm("are you sure you want to delete this customer?")
    if(!c)return;
    this.customerService.deleteCustomer(id).subscribe({
      next:data=>{
        alert("Customer has been successfuly deleted")
      },
      error:err => {
        console.log(err)
      }
    })

  }

  onEditCustomer(id: number) {

  }
}
