
function initialize() {
    alert("hello world");
    var latlng = new google.maps.LatLng(-34.397, 150.644);
    var myOptions = {
      zoom: 8,
      center: latlng,
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var div = document.getElementById("map_canvas");
    if (div == null){
        alert("div is null");
    }
    var map = new google.maps.Map(div,
        myOptions);
  }



