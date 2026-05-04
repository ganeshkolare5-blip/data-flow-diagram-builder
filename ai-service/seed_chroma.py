import chromadb

client = chromadb.Client()

collection = client.create_collection(name="ai_knowledge")

documents = [
    "Data Flow Diagram represents system processes and data movement.",
    "Inventory Management helps track stock and orders.",
    "System optimization improves performance and efficiency.",
    "Security ensures protection of user data.",
    "Monitoring tracks system activities.",
    "Scalability allows system growth.",
    "User management handles authentication and roles.",
    "Reports summarize system insights.",
    "Caching improves response time.",
    "APIs enable communication between services."
]

ids = [f"id_{i}" for i in range(len(documents))]

collection.add(documents=documents, ids=ids)

print("ChromaDB seeded successfully!")