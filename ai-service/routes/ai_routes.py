from flask import Blueprint, request, jsonify
from datetime import datetime
import redis
import hashlib
import json

ai_bp = Blueprint("ai_bp", __name__)

# Safe Redis Connection
try:
    redis_client = redis.Redis(
        host="localhost",
        port=6379,
        decode_responses=True
    )
    redis_client.ping()
    redis_available = True
except:
    redis_available = False


# Generate SHA256 Key
def generate_key(text, route):
    raw_text = route + text
    return hashlib.sha256(raw_text.encode()).hexdigest()


# ---------------- DESCRIBE ----------------
@ai_bp.route("/describe", methods=["POST"])
def describe():
    data = request.get_json()

    if not data or "input" not in data:
        return jsonify({"error": "Input is required"}), 400

    user_input = data["input"]
    key = generate_key(user_input, "describe")

    cached = None
    if redis_available:
        cached = redis_client.get(key)

    if cached:
        return jsonify(json.loads(cached))

    response = {
        "status": "success",
        "input": user_input,
        "generated_at": datetime.now().isoformat()
    }

    if redis_available:
        redis_client.setex(key, 900, json.dumps(response))

    return jsonify(response)


# ---------------- RECOMMEND ----------------
@ai_bp.route("/recommend", methods=["POST"])
def recommend():
    data = request.get_json()

    if not data or "input" not in data:
        return jsonify({"error": "Input is required"}), 400

    user_input = data["input"]
    key = generate_key(user_input, "recommend")

    cached = None
    if redis_available:
        cached = redis_client.get(key)

    if cached:
        return jsonify(json.loads(cached))

    response = {
        "recommendations": [
            {
                "action_type": "Optimize",
                "description": "Improve system performance and speed.",
                "priority": "High"
            },
            {
                "action_type": "Security",
                "description": "Protect user data and strengthen login security.",
                "priority": "Medium"
            },
            {
                "action_type": "Monitoring",
                "description": "Track system activity and logs regularly.",
                "priority": "Low"
            }
        ]
    }

    if redis_available:
        redis_client.setex(key, 900, json.dumps(response))

    return jsonify(response)


# ---------------- REPORT ----------------
@ai_bp.route("/generate-report", methods=["POST"])
def generate_report():
    data = request.get_json()

    if not data or "input" not in data:
        return jsonify({"error": "Input is required"}), 400

    user_input = data["input"]
    key = generate_key(user_input, "report")

    cached = None
    if redis_available:
        cached = redis_client.get(key)

    if cached:
        return jsonify(json.loads(cached))

    response = {
        "title": f"{user_input} Report",
        "summary": f"{user_input} is designed to improve workflow and efficiency.",
        "overview": f"The {user_input} system helps manage users, data, and core operations effectively.",
        "key_items": [
            "User Management",
            "Data Processing",
            "System Security",
            "Reporting Module"
        ],
        "recommendations": [
            "Improve scalability",
            "Enhance security",
            "Add analytics dashboard"
        ]
    }

    if redis_available:
        redis_client.setex(key, 900, json.dumps(response))

    return jsonify(response)