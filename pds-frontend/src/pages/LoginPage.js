  import React, { useState, useEffect } from "react";
  import { useNavigate } from "react-router-dom"; // Hook for programmatic navigation
  import { useAuth } from "../context/AuthContext"; // Import the AuthContext hook
import toast from 'react-hot-toast';

  const LoginPage = () => {
    const [userId, setUserId] = useState(""); // State for user ID input
    const [password, setPassword] = useState(""); // State for password input
    const { login, isAuthenticated, authMessage } = useAuth(); // Get login function, auth status, and messages from AuthContext
    const navigate = useNavigate(); // Initialize navigate hook

    // Effect to redirect to the dashboard if the user becomes authenticated
    useEffect(() => {
      if (isAuthenticated) {
        navigate("/dashboard"); // Redirect to dashboard page
      }
    }, [isAuthenticated, navigate]); // Dependencies: re-run when isAuthenticated or navigate changes

    // Handler for form submission (login attempt)
    const handleLogin = async (e) => {
      e.preventDefault(); // Prevent default form submission behavior
      await login(userId, password); // Call the login function from AuthContext
      // Redirection after successful login is handled by the useEffect above
    };

    return (
      <div className="flex items-center justify-center min-h-screen bg-[#f0f0d7]">
        <div className="bg-white p-8 shadow-lg w-full max-w-md border border-[#228B22] rounded-xl">
          <h2 className="text-2xl font-bold text-center text-[#1a2236] mb-6">
            PDS Dashboard Login
          </h2>
          <form onSubmit={handleLogin}>
            <div className="mb-4">
              <label
                htmlFor="userId"
                className="block text-[#1a2236] text-sm font-bold mb-2"
              >
                User ID:
              </label>
              <input
                type="text"
                id="userId"
                className="shadow appearance-none border w-full py-2 px-3 text-[#1a2236] leading-tight focus:outline-none focus:shadow-outline border-[#228B22] rounded-lg"
                value={userId}
                onChange={(e) => setUserId(e.target.value)} // Update userId state on change
                required // Make input required
              />
            </div>
            <div className="mb-6">
              <label
                htmlFor="password"
                className="block text-[#1a2236] text-sm font-bold mb-2"
              >
                Password:
              </label>
              <input
                type="password"
                id="password"
                className="shadow appearance-none border w-full py-2 px-3 text-[#1a2236] mb-3 leading-tight focus:outline-none focus:shadow-outline border-[#228B22] rounded-lg"
                value={password}
                onChange={(e) => setPassword(e.target.value)} // Update password state on change
                required // Make input required
              />
            </div>
            {/* Display login/logout messages from AuthContext */}
            {/* {authMessage && (
              <p className={`text-center mb-4 text-sm ${authMessage.type === 'error' ? 'text-red-500' : 'text-green-600'}`}>
                {authMessage.text}
              </p>
            )} */}
            <div className="flex items-center justify-between">
              <button
                type="submit"
                className="bg-[#228B22] hover:bg-[#1a6b1a] text-white font-bold py-2 px-4 focus:outline-none focus:shadow-outline w-full transition-colors duration-200 rounded-lg"
                // The disabled state could be linked to `loadingAuth` from AuthContext if desired
                disabled={false}
              >
                Login
              </button>
            </div>
          </form>
        </div>
      </div>
    );
  };

  export default LoginPage;
