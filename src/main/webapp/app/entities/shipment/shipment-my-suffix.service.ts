import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ShipmentMySuffix } from './shipment-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ShipmentMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/shipments';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/shipments';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(shipment: ShipmentMySuffix): Observable<ShipmentMySuffix> {
        const copy = this.convert(shipment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(shipment: ShipmentMySuffix): Observable<ShipmentMySuffix> {
        const copy = this.convert(shipment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ShipmentMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.etd = this.dateUtils
            .convertDateTimeFromServer(entity.etd);
    }

    private convert(shipment: ShipmentMySuffix): ShipmentMySuffix {
        const copy: ShipmentMySuffix = Object.assign({}, shipment);

        copy.etd = this.dateUtils.toDate(shipment.etd);
        return copy;
    }
}
