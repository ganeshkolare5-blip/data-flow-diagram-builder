from fastapi import FastAPI
import uvicorn

app = FastAPI(title="DFD Builder AI Service")

@app.get("/")
async def root():
    return {"message": "AI Service is running", "status": "healthy"}

@app.get("/health")
async def health():
    return {"status": "up"}

@app.post("/analyze")
async def analyze_diagram(data: dict):
    # Mock AI analysis logic
    name = data.get("name", "Unnamed")
    description = data.get("description", "")
    
    analysis = {
        "complexity": "Medium",
        "suggestions": [
            f"Add more detail to the process in {name}",
            "Verify data store connections",
            "Check for potential bottlenecks in data flow"
        ],
        "status": "completed"
    }
    return {"analysis": analysis}

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
