import { useEffect, useState } from "react";
import api from "../services/api";

export default function Book() {
  const [barbers, setBarbers] = useState([]);
  const [selectedBarber, setSelectedBarber] = useState(null);

  const [selectedDate, setSelectedDate] = useState("");
  const [selectedTime, setSelectedTime] = useState("");

  const [bookedTimes, setBookedTimes] = useState([]);

  const times = [
    "11:00", "11:30",
    "12:00", "12:30",
    "13:00", "13:30",
    "14:00", "14:30",
    "15:00", "15:30",
    "16:00", "16:30",
    "17:00", "17:30",
    "18:00", "18:30",
    "19:00"
  ];

  // ================= LOAD BARBERS =================
  useEffect(() => {
    const loadBarbers = async () => {
      try {
        const res = await api.get("/barbers");
        setBarbers(res.data || []);
      } catch (err) {
        console.error("Failed to load barbers", err);
      }
    };

    loadBarbers();
  }, []);

  // ================= LOAD BOOKED TIMES =================
  useEffect(() => {
    const loadBookedTimes = async () => {
      if (!selectedBarber || !selectedDate) return;

      try {
        const res = await api.get("/appointments/booked", {
          params: {
            barberId: selectedBarber.id,
            date: selectedDate,
          },
        });

        setBookedTimes(res.data || []);
      } catch (err) {
        console.error("Failed to load booked times", err);
      }
    };

    loadBookedTimes();
  }, [selectedBarber, selectedDate]);

  // ================= BOOK APPOINTMENT =================
  const handleBooking = async () => {
    if (!selectedBarber) return alert("Select barber");
    if (!selectedDate) return alert("Select date");
    if (!selectedTime) return alert("Select time");

    try {
      await api.post("/appointments", {
        barber: { id: selectedBarber.id },
        dateTime: `${selectedDate}T${selectedTime}:00`,
      });

      alert("Booking confirmed!");

      // reset
      setSelectedTime("");
      setBookedTimes([]);

    } catch (err) {
      console.error(err);
      alert("Booking failed");
    }
  };

  return (
    <div style={{ padding: 20, fontFamily: "Arial" }}>

      <h2>💈 Select Barber</h2>

      <div style={{ display: "grid", gridTemplateColumns: "repeat(3, 1fr)", gap: 10 }}>
        {barbers.map((b) => (
          <div
            key={b.id}
            onClick={() => setSelectedBarber(b)}
            style={{
              border: selectedBarber?.id === b.id ? "2px solid blue" : "1px solid #ccc",
              padding: 10,
              cursor: "pointer",
              borderRadius: 8,
              textAlign: "center"
            }}
          >
            <img
              src={b.imageUrl || "https://via.placeholder.com/100"}
              alt={b.name}
              style={{ width: "100%", height: 120, objectFit: "cover" }}
            />
            <h4>{b.name}</h4>
            <p>{b.specialty}</p>
          </div>
        ))}
      </div>

      <hr />

      <h2>📅 Select Date</h2>
      <input
        type="date"
        value={selectedDate}
        onChange={(e) => setSelectedDate(e.target.value)}
      />

      <h2>⏰ Select Time</h2>

      <div style={{ display: "grid", gridTemplateColumns: "repeat(4, 1fr)", gap: 10 }}>
        {times.map((time) => {
          const isBooked = bookedTimes.includes(time);

          return (
            <button
              key={time}
              disabled={isBooked}
              onClick={() => !isBooked && setSelectedTime(time)}
              style={{
                padding: 10,
                borderRadius: 8,
                border: "1px solid #ccc",
                background: isBooked
                  ? "#ccc"
                  : selectedTime === time
                  ? "#3b82f6"
                  : "white",
                color: isBooked ? "#666" : "black",
                cursor: isBooked ? "not-allowed" : "pointer"
              }}
            >
              {time}
            </button>
          );
        })}
      </div>

      <hr />

      <button
        onClick={handleBooking}
        style={{
          width: "100%",
          padding: 15,
          background: "black",
          color: "white",
          border: "none",
          borderRadius: 10,
          marginTop: 20
        }}
      >
        Confirm Booking
      </button>
    </div>
  );
}