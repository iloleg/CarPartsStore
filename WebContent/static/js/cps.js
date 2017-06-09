var CPS = {
	$Table : function (data, tableHeader, visibleFields, attributes, attributeFields, pageIndexMultiplier, update) {
		var get = function (obj, keys) {
		    var nodes = keys.split('.');
		    for (var i = 0; i < nodes.length; i++) {
		        obj = obj[nodes[i]];
		    }
		    return obj;
		};
		
		if (pageIndexMultiplier === undefined) {
			pageIndexMultiplier = 0;
		}
	
		var table = $("<table></table>").addClass("table table-bordered table-hover");
		var thead = $("<thead></thead>");
		
		var tr = $("<tr></tr>").append("<th>#</th>");
		for (var i = 0; i < tableHeader.length; ++i) {
			tr.append($("<th></th>").append(tableHeader[i]));
		}
		table.append(thead.append(tr));
		
		var tbody = $("<tbody></tbody>");
		for (var i = 0; i < data.length; ++i) {
			var object = data[i];
			var tr = $("<tr></tr>").addClass('record');
			for (var j = 0; j < attributes.length; ++j) {
				tr.attr(attributes[j], get(object, attributeFields[j]));
			}
			
			tr.append($("<td></td>").append(i  + pageIndexMultiplier + 1));
			for (var k = 0; k < visibleFields.length; ++k) {
				var span = $("<span></span>")
						  .addClass(visibleFields[k])
						  .append(get(object, visibleFields[k]))
				tr.append($("<td></td>").append(span));
			}
			tbody.append(tr);
		}
		table.append(tbody);
		
		var record = table.find(".record" )
		
		record.click(function () {
			$(".record").attr('id', '');
			$(this).attr('id', 'chosen-record');
		});
		
		var source = [];
		$.post('get_brands', function (data) {
			var result = JSON.parse(data);
			for (var i = 0; i < result.length; ++i) {
				source.push({
					'value': result[i].id,
					'text': result[i].name
				})
			}
		});
		
		if (typeof update == "function") {
			//record.dblclick(function () {
				//var self = record;
				
				$(record).find('span:not([class^=brand])').editable({
				    type: 'text',
				    title: 'Enter new value',
				    success: function (resp, value) {
				    	var self = $(this).closest('tr');
				    	$(this).html(value);
				    	update($(self))
				    }
				});
				
				$(record).find('span[class^=brand]').editable({
					type: 'select',
					source: function() {
						 return source;
					},
					display: function (value, source) {
						if (source !== undefined) {
							var self = $(this).closest('tr')
							$(this).html(source[value - 1].text);
							$(self).attr('brand-id', value);
							update($(self));
						}
					}
				});
			//});
		}
		
		table.find("thead").click(function () {
			$(".record").attr('id', '');
		});
		
		return table;
	}
};