<jsp:root 
    xmlns:c="http://java.sun.com/jsp/jstl/core" 
    xmlns:fn="http://java.sun.com/jsp/jstl/functions" 
    xmlns:jsp="http://java.sun.com/JSP/Page" 
    xmlns:spring="http://www.springframework.org/tags" version="2.0">
  <jsp:output omit-xml-declaration="yes" />
  <div dojoType="dijit.Dialog" id="console_block_panel" title="Block">
      <div class="console_setting_row">
          <b>Are you sure you want to block this customer?</b>
          <p />
          Blocking a customer will block the email used for this order from ordering again.
          <br /> This is useful to manage customers who are abusing the mobile ordering process.
          <br /> Blocking a user will not automatically cancel this order.
          <div class="dijitDialogPaneActionBar">
                <button dojoType="dijit.form.Button" type="submit">Yes</button>
                <button dojoType="dijit.form.Button" type="button">Cancel</button>
          </div>
      </div>
  </div>
</jsp:root>