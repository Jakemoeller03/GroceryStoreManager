// src/pages/Home.js
import React from 'react';
import { Link } from 'react-router-dom';
import TomatoLogo from '../images/TomatoLogo.png';

function Home() {
    return (
        <div style={{
            backgroundColor: '#fff',
            minHeight: '100vh',
            display: 'flex',
            flexDirection: 'column'
        }}>
            <div style={{ backgroundColor: '#db3d3d', height: '30px' }} />

            <nav style={{
                display: 'flex',
                justifyContent: 'space-around',
                padding: '10px',
                fontWeight: 'bold'
            }}>
                <a href="/">Home</a>
                <a href="/items">Items</a>
                <a href="/orders">Orders</a>
                <a href="/EditItems">EditItems</a>
            </nav>

            <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
                <img src={TomatoLogo} alt="Tomato Logo" style={{ width: '100px', height: '100px' }} />
            </div>

            <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
                <div style={{
                    border: '1px solid #ccc',
                    borderRadius: '8px',
                    padding: '20px',
                    width: '300px'
                }}>
                    <label>Email</label>
                    <input type="email" placeholder="Value"
                           style={{ width: '100%', marginBottom: '10px', padding: '8px' }} />
                    <label>Password</label>
                    <input type="password" placeholder="Value"
                           style={{ width: '100%', marginBottom: '10px', padding: '8px' }} />

                    <Link to="/items" style={{ textDecoration: 'none' }}>
                        <button style={{
                            width: '100%',
                            padding: '10px',
                            backgroundColor: '#333',
                            color: '#fff',
                            borderRadius: '4px',
                            cursor: 'pointer'
                        }}>
                            Sign In
                        </button>-
                    </Link>
                </div>
            </div>

            <div style={{
                backgroundColor: '#db3d3d',
                height: '30px',
                marginTop: 'auto'
            }} />
        </div>
    );
}

export default Home;
