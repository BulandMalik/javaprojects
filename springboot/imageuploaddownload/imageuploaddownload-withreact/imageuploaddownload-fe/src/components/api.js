const API_BASE_URL = 'http://localhost:8080';

// Function to get all profiles
/* export const getProfiles = async () => {
    const response = await fetch(`${API_BASE_URL}/profiles`,{
      method: 'GET',
      mode: 'cors',
      credentials: 'include', // Include credentials in the request
      headers: {
        'Content-Type': 'application/json',
        // Add any other headers as needed
      },
    }); */

export const getProfiles = async () => {
  const response = await fetch(`${API_BASE_URL}/profiles`);
  if (!response.ok) {
    throw new Error('Error fetching profiles');
  }
  return response.json();
};

// Function to create a new profile
export const addProfile = async (profileData) => {
  const response = await fetch(`${API_BASE_URL}/profiles`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(profileData),
  });
  if (!response.ok) {
    throw new Error('Error adding profile');
  }
};

// Function to upload a document for a given profile
export const uploadDocument = async (profileId, documentData) => {
  const formData = new FormData();
  formData.append('file', documentData.file);
  formData.append('documentCategory',documentData.category);

  console.log("documentData",documentData);
  console.log("file",documentData.file);
  const response = await fetch(`${API_BASE_URL}/profiles/${profileId}/upload`, {
    method: 'POST',
    body: formData,
  });

  if (!response.ok) {
    throw new Error('Error uploading document');
  }
};

// Function to download a file for a given profile and object key
export const downloadDocument = async (profileId, objectKey) => {
  const response = await fetch(`${API_BASE_URL}/profiles/${profileId}/download/${objectKey}`);

  console.log("download response status:"+response.status);
/*  if (!response.ok) {
    throw new Error('Error downloading document');
  }


    console.log(response)
    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', `${objectKey}`);
    document.body.appendChild(link);
    link.click();
*/
    window.open(response.url, '_blank');    
  // Assuming the response is a redirect (HTTP 302)
  if (response.status === 302) {
    console.log("download response url:"+response.status+"____"+response.url);
    const redirectUrl = response.headers.get('Location');
    console.log('Redirected URL:', redirectUrl);
    // The browser will handle the redirect automatically
    // Get the redirect URL from the response

    // Open a new browser tab with the redirect URL
    window.open(redirectUrl, '_blank');
  }
};