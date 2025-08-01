import React from "react";
import { BrowserRouter as Router } from "react-router-dom";
import { AuthProvider } from "./context/AuthContext"; // Import AuthProvider
import AppRoutes from "./routes/AppRoutes"; // Import AppRoutes
import { Toaster } from 'react-hot-toast';

const App = () => {
  return (
    <Router>
      <AuthProvider> {/* Wrap the entire application with AuthProvider */}
        <div className="w-full h-full bg-[#f0f0d7]">
          <Toaster position="top-center" />
          <AppRoutes /> {/* Render the AppRoutes component */}
        </div>
      </AuthProvider>
    </Router>
  );
};

export default App;
