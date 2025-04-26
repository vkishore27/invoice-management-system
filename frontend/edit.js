function fetchInvoice() {
    const invoiceId = document.getElementById("invoiceId").value;
  
    fetch(`http://localhost:8080/api/invoices/${invoiceId}`)
      .then(response => {
        if (!response.ok) {
          throw new Error("Invoice not found");
        }
        return response.json();
      })
      .then(data => {
        document.getElementById("editForm").style.display = "block";
        document.getElementById("customerName").value = data.customerName;
        document.getElementById("invoiceDate").value = data.invoiceDate;
        document.getElementById("totalAmount").value = data.totalAmount;
        document.getElementById("status").value = data.status;
  
        // Save ID to use on update
        document.getElementById("editForm").setAttribute("data-id", invoiceId);
        document.getElementById("result").innerText = "";
      })
      .catch(error => {
        document.getElementById("editForm").style.display = "none";
        document.getElementById("result").innerText = "❌ " + error.message;
      });
  }
  
  document.getElementById("editForm").addEventListener("submit", function (e) {
    e.preventDefault();
  
    const invoiceId = e.target.getAttribute("data-id");
  
    const updatedInvoice = {
      customerName: document.getElementById("customerName").value,
      invoiceDate: document.getElementById("invoiceDate").value,
      totalAmount: parseFloat(document.getElementById("totalAmount").value),
      status: document.getElementById("status").value
    };
  
    fetch(`http://localhost:8080/api/invoices/${invoiceId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(updatedInvoice)
    })
      .then(response => {
        if (!response.ok) {
          throw new Error("Failed to update invoice");
        }
        return response.json();
      })
      .then(data => {
        document.getElementById("result").innerText = "✅ Invoice updated successfully!";
      })
      .catch(error => {
        document.getElementById("result").innerText = "❌ " + error.message;
      });
  });
  