import React, { useState } from 'react';
import InsurenceCardForm from './components/InsurenceCardForm';
import InsuranceData from './components/InsuranceData';

const App = () => {
  const [responseData, setResponseData] = useState(null);

  return (
    <div>
      <h1>Insurance Card OCR Scanner (Front & Back)</h1>
      <InsurenceCardForm setResponseData={setResponseData} />
      <InsuranceData responseData={responseData} />
    </div>
  );
};

export default App;