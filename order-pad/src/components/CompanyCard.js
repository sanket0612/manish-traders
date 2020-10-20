import React, { Component } from "react";
import { Button, ButtonGroup,Container,Table} from 'reactstrap';
import { Link, withRouter } from 'react-router-dom';
import { instanceOf } from 'prop-types';
import { withCookies, Cookies } from 'react-cookie';


class CompanyCard extends Component {
    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };


    constructor(props) {
        super(props);
        const {cookies} = props;
        this.state = {companies: [], csrfToken: cookies.get('XSRF-TOKEN'), isLoading: true};
        this.getProperDate = this.getProperDate.bind(this);
        this.removeAll = this.removeAll.bind(this);
    }

    async componentDidMount() {
        this.setState({isLoading: true});

        fetch('/sync/company', {credentials: 'include'})
            .then(response => response.json())
            .then(data => this.setState({companies: data, isLoading: false}))
            .catch(() => this.props.history.push('/'));
    }

    async removeAll() {
        await fetch(`/sync/company`, {
            method: 'DELETE',
            headers: {
                'X-XSRF-TOKEN': this.state.csrfToken,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            credentials: 'include'
        }).then(() => {
            this.setState({companies: []});
        });
    }


    getProperDate(input){
            var year = input.slice(0,4);
            var month = input.slice(4,6);
            var day = input.slice(6);
            return new Date(year,month, day).toJSON().slice(0,10);
    }

    render() {
        const {companies, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const companyList = companies.map(company => {
            return <tr key={company.id}>
                <td style={{whiteSpace: 'nowrap'}}>{company.number}</td>
                <td style={{whiteSpace: 'nowrap'}}>{company.name}</td>
                <td style={{whiteSpace: 'nowrap'}}>{this.getProperDate(company.startingFrom)}</td>
                <td style={{whiteSpace: 'nowrap'}}>{this.getProperDate(company.endingAt)}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"companys/" + company.id}>Select Company</Button>{''}
                        <Button size="sm" color="danger" onClick={() => this.removeAll()}>Delete All</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <Container fluid>
                    <h3>My Companies</h3>
                    <Table className="mt-4" hover bordered responsive>
                        <thead>
                        <tr>
                            <th width="10%">Company Number</th>
                            <th width="20%">Company Name</th>
                            <th width="10%">Starting From</th>
                            <th width="10%">Ending At</th>
                            <th width="20%">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {companyList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default withCookies(withRouter(CompanyCard));