import { useEffect, useState } from "react";
import API from "../services/api";

function ListPage() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

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

  // 🔵 Loading State
  if (loading) {
    return <p className="text-center mt-5">Loading data...</p>;
  }

  // 🔴 Error State
  if (error) {
    return <p className="text-red-500 text-center mt-5">{error}</p>;
  }

  // 🟡 Empty State
  if (data.length === 0) {
    return <p className="text-center mt-5">No records found</p>;
  }

  // 🟢 Success State
  return (
    <div className="p-5">
      <h1 className="text-xl font-bold mb-4">DFD Records</h1>

      <table className="table-auto w-full border border-gray-300">
        <thead className="bg-gray-200">
          <tr>
            <th className="p-2 border">ID</th>
            <th className="p-2 border">Title</th>
            <th className="p-2 border">Status</th>
            <th className="p-2 border">Created</th>
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
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ListPage;
