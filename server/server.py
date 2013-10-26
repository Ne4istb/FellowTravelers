import re
from wsgiref.simple_server import make_server

import restlite
import datetime


class Order:
    def __init__(self, longitude_start, latitude_start, longitude_end, latitude_end, phone, price):
        self.longitude_start = longitude_start
        self.latitude_start = latitude_start
        self.longitude_end = longitude_end
        self.latitude_end = latitude_end
        self.phone = phone
        self.created_at = str(datetime.datetime.utcnow())
        self.updated_at = str(datetime.datetime.utcnow())
        self.id = hash((self.phone, self.created_at))
        self.status = "Active"
        self.price = price  # get price func have to be called

    def generate_json(self):
        return {'id': self.id, 'status': self.status,
                'direction':
                {'startPoint': ({'longitude': self.longitude_start, 'latitude': self.latitude_start}),
                 'endPoint': ({'longitude': self.longitude_end, 'latitude': self.latitude_end})},
                'price': self.price, 'phone': self.phone,
                'createdAt': self.created_at, 'updatedAt': self.updated_at}


orders_arr = {0: Order(32.556, 56.458, 26.354, 87.244, "+380933443339", 25.6)}

#order_obj = Order(32.556, 56.458, 26.354, 87.244, "+380933443339")


@restlite.resource
def create_order():
    def POST(request, entity):
        try:
            new_order = Order(entity[u'longitude_start'], entity[u'latitude_start'],
                              entity[u'longitude_end'], entity[u'latitude_end'],
                              entity[u'phone'], entity[u'price'])
        except Exception, e:
            print e
            return request.response('Failed')

        orders_arr[new_order.id] = new_order
        return request.response({"id": new_order.id})

    return locals()

@restlite.resource
def order_by_id():
    def GET(request):
        mask = re.compile('id=(\d+)')
        m = mask.search(request['QUERY_STRING'])
        if m:
            order_id = int(m.group(1))
        else:
            return request.response('invalid ID')

        if order_id in orders_arr:
            responce = [orders_arr[order_id].generate_json()]
        else:
            responce = 'Failed ot find element with ID %s' % order_id
        return request.response(responce)

    def DELETE(request):
        mask = re.compile('id=(\d+)')
        m = mask.search(request['QUERY_STRING'])
        if m:
            order_id = int(m.group(1))
        else:
            return request.response('invalid ID')

        if order_id in orders_arr:
            del orders_arr[order_id]
            responce = 'Order is successfully deleted'
        else:
            responce = 'Failed ot find element with ID %s' % order_id
        return request.response(responce)

    return locals()


@restlite.resource
def orders():
    def GET(request):
        orders_list = []
        for key in orders_arr.keys():
            orders_list.append(orders_arr[key].generate_json())
        return request.response(orders_list)

    return locals()


routes = [
    (r'GET,PUT,POST /(?P<type>((json)))/(?P<path>.*)', 'GET,PUT,POST /%(path)s', 'ACCEPT=application/%(type)s'),
    (r'GET /order\?id=(?P<id>)', order_by_id),
    (r'GET /orders', orders),
    (r'POST /order', create_order),
    (r'DELETE /order\?id=(?P<id>)', order_by_id)

]
#\?id=(?P<id>)

def run_server():
    httpd = make_server('172.27.40.14', 8000, restlite.router(routes))
    httpd.serve_forever()


run_server()