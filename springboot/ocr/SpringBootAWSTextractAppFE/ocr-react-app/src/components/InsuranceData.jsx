// src/components/DisplayResult.js
import React from 'react';

const DisplayResult = ({ responseData }) => {
  if (!responseData) {
    return <div>No data available. Please upload and scan images.</div>;
  }

  return (
    <div>
      <h2>Scanned Insurance Card Data (Front & Back)</h2>
      <pre>{JSON.stringify(responseData, null, 2)}</pre>
    </div>
  );
};

export default DisplayResult;