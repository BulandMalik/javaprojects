// src/components/UploadImage.js
import React, { useState } from 'react';

const UploadImage = ({ setResponseData }) => {
  const [frontImage, setFrontImage] = useState(null);
  const [backImage, setBackImage] = useState(null);
  const [frontImageUrl, setFrontImageUrl] = useState(null);
  const [backImageUrl, setBackImageUrl] = useState(null);

  // Handle front image upload
  const handleFrontImageChange = (event) => {
    setFrontImage(event.target.files[0]);
  };

  // Handle back image upload
  const handleBackImageChange = (event) => {
    setBackImage(event.target.files[0]);
  };

  // Function to handle the form submission (API call)
  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!frontImage || !backImage) {
      alert('Please select both the front and back images of the insurance card!');
      return;
    }

    setResponseData(null);
    const formData = new FormData();
    formData.append('frontImage', frontImage);
    formData.append('backImage', backImage);
    
    const frontImageUrl = URL.createObjectURL(frontImage); // Convert to URL
    setFrontImageUrl(frontImageUrl);    
    const backImageUrl = URL.createObjectURL(backImage); // Convert to URL
    setBackImageUrl(backImageUrl);    


    try {
      // Assuming your backend is running at http://localhost:5000/api/scan
      const response = await fetch('http://localhost:8080/api/ocr/extractInsuranceInfo', {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) {
        throw new Error('Failed to scan the images');
      }

      const result = await response.json();
      console.log(result);
      setResponseData(result);  // Pass the response to the parent component
    } catch (error) {
      console.error('Error scanning images:', error);
    }
  };

  return (
    <div>
      <h2>Upload Front & Back Insurance Card for Scanning</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Front Image: </label>
          <input type="file" accept="image/*" onChange={handleFrontImageChange} />
        </div>
        <div>
          <label>Back Image: </label>
          <input type="file" accept="image/*" onChange={handleBackImageChange} />
        </div>
        <button type="submit">Upload & Scan</button>
      </form>

      {/* Display thumbnails */}
      <div >
          {frontImageUrl && (
              <div>
                <h4>Front Image</h4>
                <img
                    src={frontImageUrl}
                    alt="Front Insurance Card"
                    style={{ width: '350px', height: '200px'}} // Small thumbnail size
                />
              </div>
          )}

          {backImageUrl && (
              <div>
                  <h4>Back Image</h4>
                  <img
                      src={backImageUrl}
                      alt="Back Insurance Card"
                      style={{ width: '350px', height: '200px' }} // Small thumbnail size
                  />
              </div>
          )}
      </div>

    </div>
  );
};

export default UploadImage;

