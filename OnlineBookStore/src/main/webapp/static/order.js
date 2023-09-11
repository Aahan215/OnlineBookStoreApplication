var table;

function getOrderUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/order";
}

function getOrderItemUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/order-item";
}

function getInvoiceUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content");
    return baseUrl + "/api/invoice";
}

//BUTTON ACTIONS
function getOrderList() {
    var url = getOrderUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            console.log(data);
            displayOrderList(data);
        },
        error: handleAjaxError
    });
}

function viewOrder(id, status) {
    var url = getOrderUrl() + "/view/" + id;
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            data=data.reverse();
            console.log("in view order:",status);
            var $header = $('#exampleModalLabel');
            $header.text("Order :" + id);
            viewOrderList(data, status);
        },
        error: handleAjaxError
    });
}

//UI DISPLAY METHODS
// Function to pad single-digit numbers with leading zero
function padZero(number) {
    return number.toString().padStart(2, '0');
}

function displayOrderList(data) {
    console.log(data);
    var $tbody = $('#order-table').find('tbody');
    table.clear().draw();
    var dataRows=[];
    for (var i in data) {
        var e = data[i];
        var date = e.dateTime;
        var formattedDatetime = date.slice(2, 3).join('-') + '/' +
                    padZero(date[1]) + '/' + date[0] + ' ' +
                    padZero(date[3]) + ':' + padZero(date[4]) + ':' +
                    padZero(date[5]);
        console.log("displayOrderList",e.status);
        var status=e.status;
        var buttonHtml1 = '<button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#exampleModal" onclick="viewOrder(' + e.id + ',\'' + e.status + '\')">View Order</button>&nbsp;';
        buttonHtml1 += '<button type="button" class="btn btn-outline-primary" onclick="printOrder(' + e.id + ', \'' + e.status + '\')">Download Invoice</button>';
        console.log(e.id, formattedDatetime, e.status);
        dataRows.push([e.id, formattedDatetime, e.status, buttonHtml1]);
    }
    table.rows.add(dataRows).draw();
}

function viewOrderList(data, status) {
    var $tbody = $('#view-order-table').find('tbody');
    $tbody.empty();
    var totalSellingPrice = 0;
    for (var i in data) {
        var e = data[i];
        console.log(e);
        var sellingPrice= ((e.price * e.quantity).toFixed(2));
        var rowid = "row-" + e.id;
        var row = '<tr id=' + rowid + '>' +
            '<td>' + e.title + '</td>' +
            '<td>' + e.author + '</td>' +
            '<td>' + e.genre + '</td>' +
            '<td>' + e.price + '</td>' +
            '<td>' + e.quantity + '</td>' +
            '<td>' + sellingPrice + '</td>' +
            '</tr>';
        $tbody.append(row);
        totalSellingPrice += (e.price * e.quantity);
    }
    var $sellingPrice = $('#modal-footer').find('h6');
    $sellingPrice.text('Total Selling Price : ' + totalSellingPrice.toFixed(2));
}

function displayEditOrder(id) {
    var url = getOrderUrl() + "/" + id;
    $.ajax({
        url: url,
        type: 'GET',
        success: function(data) {
            displayOrder(data);
        },
        error: handleAjaxError
    });
}

function displayOrder(data) {
    $("#order-edit-form input[name=dateTime]").val(data.dateTime);
    $("#order-edit-form input[name=id]").val(data.id);
    $('#edit-order-modal').modal('toggle');
}

function printOrder(id) {
    window.location.href = getInvoiceUrl() + "/" + id;
    console.log("in print order");
}


//INITIALIZATION CODE
function init() {
    getOrderList();
        table = $('#order-table').DataTable({
          columnDefs: [
            { targets: [3], orderable: false },
            { targets: [0, 1, 2, 3], className: "text-center" }
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
            { searchable: true },
            { searchable: true },
            { searchable: true },
            { searchable: false }
          ],
        });
}

$(document).ready(init);