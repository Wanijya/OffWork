import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext'; // Import AuthContext hook
import LoginPage from '../pages/LoginPage'; // Import LoginPage component
import DashboardPage from '../pages/DashboardPage'; // Import DashboardPage component
import PdsLoadingSpinner from '../components/PdsLoadingSpinner'; // Import loading spinner component

// ProtectedRoute component to guard routes that require authentication
const ProtectedRoute = ({ children }) => {
  const { isAuthenticated, loadingAuth } = useAuth(); // Get authentication status and loading state

  // Show a loading spinner while authentication status is being determined
  if (loadingAuth) {
    return <PdsLoadingSpinner />;
  }

  // If the user is authenticated, render the children (the protected component, e.g., DashboardPage)
  // If not authenticated, redirect to the root path (which is the LoginPage)
  return isAuthenticated ? children : <Navigate to="/" replace />;
};

// Component to handle the logic for the root path ("/")
// It decides whether to show the login page or redirect to the dashboard
const HomeOrLogin = () => {
  const { isAuthenticated, loadingAuth } = useAuth(); // Get authentication status and loading state

  // Show a loading spinner while authentication status is being determined
  if (loadingAuth) {
    return <PdsLoadingSpinner />;
  }

  // If the user is authenticated, navigate them to the dashboard
  // If not authenticated, render the LoginPage component
  return isAuthenticated ? <Navigate to="/dashboard" replace /> : <LoginPage />;
};

// 404 Not Found component for unmatched routes
const NoMatch = () => (
  <div className="flex items-center justify-center min-h-screen bg-gray-100">
    <h1 className="text-4xl font-bold text-gray-800">404 - Page Not Found</h1>
  </div>
);

// Main component that defines all application routes
const AppRoutes = () => {
  return (
    <Routes>
      {/*
        Root path ("/") route:
        This route uses the HomeOrLogin component to decide whether to render the LoginPage
        or redirect to the DashboardPage based on the authentication status.
      */}
      <Route path="/" element={<HomeOrLogin />} />

      {/*
        Dashboard route ("/dashboard"):
        This is a protected route. It uses the ProtectedRoute component as a wrapper.
        Only authenticated users can access the DashboardPage.
      */}
      <Route
        path="/dashboard"
        element={
          <ProtectedRoute>
            <DashboardPage />
          </ProtectedRoute>
        }
      />

      {/*
        Fallback route for any unmatched paths:
        Renders the NoMatch (404) component if no other route matches the URL.
      */}
      <Route path="*" element={<NoMatch />} />
    </Routes>
  );
};

export default AppRoutes;
