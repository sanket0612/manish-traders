import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavBar';
import { Link, withRouter } from 'react-router-dom';
import { instanceOf } from 'prop-types';
import { withCookies, Cookies } from 'react-cookie';

class OrderList extends Component {

    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };


    constructor(props) {
        super(props);
        const {cookies} = props;
        this.state = {orders: [], csrfToken: cookies.get('XSRF-TOKEN'), isLoading: true};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('/v1/api/order', {credentials: 'include'})
            .then(response => response.json())
            .then(data => this.setState({orders: data, isLoading: false}))
            .catch(() => this.props.history.push('/'));
    }

    async remove(id) {
        await fetch(`/v1/api/order/${id}`, {
            method: 'DELETE',
            headers: {
                'X-XSRF-TOKEN': this.state.csrfToken,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(() => {
            let updatedOrders = [...this.state.orders].filter(i => i.id !== id);
            this.setState({orders: updatedOrders});
        });
    }

    render() {
        const {orders, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const orderList = orders.map(order => {
            return <tr key={order.id}>
                <td style={{whiteSpace: 'nowrap'}}>{order.orderNumber}</td>
                <td style={{whiteSpace: 'nowrap'}}>{order.partyName}</td>
                <td style={{whiteSpace: 'nowrap'}}>{order.item}</td>
                <td style={{whiteSpace: 'nowrap'}}>{order.rate}</td>
                <td style={{whiteSpace: 'nowrap'}}>{order.quantity}</td>
                <td style={{whiteSpace: 'nowrap'}}>{order.comments}</td>
                <td style={{whiteSpace: 'nowrap'}}>{order.date}</td>
                <td style={{whiteSpace: 'nowrap'}}>{order.createdBy}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"orders/" + order.id}>Edit</Button>{'  '}
                        <Button size="sm" color="danger" onClick={() => this.remove(order.id)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/orders/new">Add Order</Button>
                    </div>
                    <h3>My Sales Order</h3>
                    <Table className="mt-4" hover bordered responsive>
                        <thead>
                        <tr>
                            <th width="10%">Order No.</th>
                            <th width="20%">PartyName</th>
                            <th width="20%">Products</th>
                            <th width="10%">Rate</th>
                            <th width="10%">Quantity/POS</th>
                            <th>Any Special Request</th>
                            <th>Date & Time</th>
                            <th>Created By</th>
                            <th width="10%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                            {orderList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default withCookies(withRouter(OrderList));