let itemCount = 0;
let editingRow = null;
const productUOMMapping = {
    'egg': 'each',
    'milk': 'litre',
    'biscuit': 'each',
    'water': 'litre',
    'oil': 'litre',
    'rice': 'kg'

    // Add more mappings as needed
};

function updateUOM() {
    const productName = document.getElementById('productName').value;
    const uomField = document.getElementById('uom');
    uomField.value = productUOMMapping[productName] || '';
}


function addItem() {
    const productName = document.getElementById('productName').value;
    const uom = document.getElementById('uom').value;
    const quantity = document.getElementById('quantity').value;
    const unitprice = document.getElementById('unitprice').value;
    const totalPrice = quantity * unitprice;

    if (editingRow) {
        updateRow(editingRow, productName, uom, quantity, unitprice, totalPrice);
        resetForm();
        return;
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
    const actionCell = row.insertCell(6);

    const editButton = document.createElement('button');
    editButton.textContent = 'Edit';
    editButton.onclick = () => editItem(row);
    actionCell.appendChild(editButton);

    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Delete';
    deleteButton.onclick = () => deleteItem(row);
    actionCell.appendChild(deleteButton);

    resetOrderLineInputs();
}

function updateRow(row, productName, uom, quantity, unitprice, totalPrice) {
    const cells = row.getElementsByTagName('td');
    cells[1].textContent = productName;
    cells[2].textContent = quantity;
    cells[3].textContent = uom;
    cells[4].textContent = unitprice;
    cells[5].textContent = totalPrice;
}

function resetOrderLineInputs() {
    document.getElementById('productName').value = '';
    document.getElementById('uom').value = '';
    document.getElementById('quantity').value = '';
    document.getElementById('unitprice').value = '';
    editingRow = null;
    document.getElementById('addButton').textContent = 'Add';
}

function editItem(row) {
    event.preventDefault();
    const cells = row.getElementsByTagName('td');
    document.getElementById('productName').value = cells[1].textContent;
    document.getElementById('quantity').value = cells[2].textContent;
    document.getElementById('uom').value = cells[3].textContent;
    document.getElementById('unitprice').value = cells[4].textContent;

    document.getElementById('addButton').textContent = 'Update';
    editingRow = row;
}

function deleteItem(row) {
    if (confirm('Are you sure to delete this Product-' +row.childNodes[1].innerHTML)) {
        const table = document.getElementById('orderLineTable');
        table.deleteRow(row.rowIndex - 1);
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
    }
    
    function updateRowNumbers() {
        const table = document.getElementById('orderLineTable');
        const rows = table.getElementsByTagName('tr');
        for (let i = 0; i < rows.length; i++) {
            const cell = rows[i].getElementsByTagName('td')[0];
            cell.textContent = i + 1;
            }
}

function handleSubmit(event) {
    event.preventDefault();
    // Handle form submission, e.g., send data to server
    alert('Form submitted!');
}

function resetForm() {
    resetOrderLineInputs();
    document.getElementById('orderForm').reset();
}