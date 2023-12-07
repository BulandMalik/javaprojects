import React, { useState, useEffect } from 'react';
import ProfileForm from './components/ProfileForm';
import DocumentUploadForm from './components/DocumentUploadForm';
import ProfileList from './components/ProfileList';
import { getProfiles, downloadDocument } from './components/api';

function App() {
  const [profiles, setProfiles] = useState([]);

  useEffect(() => {
    fetchProfiles();
  }, []);

  const fetchProfiles = async () => {
    try {
      const profilesData = await getProfiles();
      setProfiles(profilesData);
    } catch (error) {
      console.error('Error fetching profiles:', error);
    }
  };

  const handleDocumentClick = async (documentId,objectKey) => {
    try {
      console.log("documentId to download:"+documentId+" with objectKey="+objectKey);
      const response = await downloadDocument(documentId, objectKey);
      console.log(response);
    } catch (error) {
      console.error('Error downloading document:', error);
    }
  };

  const handleDocumentUpload = () => {
    // You can perform additional actions after a document is uploaded
    // For example, you might want to refresh the profile list
    fetchProfiles();
  };

  return (<>
      <h1>Profile Management System</h1>
    <div>
      <ProfileForm onProfileSubmit={fetchProfiles} />
      <br/><br/>
    </div>
    <div>
      <DocumentUploadForm profileId="1" onDocumentUpload={handleDocumentUpload} />
      <br/><br/>
    </div>
    <div>
      <ProfileList profiles={profiles} onDocumentClick={handleDocumentClick} />
    </div>
  </>
  );
}

export default App;