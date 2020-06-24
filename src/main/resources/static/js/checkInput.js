$(document).ready(function(){

    $("#amount").on('input',function(e){
        if ($("#amount").val()==[]){
            $("#amount").val(0);
        }
    });

    $("#price").on('input',function(e){
        if ($("#price").val()==[]){
            $("#price").val(0);
        }
    });

    $("#findProductId").on('input',function(e){
        if ($("#findProductId").val()==[]){
            $("#findProductId").val(0);
        }
    });

});
