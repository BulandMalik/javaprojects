import React, { useState } from 'react';
import InsuranceDataForm from './InsurenceCardForm';

function InsuranceCardForm() {
  const [frontImage, setFrontImage] = useState(null);
  const [backImage, setBackImage] = useState(null);
  const [insuranceData, setInsuranceData] = useState(null);

  const handleFileChange = (e, side) => {
    const file = e.target.files[0];
    if (side === 'front') {
      setFrontImage(file);
    } else {
      setBackImage(file);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!frontImage || !backImage) {
      alert('Please upload both front and back of the insurance card.');
      return;
    }

    const formData = new FormData();
    formData.append('frontImage', frontImage);
    formData.append('backImage', backImage);

    // Call backend to process the images
    try {
      const response = await fetch('/api/ocr/extractInsuranceInfo', {
        method: 'POST',
        body: formData,
      });
      const data = await response.json();
      setInsuranceData(data);
    } catch (error) {
      console.error('Error processing insurance card:', error);
    }
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Upload Front Image:</label>
          <input type="file" accept="image/*" onChange={(e) => handleFileChange(e, 'front')} required />
        </div>
        <div>
          <label>Upload Back Image:</label>
          <input type="file" accept="image/*" onChange={(e) => handleFileChange(e, 'back')} required />
        </div>
        <button type="submit">Process Insurance Card</button>
      </form>

      {insuranceData && (
        <div>
          <h3>Extracted Insurance Information</h3>
          <pre>{JSON.stringify(insuranceData, null, 2)}</pre>
        </div>
      )}
    </div>
  );
}

export default InsuranceCardForm;
