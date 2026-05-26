import { useState } from "react";
import api from "../services/api";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
  
    try {
      const res = await api.post("/auth/login", {
        email,
        password,
      });
  
      console.log("LOGIN RESPONSE:", res.data);
  
      const token = res.data?.token;
      const role = res.data?.role;
  
      if (!token) {
        throw new Error("No token received from backend");
      }
  
      localStorage.setItem("token", token);
      localStorage.setItem("role", role || "ROLE_USER");
  
      if (role === "ROLE_ADMIN") {
        window.location.href = "/admin";
      } else {
        window.location.href = "/";
      }
  
    } catch (err) {
      console.error("LOGIN ERROR:", err);
      alert("Login failed");
    }
  };

  return (
    <div style={styles.container}>

      {/* LOGO FROM S3 */}
      <img
        src="https://diamond-barbershop-images-2026-hanad.s3.eu-north-1.amazonaws.com/Diamond.jpg"
        alt="Logo"
        style={styles.logo}
      />

      <h2 style={styles.title}>Barber Login</h2>

      <form onSubmit={handleLogin} style={styles.form}>

        <input
          type="email"
          placeholder="Email"
          autoComplete="username"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={styles.input}
          required
        />

        <input
          type="password"
          placeholder="Password"
          autoComplete="current-password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={styles.input}
          required
        />

        <button type="submit" style={styles.button} disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>

      </form>
    </div>
  );
}

const styles = {
  container: {
    height: "100vh",
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    alignItems: "center",
    fontFamily: "Arial",
    backgroundColor: "#f5f5f5",
  },

  logo: {
    width: "120px",
    marginBottom: "20px",
  },

  title: {
    marginBottom: "20px",
  },

  form: {
    display: "flex",
    flexDirection: "column",
    gap: "10px",
    width: "250px",
  },

  input: {
    padding: "10px",
    borderRadius: "8px",
    border: "1px solid #ccc",
  },

  button: {
    padding: "10px",
    backgroundColor: "black",
    color: "white",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
  },
};