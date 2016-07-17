//set all value to zero by default
	$(document).ready(function(){
    	// disable by default
    	$("#height").prop('disabled', true);
    	$("#width").prop('disabled', true);
	});
	
	function productTypeChanged(dropDown)
	{
		var selectedItem = dropDown.options[dropDown.selectedIndex].innerHTML;
	    if(selectedItem == "Round"){
	    	$("#height").prop('disabled', true);
	    	$("#width").prop('disabled', true);
	    	$("#perimeter").prop('disabled', false);
	    }else{
	    	$("#height").prop('disabled', false);
	    	$("#width").prop('disabled', false);
	    	$("#perimeter").prop('disabled', true);
	    }
	}