import os
import redis
import time
from sentence_transformers import SentenceTransformer
from flask import Flask, jsonify
from routes.ai_routes import ai_bp

app = Flask(__name__)

# Configuration from Environment Variables
REDIS_HOST = os.getenv("REDIS_HOST", "localhost")
REDIS_PORT = int(os.getenv("REDIS_PORT", 6379))
MODEL_NAME = os.getenv("MODEL_NAME", "all-MiniLM-L6-v2")

print(f"Loading AI model: {MODEL_NAME}...")
model = SentenceTransformer(MODEL_NAME)
print("Model loaded successfully")

# service start time
start_time = time.time()

# Global Redis client (can be imported by routes)
redis_client = redis.Redis(
    host=REDIS_HOST,
    port=REDIS_PORT,
    decode_responses=True
)

app.register_blueprint(ai_bp)

@app.route("/")
def home():
    return {"message": "AI Service Running", "model": MODEL_NAME}

@app.route("/health")
def health():
    uptime = int(time.time() - start_time)
    try:
        redis_client.ping()
        redis_status = "connected"
    except:
        redis_status = "disconnected"

    return jsonify({
        "status": "healthy",
        "model": MODEL_NAME,
        "redis": redis_status,
        "uptime": f"{uptime} seconds"
    })

@app.after_request
def add_security_headers(response):
    response.headers["X-Content-Type-Options"] = "nosniff"
    response.headers["X-Frame-Options"] = "DENY"
    response.headers["X-XSS-Protection"] = "1; mode=block"
    response.headers["Strict-Transport-Security"] = "max-age=31536000; includeSubDomains"
    response.headers["Content-Security-Policy"] = (
        "default-src 'self'; "
        "script-src 'self'; "
        "style-src 'self'; "
        "img-src 'self' data:; "
        "object-src 'none'; "
        "base-uri 'self'; "
        "frame-ancestors 'none'"
    )
    response.headers["Server"] = "SecureServer"
    return response

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)