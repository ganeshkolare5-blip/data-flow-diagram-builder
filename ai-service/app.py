from flask import Flask
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

    return {
        "status": "healthy",
        "model": MODEL_NAME,
        "avg_response_time": f"{avg_response_time} sec",
        "uptime": f"{uptime} seconds"
    }

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)