import React, { useState, useEffect } from 'react'
import './index.css'
import ErrorBoundary from './components/common/ErrorBoundary'
import Skeleton from './components/common/Skeleton'
import EmptyState from './components/common/EmptyState'

function App() {
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // Simulate loading for demonstration
    const timer = setTimeout(() => setLoading(false), 2000);
    return () => clearTimeout(timer);
  }, []);

  return (
    <ErrorBoundary>
      <div className="container">
        <h1>DFD Builder Pro</h1>
        <p>The ultimate tool for designing and analyzing Data Flow Diagrams.</p>
        
        {loading ? (
          <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem', alignItems: 'center' }}>
            <Skeleton width="150px" height="30px" borderRadius="9999px" />
            <div className="features-grid" style={{ width: '100%' }}>
              {[1, 2, 3, 4].map(i => (
                <div key={i} className="feature-card">
                  <Skeleton width="80%" height="1.5rem" style={{ marginBottom: '1rem' }} />
                  <Skeleton width="100%" height="0.9rem" />
                  <Skeleton width="60%" height="0.9rem" style={{ marginTop: '0.5rem' }} />
                </div>
              ))}
            </div>
          </div>
        ) : (
          <>
            <div className="status-badge">
              <span style={{ marginRight: '8px' }}>●</span>
              All Systems Operational
            </div>

            <div className="features-grid">
              <div className="feature-card">
                <h3>Diagram Editor</h3>
                <p>Drag & drop interface for seamless DFD creation.</p>
              </div>
              <div className="feature-card">
                <h3>AI Analysis</h3>
                <p>Smart suggestions to optimize your data flows.</p>
              </div>
              <div className="feature-card">
                <h3>Collaboration</h3>
                <p>Share and edit diagrams with your team in real-time.</p>
              </div>
              <div className="feature-card">
                <h3>Notifications</h3>
                <p>Stay updated with automated email reminders.</p>
              </div>
            </div>
            
            {/* Demonstrating Empty State below if no projects were found (mocked) */}
            <div style={{ marginTop: '3rem', borderTop: '1px solid rgba(255,255,255,0.1)', paddingTop: '2rem' }}>
              <EmptyState 
                title="No Recent Projects" 
                message="You haven't created any diagrams yet. Start by clicking 'New Diagram' (coming soon)." 
              />
            </div>
          </>
        )}
      </div>
    </ErrorBoundary>
  )
}

export default App
