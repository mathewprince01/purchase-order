//
//

function pop() {
    var element = document.getElementById('pop');
    
      element.style.display = "block";
    const orderIdInput = document.getElementById('orderId');
    orderIdInput.disabled = false;
    
  }

  function off() {
    var element = document.getElementById('pop');
    
      element.style.display = "none";
    
    document.getElementById('orderForm').reset();
    document.getElementById('orderline').reset();
    const table = document.getElementById('orderLineTable');
    table.innerHTML = ''; // Clear order line items table
    itemCount = 0; 
    clearErrorMessagesMainForm();
   
  }

document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('input, select, textarea').forEach(input => {
        input.addEventListener('focus', function() {
            // Handle keydown for text inputs and textareas
            if (input.tagName.toLowerCase() === 'input' || input.tagName.toLowerCase() === 'textarea') {
                input.addEventListener('keydown', function() {
                    this.style.borderColor = 'black';
                    document.getElementById('error-message-orderId').textContent = '';
                }, { once: true });
            }
            if (input.tagName.toLowerCase() === 'select' || input.type === 'date') {
                input.addEventListener('change', function() {
                    this.style.borderColor = 'black';
                   
                }, { once: true });
            }
        });
    });
});
//
//
let itemCount = 0;
const productUOMMapping = {
    'Egg': 'each',
    'Milk': 'litre',
    'biscuit': 'each',
    'water': 'litre',
    'oil': 'litre',
    'rice': 'kg'
    // Add more mappings as needed
};
// select onchange update uom change
function changes() {
    updateUOM();
    clearErrorMessages(); // Clear error messages when changing product
}

function updateUOM() {
    const productName = document.getElementById('productName').value;
    const uomField = document.getElementById('uom');
    uomField.value = productUOMMapping[productName] || '';
}

let currentIndex=0;
let editingRow = "";
function editItem(row) {
    event.preventDefault();
    editingRow = row;
    currentIndex=row.rowIndex;
    const cells = row.getElementsByTagName('td');
    document.getElementById('productName').value = cells[1].textContent;
    document.getElementById('quantity').value = cells[2].textContent;
    document.getElementById('uom').value = cells[3].textContent;
    document.getElementById('unitprice').value = cells[4].textContent;

    document.getElementById('addButton').textContent = 'Update';
    document.getElementById('cancelButton').textContent = 'Cancel';
    document.getElementById('line-item-title').textContent = 'Update Line Items';
   
}
function addItem() {
    let isvalid = true;
    const productName = document.getElementById('productName').value;
    const uom = document.getElementById('uom').value;
    const quantity = document.getElementById('quantity').value;
    const unitprice = document.getElementById('unitprice').value;
    const totalPrice = quantity * unitprice;

    clearErrorMessages(); // Clear error messages before checking conditions

    if (editingRow) {
        updateRow(editingRow, productName, uom, quantity, unitprice, totalPrice);
    
        return true;
    }

    if (productName === "") {
        document.getElementById('error-message-productName').textContent = '';
        document.getElementById('productName').style.borderColor = 'red';
        isvalid = false;
    }
    if (isNaN(quantity) || quantity <= 0) {
        document.getElementById('quantity').setAttribute('placeholder',"positive number.");
        document.getElementById('quantity').style.borderColor = 'red';
        isvalid = false;
    }
    if (isNaN(unitprice) || unitprice <= 0) {
        document.getElementById('unitprice').setAttribute('Placeholder', "price can't be 0 or -ve");
        document.getElementById('unitprice').style.borderColor = 'red';
        isvalid = false;
    }
        
    if (isProductInTable(productName)) {
        document.getElementById('error-message-productName').textContent = 'Product already added.';
        document.getElementById('productName').style.borderColor = 'red';
        isvalid = false;
    }
    if (!isvalid) {
        return false; 
    }
    itemCount++;

    const table = document.getElementById('orderLineTable');
    const row = table.insertRow();

    row.setAttribute('data-item-id', itemCount);
    row.insertCell(0).textContent = itemCount;
    row.insertCell(1).textContent = productName;
    row.insertCell(2).textContent = quantity;
    row.insertCell(3).textContent = uom;
    row.insertCell(4).textContent = unitprice;
    row.insertCell(5).textContent = totalPrice;

    // Create the new cell for the images
    var actionCell = row.insertCell(6);

    // Create the edit image element
    var editImg = document.createElement('img');
    editImg.src = 'edit.png';
    editImg.alt = 'edit';
    editImg.width = 30;
    editImg.height = 30;
    editImg.onclick = function() { editItem(this.parentNode.parentNode);};

    // Create the delete image element
    var deleteImg = document.createElement('img');
    deleteImg.src = 'trash.png';
    deleteImg.alt = 'delete';
    deleteImg.width = 30;
    deleteImg.height = 30;
    deleteImg.onclick = function() { deleteItem(this.parentNode.parentNode); };

    // Append the images to the action cell
    actionCell.appendChild(editImg);
    actionCell.appendChild(deleteImg);
    resetOrderLineInputs();
    return true;
 }  

