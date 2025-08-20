function confirmDelete(message) {
    return confirm(message || 'Are you sure you want to delete this item?');
}

function validateAddCustomerForm() {
    const accountNumber = document.getElementById('accountNumber').value;
    const name = document.getElementById('name').value;
    const address = document.getElementById('address').value;
    const telephoneNumber = document.getElementById('telephoneNumber').value;
    const unitsConsumed = document.getElementById('unitsConsumed').value;

    const telephonePattern = /^\+94[0-9]{9}$/;
    if (!telephonePattern.test(telephoneNumber)) {
        alert('Please enter a valid telephone number (e.g., +94712345678)');
        return false;
    }
    if (unitsConsumed < 0) {
        alert('Units consumed cannot be negative');
        return false;
    }
    if (!accountNumber || !name || !address) {
        alert('All fields are required');
        return false;
    }
    return true;
}

function validateAddItemForm() {
    const itemId = document.getElementById('itemId').value;
    const name = document.getElementById('name').value;
    const price = document.getElementById('price').value;
    const stock = document.getElementById('stock').value;

    if (!itemId || !name) {
        alert('Item ID and Name are required');
        return false;
    }
    if (price < 0) {
        alert('Price cannot be negative');
        return false;
    }
    if (stock < 0) {
        alert('Stock cannot be negative');
        return false;
    }
    return true;
}

function validateBillingForm() {
    const customerId = document.getElementById('customerId').value;
    const itemId = document.getElementById('itemId').value;
    const unitsConsumed = document.getElementById('unitsConsumed').value;

    if (!customerId || !itemId) {
        alert('Please select a customer and an item');
        return false;
    }
    if (unitsConsumed <= 0) {
        alert('Units consumed must be greater than 0');
        return false;
    }
    return true;
}