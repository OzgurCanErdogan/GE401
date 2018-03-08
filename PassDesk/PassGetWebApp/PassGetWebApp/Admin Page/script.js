$(document).ready(function () {
    $('.active-basket').click(function () {

        var total = 0;
        $(".active-basket-item-price").each(function (index, element) {
            total += parseFloat(element.innerText);
            console.log("value", element.innerText);
            console.log("total", total);
        });

        $("#active-basket-total").text(total);

        $('#showBasketModal').modal('show');
    })
});