function isProductInTable(productName) {
    const table = document.getElementById('orderLineTable');
    const rows = table.getElementsByTagName('tr');
    for (let i = 0; i < rows.length; i++) {
        const cell = rows[i].getElementsByTagName('td')[1];
        if (cell && cell.textContent === productName) {
            return true;
        }
    }
    return false;
}

function isProductUpdateInTable(productName) {
    const table = document.getElementById('orderLineTable');
    const rows = table.getElementsByTagName('tr');
    for (let i = 0; i < rows.length; i++) {
        const cell = rows[i].getElementsByTagName('td')[1];
        
        if(i+1 === currentIndex){
            continue;
            
        };
            
        if (cell && (cell.textContent.trim() === productName)) {
            return true;
        }
    }
    return false;
}
//update line

function updateRow(editingRow, productName, uom, quantity, unitprice, totalPrice) {
    
    const cells = editingRow.getElementsByTagName('td');
    
    // Check if product is already in table, except for the current row
    if (isProductUpdateInTable(productName)) {
        document.getElementById('error-message-productName').textContent = 'Product already added.';
        document.getElementById('productName').style.borderColor = 'red';
        return false;
    }

    // Validate quantity and unitprice
    if (quantity === "" || isNaN(quantity) || quantity <= 0) {
        document.getElementById('quantity').setAttribute('placeholder', 'Enter a valid quantity.');
        document.getElementById('quantity').style.borderColor = 'red';
        return false;
    }

    if (unitprice === "" || isNaN(unitprice) || unitprice <= 0) {
        document.getElementById('unitprice').setAttribute('placeholder', 'Enter a valid unit price.');
        document.getElementById('unitprice').style.borderColor = 'red';
        return false;
    }

    // Update the table row
    cells[1].textContent = productName;
    cells[2].textContent = quantity;
    cells[3].textContent = uom;
    cells[4].textContent = unitprice;
    cells[5].textContent = totalPrice; 

    resetOrderLineInputs(); // Reset the input fields
    
    return true;
}
function resetOrderLineInputs() {
    document.getElementById('productName').value = '';
    document.getElementById('uom').value = '';
    document.getElementById('quantity').value = '';
    document.getElementById('unitprice').value = '';
    document.getElementById('addButton').textContent = 'Add';
    document.getElementById('cancelButton').textContent = 'Clear';
    document.getElementById('line-item-title').textContent = 'Add Line Items';
    clearErrorMessages();
    editingRow = null;
}



function deleteItem(row) {
    if (confirm('Are you sure to delete this Product-' +row.childNodes[1].innerHTML)) {
        const table = document.getElementById('orderLineTable');
        table.deleteRow(row.rowIndex - 1);
        itemCount--;
        updateRowNumbers();
    }
}

function cancelItem(row){
    document.getElementById('productName').value = '';
    document.getElementById('uom').value = '';
    document.getElementById('quantity').value = '';
    document.getElementById('unitprice').value = '';
    editingRow = null;
    document.getElementById('addButton').textContent = 'Add';
    clearErrorMessages();
}
    
function updateRowNumbers() {
        const table = document.getElementById('orderLineTable');
        const rows = table.getElementsByTagName('tr');
        for (let i = 0; i < rows.length; i++) {
            const cell = rows[i].getElementsByTagName('td')[0];
            cell.textContent = i + 1;
            }
}

