dojo.addOnLoad(function(){
    dojo.require("dijit.layout.TabContainer");
    dojo.require("dijit.layout.ContentPane");
    dojo.require("dojo.fx");
    dojo.require("dojo.NodeList-fx");
    dojo.require("dojo.NodeList-traverse");
    dojo.require("dojox.validate.regexp");
    dojo.require("dijit.form.ValidationTextBox");
});

dojo.ready(function(){
   var deleteButton = dojo.query(".delete_ban");
   var cancelDeleteButton = dojo.query(".cancel_ban_delete");
   var confirmDeleteAction = function(event){
       var panel = dojo.query(event.currentTarget).next(".delete_ban_confirmation");
       panel.style("display", "block");
   }
  var cancelDeleteAction = function(event){
       var panel = dojo.query(event.currentTarget).parent(".delete_ban_confirmation");
       panel.style("display", "none");
   }
   cancelDeleteButton.connect("onclick", cancelDeleteAction);
   deleteButton.connect("onclick", confirmDeleteAction);
});
