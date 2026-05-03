import React, { useState, useEffect } from 'react'
import './App.css'
import ErrorBoundary from './components/common/ErrorBoundary'
import EmptyState from './components/common/EmptyState'
import { DiagramSkeleton } from './components/common/Skeleton'

function App() {
  const [diagrams, setDiagrams] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchDiagrams = async () => {
      try {
        setLoading(true)
        // Calling our real backend API
        const response = await fetch('http://localhost:8080/diagrams?size=50')
        if (!response.ok) {
          throw new Error('Failed to fetch diagrams')
        }
        const data = await response.json()
        
        // Spring Boot Page object has content in .content
        setDiagrams(data.content || [])
        setLoading(false)
      } catch (err) {
        console.error("Fetch error:", err)
        setError(err.message)
        setLoading(false)
      }
    }

    fetchDiagrams()
  }, [])

  const handleCreate = () => {
    alert("Redirecting to diagram builder...")
  }

  return (
    <ErrorBoundary>
      <div className="container">
        <header className="header">
          <h1>Data Flow Diagram Builder</h1>
          <p className="subtitle">Day 12 - Integrated System with 30 Seeded Records</p>
        </header>

        <main className="content">
          {loading ? (
            <div className="skeleton-grid">
              {[1, 2, 3, 4, 5, 6].map(i => <DiagramSkeleton key={i} />)}
            </div>
          ) : error ? (
            <div className="error-message">
                <h2>Error loading diagrams</h2>
                <p>{error}</p>
                <button onClick={() => window.location.reload()}>Retry</button>
            </div>
          ) : diagrams.length > 0 ? (
            <div className="diagram-grid">
              {diagrams.map(d => (
                <div key={d.id} className="diagram-card">
                  <h3>{d.name}</h3>
                  <p>{d.description}</p>
                  <div className="card-footer">
                    <span className="badge">{d.role || 'USER'}</span>
                    <span className="date">{new Date(d.createdAt).toLocaleDateString()}</span>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <EmptyState 
              title="No Diagrams Found"
              message="The database is currently empty. Start by creating your first architecture diagram."
              action={{ label: "Create Diagram", onClick: handleCreate }}
            />
          )}
        </main>
      </div>
    </ErrorBoundary>
  )
}

export default App
