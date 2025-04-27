let currentInvoiceId = null;

function fetchInvoice() {
  const invoiceId = document.getElementById("invoiceId").value;
  fetch(`http://localhost:8080/api/invoices/${invoiceId}`)
    .then(response => {
      if (!response.ok) throw new Error("Invoice not found");
      return response.json();
    })
    .then(invoice => {
      currentInvoiceId = invoice.id;

      // Populate all fields
      document.getElementById("editForm").style.display = "block";
      document.getElementById("customerName").value = invoice.customerName;
      document.getElementById("customerAddress").value = invoice.customerAddress;
      document.getElementById("mobile").value = invoice.mobile;
      document.getElementById("gstin").value = invoice.gstin;
      document.getElementById("invoiceNo").value = invoice.invoiceNo;
      document.getElementById("invoiceDate").value = invoice.invoiceDate;
      document.getElementById("transport").value = invoice.transport;
      document.getElementById("lrNo").value = invoice.lrNo;
      document.getElementById("baleNo").value = invoice.baleNo;
      document.getElementById("destination").value = invoice.destination;
      document.getElementById("bankName").value = invoice.bankName;
      document.getElementById("accountNo").value = invoice.accountNo;
      document.getElementById("ifscCode").value = invoice.ifscCode;
      document.getElementById("taxableAmount").value = invoice.taxableAmount;
      document.getElementById("cgstAmount").value = invoice.cgstAmount;
      document.getElementById("sgstAmount").value = invoice.sgstAmount;
      document.getElementById("igstAmount").value = invoice.igstAmount;
      document.getElementById("totalTax").value = invoice.totalTax;
      document.getElementById("roundOff").value = invoice.roundOff;
      document.getElementById("invoiceTotal").value = invoice.invoiceTotal;
      document.getElementById("invoiceTotalInWords").value = invoice.invoiceTotalInWords;
      document.getElementById("status").value = invoice.status;

      // Clear & populate items
      const itemsDiv = document.getElementById("items");
      itemsDiv.innerHTML = '';
      invoice.items.forEach(item => addItem(item));
    })
    .catch(error => {
      document.getElementById("result").innerText = "❌ " + error.message;
      document.getElementById("editForm").style.display = "none";
    });
}

// Add a new item row (with data if editing)
function addItem(data = {}) {
  const itemsDiv = document.getElementById("items");

  const container = document.createElement("div");
  container.className = "item-group";
  container.innerHTML = `
    <input placeholder="Particulars" value="${data.particulars || ''}"/>
    <input placeholder="HSN Code" value="${data.hsnCode || ''}"/>
    <input placeholder="Challan No" value="${data.challanNo || ''}"/>
    <input placeholder="Unit" value="${data.unit || ''}"/>
    <input placeholder="Rate" type="number" value="${data.rate || 0}"/>
    <input placeholder="Qty" type="number" value="${data.quantity || 0}"/>
    <input placeholder="Amount" type="number" value="${data.amount || 0}"/>
    <button type="button" onclick="this.parentElement.remove()">❌</button>
    <br><br>
  `;
  itemsDiv.appendChild(container);
}

// Handle Submit
document.getElementById("editForm").addEventListener("submit", function (e) {
  e.preventDefault();

  const items = Array.from(document.querySelectorAll(".item-group")).map(group => {
    const inputs = group.querySelectorAll("input");
    return {
      particulars: inputs[0].value,
      hsnCode: inputs[1].value,
      challanNo: inputs[2].value,
      unit: inputs[3].value,
      rate: parseFloat(inputs[4].value),
      quantity: parseInt(inputs[5].value),
      amount: parseFloat(inputs[6].value)
    };
  });

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
    items: items
  };

  fetch(`http://localhost:8080/api/invoices/${currentInvoiceId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(invoiceData)
  })
    .then(response => {
      if (!response.ok) throw new Error("Failed to update invoice");
      return response.json();
    })
    .then(data => {
      document.getElementById("result").innerText = "✅ Invoice updated!";
    })
    .catch(error => {
      document.getElementById("result").innerText = "❌ " + error.message;
    });
});
