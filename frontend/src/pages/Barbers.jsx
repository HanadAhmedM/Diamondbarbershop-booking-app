import { useEffect, useState } from "react";
import api from "../services/api";
import "./Book.css";

export default function Book() {
  const [barbers, setBarbers] = useState([]);

  const [step, setStep] = useState(1);

  const [selectedBarber, setSelectedBarber] = useState(null);
  const [selectedDate, setSelectedDate] = useState("");
  const [selectedTime, setSelectedTime] = useState("");

  const [bookedTimes, setBookedTimes] = useState([]);
  const [loading, setLoading] = useState(false);

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

  // 💈 LOAD BARBERS
  useEffect(() => {
    const loadBarbers = async () => {
      try {
        const res = await api.get("/barbers");
        setBarbers(res.data);
      } catch (err) {
        console.error("Failed to load barbers", err);
      }
    };

    loadBarbers();
  }, []);

  // ⏰ LOAD BOOKED TIMES
  useEffect(() => {
    if (!selectedBarber || !selectedDate) return;

    const loadBooked = async () => {
      try {
        const res = await api.get(
          `/appointments/booked?barberId=${selectedBarber.id}&date=${selectedDate}`
        );
        setBookedTimes(res.data);
      } catch (err) {
        console.error("Failed to load booked times", err);
      }
    };

    loadBooked();
  }, [selectedBarber, selectedDate]);

  // 💥 BOOK APPOINTMENT
  const handleBooking = async () => {
    if (!selectedBarber) return alert("Select barber");
    if (!selectedDate) return alert("Select date");
    if (!selectedTime) return alert("Select time");

    setLoading(true);

    try {
      await api.post("/appointments", {
        barber: { id: selectedBarber.id },
        dateTime: `${selectedDate}T${selectedTime}:00`
      });

      alert("Booking confirmed!");

      // RESET
      setStep(1);
      setSelectedBarber(null);
      setSelectedDate("");
      setSelectedTime("");
      setBookedTimes([]);

    } catch (err) {
      console.error(err);
      alert("Booking failed");
    }

    setLoading(false);
  };

  return (
    <div className="booking-container">

      {/* STEPS */}
      <div className="steps">
        <div className={step >= 1 ? "active-step" : ""}>Barber</div>
        <div className={step >= 2 ? "active-step" : ""}>Date</div>
        <div className={step >= 3 ? "active-step" : ""}>Time</div>
      </div>

      {/* STEP 1 - BARBER */}
      {step === 1 && (
        <>
          <h2>Select Barber</h2>

          <div className="barbers-grid">
            {barbers.map((barber) => (
              <div
                key={barber.id}
                className="barber-card"
                onClick={() => {
                  setSelectedBarber(barber);
                  setStep(2);
                }}
              >
                <img
                  src={barber.imageUrl || "https://via.placeholder.com/150"}
                  alt={barber.name}
                />
                <h3>{barber.name}</h3>
                <p>{barber.specialty}</p>
              </div>
            ))}
          </div>
        </>
      )}

      {/* STEP 2 - DATE */}
      {step === 2 && (
        <>
          <h2>Select Date</h2>

          <input
            type="date"
            value={selectedDate}
            onChange={(e) => {
              setSelectedDate(e.target.value);
              setStep(3);
            }}
          />

          <button onClick={() => setStep(1)}>Back</button>
        </>
      )}

      {/* STEP 3 - TIME */}
      {step === 3 && (
        <>
          <h2>Select Time</h2>

          <div className="times-grid">
            {times.map((time) => {
              const isBooked = bookedTimes.includes(time);

              return (
                <button
                  key={time}
                  disabled={isBooked}
                  onClick={() => setSelectedTime(time)}
                  className={`
                    time-btn
                    ${selectedTime === time ? "active-time" : ""}
                    ${isBooked ? "booked-time" : ""}
                  `}
                >
                  {isBooked ? "Booked" : time}
                </button>
              );
            })}
          </div>

          <button
            className="confirm-btn"
            onClick={handleBooking}
            disabled={loading}
          >
            {loading ? "Booking..." : "Confirm Booking"}
          </button>

          <button onClick={() => setStep(2)}>Back</button>
        </>
      )}

    </div>
  );
}