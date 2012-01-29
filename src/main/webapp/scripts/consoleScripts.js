// Load the widget parser
dojo.require("dojox.mobile.parser");
// Load the base lib
dojo.require("dojox.mobile");

dojo.requireIf(!dojo.isWebKit, "dojox.mobile.compat");

dojo.require("dojox.mobile.FixedSplitter");

dojo.require("dojox.mobile.ScrollableView");

dojo.addOnLoad(function(){
    reloadOrderList();
});

function reloadOrderList(){
    var orderList = dijit.byId("order_list");
    dojo.xhrGet({
    url:"console/api/order/list", handleAs:"json",
    load: function(data){
      for(var key in data){
          var order = data[key];
          //var clickAction = function(e){alert("OrderID: " + order.orderID);};
          var orderListItem = new dojox.mobile.ListItem({
              label : "Order " + order.orderID
              //onClick : dojo.hitch(clickAction, "onclick"),
              //clickable : true
          });
          orderList.addChild(orderListItem);
      }
    }
    });
}

function loadOrderPanel(orderID){
    alert("Order ID: " + orderID);
    var orderPanel = dijit.byId("order_panel");
    dojo.xhrGet({
    url:"console/api/order/" + orderID, handleAs:"json",
    load: function(data){
       orderPanel.innerHTML = data.orderID; 
    }
    });
}