// src/services/api.js
import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api/dashboard",
  headers: {
    "Content-Type": "application/json",
  },

  withCredentials: true, // if credentials are needed in CORS
});

export default apiClient;
