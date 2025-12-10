import React, { useState } from 'react';
import { useAuth } from '../../context/AuthContext';

const AuthModal = ({ onClose }: { onClose: () => void }) => {

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 z-50 flex justify-center items-center">
      <div className="bg-white rounded-2xl p-8 shadow-2xl w-96 relative">

        </div>
    </div>
  );
};

export default AuthModal;