function clearErrorMessages() {
    document.getElementById('error-message-productName').textContent = '';
    document.getElementById('error-message-quantity').textContent = '';
    document.getElementById('error-message-unitprice').textContent = '';

    document.getElementById('productName').style.borderColor = 'black';
    document.getElementById('quantity').style.borderColor = 'black';
    document.getElementById('unitprice').style.borderColor = 'black';
}
function clearErrorMessagesMainForm() {
    document.getElementById('error-message-orderId').textContent = '';
    document.getElementById('error-message-orderDate').textContent = '';
    document.getElementById('error-message-vendorName').textContent = '';
    document.getElementById('error-message-expectedDeliveryDate').textContent = '';
    document.getElementById('error-message-shippingAddress').textContent = '';

    document.getElementById('orderId').style.borderColor = 'black';
    document.getElementById('orderDate').style.borderColor = 'black';
    document.getElementById('vendorName').style.borderColor = 'black';
    document.getElementById('expectedDeliveryDate').style.borderColor = 'black';
    document.getElementById('shippingAddress').style.borderColor = 'black';
    clearErrorMessages();
}


const orderList = [];
function handleSubmit() {
    if (!validateMainForm()) {
        return false;
    }

    // Get form data
    const orderId = document.getElementById('orderId').value;
    const orderDate = document.getElementById('orderDate').value;
    const vendorName = document.getElementById('vendorName').value;
    const expectedDeliveryDate = document.getElementById('expectedDeliveryDate').value;
    const shippingAddress = document.getElementById('shippingAddress').value;

    // Get product list data
    const productList = [];
    const table = document.getElementById('orderLineTable');
    const rows = table.getElementsByTagName('tr');
    for (let i = 0; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        if (cells.length > 0) {
            const ind = cells[0].innerText;
            const product = cells[1].innerText;
            const quantity = cells[2].innerText;
            const uom = cells[3].innerText;
            const unitprice = cells[4].innerText;
            const totalPrice = cells[5].innerText;
            productList.push({ind, product, quantity, uom, unitprice, totalPrice});
        }
    }
    const order = {
        orderId,
        orderDate,
        vendorName,
        expectedDeliveryDate,
        shippingAddress,
        productList
    };
    
    if (editingMasterRow) {
        // Update the existing order
        const existingOrderIndex = orderList.findIndex(o => o.orderId === orderId);
        if (existingOrderIndex !== -1) {
            orderList[existingOrderIndex] = order;

            // Update the master table row
            const cells = editingMasterRow.getElementsByTagName('td');
            cells[1].textContent = orderId;
            cells[2].textContent = vendorName;
            cells[3].textContent = orderDate;
            cells[4].textContent = expectedDeliveryDate;
            cells[5].textContent = shippingAddress;
            cells[6].textContent = calculateTotalAmount(productList);

            // Reset form fields and button text
            document.getElementById('orderForm').reset();
            document.getElementById('orderline').reset();
            document.getElementById('submitButton').textContent = 'Submit';
            document.getElementById('addButton').textContent = 'Add';
            document.getElementById('cancelButton').textContent = 'Clear';
            document.getElementById('line-item-title').textContent = 'Add Line Items';

            // Clear the editingMasterRow variable
            editingMasterRow = null;
            resetForm();
            resetOrderLineInputs(); // Reset the input fields for the order line items   
            return true;
            
        }
    } else {
        // Push the order object to the orderList array
        orderList.push(order);

        // Add data to master table
        const masterTableBody = document.getElementById('masterTableBody');
        const newRow = document.createElement('tr');
        newRow.innerHTML = `
            <td>${masterTableBody.children.length + 1}</td>
            <td>${orderId}</td>
            <td>${vendorName}</td>
            <td>${orderDate}</td>
            <td>${expectedDeliveryDate}</td>
            <td>${shippingAddress}</td>
            <td>${calculateTotalAmount(productList)}</td>
            <td>
                <img src="edit.png" onclick="editMasterTableRow(this)" class="editbutton">
                <img src="delete.png" onclick="deleteMasterTableRow(this)" class="editbutton">
                <img src="view.png" onclick="viewMasterTableRow(this)" class="editbutton">
            </td>
        `;
        masterTableBody.appendChild(newRow);

        // Reset form and order line items
        resetForm();
        
        clearErrorMessagesMainForm();
        return true;
    }
}

// new code

