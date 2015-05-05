function saveJicgn(){
		$("#jcgcForm").submit();
}
function validateJicgnForGongnId(){
			var gongnid = $("#gongnid").val();
			var url = "basicFunction.do?method=select";
			var parameters = {gongnid:gongnid};
			
			$.get(url, parameters,function(data,textStatus)
			{
				$('#gongnMessage').html(data);
			}, "text");
		}
function validateJicgnForQuanxwz(){
			var quanxwz = $("#quanxwz").val();
			var url = "basicFunction.do?method=getPost";
			var parameters = {quanxwz:quanxwz};
			
			$.get(url, parameters,function(data,textStatus)
			{
				$('#quanxwzMessage').html(data);
			}, "text");
		}
function addJCGN(functionName,name,url){
			$('#mydialog').show();
			$("#gongnfl").val(functionName);
			$("#gongnid").val('');
			$("#quanxwz").val('');
			$('#gongnmc').val(name);
			$('#gongnurl').val(url);
			$('#gongnMessage').html('');
			$('#quanxwzMessage').html('');
			$('#mydialog').dialog({ 
				collapsible: true, 
				minimizable: true, 
				maximizable: true, 
				onClose:function(){
			    },
				buttons: [{ 
					text: '±£´æ', 
					iconCls: 'icon-ok', 
					handler: function() { 
							saveJicgn();
					} 
					}, { 
					text: 'È¡Ïû', 
					handler: function() { 
						$('#mydialog').dialog('close'); 
					} 
				}] 
				}); 
		} 