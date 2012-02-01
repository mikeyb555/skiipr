// Load the widget parser
dojo.require("dojox.mobile.parser");
// Load the base lib
dojo.require("dojox.mobile");

dojo.requireIf(!dojo.isWebKit, "dojox.mobile.compat");

dojo.require("dojox.mobile.FixedSplitter");

dojo.require("dojox.mobile.ScrollableView");

dojo.require("dojox.timing");

dojo.require( "dojo.date.locale" );

dojo.require("dojox.widget.Standby");

var contentLoadingPanel;
var orderLoadingPanel;
var lastChangeToken = 0;

dojo.addOnLoad(function(){
    contentLoadingPanel = dijit.byId("order_loading");
    var contentPanel = dojo.byId("order_content_panel");
    contentLoadingPanel.target = contentPanel;
    
    orderLoadingPanel = dijit.byId("order_list_loading");
    var orderListPanel = dojo.byId("order_list");
    orderLoadingPanel.target = orderListPanel;
    
    var orderCheckTimer = new dojox.timing.Timer(1000);
    orderCheckTimer.onTick = function(){checkForChange()};
    orderCheckTimer.start();
});

function reloadOrderList(){
    orderLoadingPanel.show();
    var orderList = dijit.byId("order_list");
    orderList.destroyDescendants();
    dojo.xhrGet({
    url:"console/api/order/list", handleAs:"json",
    load: function(data){
      for(var key in data){
          var order = data[key];
          var clickAction = {orderID : order.orderID, doClick : function(){loadOrderPanel(this.orderID)}};
          var orderListItem = new dojox.mobile.ListItem({
              label : "Order " + order.orderID,
              onClick : dojo.hitch(clickAction, "doClick"),
              clickable : true
          });
          orderList.addChild(orderListItem);
      }
    }
    });
    orderLoadingPanel.hide();
}

function loadOrderPanel(orderID){
    contentLoadingPanel.show();
    var productList  = dijit.byId("product_list");
    dojo.xhrGet({
    url:"console/api/order/" + orderID, handleAs:"json",
    load: function(data){
        
        var orderIDPanel = dojo.byId("order_id_panel");
        var orderEmailPanel = dojo.byId("order_email_panel");
        var orderTimePanel = dojo.byId("order_time_panel");
        var orderPaymentPanel = dojo.byId("order_payment_panel");
        var orderStatusPanel = dojo.byId("order_status_panel");

        orderIDPanel.innerHTML = data.orderID;
        orderEmailPanel.innerHTML = data.email;
        var orderDate = new Date(data.orderTime * 1000);
        startWaitingTimer(orderDate);
        orderTimePanel.innerHTML = formatDate(orderDate);
        orderStatusPanel.innerHTML = data.status;
        orderPaymentPanel.innerHTML = data.paymentType;
        
        productList.destroyDescendants();
        var products = data.orderProducts;
        for(var key in products){
            var product = products[key].product;
            var productListItem = new dojox.mobile.ListItem({
                label : product.name
            });
            productList.addChild(productListItem);
        }
        }
        
    });
    contentLoadingPanel.hide();
}

function formatDate(orderDate){
    return dojo.date.locale.format(orderDate, {timePattern : "K:m a", datePattern : "EEEE, MMMM d, y"});
}

function startWaitingTimer(time){
    var orderWaitingPanel = dojo.byId("order_waiting_panel");
    var updateTimeFunction = function(){
        var now = new Date();
        var diff = dojo.date.difference(time, now, "second");
        orderWaitingPanel.innerHTML = formatTimeDifference(diff);
    }
    updateTimeFunction();
    var timer = new dojox.timing.Timer(1000);
    timer.onTick = updateTimeFunction;
    timer.start();
}

function formatTimeDifference(sec_numb){
    var hours   = Math.floor(sec_numb / 3600);
    var minutes = Math.floor((sec_numb - (hours * 3600)) / 60);
    var seconds = sec_numb - (hours * 3600) - (minutes * 60);

    if (hours   < 10) {hours   = "0"+hours;}
    if (minutes < 10) {minutes = "0"+minutes;}
    if (seconds < 10) {seconds = "0"+seconds;}
    return hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
}

function checkForChange(){
    dojo.xhrGet({
    url:"console/api/order/change", handleAs:"text",
    load: function(data){
        if(data != lastChangeToken){
            lastChangeToken = data;
            reloadOrderList();
        }
    }
    });
}