import axios from "axios";
import React from "react";
import { Link, Route, BrowserRouter as Router, Routes } from "react-router-dom";

function SummarizationForm() {
  const [websiteUrl, setWebsiteUrl] = React.useState("");
  const [summary, setSummary] = React.useState("");
  const [loading, setLoading] = React.useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axios.post("/summarize", websiteUrl, {
        headers: {
          "Content-Type": "application/json",
        },
      });
      setSummary(response.data);
    } catch (error) {
      console.error("Error summarizing:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="max-w-md mx-auto my-10 p-4 bg-white rounded-lg shadow-md">
      <h2 className="text-xl font-bold mb-4">Summarization Form</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label htmlFor="websiteUrl" className="block text-sm font-medium text-gray-700">
            Website URL:
          </label>
          <input
            type="text"
            id="websiteUrl"
            value={websiteUrl}
            onChange={(e) => setWebsiteUrl(e.target.value)}
            className="mt-1 p-2 w-full border rounded-md"
          />
        </div>
        <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded-md" disabled={loading}>
          {loading ? "Loading..." : "Summarize"}
        </button>
      </form>
      {summary && (
        <div className="mt-4">
          <h3 className="text-lg font-bold mb-2">Summary:</h3>
          <p className="text-gray-700">{summary}</p>
        </div>
      )}
    </div>
  );
}

function PreviousRequests() {
  const [requests, setRequests] = React.useState("");

  React.useEffect(() => {
    const fetchRequests = async () => {
      try {
        const response = await axios.get("/requests");
        setRequests(response.data);
      } catch (error) {
        console.error("Error fetching requests:", error);
      }
    };
    fetchRequests();
  }, []);

  return (
    <div className="max-w-md mx-auto my-10 p-4 bg-white rounded-lg shadow-md">
      <h2 className="text-xl font-bold mb-4">Previous Requests</h2>
      <pre className="text-gray-700 whitespace-pre-wrap">{requests}</pre>
    </div>
  );
}

function App() {
  return (
    <Router>
      <nav className="bg-gray-800 text-white py-4">
        <div className="container mx-auto flex justify-between">
          <Link to="/" className="text-lg font-bold">
            Summarization Form
          </Link>
          <Link to="/requests" className="text-lg font-bold">
            Previous Requests
          </Link>
        </div>
      </nav>

      <Routes>
        <Route path="/requests" element={<PreviousRequests />} />
        <Route path="/" element={<SummarizationForm />} />
      </Routes>
    </Router>
  );
}

export default App;
