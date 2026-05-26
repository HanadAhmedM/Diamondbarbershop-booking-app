import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Barbers from "./pages/Barbers";
import Book from "./pages/Book";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* Auth */}
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* App */}
        <Route path="/barbers" element={<Barbers />} />
        <Route path="/book" element={<Book />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;