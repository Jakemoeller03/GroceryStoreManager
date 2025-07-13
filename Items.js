import React, { useState } from 'react';
import TomatoLogo from '../images/TomatoLogo.png';
import { NavLink } from 'react-router-dom';

function Items() {
    const allItems = Array.from({ length: 340 }, (_, i) => `Item ${i + 1}`);
    const itemsPerPage = 50;
    const totalPages = Math.ceil(allItems.length / itemsPerPage);

    const [currentPage, setCurrentPage] = useState(1);

    const startIndex = (currentPage - 1) * itemsPerPage;
    const displayedItems = allItems.slice(startIndex, startIndex + itemsPerPage);

    const goToPage = (page) => {
        if (page >= 1 && page <= totalPages) {
            setCurrentPage(page);
        }
    };

    return (
        <div style={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>

            {/* Top Red Bar */}
            <div style={{ backgroundColor: '#db3d3d', height: '30px' }} />

            {/* Page Content */}
            <div style={{ flexGrow: 1 }}>

                {/* Navbar */}
                <nav style={{
                    display: 'flex',
                    justifyContent: 'flex-start',
                    gap: '20px',
                    padding: '10px 40px',
                    fontWeight: 'bold'
                }}>
                    <NavLink
                        to="/"
                        style={({ isActive }) => ({
                            textDecoration: isActive ? 'underline' : 'none',
                            color: isActive ? '#db3d3d' : 'black'
                        })}
                    >
                        Home
                    </NavLink>
                    <NavLink
                        to="/items"
                        style={({ isActive }) => ({
                            textDecoration: isActive ? 'underline' : 'none',
                            color: isActive ? '#db3d3d' : 'black'
                        })}
                    >
                        Items
                    </NavLink>
                    <NavLink
                        to="/orders"
                        style={({ isActive }) => ({
                            textDecoration: isActive ? 'underline' : 'none',
                            color: isActive ? '#db3d3d' : 'black'
                        })}
                    >
                        Orders
                    </NavLink>
                    <NavLink
                        to="/EditItems"
                        style={({ isActive }) => ({
                            textDecoration: isActive ? 'underline' : 'none',
                            color: isActive ? '#db3d3d' : 'black'
                        })}
                    >
                        EditItems
                    </NavLink>
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
                        <span style={{ marginLeft: '10px', fontSize: '18px' }}>🔍</span>
                    </div>
                </div>

                {/* Items List */}
                <div style={{
                    margin: '0 auto',
                    backgroundColor: '#f3eaea',
                    width: '90%',
                    height: '650px',
                    overflowY: 'scroll',
                    padding: '15px',
                }}>
                    <div style={{
                        display: 'flex',
                        justifyContent: 'space-between',
                        marginBottom: '10px',
                        fontWeight: 'bold'
                    }}>
                        <div>List of Items</div>

                    </div>

                    {displayedItems.map((item, index) => (
                        <div key={index} style={{ padding: '5px 0', borderBottom: '1px solid #ddd' }}>
                            {item}
                        </div>
                    ))}
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

export default Items;
