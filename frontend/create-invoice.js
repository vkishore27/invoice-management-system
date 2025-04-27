// Add a new item row to the item table
function addItem(data = {}) {
  const tableBody = document.getElementById("itemTableBody");

  const row = document.createElement("tr");
  row.innerHTML = `
    <td><input type="text" value="${data.particulars || ''}" placeholder="Particulars" /></td>
    <td><input type="text" value="${data.hsnCode || ''}" placeholder="HSN Code" /></td>
    <td><input type="text" value="${data.challanNo || ''}" placeholder="Challan No" /></td>
    <td><input type="text" value="${data.unit || ''}" placeholder="Unit" /></td>
    <td><input type="number" value="${data.rate || 0}" placeholder="Rate" step="0.01"/></td>
    <td><input type="number" value="${data.quantity || 0}" placeholder="Qty" step="1"/></td>
    <td><input type="number" value="${data.amount || 0}" placeholder="Amount" step="0.01"/></td>
    <td><button type="button" onclick="this.parentElement.parentElement.remove()">❌</button></td>
  `;
  tableBody.appendChild(row);
}

// Handle Form Submit
document.getElementById("invoiceForm").addEventListener("submit", function (e) {
  e.preventDefault();

  // Capture all invoice fields
  const invoiceData = {
    customerName: document.getElementById("customerName").value,
    customerAddress: document.getElementById("customerAddress").value,
    mobile: document.getElementById("mobile").value,
    gstin: document.getElementById("gstin").value,
    invoiceNo: document.getElementById("invoiceNo").value,
    invoiceDate: document.getElementById("invoiceDate").value,
    transport: document.getElementById("transport").value,
    lrNo: document.getElementById("lrNo").value,
    baleNo: document.getElementById("baleNo").value,
    destination: document.getElementById("destination").value,
    bankName: document.getElementById("bankName").value,
    accountNo: document.getElementById("accountNo").value,
    ifscCode: document.getElementById("ifscCode").value,
    taxableAmount: parseFloat(document.getElementById("taxableAmount").value),
    cgstAmount: parseFloat(document.getElementById("cgstAmount").value),
    sgstAmount: parseFloat(document.getElementById("sgstAmount").value),
    igstAmount: parseFloat(document.getElementById("igstAmount").value),
    totalTax: parseFloat(document.getElementById("totalTax").value),
    roundOff: parseFloat(document.getElementById("roundOff").value),
    invoiceTotal: parseFloat(document.getElementById("invoiceTotal").value),
    invoiceTotalInWords: document.getElementById("invoiceTotalInWords").value,
    status: document.getElementById("status").value,
    items: []
  };

  // Capture all line items
  const rows = document.querySelectorAll("#itemTableBody tr");
  rows.forEach(row => {
    const inputs = row.querySelectorAll("input");
    const item = {
      particulars: inputs[0].value,
      hsnCode: inputs[1].value,
      challanNo: inputs[2].value,
      unit: inputs[3].value,
      rate: parseFloat(inputs[4].value),
      quantity: parseInt(inputs[5].value),
      amount: parseFloat(inputs[6].value)
    };
    invoiceData.items.push(item);
  });

  // Send POST request to backend
  fetch("http://localhost:8080/api/invoices", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(invoiceData)
  })
    .then(response => {
      if (!response.ok) throw new Error("Failed to create invoice");
      return response.json();
    })
    .then(data => {
      document.getElementById("result").innerText = "✅ Invoice created successfully! ID: " + data.id;
      document.getElementById("invoiceForm").reset();
      document.getElementById("itemTableBody").innerHTML = ""; // Clear items
    })
    .catch(error => {
      document.getElementById("result").innerText = "❌ " + error.message;
    });
});
