import requests

url = "http://127.0.0.1:5000/describe"

demo_inputs = [
    "Inventory System", "User Login", "Payment System",
    "E-commerce App", "Hospital Management", "Banking System",
    "Student Portal", "Online Booking", "AI Assistant",
    "Data Processing", "Security System", "Monitoring Tool",
    "Cloud Storage", "File Management", "CRM System",
    "ERP System", "Log Analysis", "Report Generator",
    "Dashboard UI", "Chat Application", "Streaming Service",
    "IoT System", "Automation Tool", "Analytics Engine",
    "Search Engine", "Email System", "Notification System",
    "Backup System", "HR Management", "Workflow Engine"
]

with open("demo_outputs.txt", "w") as f:
    for item in demo_inputs:
        response = requests.post(url, json={"input": item})
        output = f"{item} => {response.json()}\n"
        print(output)
        f.write(output)