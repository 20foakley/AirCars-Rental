import React, { useState, useEffect } from 'react';
import CarListing from './CarListing';
import Spinner from './Spinner';

type Car = {
  id: number;
  make: string;
  model: string;
  year: number;
};

const CarListings = () => {
  const [cars, setCars] = useState<Car[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const controller = new AbortController();
    const signal = controller.signal;

    const fetchCars = async () => {
      try {
        const res = await fetch('/api/cars', { signal });
        if (!res.ok) throw new Error(`Error: ${res.status}`);
        const data: Car[] = await res.json();
        setCars(data);
      } catch (error) {
        if (error.name !== 'AbortError') {
          console.error('Error fetching cars:', error);
        }
      } finally {
        setLoading(false);
      }
    };

    fetchCars();

    return () => controller.abort();
  }, []);

  return (
    <section>
      {loading ? (
        <Spinner loading={loading}></Spinner>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {cars.map((car) => (
            <CarListing key={car.id} car={car} />
          ))}
        </div>
      )}
    </section>
  );
};

export default CarListings;