function viewOrderDetails(index) {
    const order = orderList[index];
    document.getElementById('orderIdDetail').innerText = order.orderId;
    document.getElementById('orderDateDetail').innerText = order.orderDate;
    document.getElementById('vendorNameDetail').innerText = order.vendorName;
    document.getElementById('expectedDeliveryDateDetail').innerText = order.expectedDeliveryDate;
    document.getElementById('shippingAddressDetail').innerText = order.shippingAddress;

    const detailTableBody = document.getElementById('detailTableBody');
    detailTableBody.innerHTML = '';
    order.productList.forEach((product, index) => {
        const newRow = document.createElement('tr');
        newRow.innerHTML = `
            <td>${index + 1}</td>
            <td>${product.product}</td>
            <td>${product.quantity}</td>
            <td>${product.uom}</td>
            <td>${product.unitprice}</td>
            <td>${product.totalPrice}</td>
        `;
        detailTableBody.appendChild(newRow);
    });

    $('#viewDetailsModal').modal('show');
}

function deleteOrder(index) {
    if (confirm('Are you sure you want to delete this order?')) {
        orderList.splice(index, 1);
        displayOrders();
    }
}

function displayOrders() {
    const masterTableBody = document.getElementById('masterTableBody');
    masterTableBody.innerHTML = '';
    orderList.forEach((order, index) => {
        const newRow = document.createElement('tr');
        newRow.innerHTML = `
            <td>${index + 1}</td>
            <td>${order.orderId}</td>
            <td>${order.vendorName}</td>
            <td>${order.orderDate}</td>
            <td>${order.expectedDeliveryDate}</td>
            <td>${order.shippingAddress}</td>
            <td>${order.productList.length}</td>
            <td>
                <button class="btn btn-primary btn-sm" onclick="viewOrderDetails(${index})">View Details</button>
                <button class="btn btn-danger btn-sm" onclick="deleteOrder(${index})">Delete</button>
            </td>
        `;
        masterTableBody.appendChild(newRow);
    });
}
// Validate form data (if needed)
function validateMainForm() {
    let isValid = true;
    
    // Clear previous error messages
   // clearFormErrorMessages();

    const orderId = document.getElementById('orderId').value.trim();
    const orderDate = document.getElementById('orderDate').value.trim();
    const vendorName = document.getElementById('vendorName').value.trim();
    const expectedDeliveryDate = document.getElementById('expectedDeliveryDate').value.trim();
    const shippingAddress = document.getElementById('shippingAddress').value.trim();
    const productName = document.getElementById('productName').value.trim();
    const quantity = document.getElementById('quantity').value.trim();
    const unitprice = document.getElementById('unitprice').value.trim();
    let lineItemTable = document.getElementById('orderLineTable');

    if (orderId === "" ) {
        document.getElementById('error-message-orderId').textContent = 'Order ID already exists or is empty.';
        document.getElementById('orderId').style.borderColor = 'red';
        isValid = false;
    }
    if (orderDate === "") {
        document.getElementById('orderDate').style.borderColor = 'red';
        isValid = false;
    }
    if (vendorName === "") {
        document.getElementById('error-message-vendorName').textContent = '';
        document.getElementById('vendorName').style.borderColor = 'red';
        isValid = false;
    }
    if (expectedDeliveryDate === "") {
        document.getElementById('expectedDeliveryDate').style.borderColor = 'red';
        isValid = false;
    }
    if (shippingAddress === "") {
        document.getElementById('shippingAddress').setAttribute('placeholder'," Shipping Address is requried.");
        document.getElementById('shippingAddress').style.borderColor = 'red';
        isValid = false;
    }
    // if (lineItemTable.length === 0 || (lineItemTable.length === 1 && lineItemTable[0].getElementsByTagName('td').length === 0)) {
        if (productName === "") {
            document.getElementById('error-message-productName').textContent = '';
            document.getElementById('productName').style.borderColor = 'red';
            isvalid = false;
         }
        if (isNaN(quantity) || quantity <= 0) {
            document.getElementById('quantity').setAttribute('placeholder',"positive number.");
            document.getElementById('quantity').style.borderColor = 'red';
            isvalid = false;
        }
        if (isNaN(unitprice) || unitprice <= 0) {
            document.getElementById('unitprice').setAttribute('Placeholder', "price can't be 0 or -ve");
            document.getElementById('unitprice').style.borderColor = 'red';
            isvalid = false;
        }
    //}

    return isValid;
}
   
