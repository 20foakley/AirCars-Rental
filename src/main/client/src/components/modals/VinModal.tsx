import React, { useState } from 'react';
import { decodeVin as decodeVinService, addVehicle as addVehicleService } from '../../services/vehicleService';
import { Vehicle } from '../../types/vehicle';
import { toast } from 'react-toastify';

interface VinModalProps {
  onClose: () => void;
}

const VinModal: React.FC<VinModalProps> = ({ onClose }) => {
  const [vin, setVin] = useState('');
  const [modelYear, setModelYear] = useState('');
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');
  const [successMsg, setSuccessMsg] = useState('');
  const [decodedVehicle, setDecodedVehicle] = useState<Partial<Vehicle>>({});

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setErrorMsg('');
    setSuccessMsg('');
    setDecodedVehicle({});

    if (!vin || !modelYear) {
      setErrorMsg('VIN and Model Year are required.');
      return;
    }

    setLoading(true);

    try {
      // Decode VIN
      const vehicleData = await decodeVinService(vin, Number(modelYear));
      if (!vehicleData || !vehicleData.vin) {
        setErrorMsg('Could not decode VIN. Please check input.');
        return;
      }
      setDecodedVehicle(vehicleData);

      // Add to DB
      const addedVehicle = await addVehicleService(vehicleData);
      setSuccessMsg(`Vehicle ${addedVehicle.vin} added successfully!`);
      toast.success(`Vehicle ${addedVehicle.vin} added successfully!`);

      // Optional: close modal automatically after short delay
      setTimeout(() => {
        onClose();
      }, 1000);
    } catch (err: any) {
      console.error(err);
      setErrorMsg(err.message || 'Failed to add vehicle. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 z-50 flex justify-center items-center">
      <div className="bg-white rounded-2xl p-8 shadow-2xl w-96 relative">
        <button
          onClick={onClose}
          className="absolute top-3 right-3 text-gray-400 hover:text-gray-800 text-xl"
        >
          ✕
        </button>
        <h2 className="text-2xl font-semibold mb-6 text-center">Add Vehicle</h2>

        <form onSubmit={handleSubmit} className="space-y-5">
          <div>
            <input
              type="text"
              value={vin}
              onChange={(e) => setVin(e.target.value)}
              placeholder="Enter VIN"
              required
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          <div>
            <input
              type="number"
              value={modelYear}
              onChange={(e) => setModelYear(e.target.value)}
              placeholder="Enter Model Year"
              required
              className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
          </div>

          {errorMsg && (
            <div className="text-red-500 text-sm text-center">{errorMsg}</div>
          )}
          {successMsg && (
            <div className="text-green-600 text-sm text-center">{successMsg}</div>
          )}

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-blue-600 hover:bg-blue-700 text-white py-2 rounded-md transition duration-200 disabled:opacity-50"
          >
            {loading ? 'Processing...' : 'Add Vehicle'}
          </button>
        </form>

        {/* Optional preview of decoded vehicle */}
        {decodedVehicle.vin && (
          <div className="mt-6 p-4 border rounded bg-gray-50 text-sm">
            <p><strong>VIN:</strong> {decodedVehicle.vin}</p>
            <p><strong>Make:</strong> {decodedVehicle.make}</p>
            <p><strong>Model:</strong> {decodedVehicle.model}</p>
            <p><strong>Year:</strong> {decodedVehicle.year}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default VinModal;
