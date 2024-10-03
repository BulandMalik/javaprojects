// src/components/InsuranceData.js
import React from 'react';

const InsuranceData = ({ responseData }) => {
  if (!responseData) {
    return <div>No data available. Please upload and scan images.</div>;
  }

  return (
    <div>
        <h3>Insurance Card Details</h3>
        <table border="1" cellPadding="10">
            <thead>
                <tr>
                    <th>Field</th>
                    <th>Value</th>
                </tr>
            </thead>
            <tbody>
                {Object.entries(responseData).map(([key, value]) => (
                    <tr key={key}>
                        <td>{key.replace(/_/g, ' ')}</td> {/* Replace underscores with spaces */}
                        <td>{value}</td>
                    </tr>
                ))}
            </tbody>
        </table>
    </div>
  );
};

export default InsuranceData;