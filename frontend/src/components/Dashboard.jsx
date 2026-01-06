import { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Dashboard() {
    const [companies, setCompanies] = useState([]);
    const [view, setView] = useState('home'); 
    const navigate = useNavigate();
    
    // We keep this here because 'handleCompanyClick' needs it later
    const user = JSON.parse(localStorage.getItem('user'));

    // 1. Fetch Data on Load
    useEffect(() => {
        // FIX: Read the user AGAIN inside the effect so we don't depend on the outer variable
        const loggedInUser = JSON.parse(localStorage.getItem('user'));

        if (!loggedInUser) {
            navigate('/login');
            return;
        }

        const fetchDashboard = async () => {
            try {
                // Use 'loggedInUser' here instead of 'user'
                const response = await axios.get(`http://localhost:8080/api/dashboard/${loggedInUser.id}`);
                setCompanies(response.data);
            } catch (error) {
                console.error("Error fetching dashboard", error);
            }
        };

        fetchDashboard();
    }, [navigate]); // Now the linter is happy!

    // ... Rest of your code (isDone, handleCompanyClick, etc) stays exactly the same ...

    // 2. Logic to check if task is "Done"
    const isDone = (lastClickedAt, frequency) => {
        if (!lastClickedAt) return false;

        const lastClickDate = new Date(lastClickedAt);
        const today = new Date();

        if (frequency === 'DAILY') {
            return lastClickDate.toDateString() === today.toDateString();
        } 
        
        if (frequency === 'WEEKLY') {
            const dayOfWeek = today.getDay(); 
            const distToMonday = (dayOfWeek + 6) % 7; 
            const lastMonday = new Date(today);
            lastMonday.setDate(today.getDate() - distToMonday);
            lastMonday.setHours(0, 0, 0, 0);

            return lastClickDate >= lastMonday;
        }

        return false;
    };

    // 3. Handle Clicking a Company Button
    const handleCompanyClick = async (companyId, url) => {
        window.open(url, '_blank');

        try {
            await axios.post(`${import.meta.env.VITE_API_URL || 'http://localhost:8080'}/api/dashboard/click`, {
                userId: user.id, // We use the outer 'user' variable here, which is fine!
                companyId: companyId
            });

            setCompanies(prev => prev.map(item => {
                if (item.company.id === companyId) {
                    return { ...item, lastClickedAt: new Date().toISOString() };
                }
                return item;
            }));

        } catch (error) {
            console.error("Failed to record click", error);
        }
    };

    // 4. Helper to Filter Companies based on View
    const getFilteredCompanies = () => {
        return companies.filter(c => c.company.checkFrequency === view.toUpperCase());
    };

    return (
        <div style={styles.container}>
            <div style={styles.header}>
                <h1>Job Search Dashboard</h1>
                <button onClick={() => {
                    localStorage.removeItem('user');
                    navigate('/login');
                }} style={styles.logoutBtn}>Logout</button>
            </div>

            {view === 'home' && (
                <div style={styles.buttonGrid}>
                    <button onClick={() => setView('daily')} style={styles.bigButton}>
                        📅 Daily Check
                    </button>
                    <button onClick={() => setView('weekly')} style={styles.bigButton}>
                        🗓️ Weekly Check
                    </button>
                </div>
            )}

            {(view === 'daily' || view === 'weekly') && (
                <div>
                    <button onClick={() => setView('home')} style={styles.backButton}>
                        ← Back
                    </button>
                    
                    <h2>{view === 'daily' ? 'Daily' : 'Weekly'} Check-in</h2>
                    
                    <div style={styles.listGrid}>
                        {getFilteredCompanies().map(item => {
                            const done = isDone(item.lastClickedAt, item.company.checkFrequency);
                            return (
                                <button
                                    key={item.id}
                                    onClick={() => handleCompanyClick(item.company.id, item.company.careerUrl)}
                                    style={{
                                        ...styles.companyButton,
                                        backgroundColor: done ? '#28a745' : '#dc3545',
                                    }}
                                >
                                    {item.company.name}
                                    {done && " ✅"}
                                </button>
                            );
                        })}
                    </div>
                    
                    {getFilteredCompanies().length === 0 && <p>No companies found for this list.</p>}
                </div>
            )}
        </div>
    );
}

const styles = {
    container: { padding: '30px', maxWidth: '800px', margin: '0 auto', fontFamily: 'Arial, sans-serif' },
    header: { display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '40px' },
    logoutBtn: { padding: '5px 10px', cursor: 'pointer', background: 'transparent', border: '1px solid #ccc' },
    buttonGrid: { display: 'flex', gap: '20px', justifyContent: 'center' },
    bigButton: { padding: '40px', fontSize: '24px', cursor: 'pointer', borderRadius: '10px', border: 'none', backgroundColor: '#007bff', color: 'white', minWidth: '200px' },
    backButton: { marginBottom: '20px', padding: '10px', cursor: 'pointer', border: 'none', background: '#eee' },
    listGrid: { display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(150px, 1fr))', gap: '15px' },
    companyButton: { padding: '20px', color: 'white', fontSize: '16px', border: 'none', borderRadius: '8px', cursor: 'pointer', fontWeight: 'bold' }
};

export default Dashboard;