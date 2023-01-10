import React, { Component } from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link"><Link to="/clients?tid=1&fid=2&pid=3">Tenant 1 Clients</Link></Button><br/>
                    <Button color="link"><Link to="/clients?tid=2&fid=2&pid=3">Tenant 2 Clients</Link></Button><br/>
                    <Button color="link"><Link to="/clients?tid=1&fid=2&pid=3&email=bm_test@gmail.com">Tenant 1 Clients for internal User (BMalik)</Link></Button><br/>
                    <Button color="link"><Link to="/clients?tid=2&fid=2&pid=3&email=ya_test@prescribenow.com">Tenant 2 Clients for internal User (YAlsi)</Link></Button><br/>
                    <Button color="link"><Link to="/clients?tid=2&fid=2&pid=3&email=sa@gmail.com">Tenant 2 Clients for Provider User (SAlvi)</Link></Button><br/>
                </Container>
            </div>
        );
    }
}

export default Home;