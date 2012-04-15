dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dojo.fx");
dojo.require("dojo.NodeList-fx");
dojo.require("dojo.NodeList-traverse");
dojo.require("dojox.validate.regexp");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.Button");

dojo.ready(function(){
   var deleteButton = dojo.query(".confirm_delete");
   var cancelDeleteButton = dojo.query(".cancel_delete");
   
   var editButton = dojo.query(".edit_item");
   var cancelEditButton = dojo.query(".cancel_edit");
   
   var confirmDeleteAction = function(event){
       dojo.query(event.currentTarget).nextAll(".delete_confirmation").style("display", "block");
       dojo.query(event.currentTarget).nextAll(".edit_panel").style("display", "none");
   }
  var cancelDeleteAction = function(event){
       dojo.query(event.currentTarget).parents(".delete_confirmation").style("display", "none");
   }
   
  var editItemAction = function(event){
       dojo.query(event.currentTarget).nextAll(".edit_panel").style("display", "block");
       dojo.query(event.currentTarget).nextAll(".delete_confirmation").style("display", "none");
   }
  var cancelEditAction = function(event){
       dojo.query(event.currentTarget).parents(".edit_panel").style("display", "none");
  }
   cancelDeleteButton.connect("onclick", cancelDeleteAction);
   deleteButton.connect("onclick", confirmDeleteAction);

   cancelEditButton.connect("onclick", cancelEditAction);
   editButton.connect("onclick", editItemAction);
});

function termsAccept(event){
       var registerSubmitButton = dijit.byId("register_form_submit");
       registerSubmitButton.setAttribute("disabled", !this.checked);
}
