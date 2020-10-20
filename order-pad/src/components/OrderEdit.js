import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label, Row, Col,
    ButtonGroup} from 'reactstrap';
import AppNavbar from './AppNavBar';
import {DropdownButton, Dropdown} from 'react-bootstrap';
import {instanceOf} from 'prop-types';
import {Cookies, withCookies} from 'react-cookie';

class OrderEdit extends Component {

    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    emptyOrder = {
        orderNumber : '',
        partyName: '',
        product: '',
        rate: '',
        quantity: '',
        comments: '',
        date:''
    };

    constructor(props) {
        super(props);
        const {cookies} = props;
        this.state = {
            order: this.emptyOrder,
            csrfToken: cookies.get('XSRF-TOKEN')
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            try {
                const order = await (await fetch(`/v1/api/order/${this.props.match.params.id}`, {credentials: 'include'})).json();
                this.setState({order: order});
            } catch (error) {
                this.props.history.push('/');
            }

        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let order = {...this.state.order};
        order[name] = value;
        this.setState({order});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {order, csrfToken} = this.state;
        await fetch("/v1/api/order" + (order.id ? '/' + order.id : ''), {
            method: (order.id) ? 'PUT' : 'POST',
            headers: {
                'X-XSRF-TOKEN': csrfToken,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(order),
            credentials: 'include'
        });
        this.props.history.push('/orders');
    }

    render() {
        const {order} = this.state;
        const title = <h2>{order.id ? 'Edit Order' : 'Created Sales Order'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                <Form onSubmit={this.handleSubmit}>
                        <Row form>
                        <Col>
                            {title}
                        </Col>
                        <Col>
                        <Button color="primary" tag={Link} to="/order/new" size="lg">Save & Add Another</Button>{' '}
                            <Button color="primary" type="submit" size="lg">Save</Button>{' '}
                            <Button color="secondary" tag={Link} to="/orders" size="lg">Close</Button>
                            <br/>
                        </Col>
                    </Row>
                    <hr/> 
                    <h3>Basic Information</h3>
                    <Row form>
                        <Col md={4}>
                        <FormGroup>
                            <Label for = "orderNumber">Sales Order #</Label>
                            <Input type="number" name="orderNumber" id="orderNumber" placeholder="Order Number here" value={order.orderNumber || ''}
                                onChange={this.handleChange}
                            />
                        </FormGroup>
                        </Col>
                        <Col md={4}>
                            <FormGroup>
                                <Label for="date">Created On</Label>
                                <Input type="date" name="date" id="date" value={order.date || ''}
                                    onChange={this.handleChange} autoComplete="date-level1"/>
                            </FormGroup>
                        </Col>
                        <Col md={4}>
                            <FormGroup>
                                <Label for="createdBy">Created By</Label>
                                <Input type="text" name="createdBy" id="createdBy" value={order.date || ''}
                                    onChange={this.handleChange} autoComplete="createdBy-level1"/>
                            </FormGroup>
                        </Col>
                    </Row>
                    <h3>Party Details</h3>
                    <Row form>
                        <Col>
                            <FormGroup>
                            <Label for="sortBy">Sort By</Label>
                            <Input type="dropdown" name="sortBy" id="sortBy" value={order.sortBy || ''}
                                onChange={this.handleChange} autoComplete="product-level1"/>
                            </FormGroup>
                        </Col>
                        <Col>
                            <FormGroup>
                            <Label for="partyName">Party Name</Label>
                            <Input type="text" name="partyName" id="partyName" value={order.partyName || ''}
                                onChange={this.handleChange}/>
                            </FormGroup>
                        </Col>
                    </Row>
                    <h3>Order Items</h3>
                    <Row form>
                        <Col>
                            <FormGroup>
                            <Label for="item">Item name</Label>
                            <Input type="text" name="item" id="item" value={order.item || ''}
                                onChange={this.handleChange} autoComplete="item-level1"/>
                            </FormGroup>
                        </Col>
                        <Col>
                            <FormGroup>
                                <Label for="quantity">Quantity</Label>
                                <Input type="number" name="quantity" id="quantity" value={order.quantity || ''}
                                    onChange={this.handleChange} autoComplete="quantity-level1"/>
                            </FormGroup>
                        </Col>
                        <Col>
                            <FormGroup>
                                <Label for="unit">Unit</Label>
                                <DropdownButton id="unit"  name="unit" value={order.unit || ''} title={order.unit} onChange={this.handleChange}>
                                    <Dropdown.Item >Boxes</Dropdown.Item>
                                    <Dropdown.Item >Packets</Dropdown.Item>
                                    <Dropdown.Item >Crates</Dropdown.Item>
                                </DropdownButton>
                            </FormGroup>
                        </Col>
                        <Col>
                            <FormGroup>
                                <Label for="rate">Rate</Label>
                                <Input type="number" name="rate" id="rate" value={order.rate || ''}
                                    onChange={this.handleChange} autoComplete="rate-level1"/>
                            </FormGroup>
                        </Col>
                    </Row>
                    <h3>Remarks</h3>
                    <FormGroup>
                    <Label for="comments">Type Special Comments/ Requests given by party</Label>
                    <Input type="textarea" name="comments" id="comments" value={order.comments || ''}
                                   onChange={this.handleChange} autoComplete="comments-level1" placeholder="Start Typing"/>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withCookies(withRouter(OrderEdit));