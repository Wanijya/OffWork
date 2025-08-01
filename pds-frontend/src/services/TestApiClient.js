import { useState, useCallback, useEffect, useRef } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router";
import Swal from "sweetalert2";
import { useDispatch } from "react-redux";
import { authenticateUser, logout } from "../redux/authSlice";

const TestApiClient = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const location = useLocation();

  const currentRouteRef = useRef(location.pathname);

  const [currentRoute, setCurrentRoute] = useState(location.pathname);

  // Update currentRoute when location changes
  useEffect(() => {
    currentRouteRef.current = location.pathname;
    setCurrentRoute(location.pathname);
  }, [location]);

  const handleUnauthorizedResponse = useCallback(
    (error) => {
      if (error.response) {
        console.log("....................");
        // Extract intended route from response if available
        const intendedRoute = error.response.data?.intendedRoute || "/";
        const xReactRoute = error.response.headers?.["x-react-route"] || "/";
        const xIntendedRoute =
          error.response.headers?.["x-intended-route"] || "/";

        // Use the first available route in order of preference
        const targetRoute =
          intendedRoute || xReactRoute || xIntendedRoute || "/";

        console.log(
          "Handling unauthorized response, navigating to:",
          targetRoute
        );

        // Force route update
        setCurrentRoute(targetRoute);
        currentRouteRef.current = targetRoute;

        // Use replace instead of navigate to avoid adding to history
        navigate(targetRoute, { replace: true });

        console.log("401 Error: Blocking requests");

        // Accessing the X-React-Route header
        const routeHeader = error.response.headers["X-React-Route"];
        console.log("X-React-Route header:", routeHeader);

        const userString = localStorage.getItem("user");
        // Update the route in headers when 451 error occurs
        const config = error.config || {};
        console.log("headers before update:::", config.headers);

        console.log("current Route:::", currentRoute);
        console.log("Headers updated for 451 error:", config.headers);

        if (userString) {
          console.log("calling logoutRequest//////");
          navigate("/logout");
          console.log("called logoutRequest//////");
          return;
        }
      }
    },
    [navigate]
  );

  const [client] = useState(() => {
    const axiosInstance = axios.create({
      baseURL: `${process.env.REACT_APP_URL}`,
      // baseURL: "http://localhost:9090",
      // baseURL: "https://epos.ddd.gov.in/scm_ddd_spring2",
      withCredentials: true,
    });

    // Request Interceptor
    axiosInstance.interceptors.request.use(
      (config) => {
        console.log("Request interceptor - URL:", config.url);
        console.log("Current route:", currentRouteRef.current);

        // Set route header
        config.headers["X-React-Route"] = currentRouteRef.current;

        try {
          const userStr = localStorage.getItem("user");
          if (userStr) {
            const user = JSON.parse(userStr);
            if (user?.token) {
              config.headers.Authorization = `Bearer ${user.token}`;
            }
          }
        } catch (error) {
          console.error("Error processing session storage:", error);
        }

        if (config.url !== "/auth/logout2") {
          const csrfToken = document.cookie
            .split("; ")
            .find((row) => row.startsWith("XSRF-TOKEN="))
            ?.split("=")[1];

          if (csrfToken && config.method !== "get") {
            config.headers["X-XSRF-TOKEN"] = csrfToken;
          }
        }

        return config;
      },
      (error) => Promise.reject(error)
    );

    // Response Interceptor
    axiosInstance.interceptors.response.use(
      (response) => response,
      async (error) => {
        if (error.response) {
          if (error.response.status === 401) {
            handleUnauthorizedResponse(error);
          } else if (error.response.status === 403) {
            navigate("/unauthorized-access");
          } else if (error.response.status === 500) {
            navigate("/internal-server-issue");
          } else if (error.response.status === 419) {
            const result = await Swal.fire({
              title: "Session Expired",
              text: `Do you want to extend session?`,
              icon: null,
              showCancelButton: true,
              confirmButtonColor: "#dc2626",
              cancelButtonColor: "#ffffff",
              cancelButtonText: "No",
              confirmButtonText: "Yes",
              customClass: {
                confirmButton: "custom-confirm-button",
                cancelButton: "custom-cancel-button",
                popup: "custom-swal-width",
              },
            });

            if (result.isConfirmed) {
              try {
                const response = await refreshToken();

                console.log("response is noted.....");
                console.log(response);

                if (response && response.success) {
                  console.log("response from backend");
                  console.log("response.data", response.data);

                  // Clear old data
                  sessionStorage.removeItem("user");
                  localStorage.removeItem("user");

                  // Set new token data
                  document.cookie = `isAuth=true; path=/`;
                  sessionStorage.setItem(
                    "user",
                    JSON.stringify(response.data.token)
                  );
                  localStorage.setItem(
                    "user",
                    JSON.stringify(response.data.token)
                  );

                  dispatch(
                    authenticateUser({
                      isAuthenticated: true,
                      token: response.data.token,
                    })
                  );

                  console.log("Token refreshed successfully");
                } else {
                  console.log("Token refresh failed - logging out");

                  // Clear user data and logout
                  sessionStorage.removeItem("user");
                  localStorage.removeItem("user");
                  dispatch(logout());
                  navigate("/");
                }
              } catch (refreshError) {
                console.error("Error during token refresh:", refreshError);

                // Handle refresh error by logging out
                sessionStorage.removeItem("user");
                localStorage.removeItem("user");
                dispatch(logout());
                navigate("/");
              }
            } else {
              navigate("/logout");
            }
          }
        }
        return Promise.reject(error);
      }
    );

    return axiosInstance;
  });

  const getData = useCallback(
    async (endpoint) => {
      try {
        const response = await client.get(endpoint);
        return response.data;
      } catch (error) {
        console.error(`Error fetching data from ${endpoint}:`, error);
        throw error;
      }
    },
    [client]
  );
   const getDataBigData=useCallback(
    async (endpoint) => {
      try {
        const response = await client.get(endpoint,{responseType:'blob' });
        return response;
      } catch (error) {
        console.error(`Error fetching data from ${endpoint}:`, error);
        throw error;
      }
    },
    [client]
  );

  const postData = useCallback(
    async (endpoint, data, config = {}) => {
      try {
        // Merge the provided config with default configs
        const mergedConfig = {
          ...config,
          headers: {
            ...config.headers,
          },
        };

        // If it's FormData, don't set Content-Type as axios will set it automatically
        if (!(data instanceof FormData)) {
          mergedConfig.headers["Content-Type"] = "application/json";
        }

        const response = await client.post(endpoint, data, mergedConfig);
        return response.data;
      } catch (error) {
        console.error(`Error posting data to ${endpoint}:`, error);
        throw error;
      }
    },
    [client]
  );

  const logoutRequest = useCallback(
    async (endpoint, token) => {
      try {
        console.log("inside logout request");
        const config = {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
          withCredentials: true,
        };

        const response = await client.post(endpoint, {}, config);
        console.log("logout response:::::::", response);
        return response.data;
      } catch (error) {
        console.error("Logout request failed:", error);
        throw error;
      }
    },
    [client]
  );

  const deleteData = useCallback(
    async (endpoint) => {
      try {
        const response = await client.delete(endpoint);
        return response.data;
      } catch (error) {
        console.error(`Error deleting data from ${endpoint}:`, error);
        throw error;
      }
    },
    [client]
  );

  const updateData = useCallback(
    async (endpoint, data) => {
      try {
        const response = await client.put(endpoint, data);
        return response.data;
      } catch (error) {
        console.error(`Error updating data at ${endpoint}:`, error);
        throw error;
      }
    },
    [client]
  );

  const refreshToken = async () => {
    try {
      const userStr = localStorage.getItem("user");

      if (!userStr) {
        console.error("No user found in localStorage");
        return null;
      }

      const user = JSON.parse(userStr);

      const response = await axios.post(
        `${process.env.REACT_APP_URL}/auth/public/refresh-token`,
        {},
        {
          headers: {
            Authorization: `Bearer ${user.token}`,
          },
        }
      );

      console.log("api test ..........");
      console.log("Full refresh token response:", response.data);

      // Return response.data directly since that contains your LoginResponse structure
      return response.data;
    } catch (error) {
      console.error("Error refreshing token:", error);
      return null;
    }
  };

  return {
    getData,
    getDataBigData,
    postData,
    logoutRequest,
    deleteData,
    updateData,
  };
};

export default TestApiClient;
