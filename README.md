# AI Service - Documentation

## Overview

This AI Service is a Flask-based backend that provides intelligent responses for:

- Description generation
- Recommendations
- Report generation

### Features

- Redis caching (15 min TTL)
- Response time tracking
- Fallback handling for failures
- Security headers implementation
- Docker support

# Setup Instructions

1. Clone the repository

```bash
git clone https://github.com/Archana-8868/data-flow-diagram-builder
cd data-flow-diagram-builder/ai-service


2. Install dependencies

-bash
pip install -r requirements.txt
```

3. Run the application

-bash
python app.py

4. Access the service

http://127.0.0.1:5000

=>API Endpoints

🔹 1. Describe

POST `/describe`

Request:

-json
{
"input": "Data Flow Diagram"
}

Response:

-json
{
"generated_at": "2026-05-01T15:18:11.160962",
"input": "Data Flow Diagram",
"response_time": 0.0,
"status": "success"
}

🔹 2. Recommend

POST `/recommend`

Request:

-json
{
"input": "System Optimization"
}

Response:

-json
{
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
],
"response_time": 0.0
}

🔹 3. Generate Report

POST `/generate-report`

Request:

-json
{
"input": "Data Flow Diagram"
}

Response:

-json
{
"key_items": [
"User Management",
"Data Processing",
"System Security",
"Reporting Module"
],
"overview": "The Data Flow Diagram system helps manage users, data, and core operations effectively.",
"recommendations": [
"Improve scalability",
"Enhance security",
"Add analytics dashboard"
],
"response_time": 0.0,
"summary": "Data Flow Diagram is designed to improve workflow and efficiency.",
"title": "Data Flow Diagram Report"
}

=>Environment Variables

Create a `.env` file using:

-text
REDIS_HOST=localhost
REDIS_PORT=6379
MODEL_NAME=all-MiniLM-L6-v2
PORT=5000

=>Status

*All endpoints working
*Response time < 2 seconds
\*AI service fully functional

## Author-Archana
