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

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var fileErrorData=[];
var processCount = 0;

function processData() {
    var file = $('#BookFile')[0].files[0];
    var tsv = (file) => file.toLowerCase().endsWith('.tsv');
    if (!tsv) {
        console.log("Invalid file format: Not TSV.");
        warning("Invalid file format: Not TSV.");
        return;
    } else {
        readFileData(file, readFileDataCallback);
    }
}

function readFileDataCallback(results) {
    fileData = results.data;
    if (fileData.length == 0) {
        console.log("File Empty");
        warning("File Empty");
    } else if (fileData.length > 5000) {
        console.log("File size exceeds limit");
        warning("The file size (" + fileData.length + ") exceeds the limit of 5000.");
    } else {
        const firstRecord = fileData[0];
        const updatedKeys = Object.keys(firstRecord).map(key => key.toLowerCase().trim());

        const fileDataFiltered = fileData.map(obj => {
          const lowercaseHeaders = {};
          Object.keys(obj).forEach(key => {
            const lowercaseKey = key.toLowerCase().trim();
            if (lowercaseKey !== "") {  // Skip empty headers
                lowercaseHeaders[lowercaseKey] = obj[key];
            }
          });
          return lowercaseHeaders;
        });
        fileData= fileDataFiltered;
        if (!checkHeaderMatch(fileData[0])) {
            console.log("File headers do not match the expected format");
            warning("File headers do not match the expected format");
            return;
        }
        uploadRows();
    }
}

function checkHeaderMatch(uploadedHeaders) {
    var expectedHeaders = ["title", "author", "genre", "price", "availability"];
    var extractedHeaders = Object.keys(uploadedHeaders);
    extractedHeaders = extractedHeaders.map(header => header.toLowerCase().trim());

    if (extractedHeaders.length !== expectedHeaders.length) {
        return false;
    }
    extractedHeaders.sort();
    for (var i = 0; i < expectedHeaders.length; i++) {
        if (extractedHeaders[i] !== expectedHeaders[i]) {
            return false;
        }
    }
    return true;
}

function uploadRows() {
    //Update progress
    updateUploadDialog();
    var row= fileData;
    var json = JSON.stringify(row);
    var url = getBookListUrl();
    console.log(json);
    //Make ajax call
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function(response) {
            $('#upload-book-modal').modal('hide');
            refresh();
            success("File uploaded successfully");
        },
        error: function(response) {
            var resp = JSON.parse(response.responseText);
            const inputString = resp.message;
            // Remove the square brackets at the beginning and end of the string
            const cleanedString = inputString.slice(1, -1);

            // Split the string using the comma (,) as the delimiter
            const errorArray = cleanedString.split(', ');

            var i=0;
            // Loop through the errorArray and format each error as a CSV row
            errorArray.forEach((error) => {
              const index = error.indexOf('=');
              const errorCode = error.slice(0, index);
              const errorMessage = error.slice(index + 1);
              const errorRowIndex = parseInt(errorCode, 10);
                if (!isNaN(errorRowIndex) && errorRowIndex >= 0 && errorRowIndex < fileData.length) {
                  if (!errorData[i]) {
                    errorData[i] = Object.assign({}, fileData[errorRowIndex], { error: errorMessage });
                    i++;
                  }
                }
            });
            updateUploadDialog();
            $("#download-errors").prop('disabled', false);
            danger("Invalid file: File has errors");

        }
    });
//    $("#download-errors").prop('disabled', false);
}

function downloadErrors() {
    if (errorData.length === 0) {
        $("#download-errors").prop('disabled', true);
        warning("No errors to download");
        return;
    }
    writeFileData(errorData);
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

function resetUploadDialog() {
    //Reset file name
    var $file = $('#bookFile');
    $file.val('');
    $('#bookFileName').html("Choose File");
    //Reset various counts
    processCount = 0;
    fileData = [];
    errorData = [];
    fileErrorData=[];
    //Update counts
    updateUploadDialog();
}

function updateUploadDialog() {
    $('#rowCount').html("" + fileData.length);
    $('#processCount').html("" + processCount);
    $('#errorCount').html("" + errorData.length);
}

function updateFileName() {
    processCount = 0;
    fileData = [];
    errorData = [];
    updateUploadDialog();
    $("#download-errors").prop('disabled', true);
    var $file = $('#bookFile');
    var fileName = $file.val();
    $('#bookFileName').html(fileName);
}

function displayUploadData() {
    $("#download-errors").prop('disabled', true);
    resetUploadDialog();
    $('#upload-book-modal').modal('toggle');
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
    $('#upload-data').click(displayUploadData);
    $('#process-data').click(processData);
    $('#download-errors').click(downloadErrors);
    $('#bookFile').on('change', updateFileName);
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