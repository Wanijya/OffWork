import React from "react";

const PdsErrorMessage = ({ error }) => {
  // Check if error is an object with a response status, or just a string
  const statusCode =
    error && error.response && error.response.status
      ? error.response.status
      : null;
  const errorMessage =
    error && error.message
      ? error.message
      : typeof error === "string"
      ? error
      : "An unknown error occurred.";

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50 px-6 py-12">
      {/* Large Status Code */}
      {statusCode && (
        <h1 className="text-[7rem] font-extrabold text-red-600 tracking-tight drop-shadow-sm">
          {statusCode}
        </h1>
      )}

      {/* Subheading */}
      <p className="text-xl font-semibold text-gray-800 mt-2">
        Oops! Something went wrong.
      </p>

      {/* Detailed Error Message */}
      <p className="text-md text-gray-700 mt-2 text-center">{errorMessage}</p>

      {/* Hint / Footer */}
      <p className="text-sm text-gray-500 mt-1">
        Please check the server response or try again later.
      </p>
    </div>
  );
};

export default PdsErrorMessage;
