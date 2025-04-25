document.getElementById("invoiceForm").addEventListener("submit", function (e) {
    e.preventDefault();
  
    const invoiceData = {
      customerName: document.getElementById("customerName").value,
      invoiceDate: document.getElementById("invoiceDate").value,
      totalAmount: parseFloat(document.getElementById("totalAmount").value),
      status: document.getElementById("status").value
    };
  
    fetch("http://localhost:8080/api/invoices", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(invoiceData)
    })
      .then(response => response.json())
      .then(data => {
        document.getElementById("result").innerText = `✅ Invoice Created! ID: ${data.id}`;
        document.getElementById("invoiceForm").reset();
      })
      .catch(error => {
        console.error("Error:", error);
        document.getElementById("result").innerText = "❌ Failed to create invoice.";
      });
  });
  