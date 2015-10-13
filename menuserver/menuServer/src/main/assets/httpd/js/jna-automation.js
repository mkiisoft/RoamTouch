var jNA = {
	
	/**
	 * Java applet loading function
	 */
    init: function() {
    	var appletStr_jna = '<applet name="jna_automation" id="jna_automation" code="coby.jna_automation.class" archive="../automation/sjna_automation.jar" width="0" height="0">' +
    						'<param name="separate_jvm" value="true">' +
        					'<param name="mayscript" value="yes">' +
        					'<param name="scriptable" value="true">' +
        				'</applet>';
    	document.getElementById('jna_automation').innerHTML = appletStr_jna;
    	jNA.bridge = document.jna_automation;
    },
       
    setWindowsFocus: function(name) {
		return jNA.bridge.setWindowsFocus(name);    	
	},	
	
	typeCharacter: function(character) {
    	return this._executeFunction('typeCharacter', character);  
	},
		
	pressEnterKey: function() {
    	return this._executeFunction('pressEnterKey');  
	},
	
	_executeFunction: function(func, args) {
		try {		
			return jNA.bridge.func(args);			
		}
		catch (e) {
			this._convertException(e);
		}
	},
	
	_convertException: function(e) {
	
		var message = '';
		
		if(e instanceof Error){
			message = e.message;
		}
		else if(typeof e == 'string'){
			message = e;
		}
		/*if(message.indexOf('jssc.SerialPortException: ') == 0 || message.indexOf('jssc.SerialPortTimeoutException: ') == 0){
			message = message.replace('Port name - ', '').replace(' Method name - ', '');
			if(message.indexOf('jssc.SerialPortException: ') == 0){
				var exceptionArray = message.replace('jssc.SerialPortException: ', '').replace(' Exception type - ', '').split(';');
				throw new jSSC.SerialPortException(exceptionArray[0], exceptionArray[1], exceptionArray[2].substr(0, exceptionArray[2].length - 1));
			}
			else if(message.indexOf('jssc.SerialPortTimeoutException: ') == 0){
				var exceptionArray = message.replace('jssc.SerialPortTimeoutException: ', '').replace(' Serial port operation timeout (', '').replace(' ms).', '').split(';');
				throw new jSSC.SerialPortTimeoutException(exceptionArray[0], exceptionArray[1], exceptionArray[2]);
			}	
		}
		else if(message.indexOf('Error calling method on NPObject') != -1){//WebKit fix for correct Exception catch
			this._convertException(this.getLastError());
		}
		else {
			throw new jSSC.SerialPortException(this.getPortName(), 'unknown', message);
		}*/
	},
	
}
