import React, { Component } from "react";
import "../App.css";
import AppNavbar from "./AppNavBar";
import { Link } from "react-router-dom";
import { withCookies } from "react-cookie";
import { Row, Col } from "react-bootstrap";
import {  Button, Container } from "reactstrap";
import homepage from '../assets/homepage.jpeg';
import {
  Card,
  CardImg,
  CardText,
  CardBody,
  CardTitle,
  CardSubtitle,


} from "reactstrap";

class Home extends Component {
  state = {
    isLoading: true,
    isAuthenticated: false,
    user: undefined,
    isTally: false
  };

  constructor(props) {
    super(props);
    const { cookies } = props;
    this.state.csrfToken = cookies.get("XSRF-TOKEN");
    this.login = this.login.bind(this);
    this.logout = this.logout.bind(this);
  }

  async componentDidMount() {
    const response = await fetch("/api/user", { credentials: "include" });
    const body = await response.text();
    if (body === "") {
      this.setState({ isAuthenticated: false });
    } else {
      this.setState({ isAuthenticated: true, user: JSON.parse(body) });
    }
  }

  login() {
    let port = window.location.port ? ":" + window.location.port : "";
    if (port === ":3000") {
      port = ":8080";
    }
    window.location.href = "//" + window.location.hostname + port + "/private";
  }

  logout() {
    fetch("/api/logout", {
      method: "POST",
      credentials: "include",
      headers: { "X-XSRF-TOKEN": this.state.csrfToken },
    })
      .then((res) => res.json())
      .then((response) => {
        window.location.href =
          response.logoutUrl +
          "?id_token_hint=" +
          response.idToken +
          "&post_logout_redirect_uri=" +
          window.location.origin;
      });
  }

  render() {
    const message = this.state.user ? (
     <CardText>Welcome, {this.state.user.name}!</CardText> 
    ) : (
        <CardText> Please log in to manage your Sales Order.</CardText>
    );


    const button = this.state.isAuthenticated ? (
      <div>
        <Button color="link">
          <Link to="/orders">Manage Sales Order</Link>
        </Button>
        <br />
        <Button color="link">
          <Link to="/company">Select Your Company</Link>
        </Button>
        <br />
        <Button color="link">
          <Link to="/cashDiscount">Calculate Cash Discount</Link>
        </Button>
        <br />
        <Button color="link" onClick={this.logout}>
          Logout
        </Button>
      </div>
    ) : (
      <Button color="primary" onClick={this.login}>
        Login
      </Button>
    );

  
    return (
      <div>
        <AppNavbar />
         <Container>
          <Row>
            <Col xs={{ order: "last" }}></Col>
            <Col xs>
              <Row>
                <Col sm="12" md={{ size: 6, offset: 1 }}>
                </Col>
              </Row>
              <Card>
                <CardBody>
                  <CardTitle>Login</CardTitle>
                  <CardSubtitle>Using Okta to Login to your application</CardSubtitle>
                </CardBody>
                <CardImg
                  width="100%"
                  src={homepage}
                  alt="Card image cap"
                />
                <CardBody>
                {message}
                {button}
                </CardBody>
              </Card>
            </Col>
             <Col xs={{ order: "first" }}></Col>
          </Row>
        </Container>
      </div>
    );
  }
}

export default withCookies(Home);
