        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Manage</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Users
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table id="manage-data" class="table table-bordered table-hover">
                                    <thead>
                                        <tr>
                                            <th>#</th>
                                            <th>Username</th>
                                            <th>Role</th>
                                        </tr>
                                    </thead>
                                </table>
                            </div>
                            <button id="btn-delete" class="btn btn-danger col-lg-offset-10 col-lg-2">Delete Chosen Record</button>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12s -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->
        <script>
        	$(document).ready(function () {
        		var $chosen;
 
        		$.post('getUsers', function (responce) {
        			console.log(responce);
        			var data = JSON.parse(responce);
        			var table = "<tbody>"
        			
        			for (var i = 0; i < data.length; ++i) {
        				var object = data[i];
        				table += "<tr class=\"record\" object-id=\"" + object.id + "\"" + ">" + 
        						 "<td>" + (i + 1) + "</td>" + 
        						 "<td>" + object.username + "</td>" + 
        						 "<td>" + object.role + "</td>" + 
        						 "</tr>";
        			}
        			
        			table += "</tbody>";
        			$("#manage-data").append(table);
        			
            		$(".record" ).click(function () {
            			$(".record").css("background-color", "");
            			$(this).css("background-color", "lightblue");
            			$chosen = $(this);
            		});
            		
            		$("thead").click(function () {
            			$(".record").css("background-color", "");
            		});
            		
            		$("#btn-delete").click(function () {
            			var id = $chosen.attr("object-id");
            			console.log(id);
            			if (prompt("Are you sure?\n(Type \"yes\")").toLowerCase() == "yes") {
            				$.post('delete_user', {"id" : id }, function (responce) {
            					console.log(responce);
            					if (JSON.parse(responce).status == "success") {
            						alert("Successfully deleted!");
            						location.reload();
            					} else {
            						alert("Cannot delete this record.");
            					};
            				});
            			}
            		});
        		})
        	});
        </script>
