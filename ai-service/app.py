from flask import Flask, jsonify
from routes.ai_routes import ai_bp
import time
import redis

app = Flask(__name__)
app.register_blueprint(ai_bp)

# service start time
start_time = time.time()

# Redis connection
redis_client = redis.Redis(
    host="localhost",
    port=6379,
    decode_responses=True
)

MODEL_NAME = "llama3-70b-8192"
avg_response_time = 1.4


@app.route("/health")
def health():
    uptime = int(time.time() - start_time)

    resp = jsonify({
        "status": "healthy",
        "model": MODEL_NAME,
        "avg_response_time": f"{avg_response_time} sec",
        "uptime": f"{uptime} seconds"
    })

    return resp


# ✅ ADD THIS BLOCK (VERY IMPORTANT — fixes ZAP findings)
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

    # 🔥 REMOVE default server header completely
    response.headers["Server"] = "SecureServer"

    return response
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)