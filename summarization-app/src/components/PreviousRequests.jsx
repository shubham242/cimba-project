import axios from "axios";
import { useEffect, useState } from "react";

const PreviousRequests = () => {
  const [requests, setRequests] = useState("");

  useEffect(() => {
    const fetchRequests = async () => {
      try {
        const response = await axios.get("http://localhost:8080/requests");
        console.log("response", response.data);
        const data = response.data;
        data.reverse();
        setRequests(data);
      } catch (error) {
        console.error("Error fetching requests:", error);
      }
    };
    fetchRequests();
  }, []);

  return (
    <div className="w-1/2 mx-auto my-10 p-4 bg-white rounded-lg shadow-md">
      <h2 className="text-2xl font-bold mb-4 text-center">Previous Requests</h2>
      {requests.length > 0 &&
        requests.map((request) => (
          <div key={request.id} className="bg-gray-100 p-4 mb-4 rounded-lg">
            <h3 className="text-lg font-bold mb-2">Website URL</h3>
            <p className="text-gray-700">{request.websiteUrl}</p>
            <h3 className="text-lg font-bold mt-4 mb-2">Summary</h3>
            <p className="text-gray-700">{request.summary}</p>
          </div>
        ))}
    </div>
  );
};

export default PreviousRequests;
