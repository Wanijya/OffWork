import React from 'react';
import ReactDOM from 'react-dom/client'; // Import createRoot from ReactDOM/client
import App from "./App";
import "./index.css"; // Global styles, including Tailwind CSS

// Use React 18's createRoot API to render the application
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
