from fastapi import FastAPI
import uvicorn

app = FastAPI(title="DFD Builder AI Service")

@app.get("/")
async def root():
    return {"message": "AI Service is running", "status": "healthy"}

@app.get("/health")
async def health():
    return {"status": "up"}

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)
