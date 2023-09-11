var table;

function getBookUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/book";
}
function getCartUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/cart";
}

function getBookListUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/book/list";
}

//BUTTON ACTIONS
function displayBook(event) {
    //Set the values to update
    var $form = $("#createOrder-form");
    var json = toJson($form);
    var url = getBookUrl();
    var json1 = JSON.parse(json);

    $.ajax({
        url: url,
        type: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        success: function(response) {
            success("Success");
            getBookList();
            resetFormFields($form);
        },
        error: handleAjaxError
    });
    return false;
}

function getBookList() {
    var url = getBookUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            displayBookList(data);
        },
        error: handleAjaxError
    });
}

//UI DISPLAY METHODS

function displayBookList(data) {
    table.clear().draw();
    var dataRows=[];
    for (var i in data) {
        var e = data[i];
       var buttonHtml = ' <button class="btn btn-outline-primary" onclick="addBook(\'' + e.title + '\', \'' + e.author + '\', \'' + e.genre + '\', ' + e.price + ')">Add</button>';
        dataRows.push([e.id,e.title, e.author, e.genre,parseFloat(e.price).toFixed(2),e.availability, buttonHtml]);
    }
    table.rows.add(dataRows).draw();
}

function addBook( title,author,genre,price) {
    // Create a JSON object from the row data
    var $form = $("#createOrder-form");
    var json = {
        "title": title,
        "author": author,
        "genre": genre,
        "price": price,
        "quantity":1
    };
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
            getBookList();
            resetFormFields($form);
        },
        error: handleAjaxError
    });
    return false;
}

function displayEditBook(id) {
    var url = getBookUrl() + "/" + id;
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            displayBook(data);
        },
        error: handleAjaxError
    });
}


function displayBook(data) {
    $("#book-edit-form input[name=title]").val(data.title);
    $("#book-edit-form input[name=author]").val(data.author);
    $("#book-edit-form input[name=genre]").val(data.genre);
    $("#book-edit-form input[name=price]").val(data.price);
    $("#book-edit-form input[name=availability]").val(data.availability);
    $("#book-edit-form input[name=id]").val(data.id);
    $('#edit-book-modal').modal('toggle');
}

function refresh() {
    getBookList();
    resetFormFields($('#createOrder-form'));
}

//INITIALIZATION CODE
function init() {
    $('#refresh-data').click(refresh);
    getBookList();
    table = $('#createOrder-table').DataTable({
      columnDefs: [
        { targets: [0, 1, 2, 3,4,5,6], className: "text-center" },
        { targets: 0, visible: false},
        {targets: 5,visible: false}
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