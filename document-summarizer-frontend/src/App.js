import React, { useState } from "react";
import FileUpload from "./components/FileUpload";
import Chatbot from "./components/Chatbot";

const App = () => {
    const [summary, setSummary] = useState("");

    return (
        <div>
            <h1 className="text-center mt-4">Document Summarizer and Chatbot</h1>
            <div className="container mt-5">
                <FileUpload onSummaryGenerated={(summary) => setSummary(summary)} />
                <hr />
                {summary && <Chatbot documentSummary={summary} />}
                <Chatbot />
            
            </div>
        </div>
    );
};

export default App;
