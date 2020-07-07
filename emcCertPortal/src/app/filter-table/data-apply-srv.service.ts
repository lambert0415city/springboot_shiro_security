import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'

import { Observable } from "rxjs"; // 明确请求后的可观察对象是Rx的可观察对象，如未指明是Rx的可观察对象，会报错。
import 'rxjs/add/operator/toPromise'; // 引入toPromise操作符，否则会报错，toPromise方法未定义。

@Injectable({
  providedIn: 'root'
})
export class DataApplySrvService {

  constructor(public http:HttpClient) { }

  httpOptions = {headers: new HttpHeaders({
      'Accept': "Application/json ; odata=verbose"
    })
  };

  doLogin(){
　　let url = '请求的路径'
　　//subsctibe: 利用rxjs封装的http获取一部请求的数据，类似于promise

　　this.http.get(url,this.httpOptions).subscribe((data:any)=>{
        return data;
　　　　//data:返回的数据
　　})

    this.http.get(url,this.httpOptions)
    .toPromise()    // 将可观察对象转为承诺，接下来按照承诺的方式处理。
    .then(response => {
        let data = response.json();
        if (data.is_ok == 2) {
            let result:string = data.rs;
            console.log(result);
            return Promise.resolve(result);
        }
        return Promise.reject("false");
    }).catch(error => {
        console.log(error);
        return Promise.reject("false"); 
    });
  }


  // get() : Promise<string> {
  //   let serverurl: string = "url地址";
  //   // 创建请求参数对象，所有请求参数被放在此对象中。
  //   let param = new URLSearchParams();
  //   param.append("param1","test"); // 向请求参数中放入参数及值
  //   // 创建请求设置对象，将请求参数作为其构造方法参数对象的"search"属性值。
  //   // 请注意，在这里我们并未明确请求头（Headers），Angular会根据请求方法（method）
  //   // 及请求参数的类型，自动确定请求头类型。
  //   let options = new RequestOptions({search:param});
  //   // 发起get请求。注意http属性必须由依赖注入而来。
  //   return this.http.get(serverurl, options)
  //   .toPromise()    // 将可观察对象转为承诺，接下来按照承诺的方式处理。
  //   .then(response => {
  //       let data = response.json();
  //       if (data.is_ok == 2) {
  //           let result:string = data.rs;
  //           console.log(result);
  //           return Promise.resolve(result);
  //       }
  //       return Promise.reject("false");
  //   }).catch(error => {
  //       console.log(error);
  //       return Promise.reject("false"); 
  //   });
  // }

}
