import React, { useState } from 'react';

function PaymentForm() {
    const [tenantId, setTenantId] = useState('');
    const [amount, setAmount] = useState('');
    const [paymentNonce, setPaymentNonce] = useState('');
    const [paymentResult, setPaymentResult] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const requestOptions = {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    tenantId,
                    amount,
                    paymentNonce,
                }),
            };

            const response = await fetch('/api/payment/process-payment', requestOptions);
            console.log("response:",response)
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            const data = await response.json();
            setPaymentResult(data.message);
        } catch (error) {
            console.log("error:",error)
            console.error('There was a problem with the fetch operation:', error);
            setPaymentResult('Payment failed');
        }
    };

    return (
        <div>
            <h2>Payment Form</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Tenant ID"
                    value={tenantId}
                    onChange={(e) => setTenantId(e.target.value)}
                    required
                />
                <input
                    type="number"
                    placeholder="Amount"
                    value={amount}
                    onChange={(e) => setAmount(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Payment Nonce"
                    value={paymentNonce}
                    onChange={(e) => setPaymentNonce(e.target.value)}
                    required
                />
                <button type="submit">Pay</button>
            </form>
            {paymentResult && <p>{paymentResult}</p>}
        </div>
    );
}

export default PaymentForm;