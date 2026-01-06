import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    // 1. Validation Check
    if (!email || !password) {
      alert("Please enter both email and password.");
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/api/auth/login",
        {
          email: email,
          password: password,
        }
      );
      localStorage.setItem("user", JSON.stringify(response.data));
      navigate("/dashboard");
    } catch (error) {
      alert("Login Failed: " + (error.response?.data || "Unknown error"));
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault(); // Stop form reload if triggered by button

    // 1. Validation Check
    if (!email || !password) {
      alert("Email and Password cannot be empty.");
      return;
    }

    try {
      await axios.post("http://localhost:8080/api/auth/register", {
        email: email,
        password: password,
      });
      alert("Registration Successful! Please log in.");
    } catch (error) {
      alert(
        "Registration Failed: " + (error.response?.data || "Unknown error")
      );
    }
  };

  return (
    <div style={styles.container}>
      <h2>HireWatch Login</h2>
      <form onSubmit={handleLogin} style={styles.form}>
        <div style={styles.inputGroup}>
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            style={styles.input}
          />
        </div>
        <div style={styles.inputGroup}>
          <label>Password:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            style={styles.input}
          />
        </div>
        <button type="submit" style={styles.button}>
          Login
        </button>
      </form>

      <hr />
      <button onClick={handleRegister} style={styles.secondaryButton}>
        Register New Account
      </button>
    </div>
  );
}

// Simple CSS styles object for cleanliness
const styles = {
  container: {
    padding: "2rem",
    maxWidth: "400px",
    margin: "0 auto",
    textAlign: "center",
  },
  form: {
    display: "flex",
    flexDirection: "column",
    gap: "1rem",
    marginBottom: "2rem",
  },
  inputGroup: { textAlign: "left" },
  input: { width: "100%", padding: "8px", marginTop: "5px" },
  button: {
    padding: "10px",
    backgroundColor: "#007bff",
    color: "white",
    border: "none",
    cursor: "pointer",
  },
  secondaryButton: {
    padding: "10px",
    backgroundColor: "#6c757d",
    color: "white",
    border: "none",
    cursor: "pointer",
  },
};

export default Login;
