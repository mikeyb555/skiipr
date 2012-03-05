// Load the widget parser
dojo.require("dojox.mobile.parser");
// Load the base lib
dojo.require("dojox.mobile");

dojo.requireIf(!dojo.isWebKit, "dojox.mobile.compat");

dojo.require("dojox.mobile.FixedSplitter");

dojo.require("dojox.mobile.ScrollableView");

dojo.require("dojox.timing");

dojo.require("dojo.date.locale");

dojo.require("dojo.currency");

dojo.require("dojox.widget.Standby");

dojo.require("dijit.Dialog");

dojo.require("dijit.form.Button");

var contentLoadingPanel;
var orderLoadingPanel;
var lastChangeToken = 0;
var currentOrderID;
var orderPlaceHolder;
var orderListPanel;
var contentPanel;
var order;
var orderReadyButton;

dojo.addOnLoad(function(){
    contentLoadingPanel = dijit.byId("order_loading");
    contentPanel = dojo.byId("order_content_panel");
    contentLoadingPanel.target = contentPanel;
    contentLoadingPanel.zIndex = 10;
    
    orderLoadingPanel = dijit.byId("order_list_loading");
    orderListPanel = dojo.byId("order_list");
    orderLoadingPanel.target = orderListPanel;
    
    orderPlaceHolder = dojo.byId("no_order_title");
    
    var orderCheckTimer = new dojox.timing.Timer(1000);
    orderCheckTimer.onTick = function(){checkForChange()};
    orderCheckTimer.start();
    
    var blockButton = dijit.byId("block_customer_button");
    dojo.connect(blockButton, "onClick", function(e){
       var blockModal = dijit.byId("console_block_panel");
       blockModal.show();
    });
    
    orderReadyButton = dijit.byId("order_ready_button");
    dojo.connect(orderReadyButton, "onClick", function(e){
       if(order != null){
           addBlocked();
       }
    });
    
    blockCustomerButton = dijit.byId("block_customer_button");
    dojo.connect(blockCustomerButton, "onClick", function(e){
       if(order != null){
           addBlocked();
       }
    });
    
    
    
    var detailsHeading = dojo.byId("details_heading");
    detailsHeading.style.zIndex = "20";

    setupSettingsPanel();
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
    if(order != null){
        loadOrderPanel(order.orderID);
    }
    orderLoadingPanel.hide();
}

function loadOrderPanel(orderID){
    hideOrderPlaceholder();
    contentLoadingPanel.show();
    var productList  = dijit.byId("product_list");
    dojo.xhrGet({
    url:"console/api/order/" + orderID, handleAs:"json",
    load: function(data){
        order = data;
        var orderIDPanel = dojo.byId("order_id_panel");
        var orderEmailPanel = dojo.byId("order_email_panel");
        var orderTimePanel = dojo.byId("order_time_panel");
        var orderPaymentPanel = dojo.byId("order_payment_panel");
        var orderStatusPanel = dojo.byId("order_status_panel");

        currentOrderID = data.orderID;
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
                label : products[key].quantity + ' x ' + product.name,
                rightText : dojo.currency.format(product.price, {currency: "AUD"})
            });
            productList.addChild(productListItem);
        }
        
        var button = dojo.byId("order_ready_button");
        if(data.status == "NEW"){
            button.innerHTML = "Order ready for pickup";
        }else if(data.status == "READY"){
            button.innerHTML = "Order complete";
        }
        
        var orderTotal = dojo.byId("order_total");
        orderTotal.innerHTML = dojo.currency.format(data.total, {currency: "AUD"});
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

function setupSettingsPanel(){
    var settingsButton = dijit.byId("console_settings_button");
    dojo.connect(settingsButton, "onClick", function(e){
       var settingsModal = dijit.byId("console_settings_panel");
       settingsModal.show();
    });
    
    var soundEnabledRocker = dijit.byId("settings_sound");
    dojo.connect(soundEnabledRocker, "onStateChanged", function(newState){
        alert("newState = "+newState); // newState is "on" or "off"
    });
}

function hideOrderPlaceholder(){
    orderPlaceHolder.style.display = "none";
    contentPanel.style.display = "block";
}

function showOrderPlaceholder(){
    orderPlaceHolder.style.display = "block";
    contentPanel.style.display = "none";
}

function addBlocked(){
    contentLoadingPanel.show();
    var email;
    email = order.email;
    merchantID = order.merchantID;
    
    var params = "identifier=" + email + "merchantID=" + merchantID;
    dojo.xhrPost({
      url:"console/api/banned/submit",
      handleAs: "text",
      postData: params,
      load: true,
      error: false
    }); 
} 



function updateStatus(){
    contentLoadingPanel.show();
    var newStatus;
    if(order.status == "NEW"){
        newStatus = "READY";
    }else if(order.status == "READY"){
        newStatus = "COMPLETE";
    }else{
        alert("There was an error generaging your request.");
        contentLoadingPanel.hide();
        return;
    }
    var params = "status=" + newStatus;
    dojo.xhrPost({
      url:"console/api/order/" + order.orderID + "/update", handleAs:"text",
      handleAs: "text",
      postData: params,
      load: function(data){
          if(data !== "complete"){
              console.log(data);
              alert("There was an error processing your request.");
              contentLoadingPanel.hide();
          }else{
              if(order.status == "COMPLETE"){
                  showOrderPlaceholder();
              }
          }
      },
      error: function(error){
        alert("There was an error submitting your request.");
        contentLoadingPanel.hide();
      }
    }); 
}