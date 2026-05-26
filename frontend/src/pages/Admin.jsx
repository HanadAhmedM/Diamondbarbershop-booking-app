import { useEffect, useState } from "react";
import api from "../services/api";

export default function Admin() {

  const [barbers, setBarbers] = useState([]);
  const [appointments, setAppointments] = useState([]);

  const [name, setName] = useState("");
  const [specialty, setSpecialty] = useState("");
  const [image, setImage] = useState(null);

  // =========================
  // LOAD DATA
  // =========================
  const loadData = async () => {
    try {
      const b = await api.get("/admin/barbers");
      const a = await api.get("/admin/appointments");

      setBarbers(b.data);
      setAppointments(a.data);

    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const b = await api.get("/admin/barbers");
        const a = await api.get("/admin/appointments");
  
        setBarbers(b.data);
        setAppointments(a.data);
  
      } catch (err) {
        console.error(err);
      }
    };
  
    fetchData();
  }, []);
  // =========================
  // ADD BARBER
  // =========================
  const addBarber = async () => {

    if (!name || !specialty || !image) {
      return alert("Fill all fields");
    }

    try {
      const formData = new FormData();

      formData.append("name", name);
      formData.append("specialty", specialty);
      formData.append("image", image);

      await api.post("/admin/barbers", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      setName("");
      setSpecialty("");
      setImage(null);

      loadData();

    } catch (err) {
      console.error(err);
      alert("Failed to add barber");
    }
  };

  // =========================
  // DELETE BARBER
  // =========================
  const deleteBarber = async (id) => {
    await api.delete(`/admin/barbers/${id}`);
    loadData();
  };

  // =========================
  // DELETE BOOKING
  // =========================
  const deleteBooking = async (id) => {
    await api.delete(`/admin/appointments/${id}`);
    loadData();
  };

  return (
    <div style={styles.container}>

      <h1>💈 Admin Dashboard</h1>

      {/* ADD BARBER */}
      <div style={styles.card}>

        <h2>Add Barber</h2>

        <input
          style={styles.input}
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />

        <input
          style={styles.input}
          placeholder="Specialty"
          value={specialty}
          onChange={(e) => setSpecialty(e.target.value)}
        />

        <input
          type="file"
          onChange={(e) => setImage(e.target.files[0])}
        />

        <button style={styles.button} onClick={addBarber}>
          Add Barber
        </button>
      </div>

      {/* BARBERS */}
      <div style={styles.card}>

        <h2>Barbers</h2>

        {barbers.map((b) => (
          <div key={b.id} style={styles.barberCard}>

            <img
              src={b.imageUrl}
              alt={b.name}
              style={styles.image}
            />

            <div>
              <h3>{b.name}</h3>
              <p>{b.specialty}</p>

              <button
                style={styles.deleteBtn}
                onClick={() => deleteBarber(b.id)}
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* BOOKINGS */}
      <div style={styles.card}>

        <h2>Bookings</h2>

        {appointments.map((a) => (
          <div key={a.id} style={styles.bookingCard}>

            <p>
              💈 {a.barber?.name}
            </p>

            <p>
              👤 {a.user?.email}
            </p>

            <p>
              ⏰ {a.dateTime}
            </p>

            <button
              style={styles.deleteBtn}
              onClick={() => deleteBooking(a.id)}
            >
              Delete Booking
            </button>
          </div>
        ))}
      </div>

    </div>
  );
}

const styles = {

  container: {
    padding: 20,
    fontFamily: "Arial",
    background: "#f4f4f4",
    minHeight: "100vh",
  },

  card: {
    background: "white",
    padding: 20,
    borderRadius: 12,
    marginBottom: 20,
  },

  input: {
    display: "block",
    width: "100%",
    padding: 10,
    marginBottom: 10,
  },

  button: {
    padding: 10,
    background: "black",
    color: "white",
    border: "none",
    cursor: "pointer",
  },

  barberCard: {
    display: "flex",
    gap: 20,
    alignItems: "center",
    marginBottom: 20,
  },

  bookingCard: {
    borderBottom: "1px solid #ddd",
    paddingBottom: 10,
    marginBottom: 10,
  },

  image: {
    width: 120,
    height: 120,
    objectFit: "cover",
    borderRadius: 10,
  },

  deleteBtn: {
    background: "red",
    color: "white",
    border: "none",
    padding: 8,
    cursor: "pointer",
  },
};