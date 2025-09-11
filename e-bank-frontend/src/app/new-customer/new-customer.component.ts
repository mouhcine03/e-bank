import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CustomerService} from '../services/customer.service';
import {CustomersModel} from '../CustomersModel';
import {Router} from '@angular/router';

@Component({
  selector: 'app-new-customer',
  standalone: false,
  templateUrl: './new-customer.component.html',
  styleUrl: './new-customer.component.scss'
})
export class NewCustomerComponent implements OnInit {
  newCustomerForm! :FormGroup

  constructor(private fb: FormBuilder,private customerService:CustomerService,private router:Router) {
  }

  ngOnInit(): void {
    this.newCustomerForm=this.fb.group({
      name :["",[Validators.required,Validators.maxLength(15)]],
      email:["",[Validators.email,Validators.required]]
    })
  }

  handleSaveCustomer() {
    let customer:CustomersModel=this.newCustomerForm.value;
    this.customerService.saveCustomer(customer).subscribe({
      next:data=>{
        alert("Customer has been successfully saved")
        this.router.navigateByUrl("/customer")
      },
      error:err => {
        console.log("err");

      }
    })

  }
}
