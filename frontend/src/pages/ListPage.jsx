import { useEffect, useState } from "react";
import API from "../services/api";

function ListPage() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const res = await API.get("/all");
      setData(res.data);
    } catch (err) {
      console.error(err);
      setError("Failed to load data");
    } finally {
      setLoading(false);
    }
  };

  const search = async (q) => {
    setSearchTerm(q);
    if (!q.trim()) {
      fetchData();
      return;
    }
    try {
      const res = await API.get(`/search?q=${q}`);
      setData(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleDelete = async (id) => {
    try {
      await API.delete(`/${id}`);
      // Refresh the data after deleting
      if (searchTerm) {
        search(searchTerm);
      } else {
        fetchData();
      }
    } catch (err) {
      console.error("Failed to delete", err);
    }
  };

  // 🔵 Loading State
  if (loading) {
    return <p className="text-center mt-5">Loading data...</p>;
  }

  // 🔴 Error State
  if (error) {
    return <p className="text-red-500 text-center mt-5">{error}</p>;
  }

  return (
    <div className="p-5">
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-xl font-bold">DFD Records</h1>
        <input
          type="text"
          placeholder="Search..."
          value={searchTerm}
          className="border p-2 rounded"
          onChange={(e) => search(e.target.value)}
        />
      </div>

      {data.length === 0 ? (
        <p className="text-center mt-5">No records found</p>
      ) : (
        <table className="table-auto w-full border border-gray-300">
          <thead className="bg-gray-200">
            <tr>
              <th className="p-2 border">ID</th>
              <th className="p-2 border">Title</th>
              <th className="p-2 border">Status</th>
              <th className="p-2 border">Created</th>
              <th className="p-2 border">Actions</th>
            </tr>
          </thead>

          <tbody>
            {data.map((item) => (
              <tr key={item.id} className="text-center">
                <td className="p-2 border">{item.id}</td>
                <td className="p-2 border">{item.title}</td>
                <td className="p-2 border">{item.status}</td>
                <td className="p-2 border">
                  {item.createdAt ? item.createdAt.substring(0, 10) : "-"}
                </td>
                <td className="p-2 border">
                  <button
                    onClick={() => handleDelete(item.id)}
                    className="bg-red-500 text-white px-3 py-1 rounded"
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default ListPage;
