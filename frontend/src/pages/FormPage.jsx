import { useState } from "react";
import API from "../services/api";

function FormPage() {
  const [form, setForm] = useState({
    title: "",
    description: "",
    status: ""
  });

  const [error, setError] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async () => {
    if (!form.title) {
      setError("Title is required");
      return;
    }

    try {
      await API.post("/create", form);
      alert("Created successfully");
      // Optional: reset form after creation
      setForm({ title: "", description: "", status: "" });
      setError("");
    } catch (err) {
      console.error(err);
      setError("Failed to create record");
    }
  };

  return (
    <div className="p-5">
      <h1 className="text-xl font-bold mb-4">Create Record</h1>

      {error && <p className="text-red-500">{error}</p>}

      <input
        name="title"
        placeholder="Title"
        value={form.title}
        className="border p-2 block mb-2"
        onChange={handleChange}
      />

      <textarea
        name="description"
        placeholder="Description"
        value={form.description}
        className="border p-2 block mb-2"
        onChange={handleChange}
      />

      <input
        name="status"
        placeholder="Status"
        value={form.status}
        className="border p-2 block mb-2"
        onChange={handleChange}
      />

      <button
        onClick={handleSubmit}
        className="bg-blue-500 text-white px-4 py-2"
      >
        Submit
      </button>
    </div>
  );
}

export default FormPage;
