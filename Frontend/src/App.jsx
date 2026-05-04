import React from 'react'
import './index.css'

function App() {
  return (
    <div className="container">
      <h1>DFD Builder Pro</h1>
      <p>The ultimate tool for designing and analyzing Data Flow Diagrams.</p>
      
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
    </div>
  )
}

export default App
