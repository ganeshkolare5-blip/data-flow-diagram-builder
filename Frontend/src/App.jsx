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
    // Simulate API fetch
    const fetchDiagrams = async () => {
      try {
        setLoading(true)
        // Simulate delay
        await new Promise(resolve => setTimeout(resolve, 2000))
        
        // Simulating empty state for now
        setDiagrams([]) 
        setLoading(false)
      } catch (err) {
        setError(err)
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
          <p className="subtitle">Day 11 - UX Polish: Skeletons & Empty States</p>
        </header>

        <main className="content">
          {loading ? (
            <div className="skeleton-grid">
              {[1, 2, 3].map(i => <DiagramSkeleton key={i} />)}
            </div>
          ) : diagrams.length > 0 ? (
            <div className="diagram-grid">
              {diagrams.map(d => (
                <div key={d.id} className="diagram-card">
                  {/* Diagram Card Content */}
                </div>
              ))}
            </div>
          ) : (
            <EmptyState 
              title="No Diagrams Yet"
              message="Get started by creating your first architecture diagram."
              action={{ label: "Create Diagram", onClick: handleCreate }}
            />
          )}
        </main>
      </div>
    </ErrorBoundary>
  )
}

export default App
