import { useEffect, useState } from "react";
import API from "../services/api";

const PAGE_SIZE = 5; // Number of records per page

function ListPage() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchTerm, setSearchTerm] = useState("");

  // Pagination state
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
      setCurrentPage(1); // Reset to first page on fresh load
    } catch (err) {
      console.error(err);
      setError("Failed to load data. Make sure you are logged in.");
    } finally {
      setLoading(false);
    }
  };

  const search = async (q) => {
    setSearchTerm(q);
    setCurrentPage(1); // Reset to page 1 on new search
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

  // ── Pagination Logic ──────────────────────────────────────
  const totalPages = Math.ceil(data.length / PAGE_SIZE);
  const paginatedData = data.slice(
    (currentPage - 1) * PAGE_SIZE,
    currentPage * PAGE_SIZE
  );

  const goToPage = (page) => {
    if (page < 1 || page > totalPages) return;
    setCurrentPage(page);
  };
  // ──────────────────────────────────────────────────────────

  // 🔵 Loading State
  if (loading) {
    return (
      <p className="text-center mt-5 text-gray-500">Loading data...</p>
    );
  }

  // 🔴 Error State
  if (error) {
    return (
      <p className="text-red-500 text-center mt-5">{error}</p>
    );
  }

  return (
    <div className="p-5">
      {/* Header + Search */}
      <div className="flex justify-between items-center mb-4">
        <h1 className="text-xl font-bold">DFD Records</h1>
        <input
          id="search-input"
          type="text"
          placeholder="Search by title..."
          value={searchTerm}
          className="border p-2 rounded w-60 focus:outline-none focus:ring-2 focus:ring-blue-400"
          onChange={(e) => search(e.target.value)}
        />
      </div>

      {/* Table */}
      {data.length === 0 ? (
        <p className="text-center mt-5 text-gray-400">No records found</p>
      ) : (
        <>
          <table className="table-auto w-full border border-gray-300">
            <thead className="bg-gray-100">
              <tr>
                <th className="p-2 border text-left">ID</th>
                <th className="p-2 border text-left">Title</th>
                <th className="p-2 border text-left">Status</th>
                <th className="p-2 border text-left">Created</th>
                <th className="p-2 border text-left">Actions</th>
              </tr>
            </thead>

            <tbody>
              {paginatedData.map((item) => (
                <tr key={item.id} className="hover:bg-gray-50">
                  <td className="p-2 border">{item.id}</td>
                  <td className="p-2 border">{item.title}</td>
                  <td className="p-2 border">
                    <span
                      className={`px-2 py-1 rounded text-xs font-semibold ${
                        item.status === "ACTIVE"
                          ? "bg-green-100 text-green-700"
                          : item.status === "INACTIVE"
                          ? "bg-red-100 text-red-700"
                          : "bg-gray-100 text-gray-600"
                      }`}
                    >
                      {item.status || "—"}
                    </span>
                  </td>
                  <td className="p-2 border">
                    {item.createdAt ? item.createdAt.substring(0, 10) : "—"}
                  </td>
                  <td className="p-2 border">
                    <button
                      id={`delete-btn-${item.id}`}
                      onClick={() => handleDelete(item.id)}
                      className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded text-sm transition"
                    >
                      Delete
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          {/* ── Pagination Controls ── */}
          {totalPages > 1 && (
            <div className="flex items-center justify-between mt-4">
              <p className="text-sm text-gray-500">
                Page {currentPage} of {totalPages} &nbsp;|&nbsp; {data.length} total records
              </p>

              <div className="flex gap-2">
                <button
                  id="prev-page-btn"
                  onClick={() => goToPage(currentPage - 1)}
                  disabled={currentPage === 1}
                  className="px-3 py-1 border rounded text-sm disabled:opacity-40 hover:bg-gray-100 transition"
                >
                  ← Prev
                </button>

                {/* Page number buttons */}
                {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
                  <button
                    key={page}
                    id={`page-btn-${page}`}
                    onClick={() => goToPage(page)}
                    className={`px-3 py-1 border rounded text-sm transition ${
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
                  className="px-3 py-1 border rounded text-sm disabled:opacity-40 hover:bg-gray-100 transition"
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
