import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { ProductDemoMySuffix } from './product-demo-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProductDemoMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/product-demos';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/product-demos';

    constructor(private http: Http) { }

    create(productDemo: ProductDemoMySuffix): Observable<ProductDemoMySuffix> {
        const copy = this.convert(productDemo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(productDemo: ProductDemoMySuffix): Observable<ProductDemoMySuffix> {
        const copy = this.convert(productDemo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ProductDemoMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(productDemo: ProductDemoMySuffix): ProductDemoMySuffix {
        const copy: ProductDemoMySuffix = Object.assign({}, productDemo);
        return copy;
    }
}
