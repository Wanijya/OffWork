// src/hooks/useDashboardData.js
import { useState, useEffect } from "react";
import apiClient from "../services/api"; // Import the configured API client

// Custom hook for fetching dashboard data
// Renamed to follow React Hook naming conventions (starts with 'use')
const usePdsDashboardData = (userRole, userDistCode) => {
  // Accepts userRole and userDistCode for filtering
  const [data, setData] = useState(null); // Stores the fetched dashboard data
  const [loading, setLoading] = useState(true); // Indicates if data is currently being fetched
  const [error, setError] = useState(null); // Stores any error that occurs during fetching

  useEffect(() => {
    const fetchData = async () => {
      console.log("Fetching dashboard data...");
      setLoading(true); // Start loading
      setError(null); // Clear previous errors
      try {
        // Ensure that userRole and userDistCode are not null before making the API call
        // This prevents fetching global data before user context is fully loaded
        if (userRole === null || userDistCode === null) {
          setError(
            "User role or district code not available for data filtering."
          );
          setLoading(false);
          return; // Exit early if user info is missing
        }

        // Make the API call to get dashboard metrics, passing roleId and distCode as query parameters
        const responseData = await apiClient.get("/metrics", {
          params: {
            roleId: userRole, // Pass the user's role ID
            distCode: userDistCode, // Pass the user's district code
          },
        });
        setData(responseData.data); // Set the fetched data
        setLoading(false); // End loading
      } catch (err) {
        console.error("Failed to fetch dashboard data:", err);
        let errorMessage =
          "Failed to load dashboard data. Please ensure your Spring Boot backend is running and available on port 8080.";
        if (err.response) {
          // Specific error details from the server response
          errorMessage += ` Error: ${err.response.status} - ${
            err.response.statusText || "Unknown Server Error"
          }`;
        } else if (err.request) {
          // Network or CORS related issues
          errorMessage += ` Error: Network issue or CORS configuration problem.`;
        } else {
          // Other unexpected errors
          errorMessage += ` Error: ${err.message}`;
        }
        setError(errorMessage); // Set the error message
        setLoading(false); // End loading
      }
    };

    // Only fetch data if userRole and userDistCode are available (after login and context load)
    if (userRole !== null && userDistCode !== null) {
      fetchData();
    } else {
      // If user info is not yet available, remain in loading state
      // The DashboardPage component will handle showing the spinner until these are ready
      setLoading(true);
    }
  }, [userRole, userDistCode]); // Re-run effect when userRole or userDistCode changes

  return { data, loading, error }; // Return data, loading status, and error
};

export default usePdsDashboardData; // Export the renamed hook
