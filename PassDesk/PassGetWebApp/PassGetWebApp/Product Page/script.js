$(".product").click(function () {
    var id = $(':nth-child(1)', this).text();
    var name = $(':nth-child(2)', this).text();
    var count = $(':nth-child(3)', this).text();
    var sold = $(':nth-child(4)', this).text();

    $(".modal #product-id").text(id);
    $(".modal #product-name").text(name);
    $(".modal #product-count").text(count);
    $(".modal #product-sold").text(sold);

    console.log(name);
    $("#showProductModal").modal('show');
});

$("#button-add-product").click(function () {
    $("#addProductModal").modal('show');
});