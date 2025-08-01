import React, { createContext, useState, useEffect, useContext } from "react";
import apiClient from "../services/api"; // Import the configured API client
import toast from "react-hot-toast";

// Create the AuthContext
const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false); // Tracks if user is logged in
  const [userRole, setUserRole] = useState(null); // Stores the raw role from DB (e.g., "1", "4", "8")
  const [userDistCode, setUserDistCode] = useState(null); // Stores the district code
  const [loadingAuth, setLoadingAuth] = useState(true); // Indicates if authentication status is being checked
  const [authMessage, setAuthMessage] = useState(null); // For displaying login/logout messages

  // Effect to check initial authentication status when the component mounts
  useEffect(() => {
    const checkAuthStatus = async () => {
      // console.log("\n--- FRONTEND DEBUG: checkAuthStatus called on mount ---");
      setLoadingAuth(true); // Ensure loading state is true
      try {
        // Attempt to fetch user info from the backend
        const response = await apiClient.get("/user-info", {
          withCredentials: true, // Send cookies with the request
        });
        // console.log("FRONTEND DEBUG: /user-info response status:", response.status);
        // console.log("FRONTEND DEBUG: /user-info response data:", response.data);

        // If successful (status 200), set authentication state and user details
        if (response.status === 200) {
          setIsAuthenticated(true);
          if (response.data) {
            setUserRole(response.data.userRole); // Store the raw userRole from backend
            setUserDistCode(response.data.distCode); // Store the distCode from backend
            // console.log("FRONTEND DEBUG: isAuthenticated set to TRUE. UserRole:", response.data.userRole, "DistCode:", response.data.distCode);
          } else {
            // console.log("FRONTEND DEBUG: /user-info returned 200 but no data. Setting isAuthenticated to FALSE.");
            setIsAuthenticated(false);
            setUserRole(null);
            setUserDistCode(null);
          }
        }
      } catch (error) {
        // If authentication check fails (e.g., 401), reset state
        // console.error("FRONTEND DEBUG: Auth check failed (error caught):", error);
        if (error.response) {
          // console.error("FRONTEND DEBUG: Error Response Status:", error.response.status);
          // console.error("FRONTEND DEBUG: Error Response Data:", error.response.data);
        }
        setIsAuthenticated(false);
        setUserRole(null);
        setUserDistCode(null);
        // console.log("FRONTEND DEBUG: isAuthenticated set to FALSE due to error.");
      } finally {
        setLoadingAuth(false); // Authentication check is complete
        // console.log("FRONTEND DEBUG: checkAuthStatus finished. loadingAuth set to FALSE.");
        // console.log("FRONTEND DEBUG: Final isAuthenticated state:", isAuthenticated); // Log final state
        // console.log("--- FRONTEND DEBUG: checkAuthStatus finished ---");
      }
    };

    checkAuthStatus(); // Run the check on component mount
  }, []); // Empty dependency array means this runs once on mount

  // Login function to handle user authentication
  const login = async (userId, password) => {
    setLoadingAuth(true); // Set loading state
    setAuthMessage(null); // Clear any previous messages
    // console.log("\n--- FRONTEND DEBUG: Login attempt for userId:", userId, "---");
    try {
      // Prepare form data for login request
      const params = new URLSearchParams();
      params.append("userId", userId);
      params.append("password", password);

      // Send login request to the backend
      const response = await apiClient.post("/login", params, {
        headers: {
          "Content-Type": "application/x-www-form-urlencoded", // Required for Spring Security form login
        },
        withCredentials: true, // Send cookies
      });

      // console.log("FRONTEND DEBUG: /login response status:", response.status);

      // If login is successful
      if (response.status === 200) {
        // Fetch user info again to ensure latest role and district code are retrieved
        // This is important because /login only tells us success, not user details
        const userInfoResponse = await apiClient.get("/user-info", {
          withCredentials: true,
        });
        // console.log("FRONTEND DEBUG: /user-info after login response status:", userInfoResponse.status);
        // console.log("FRONTEND DEBUG: /user-info after login response data:", userInfoResponse.data);

        if (userInfoResponse.status === 200 && userInfoResponse.data) {
          setIsAuthenticated(true);
          setUserRole(userInfoResponse.data.userRole);
          setUserDistCode(userInfoResponse.data.distCode);
          // setAuthMessage({ type: 'success', text: 'Login successful!' });
          toast.success("Login successful!");
          // console.log("FRONTEND DEBUG: Login successful. isAuthenticated TRUE. UserRole:", userInfoResponse.data.userRole, "DistCode:", userInfoResponse.data.distCode);
          return true; // Indicate successful login
        } else {
          // This case should ideally not happen after a 200 from /login
          console.error(
            "FRONTEND DEBUG: /user-info after login failed or no data."
          );
          // setAuthMessage({ type: 'error', text: 'Login successful but failed to get user details.' });
          toast.error("Login successful but failed to get user details.");
          setIsAuthenticated(false);
          setUserRole(null);
          setUserDistCode(null);
          return false;
        }
      }
      return false; // Should not be reached if status is 200, but for safety
    } catch (err) {
      // Handle login errors
      // console.error("FRONTEND DEBUG: Login error caught:", err);
      if (err.response) {
        // console.error("FRONTEND DEBUG: Login Error Response Status:", err.response.status);
        // console.error("FRONTEND DEBUG: Login Error Response Data:", err.response.data);
      }
      if (err.response && err.response.status === 401) {
        // setAuthMessage({ type: 'error', text: 'Invalid user ID or password.' }); // Specific message for 401
        toast.error("Invalid user ID or password.");
      } else {
        // setAuthMessage({ type: 'error', text: 'An unexpected error occurred. Please try again later.' }); // Generic error
        toast.error("An unexpected error occurred. Please try again later.");
      }
      setIsAuthenticated(false); // Reset authentication state
      setUserRole(null);
      setUserDistCode(null);
      return false; // Indicate failed login
    } finally {
      setLoadingAuth(false); // Reset loading state
      // console.log("FRONTEND DEBUG: Login attempt finished. loadingAuth set to FALSE.");
      // console.log("--- FRONTEND DEBUG: Login attempt finished ---");
    }
  };

  // Logout function to handle user logout
  const logout = async () => {
    setLoadingAuth(true); // Set loading state
    setAuthMessage(null); // Clear any previous messages
    console.log("\n--- FRONTEND DEBUG: Logout attempt initiated ---");
    try {
      // Send logout request to the backend
      await apiClient.post("/logout", {}, { withCredentials: true });
      setIsAuthenticated(false); // Reset authentication state
      setUserRole(null);
      setUserDistCode(null);
      // setAuthMessage({ type: 'success', text: 'Logout successful!' }); // Set success message
      toast.success("Logout Successful see you soon!"); // Show toast notification

      // console.log("FRONTEND DEBUG: Logout successful. isAuthenticated FALSE.");
    } catch (error) {
      // Handle logout errors
      // console.error("FRONTEND DEBUG: Logout failed:", error);
      // setAuthMessage({ type: 'error', text: 'Logout failed. Please try again.' });
      toast.error("Logout failed. Please try again.");
      // Even if server logout fails, client side should log out for consistency
      setIsAuthenticated(false);
      setUserRole(null);
      setUserDistCode(null);
      // console.log("FRONTEND DEBUG: Logout failed. isAuthenticated FALSE.");
    } finally {
      setLoadingAuth(false); // Reset loading state
      // console.log("--- FRONTEND DEBUG: Logout attempt finished ---");
    }
  };

  return (
    // Provide authentication state and functions to children components
    <AuthContext.Provider
      value={{
        isAuthenticated,
        userRole,
        userDistCode,
        loadingAuth,
        authMessage,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

// Custom hook to easily consume AuthContext in functional components
export const useAuth = () => {
  return useContext(AuthContext);
};
//1
