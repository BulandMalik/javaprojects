import React, { useState } from 'react';
import { addProfile } from './api'; // Assume you have an API function for adding profiles

function ProfileForm({ onProfileSubmit }) {
  const [profile, setProfile] = useState({ profileId: '', profileName: '', storageQuota: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await addProfile(profile); // API function to add a profile
      onProfileSubmit();
      console.log('Profile added successfully');
    } catch (error) {
      console.error('Error adding profile:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Profile ID:
        <input
          type="text"
          value={profile.profileId}
          onChange={(e) => setProfile({ ...profile, profileId: e.target.value })}
        />
      </label>
      <label>
        Profile Name:
        <input
          type="text"
          value={profile.profileName}
          onChange={(e) => setProfile({ ...profile, profileName: e.target.value })}
        />
      </label>
      <label>
        Storage Quota:
        <input
          type="text"
          value={profile.storageQuota}
          onChange={(e) => setProfile({ ...profile, storageQuota: e.target.value })}
        />
      </label>
      <button type="submit">Add Profile</button>
    </form>
  );
}

export default ProfileForm;