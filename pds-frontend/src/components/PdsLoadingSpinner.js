// src/components/LoadingSpinner.js
import React from "react";

const PdsLoadingSpinner = () => {
  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className="animate-spin rounded-full h-16 w-16 border-t-4 border-b-4 border-blue-950"></div>
      <p className="ml-4 text-lg text-gray-600">Loading dashboard...</p>
    </div>
  );
};

export default PdsLoadingSpinner;
