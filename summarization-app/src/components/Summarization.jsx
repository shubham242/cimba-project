import axios from "axios";
import { useState } from "react";

const Summarization = () => {
  const [websiteUrl, setWebsiteUrl] = useState("");
  const [summary, setSummary] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    try {
      const response = await axios.post(
        "http://localhost:8080/summarize",
        { websiteUrl: websiteUrl },
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
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
};

export default Summarization;
