var CPS = {
	$Table : function (data, tableHeader, visibleFields, attributes, attributeFields, pageIndexMultiplier, update) {
		if (pageIndexMultiplier === undefined) {
			pageIndexMultiplier = 1;
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
				tr.attr(attributes[j], object[attributeFields[j]]);
			}
			
			tr.append($("<td></td>").append(i  + pageIndexMultiplier + 1));
			for (var k = 0; k < visibleFields.length; ++k) {
				tr.append($("<td></td>").addClass(visibleFields[k]).append(object[visibleFields[k]]));
			}
			tbody.append(tr);
		}
		table.append(tbody);
		
		var record = table.find(".record" )
		
		record.click(function () {
			$(".record").attr('id', '');
			$(this).attr('id', 'chosen-record');
		});
		
		if (update !== undefined) {
			record.dblclick(function () {
				$(this).attr("contenteditable", "true");
				$(this).attr('style', 'color: red');
				$(this).focus();
			});
			
			record.focusout(function () {
				$(this).removeAttr("contenteditable");
				$(this).removeAttr("style");
				update($(this));
			});
		}
		
		table.find("thead").click(function () {
			$(".record").attr('id', '');
		});
		
		return table;
	}
};