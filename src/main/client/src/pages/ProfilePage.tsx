import React from 'react';
import { useAuth } from '../context/AuthContext'

const ProfilePage = () => {
  const { user } = useAuth();

  if (!user) return <div className="text-red-500">You are not logged in. If you are seeing this, something went really wrong!</div>;

  return (
    <div className="p-4">
      <h1 className="text-2xl font-semibold">Hello, {user.username}</h1>
    </div>
  );
};

export default ProfilePage;