function orderIdExists() {
    const orderId = document.getElementById('orderId').value.trim();
    const table = document.getElementById('masterTableBody');
    for (let i = 0; i < table.rows.length; i++) {
        if (table.rows[i].cells[1].textContent === orderId) {
            document.getElementById('error-message-orderId').textContent = 'Order ID already exists or is empty';
            document.getElementById('orderId').style.borderColor = 'red';
            return true;
        }
    }
    return false;
}

// function formatProductList(productList) {
//     return productList.map(item => {
//         const totalPrice = item.quantity * item.unitprice;
//         return `${item.ind}, ${item.product}, ${item.quantity}, ${item.uom}, ${item.unitprice}, ${totalPrice}`;
//     }).join(', ');
// }


function calculateTotalAmount(productList) {
    return productList.reduce((total, item) => total + parseFloat(item.totalPrice), 0).toFixed(2);
}

function deleteMasterTableRow(button) {
    const row = button.parentNode.parentNode;
    row.parentNode.removeChild(row);
} 
document.addEventListener('DOMContentLoaded', (event) => {
    const orderDateInput = document.getElementById('orderDate');
    const expectedDeliveryDateInput = document.getElementById('expectedDeliveryDate');

    // Function to update the minimum value of expectedDeliveryDate
    function updateExpectedDeliveryDateMin() {
        expectedDeliveryDateInput.min = orderDateInput.value;
        // expectedDeliveryDateInput.value = orderDateInput.value; // Set to orderDate by default
    }

    // Add event listener to orderDate input
    orderDateInput.addEventListener('change', updateExpectedDeliveryDateMin);

    // Set the initial minimum value for expectedDeliveryDate
    updateExpectedDeliveryDateMin();
});

function resetForm() {
    clearErrorMessages();
    clearErrorMessagesMainForm();
    off();
    
    
    const table = document.getElementById('orderLineTable');
    table.innerHTML = ''; // Clear order line items table
    itemCount = 0;
}

function validateForm() {
    let Product = document.getElementById('productName');
    let qty = document.getElementById('quantity');
    let price = document.getElementById('unitprice');
    if (Product.value == '') {
        Product.style.borderColor = 'red';
 
 
    } 
    else {
        Product.style.borderColor = 'black';
 
    }
 
    if (qty.value == '') {
        qty.style.borderColor = 'red';
 
 
    } else {
        qty.style.borderColor = 'black';
 
    }
    if (price.value == '') {
        price.style.borderColor = 'red';
 
 
    } else {
        price.style.borderColor = 'black';
 
    }
}
//  



let editingMasterRow ="";

function editMasterTableRow(button) {
    pop();
    const orderIdInput = document.getElementById('orderId');
    orderIdInput.disabled = true;
    editingMasterRow = button.parentNode.parentNode;

    const cells = editingMasterRow.getElementsByTagName('td');

    // Populate main form fields
    const orderId = cells[1].textContent;
    document.getElementById('orderId').value = orderId;
    document.getElementById('orderDate').value = cells[3].textContent;
    document.getElementById('vendorName').value = cells[2].textContent;
    document.getElementById('expectedDeliveryDate').value = cells[4].textContent;
    document.getElementById('shippingAddress').value = cells[5].textContent;

    // Find the order in orderList by orderId
    const order = orderList.find(order => order.orderId === orderId);

    // Populate order line table
    const table = document.getElementById('orderLineTable');
    table.innerHTML = ''; // Clear existing rows

    if (order && order.productList) {
        order.productList.forEach((product, index) => {
            const row = table.insertRow();

            row.insertCell(0).textContent = index + 1;
            row.insertCell(1).textContent = product.product;
            row.insertCell(2).textContent = product.quantity;
            row.insertCell(3).textContent = product.uom;
            row.insertCell(4).textContent = product.unitprice;
            row.insertCell(5).textContent = product.totalPrice;

            const actionCell = row.insertCell(6);

            // Create the edit image element
            const editImg = document.createElement('img');
            editImg.src = 'edit.png';
            editImg.alt = 'edit';
            editImg.width = 30;
            editImg.height = 30;
            editImg.onclick = function() { editItem(this.parentNode.parentNode); };

            // Create the delete image element
            const deleteImg = document.createElement('img');
            deleteImg.src = 'trash.png';
            deleteImg.alt = 'delete';
            deleteImg.width = 30;
            deleteImg.height = 30;
            deleteImg.onclick = function() { deleteItem(this.parentNode.parentNode); };
            actionCell.appendChild(editImg);
            actionCell.appendChild(deleteImg);
        });
    }

    // Update the form title and submit button text
    document.getElementById('submitButton').textContent = 'Update';
}





