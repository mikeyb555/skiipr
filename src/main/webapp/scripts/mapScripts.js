dojo.addOnLoad(function(){
    
    var lat = dojo.byId("_latitude_id");
    var lon = dojo.byId("_longitude_id");
    
    if((lat !== null) && (lon !== null)){
        var latlng = new google.maps.LatLng(lat.value, lon.value);
        var myOptions = {
          zoom: 14,
          center: latlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };

        var div = dojo.byId("map_canvas");
        var map = new google.maps.Map(div, myOptions);

        var marker = new google.maps.Marker({
            position: latlng, 
            map: map,
            title:"Your address",
            draggable:true
        });

        google.maps.event.addListener(marker, 'dragend', function(){
            var position = marker.getPosition();
            setMapToPoint(position);
        });

        var findLink = dojo.byId("find_link");
        dojo.connect(findLink, "onclick", codeAddress);

        function codeAddress() {
            var streetNumber = dojo.byId("_addressNumber_id").value;
            var streetAddress = dojo.byId("_addressStreet_id").value;
            var streetPostcode = dojo.byId("_addressPostcode_id").value;
            var streetState = dojo.byId("_addressState_id").value;
            var streetCountry = dojo.byId("_addressCountry_id").value;
            var address = streetNumber + " " + streetAddress + " " + streetPostcode + " " + streetState + " " + streetCountry;
            var geocoder = new google.maps.Geocoder();
            geocoder.geocode( { 'address': address}, function(results, status) {
              if (status == google.maps.GeocoderStatus.OK) {
                var apiLocation = results[0].geometry.location;
                setMapToPoint(apiLocation);
              } else {
                alert("Geocode was not successful for the following reason: " + status);
              }
            });
        }

        function setMapToPoint(position){
            lat.value = position.lat();
            lon.value = position.lng();
            marker.setPosition(position);
            map.setCenter(position);
        }
    }
});


