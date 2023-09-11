var table;

function getBookUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/book";
}
function getBookListUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/book/list";
}

//BUTTON ACTIONS
function addBook(event) {
    //Set the values to update
    var $form = $("#book-form");
    var json = toJson($form);
    var url = getBookUrl();
    var json1 = JSON.parse(json);
    if(json1.title === ""){
        warning("Title cannot be empty");
        return false;
    }
    if(json1.author === ""){
        warning("Author cannot be empty");
        return false;
    }
    if(json1.genre === ""){
        warning("Genre cannot be empty");
        return false;
    }
    if(parseFloat(json1.price)>10000000){
        warning("Price entered is too large");
        return false;
    }
    if(parseFloat(json1.price)<0.01){
        warning("Price must be more than or equal to 0.01");
        return false;
    }
    if((parseFloat(json1.availability) - parseInt(json1.availability)) > 0) {
        warning("Quantity should be of integer type.");
        return;
    }
    json1.price=parseFloat(json1.price).toFixed(2);
    console.log(json);

    $.ajax({
        url: url,
        type: 'POST',
        data: JSON.stringify( json1),
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

function updateBook(event) {
    //Get the ID
    var id = $("#book-edit-form input[name=id]").val();
    var url = getBookUrl() + "/" + id;

    //Set the values to update
    var $form = $("#book-edit-form");
    var json = toJson($form);
    var json1 = JSON.parse(json);
    if(json1.title === ""){
        warning("Title cannot be empty");
        return false;
    }
    if(json1.author === ""){
        warning("Author cannot be empty");
        return false;
    }
    if(json1.genre === ""){
        warning("Genre cannot be empty");
        return false;
    }
    if(parseFloat(json1.price)>10000000){
        warning("Price entered is too large");
        return false;
    }
    if(parseFloat(json1.price)<0.01){
        warning("Price must be more than or equal to 0.01");
        return false;
    }
    if((parseFloat(json1.availability) - parseInt(json1.availability)) > 0) {
        warning("Quantity should be of integer type.");
        return;
    }
    json1.price=parseFloat(json1.price).toFixed(2);
    $.ajax({
        url: url,
        type: 'PUT',
        data: JSON.stringify(json1),
        headers: {
            'Content-Type': 'application/json'
        },
        success: function(response) {
            $('#edit-book-modal').modal('toggle');
            success("Item Updated");
            getBookList();
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

function deleteBook(id) {
    var url = getBookUrl() + "/" + id;

    $.ajax({
        url: url,
        type: 'DELETE',
        success: function(data) {
            getBookList();
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
        var buttonHtml = ' <button class="btn btn-outline-primary" onclick="displayEditBook(' + e.id + ')">Edit</button>' +
            ' <button class="btn btn-outline-danger" onclick="deleteBook(' + e.id + ')">Delete</button>';

        dataRows.push([e.id,e.title, e.author, e.genre,parseFloat(e.price).toFixed(2),e.availability, buttonHtml]);
    }
    table.rows.add(dataRows).draw();
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
    resetFormFields($('#book-form'));
}
// Add an event listener to the "Filter" button
$.fn.dataTable.ext.search.push(
    function(settings, data, dataIndex) {
        var minPrice = parseFloat($('#minPrice').val()) || 0;
        var maxPrice = parseFloat($('#maxPrice').val()) || Number.MAX_VALUE;
        var bookPrice = parseFloat(data[4]) || 0; // Assuming the price column is at index 4 in your table

        if ((isNaN(minPrice) && isNaN(maxPrice)) ||
            (isNaN(minPrice) && bookPrice <= maxPrice) ||
            (minPrice <= bookPrice && isNaN(maxPrice)) ||
            (minPrice <= bookPrice && bookPrice <= maxPrice)) {
            return true;
        }
        return false;
    }
);

//INITIALIZATION CODE
function init() {
    $('#add-book').click(addBook);
    $('#update-book').click(updateBook);
    $('#refresh-data').click(refresh);
    getBookList();
    table = $('#book-table').DataTable({
      columnDefs: [
        { targets: [0, 1, 2, 3,4,5,6], className: "text-center" },
        { targets: 0, visible: false},
        {
          targets: 6,
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
    // Event listener to the "Minimum Price" and "Maximum Price" inputs to redraw on input
    $('#minPrice, #maxPrice').on('input', function() {
        table.draw();
    });
}

$(document).ready(init);
