import { MemoryRouter as Router, Routes, Route } from 'react-router-dom';
import './App.css';
import { useState } from 'react';
import axios from 'axios';

function Hello() {
  const [message, setMessage] = useState("");
  return (
    <div>
      <h1>Plant Management</h1>
      <p>{message} </p>
      <button onClick={() => {
        axios.get("http://[::1]:8080/plant").then((response) => setMessage(response.data)).catch((err) => setMessage(err.message));
      }}>Make Request</button>
    </div>
  );
}

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Hello />} />
      </Routes>
    </Router>
  );
}
