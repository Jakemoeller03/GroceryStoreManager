import React, { useState } from 'react';
import TomatoLogo from "../images/TomatoLogo.png";

function Orders() {
    // Generate sample orders data
    const allOrders = Array.from({ length: 340 }, (_, i) => {
        const orderNum = i + 1;
        const statuses = ['Pending', 'Processing', 'Shipped', 'Delivered', 'Cancelled'];
        const customers = ['John Smith', 'Sarah Johnson', 'Mike Wilson', 'Lisa Brown', 'David Lee', 'Emma Davis'];
        const amounts = ['$23.45', '$67.89', '$45.67', '$89.12', '$34.56', '$78.90', '$56.34', '$123.45'];

        return {
            id: `ORD-${orderNum.toString().padStart(3, '0')}`,
            customer: customers[Math.floor(Math.random() * customers.length)],
            status: statuses[Math.floor(Math.random() * statuses.length)],
            amount: amounts[Math.floor(Math.random() * amounts.length)],
            date: new Date(2025, 0, Math.floor(Math.random() * 30) + 1).toLocaleDateString()
        };
    });

    const ordersPerPage = 50;
    const totalPages = Math.ceil(allOrders.length / ordersPerPage);
    const [currentPage, setCurrentPage] = useState(1);

    const startIndex = (currentPage - 1) * ordersPerPage;
    const displayedOrders = allOrders.slice(startIndex, startIndex + ordersPerPage);

    const goToPage = (page) => {
        if (page >= 1 && page <= totalPages) {
            setCurrentPage(page);
        }
    };

    // Sample alerts data
    const alerts = [
        { id: 1, type: 'Low Alert', item: 'GMO Oranges', hasActions: true },
        { id: 2, type: 'Out of Stock', item: 'Organic Apples', hasActions: false },
        { id: 3, type: 'Expiring Soon', item: 'Fresh Milk', hasActions: false },
        { id: 4, type: 'Delayed Order', item: 'Bread Delivery', hasActions: false }
    ];

    return (
        <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>
            {/* Top Red Bar */}
            <div style={{ backgroundColor: '#db3d3d', height: '30px' }} />

            {/* Page Content */}
            <div style={{ flexGrow: 1 }}>
                {/* Navbar */}
                <nav style={{ display: 'flex', justifyContent: 'space-around', padding: '10px', fontWeight: 'bold' }}>
                    <a href="/">Home</a>
                    <a href="/items">Items</a>
                    <a href="/orders"><u>Orders</u></a>
                </nav>

                {/* Logo */}
                <div style={{ display: 'flex', justifyContent: 'center', margin: '10px 0' }}>
                    <img src={TomatoLogo} alt="Tomato Logo" style={{ width: '60px', height: '60px' }} />
                </div>

                {/* Search */}
                <div style={{ display: 'flex', justifyContent: 'center', marginBottom: '10px' }}>
                    <div style={{
                        display: 'flex',
                        alignItems: 'center',
                        borderRadius: '15px',
                        backgroundColor: '#f2edf7',
                        padding: '5px 10px',
                        width: '400px'
                    }}>
                        <span style={{ marginRight: '10px', fontSize: '20px' }}>☰</span>
                        <input
                            type="text"
                            placeholder="Hinted search text"
                            style={{
                                flexGrow: 1,
                                border: 'none',
                                outline: 'none',
                                backgroundColor: '#f2edf7',
                                fontSize: '14px'
                            }}
                        />
                        <span style={{ marginLeft: '10px', fontSize: '18px' }}></span>
                    </div>
                </div>

                {/* Main Content Area */}
                <div style={{
                    margin: '0 auto',
                    width: '90%',
                    height: '650px',
                    display: 'flex',
                    gap: '20px'
                }}>
                    {/* Left Side - Orders List */}
                    <div style={{
                        backgroundColor: '#f3eaea',
                        flex: '1',
                        overflowY: 'scroll',
                        padding: '15px',
                        borderRadius: '5px'
                    }}>
                        <div style={{
                            display: 'flex',
                            justifyContent: 'space-between',
                            marginBottom: '10px',
                            fontWeight: 'bold'
                        }}>
                            <div>List of Orders</div>
                        </div>

                        {displayedOrders.map((order, index) => (
                            <div key={index} style={{
                                padding: '8px 0',
                                borderBottom: '1px solid #ddd',
                                display: 'flex',
                                justifyContent: 'space-between',
                                alignItems: 'center'
                            }}>
                                <div>
                                    <div style={{ fontWeight: 'bold', fontSize: '14px' }}>{order.id}</div>
                                    <div style={{ fontSize: '12px', color: '#666' }}>{order.customer}</div>
                                </div>
                                <div style={{ textAlign: 'right' }}>
                                    <div style={{ fontSize: '12px', color: '#666' }}>{order.date}</div>
                                    <div style={{ fontSize: '14px', fontWeight: 'bold' }}>{order.amount}</div>
                                </div>
                            </div>
                        ))}
                    </div>

                    {/* Right Side - Alerts */}
                    <div style={{
                        backgroundColor: '#f8f8f8',
                        width: '300px',
                        padding: '15px',
                        borderRadius: '5px'
                    }}>
                        <div style={{
                            fontWeight: 'bold',
                            marginBottom: '15px',
                            fontSize: '16px'
                        }}>
                            Alerts
                        </div>

                        {alerts.map((alert) => (
                            <div key={alert.id} style={{
                                display: 'flex',
                                alignItems: 'center',
                                marginBottom: '15px'
                            }}>
                                <div style={{
                                    width: '8px',
                                    height: '8px',
                                    borderRadius: '50%',
                                    backgroundColor: '#000',
                                    marginRight: '10px'
                                }} />
                                <div style={{ flex: 1 }}>
                                    <span style={{ fontWeight: 'bold' }}>{alert.type}</span>
                                    <span style={{ margin: '0 5px' }}>on :</span>
                                    <span style={{ fontStyle: 'italic' }}>{alert.item}</span>
                                </div>
                                {alert.hasActions && (
                                    <div style={{ display: 'flex', gap: '5px' }}>
                                        <span style={{ cursor: 'pointer', fontSize: '18px' }}>R</span>
                                        <span style={{ cursor: 'pointer', fontSize: '18px' }}>D️</span>
                                    </div>
                                )}
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            {/* Bottom Red Bar with Pagination */}
            <div style={{
                backgroundColor: '#db3d3d',
                padding: '10px',
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center'
            }}>
                <span
                    style={{ marginRight: '20px', color: '#fff', cursor: 'pointer' }}
                    onClick={() => goToPage(currentPage - 1)}
                >
                    ← Previous
                </span>

                {[...Array(totalPages).keys()].map((i) => {
                    const pageNum = i + 1;
                    if (pageNum === 1 || pageNum === totalPages || Math.abs(pageNum - currentPage) <= 2) {
                        return (
                            <span
                                key={pageNum}
                                onClick={() => goToPage(pageNum)}
                                style={{
                                    margin: '0 5px',
                                    padding: '5px 10px',
                                    borderRadius: '5px',
                                    backgroundColor: pageNum === currentPage ? '#8d1515' : 'transparent',
                                    color: '#fff',
                                    fontWeight: pageNum === currentPage ? 'bold' : 'normal',
                                    cursor: 'pointer'
                                }}
                            >
                                {pageNum}
                            </span>
                        );
                    } else if (pageNum === currentPage - 3 || pageNum === currentPage + 3) {
                        return <span key={pageNum} style={{ color: '#fff' }}>...</span>;
                    } else {
                        return null;
                    }
                })}

                <span
                    style={{ marginLeft: '20px', color: '#fff', cursor: 'pointer' }}
                    onClick={() => goToPage(currentPage + 1)}
                >
                    Next →
                </span>
            </div>
        </div>
    );
}

export default Orders;