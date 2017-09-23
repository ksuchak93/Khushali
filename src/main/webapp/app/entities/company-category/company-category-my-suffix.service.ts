import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CompanyCategoryMySuffix } from './company-category-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyCategoryMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/company-categories';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/company-categories';

    constructor(private http: Http) { }

    create(companyCategory: CompanyCategoryMySuffix): Observable<CompanyCategoryMySuffix> {
        const copy = this.convert(companyCategory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(companyCategory: CompanyCategoryMySuffix): Observable<CompanyCategoryMySuffix> {
        const copy = this.convert(companyCategory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CompanyCategoryMySuffix> {
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

    private convert(companyCategory: CompanyCategoryMySuffix): CompanyCategoryMySuffix {
        const copy: CompanyCategoryMySuffix = Object.assign({}, companyCategory);
        return copy;
    }
}
