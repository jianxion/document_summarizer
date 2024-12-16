import React, { useState } from "react";
import axios from "axios";

const Chatbot = ({ documentSummary }) => {
    const [userMessage, setUserMessage] = useState("");
    const [chatHistory, setChatHistory] = useState([]);
    const [loading, setLoading] = useState(false);

    const sendMessage = async () => {
        if (!userMessage.trim()) return;

        const newMessage = { role: "user", content: userMessage };
        setChatHistory([...chatHistory, newMessage]);

        setLoading(true);
        try {
            const response = await axios.post("http://localhost:8080/api/chat", {
                message: userMessage,
                summary: documentSummary, // Pass the summary to the back end
            });

            const botMessage = { role: "bot", content: response.data };
            setChatHistory((prev) => [...prev, botMessage]);
        } catch (err) {
            const errorMessage = { role: "bot", content: "Error: Unable to reach the chatbot." };
            setChatHistory((prev) => [...prev, errorMessage]);
        } finally {
            setLoading(false);
            setUserMessage("");
        }
    };

    return (
        <div style={{ padding: "20px", maxWidth: "600px", margin: "auto", textAlign: "center" }}>
            <h2>Chatbot</h2>
            <div
                style={{
                    border: "1px solid #ccc",
                    padding: "10px",
                    height: "300px",
                    overflowY: "auto",
                    marginBottom: "10px",
                }}
            >
                {chatHistory.map((msg, index) => (
                    <div key={index} style={{ textAlign: msg.role === "user" ? "right" : "left" }}>
                        <p>
                            <strong>{msg.role === "user" ? "You" : "Bot"}:</strong> {msg.content}
                        </p>
                    </div>
                ))}
            </div>
            <div>
                <input
                    type="text"
                    value={userMessage}
                    onChange={(e) => setUserMessage(e.target.value)}
                    placeholder="Ask something about the document..."
                    style={{ width: "80%", padding: "5px" }}
                />
                <button onClick={sendMessage} disabled={loading} style={{ marginLeft: "10px" }}>
                    {loading ? "Sending..." : "Send"}
                </button>
            </div>
        </div>
    );
};

export default Chatbot;
