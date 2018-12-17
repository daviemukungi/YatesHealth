function isDefined(x) {
    var undefined;
    return x !== undefined;
}

function getCart () {
    callGet('/cart',createCartStructure);
}


function addToCart(uuid) {
    passData('/cart?uuidStr='+uuid,'POST',null,getCart);
}

var cartForm = {};
function updateQuantity (val,uuid) {
   cartForm[uuid]=val;
}

function sendUpdatedCart() {
    if(Object.keys(cartForm).length == 0) {
        showToast('nothing to update','info');
    } else {
        var formContent = {};
        formContent.items = cartForm;
        passData('/cart','PUT',formContent,getCart);
    }
}

function removeFromCart (uuid,type) {
    passData('/cart?uuidStr='+uuid+'&type='+type,'DELETE',null,getCart);
    return false;
}

function removePackageFromCart (uuid) {
    passData('/cart?uuidStr='+uuid+'&type=medical','DELETE',null,getCart);
    return false;
}

function addToWishlist(uuid) {
    passData('/wishlist?uuidStr='+uuid,'POST',null,function(){});
}

function removeFromWishlist(uuid) {
    passData('/wishlist?uuidStr='+uuid,'DELETE',null,function(){location.reload()});
}

function addToComparison(uuid) {
    passData('/comparison?uuidStr='+uuid,'POST',null,function(){});
}

var locationTree = '';
var locationId = '';
var locId = '';
var cartContent = {};

function useLocations ()  {
    locationTree = this.data;
    generateStructure(locationTree.children);
}

function useRetailerLocations ()  {
    locationTree = this.data;
    generateLocationStructure(locationTree.children);
}

function searchTree(id) {
    locId = id;
    var queue = [];
    queue.push(locationTree);
    while(queue.length != 0) {
        var nd = queue.shift();
        if (nd.children.length > 0) {
            queue = queue.concat(nd.children);
        }

        if(id == nd.id) {
            if(nd.children.length == 0) {
                locationId = id;
                return;
            }
            return nd.children;
        }
    }
}

function generateStructure (data) {
    var idx =0;
    if(isDefined(data) && data.length > 0) {
        var html ="";
        html+="<div id='"+data[0].label+"'><select onchange='$(\"#"+data[0].label+"\").nextAll().remove();generateStructure(searchTree(this.value))' class='select2-single form-control'><option value=''>"+data[0].label+"</option>";
        while(idx < data.length) {
            html+="<option value='"+data[idx].id+"'>"+data[idx].name+"</option>";
            idx++;
        }
        html+="</select><br><br></div>";
        $("#location").append(html).show();
        $(".select2-single").select2();
    }
}

function generateLocationStructure (data) {
    $(document).ready(function() {
        var idx =0;
        if(isDefined(data) && data.length > 0) {
            var html ="";
            html+="<div id='"+data[0].label+"'><select onchange='$(\"#"+data[0].label+"\").nextAll().remove();generateLocationStructure(searchTree(this.value))' class='chosen-select form-control'><option value=''>"+data[0].label+"</option>";
            while(idx < data.length) {
                html+="<option value='"+data[idx].id+"'>"+data[idx].name+"</option>";
                idx++;
            }
            html+="</select><br><br></div>";
            $("#retailerLocation").append(html).show();
            $(".chosen-select").chosen({width: "100%"});
        }

        cartContent.locationId = locId;
        if(cartContent.locationId != ''){
            passDataNoToast('/api/checkout','POST',cartContent,createRetailerStructure);
        }
    });
}

function validateSpace (elm,val) {
    if(val == "" || !isDefined(val)) {
        throw elm;
    }

    return val;
}

function validatePassword (elm,password,confirmPassword) {
    if(password !== confirmPassword) {
        throw elm;
    }
    return password;
}

function validateEmail(elm,email) {
    var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;

    if(!pattern.test(email)) {
        throw elm;
    }
    return email;
}

function activateError (elm) {
    if(elm.toString().lastIndexOf("prescription",0) === 0) {
        $('#'+elm).find( "td input" ).fadeIn().css('border-style','solid').css('border-width','2px').css('border-color','red');
    } else {
        if($('#'+elm).length > 0 ) {

            if(elm == 'location') {
                var html= '<span id="locationError" style="color: red">Please select a location</span>';
                if($('#locationError').length > 0) {
                    $('#locationError').remove();
                }
                $('#'+elm).addClass("has-error").append(html);
            } else {
                $('#'+elm).addClass("has-error").find( "span" ).fadeIn().css("color","red");
            }
        }

        if($('.'+elm).length > 0 ) {
            $('.'+elm).addClass("has-error").find( "span" ).fadeIn().css("color","red");
        }
    }
}
