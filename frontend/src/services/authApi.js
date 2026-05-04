import axios from "axios";

// Separate API instance for auth endpoints (login/register)
// These are NOT under /api/diagram — they are under /api/auth
const authApi = axios.create({
  baseURL: "http://localhost:8080/api/auth",
});

export default authApi;
