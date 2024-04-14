import React from "react";
import { Link, Route, BrowserRouter as Router, Routes } from "react-router-dom";
import PreviousRequests from "./components/PreviousRequests";
import Summarization from "./components/Summarization";

function App() {
  return (
    <Router>
      <nav className="bg-gray-800 text-white py-4">
        <div className="container mx-auto flex justify-start">
          <Link to="/" className="text-lg font-bold mx-2">
            Summarization
          </Link>
          <Link to="/requests" className="text-lg font-bold mx-2">
            Previous Requests
          </Link>
        </div>
      </nav>

      <Routes>
        <Route path="/requests" element={<PreviousRequests />} />
        <Route path="/" element={<Summarization />} />
      </Routes>
    </Router>
  );
}

export default App;
