var table;

function getCartUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/cart";
}

function getOrderUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/order";
}

function getBookListUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/book/list";
}

//BUTTON ACTIONS
function addBook( title,author,genre,price) {
    // Create a JSON object from the row data
    var $form = $("#cart-form");
    var json = {
        "title": title,
        "author": author,
        "genre": genre,
        "price": price,
        "quantity":1
    }
    var url = getCartUrl();
    console.log(json);
    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify( json),
        headers: {
            'Content-Type': 'application/json'
        },
        success: function(response) {
            success("Success");
            getCartList();
            resetFormFields($form);
        },
        error: handleAjaxError
    });
    return false;
}

function updateCart(event) {
    //Get the ID
    var id = $("#cart-edit-form input[name=id]").val();
    var url = getCartUrl() + "/" + id;

    //Set the values to update
    var $form = $("#cart-edit-form");
    var json = toJson($form);
    var json1 = JSON.parse(json);
    $.ajax({
        url: url,
        type: 'PUT',
        data: JSON.stringify(json1),
        headers: {
            'Content-Type': 'application/json'
        },
        success: function(response) {
            $('#edit-cart-modal').modal('toggle');
            success("Item Updated");
            getCartList();
        },
        error: handleAjaxError
    });
    return false;
}
// Add a click event handler for the "Place Order" button

function placeOrder(event){
    var url = getOrderUrl();
    $.ajax({
        url: url,
        type: 'POST',
        success: function(response) {
            getCartList(); // Refresh the data table
        },
        error: handleAjaxError
    });
}

function getCartList() {
    var url = getCartUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            displayCartList(data);
        },
        error: handleAjaxError
    });
}

function deleteCart(id) {
    var url = getCartUrl() + "/" + id;

    $.ajax({
        url: url,
        type: 'DELETE',
        success: function(data) {
            getCartList();
        },
        error: handleAjaxError
    });
}

//UI DISPLAY METHODS

function displayCartList(data) {
    table.clear().draw();
    var dataRows=[];
    for (var i in data) {
        var e = data[i];
        var sellingPrice= ((e.price * e.quantity).toFixed(2));
        var buttonHtml = ' <button class="btn btn-outline-primary" onclick="addBook(\'' + e.title + '\', \'' + e.author + '\', \'' + e.genre + '\', ' + e.price + ')">Add</button>'
         + ' <button class="btn btn-outline-danger" onclick="deleteCart(' + e.id + ')">Remove</button>';

        dataRows.push([e.id,e.title, e.author, e.genre,parseFloat(e.price).toFixed(2),e.quantity,sellingPrice, buttonHtml]);
    }
    table.rows.add(dataRows).draw();
}

function displayEditCart(id) {
    var url = getCartUrl() + "/" + id;
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            displayCart(data);
        },
        error: handleAjaxError
    });
}


//INITIALIZATION CODE
function init() {
    $('#placeOrder').click(placeOrder);
    getCartList();
    table = $('#cart-table').DataTable({
      columnDefs: [
        { targets: [0, 1, 2, 3,4,5,6,7], className: "text-center" },
        { targets: 0, visible: false},
        {
          targets: 7,
          orderable: false,
          visible: userRole === 'SUPERVISOR'
        }
      ],
      searching: true,
      info: true,
      lengthMenu: [
        [5, 10, 20, -1],
        [5, 10, 20, 'All']
      ],
      deferRender: true,
      order: [[0, "desc"]],
      columns: [
                       { searchable: false },
                       { searchable: true },
                       { searchable: true },
                       { searchable: true },
                       { searchable: true },
                       { searchable: false }
                   ],
    });
}

$(document).ready(init);