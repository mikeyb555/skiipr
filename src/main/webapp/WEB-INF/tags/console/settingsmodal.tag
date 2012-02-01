<jsp:root 
    xmlns:c="http://java.sun.com/jsp/jstl/core" 
    xmlns:fn="http://java.sun.com/jsp/jstl/functions" 
    xmlns:jsp="http://java.sun.com/JSP/Page" 
    xmlns:spring="http://www.springframework.org/tags" version="2.0">
  <jsp:output omit-xml-declaration="yes" />
  <div dojoType="dijit.Dialog" id="console_settings_panel" title="Console settings" style="width: 600px;">
      <div class="console_setting_row">
          <b>Play sound on new order</b> <div dojoType="dojox.mobile.Switch" value="off" style="float: right;"><!-- required for FF3 and Opera --></div>
          <div class="clear"><!-- required for FF3 and Opera --></div>
      </div>
      <div class="console_setting_row">
          <b>Lorem Ipsum</b> <div dojoType="dojox.mobile.Switch" value="off" style="float: right;"><!-- required for FF3 and Opera --></div>
          <div class="clear"><!-- required for FF3 and Opera --></div>
      </div>
  </div>
</jsp:root>