import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

function ProtectedRoute({ children }) {
  const { token } = useContext(AuthContext);

  if (!token) {
    return <p>Please login first</p>;
  }

  return children;
}

export default ProtectedRoute;
