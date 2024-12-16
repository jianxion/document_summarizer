import React, { useState } from "react";
import axios from "axios";

const FileUpload = ({ onSummaryGenerated }) => {
    const [file, setFile] = useState(null);
    const [summary, setSummary] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleUpload = async () => {
        if (!file) {
            setError("Please select a file to upload.");
            return;
        }

        setError("");
        setLoading(true);
        const formData = new FormData();
        formData.append("file", file);

        try {
            const response = await axios.post("http://localhost:8080/api/upload", formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                },
            });
            setSummary(response.data);
            onSummaryGenerated(summary);
        } catch (err) {
            setError("An error occurred while uploading the file. Please try again.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>Document Summarizer</h2>
            <input type="file" onChange={handleFileChange} />
            <button onClick={handleUpload} disabled={loading}>
                {loading ? "Uploading..." : "Upload"}
            </button>
            {error && <p style={{ color: "red" }}>{error}</p>}
            {summary && (
                <div>
                    <h3>Summary:</h3>
                    <p>{summary}</p>
                </div>
            )}
        </div>
    );
};

export default FileUpload;