function updateOrderList(row) {
    const cells = row.getElementsByTagName('td');
    const orderId = cells[1].textContent;

    // Find the order in the orderList by orderId
    const orderIndex = orderList.findIndex(order => order.orderId === orderId);

    if (orderIndex !== -1) {
        // Get updated product list data from the table
        const productList = [];
        const table = document.getElementById('orderLineTable');
        const rows = table.getElementsByTagName('tr');
        for (let i = 0; i < rows.length; i++) {
            const cells = rows[i].getElementsByTagName('td');
            if (cells.length > 0) {
                const ind = cells[0].innerText;
                const product = cells[1].innerText;
                const quantity = cells[2].innerText;
                const uom = cells[3].innerText;
                const unitprice = cells[4].innerText;
                const totalPrice = cells[5].innerText;
                productList.push({ ind, product, quantity, uom, unitprice, totalPrice });
            }
        }

        // Update the order in the orderList
        orderList[orderIndex].productList = productList;

        // Update the master table row
        cells[6].textContent = calculateTotalAmount(productList);
        clearErrorMessages();
    }
}

function updateRow(editingRow, productName, uom, quantity, unitprice, totalPrice) {
    const cells = editingRow.getElementsByTagName('td');

    // Validate quantity and unitprice
    if (quantity === "" || isNaN(quantity) || quantity <= 0) {
        document.getElementById('quantity').setAttribute('placeholder', 'Enter a valid quantity.');
        document.getElementById('quantity').style.borderColor = 'red';
        return false;
    }

    if (unitprice === "" || isNaN(unitprice) || unitprice <= 0) {
        document.getElementById('unitprice').setAttribute('placeholder', 'Enter a valid unit price.');
        document.getElementById('unitprice').style.borderColor = 'red';
        return false;
    }

    // Update the table row
    cells[1].textContent = productName;
    cells[2].textContent = quantity;
    cells[3].textContent = uom;
    cells[4].textContent = unitprice;
    cells[5].textContent = totalPrice;

    // Update the order list in the master table
    updateOrderList(editingMasterRow);

    resetOrderLineInputs(); // Reset the input fields

    return true;
}

function calculateTotalAmount(productList) {
    return productList.reduce((total, product) => total + parseFloat(product.totalPrice), 0).toFixed(2);
}

// function resetOrderLineInputs() {
//     document.getElementById('productName').value = '';
//     document.getElementById('uom').value = '';
//     document.getElementById('quantity').value = '';
//     document.getElementById('unitprice').value = '';
// }


function viewMasterTableRow(button) {
    const row = button.parentNode.parentNode;
    const cells = row.getElementsByTagName('td');

    const orderId = cells[1].textContent;
    const vendorName = cells[2].textContent;
    const orderDate = cells[3].textContent;
    const expectedDeliveryDate = cells[4].textContent;
    const shippingAddress = cells[5].textContent;

    document.getElementById('oiid').textContent = orderId;
    document.getElementById('viname').textContent = vendorName;
    document.getElementById('oidate').textContent = orderDate;
    document.getElementById('oiedd').textContent = expectedDeliveryDate;
    // document.getElementById('viaddress').textContent = shippingAddress;

    const order = orderList.find(order => order.orderId === orderId);

    const tbody = document.getElementById('check');
    tbody.innerHTML = '';

    if (order && order.productList) {
        order.productList.forEach((product, index) => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${product.product}</td>
                <td>${product.unitprice}</td>
                <td>${product.quantity}</td>
                <td>${product.totalPrice}</td>
            `;
            tbody.appendChild(row);
        });
    }

    document.getElementById('fullview').style.display = 'block';
}

function closeFullView() {
    document.getElementById('fullview').style.display = 'none';
}

// function onsubmitHandler() {
//     validateForm();
//     addItem();
    
// }

                     