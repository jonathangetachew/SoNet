var reader = new FileReader();
reader.onload = function(r_event) {
    console.log(">>>> onload<<<");
    document.getElementById('img-display').setAttribute('src', r_event.target.result);
}

var el = document.getElementById('imageFile');
if(el){
    el.addEventListener('change', function(event) {
        console.log(">>>> get imageFile<<<");
        reader.readAsDataURL(this.files[0]);
    });
}

function readURL(input) {
    console.log(">>> readURL")
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function(e) {
            $('#img-display').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$("#imageFile").change(function() {
    console.log(('>>>>>> imageFile changed'));
    readURL(this);
});