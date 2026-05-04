import { useContext, useState } from "react";
import { AuthContext } from "./context/AuthContext";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import ListPage from "./pages/ListPage";
import FormPage from "./pages/FormPage";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  const { token, setToken } = useContext(AuthContext);

  // Controls whether to show Login or Register when not authenticated
  const [showRegister, setShowRegister] = useState(false);

  const handleLogout = () => {
    localStorage.removeItem("token");
    setToken(null);
  };

  // ── Not logged in ────────────────────────────────────────
  if (!token) {
    if (showRegister) {
      return (
        <RegisterPage onGoToLogin={() => setShowRegister(false)} />
      );
    }
    return (
      <LoginPage
        setToken={setToken}
        onGoToRegister={() => setShowRegister(true)}
      />
    );
  }

  // ── Logged in ────────────────────────────────────────────
  return (
    <ProtectedRoute>
      <div className="min-h-screen bg-gray-50">
        {/* Top Navigation Bar */}
        <header className="bg-white shadow-sm px-6 py-3 flex justify-between items-center">
          <h1 className="text-lg font-bold text-blue-600">📊 DFD Builder</h1>
          <button
            id="logout-btn"
            onClick={handleLogout}
            className="bg-red-500 hover:bg-red-600 text-white px-4 py-1.5 rounded text-sm font-medium transition"
          >
            Logout
          </button>
        </header>

        {/* Main Content */}
        <div className="container mx-auto max-w-4xl p-4">
          <FormPage />
          <hr className="my-8" />
          <ListPage />
        </div>
      </div>
    </ProtectedRoute>
  );
}

export default App;
