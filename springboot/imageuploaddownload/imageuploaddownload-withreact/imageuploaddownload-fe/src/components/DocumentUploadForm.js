import React, { useState } from 'react';
import { uploadDocument } from './api'; // Assume you have an API function for uploading documents

function DocumentUploadForm({ onDocumentUpload }) {
  const [documentData, setDocumentData] = useState({ file: null, category: '' });

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
        console.log("uploading doc for profile id=",e.target[0].value);
      await uploadDocument(e.target[0].value, documentData); // API function to upload a document
      onDocumentUpload();
      console.log('Document uploaded successfully');
    } catch (error) {
      console.error('Error uploading document:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Profile ID:
        <input
          type="text"
          id="profileId"
        />
      </label>
      <label>
        File:
        <input
          type="file"
          onChange={(e) => setDocumentData({ ...documentData, file: e.target.files[0] })}
        />
      </label>
      <label>
        Document Category:
        <select
          value={documentData.category}
          onChange={(e) => setDocumentData({ ...documentData, category: e.target.value })}
        >
          <option value="">Select Category</option>
          <option value="lab_report">Lab Report</option>
          <option value="consent_form">Consent Form</option>
          <option value="imaging_form">Imaging Result</option>
          <option value="clisinal_notes">Clinical Notes</option>
          {/* Add more categories as needed */}
        </select>
      </label>
      <button type="submit">Upload Document</button>
    </form>
  );
}

export default DocumentUploadForm;