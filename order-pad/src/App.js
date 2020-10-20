import React, {Component} from 'react';
import './App.css';
import Home from './components/Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import OrderList from './components/OrderList';
import OrderEdit from "./components/OrderEdit";
import CashDiscount  from "./components/CashDiscount";
import { CookiesProvider } from 'react-cookie';
import CompanyCard from "./components/CompanyCard";

class App extends Component {
    render() {
        return (
            <CookiesProvider>
                <Router>
                    <Switch>
                        <Route path='/' exact={true} component={Home}/>
                        <Route path='/orders' exact={true} component={OrderList}/>
                        <Route path='/orders/:id' component={OrderEdit}/>
                        <Route path='/cashDiscount' component={CashDiscount}/>
                        <Route path='/company' component={CompanyCard}/>
                    </Switch>
                </Router>
            </CookiesProvider>
        )
    }
}

export default App;