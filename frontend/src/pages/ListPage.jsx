import { useEffect, useState } from "react";
import API from "../services/api";

const PAGE_SIZE = 5;

function ListPage({ onView, onEdit }) {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    setLoading(true);
    setError("");
    try {
      const res = await API.get("/all");
      setData(res.data);
      setCurrentPage(1);
    } catch (err) {
      console.error(err);
      setError("Failed to load data. Make sure you are logged in.");
    } finally {
      setLoading(false);
    }
  };

  const search = async (q) => {
    setSearchTerm(q);
    setCurrentPage(1);
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
    if (!window.confirm("Are you sure you want to delete this record?")) return;
    try {
      await API.delete(`/${id}`);
      if (searchTerm) {
        search(searchTerm);
      } else {
        fetchData();
      }
    } catch (err) {
      console.error("Failed to delete", err);
    }
  };

  // ── Pagination ─────────────────────────────────────────────
  const totalPages = Math.ceil(data.length / PAGE_SIZE);
  const paginatedData = data.slice(
    (currentPage - 1) * PAGE_SIZE,
    currentPage * PAGE_SIZE
  );

  const goToPage = (page) => {
    if (page < 1 || page > totalPages) return;
    setCurrentPage(page);
  };

  const statusBadge = (status) => {
    const map = {
      ACTIVE:    "bg-green-100 text-green-700",
      COMPLETED: "bg-yellow-100 text-yellow-700",
      INACTIVE:  "bg-red-100 text-red-700",
    };
    return map[status] || "bg-gray-100 text-gray-600";
  };
  // ──────────────────────────────────────────────────────────

  if (loading) return <p className="text-center mt-5 text-gray-500">Loading data...</p>;
  if (error)   return <p className="text-red-500 text-center mt-5">{error}</p>;

  return (
    <div className="bg-white border border-gray-200 rounded-xl shadow-sm p-5">
      {/* Header + Search */}
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-xl font-bold text-gray-800">📋 DFD Records</h2>
        <input
          id="search-input"
          type="text"
          placeholder="Search by title..."
          value={searchTerm}
          className="border border-gray-300 rounded p-2 w-56 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
          onChange={(e) => search(e.target.value)}
        />
      </div>

      {data.length === 0 ? (
        <p className="text-center py-10 text-gray-400">No records found</p>
      ) : (
        <>
          {/* Table */}
          <div className="overflow-x-auto">
            <table className="table-auto w-full text-sm">
              <thead>
                <tr className="bg-gray-50 text-gray-600 text-left">
                  <th className="p-2 border-b font-semibold">ID</th>
                  <th className="p-2 border-b font-semibold">Title</th>
                  <th className="p-2 border-b font-semibold">Status</th>
                  <th className="p-2 border-b font-semibold">Created</th>
                  <th className="p-2 border-b font-semibold">Actions</th>
                </tr>
              </thead>
              <tbody>
                {paginatedData.map((item) => (
                  <tr key={item.id} className="hover:bg-gray-50 border-b last:border-0">
                    <td className="p-2 text-gray-500">{item.id}</td>
                    <td className="p-2 font-medium text-gray-800">{item.title}</td>
                    <td className="p-2">
                      <span className={`px-2 py-0.5 rounded-full text-xs font-semibold ${statusBadge(item.status)}`}>
                        {item.status || "—"}
                      </span>
                    </td>
                    <td className="p-2 text-gray-500">
                      {item.createdAt ? item.createdAt.substring(0, 10) : "—"}
                    </td>
                    <td className="p-2">
                      <div className="flex gap-2">
                        {/* View Detail */}
                        <button
                          id={`view-btn-${item.id}`}
                          onClick={() => onView && onView(item.id)}
                          className="bg-gray-100 hover:bg-gray-200 text-gray-700 px-3 py-1 rounded text-xs font-medium transition"
                        >
                          👁 View
                        </button>

                        {/* Edit */}
                        <button
                          id={`edit-btn-${item.id}`}
                          onClick={() => onEdit && onEdit(item.id)}
                          className="bg-blue-100 hover:bg-blue-200 text-blue-700 px-3 py-1 rounded text-xs font-medium transition"
                        >
                          ✏️ Edit
                        </button>

                        {/* Delete */}
                        <button
                          id={`delete-btn-${item.id}`}
                          onClick={() => handleDelete(item.id)}
                          className="bg-red-100 hover:bg-red-200 text-red-700 px-3 py-1 rounded text-xs font-medium transition"
                        >
                          🗑 Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Pagination */}
          {totalPages > 1 && (
            <div className="flex items-center justify-between mt-4 text-sm">
              <p className="text-gray-500">
                Page {currentPage} of {totalPages} | {data.length} records
              </p>
              <div className="flex gap-2">
                <button
                  id="prev-page-btn"
                  onClick={() => goToPage(currentPage - 1)}
                  disabled={currentPage === 1}
                  className="px-3 py-1 border rounded disabled:opacity-40 hover:bg-gray-100 transition"
                >
                  ← Prev
                </button>
                {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
                  <button
                    key={page}
                    id={`page-btn-${page}`}
                    onClick={() => goToPage(page)}
                    className={`px-3 py-1 border rounded transition ${
                      page === currentPage
                        ? "bg-blue-500 text-white border-blue-500"
                        : "hover:bg-gray-100"
                    }`}
                  >
                    {page}
                  </button>
                ))}
                <button
                  id="next-page-btn"
                  onClick={() => goToPage(currentPage + 1)}
                  disabled={currentPage === totalPages}
                  className="px-3 py-1 border rounded disabled:opacity-40 hover:bg-gray-100 transition"
                >
                  Next →
                </button>
              </div>
            </div>
          )}
        </>
      )}
    </div>
  );
}

export default ListPage;
